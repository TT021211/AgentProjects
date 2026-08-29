package com.cuixing.wms.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import org.springframework.data.repository.init.ResourceReader;
/* loaded from: RSAUtil.class */
public class RSAUtil {
    private static final String KEY_ALGORITHM = "RSA";
    private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    public static final String PUBLIC_KEY = "RSAPublicKey";
    public static final String PRIVATE_KEY = "RSAPrivateKey";
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int KEY_PAIR_INIT_SIZE = 2048;
    private static final int MAX_DECRYPT_BLOCK = 256;
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static Map<String, String> LOCAL_KEYMAP = new HashMap();

    public static String readLocalKey(String key) {
        if (null == LOCAL_KEYMAP.get(key)) {
            try {
                readPublicKeyStr();
                readPrivateKeyStr();
            } catch (Exception e) {
                MessageUtils.message("BIZ.ERR_00000", new Object[0]);
            }
        }
        return LOCAL_KEYMAP.get(key);
    }

    public static String readPublicKeyStr() throws Exception {
        String publicKeyStr = "";
        InputStream inputStream = ResourceReader.class.getClassLoader().getResourceAsStream("publickey.txt");
        if (inputStream != null) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    publicKeyStr = publicKeyStr + line;
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOCAL_KEYMAP.put(PUBLIC_KEY, publicKeyStr);
        return publicKeyStr;
    }

    public static String readPrivateKeyStr() throws Exception {
        String privateKeyStr = "";
        InputStream inputStream = ResourceReader.class.getClassLoader().getResourceAsStream("privatekey.txt");
        if (inputStream != null) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    privateKeyStr = privateKeyStr + line;
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOCAL_KEYMAP.put(PRIVATE_KEY, privateKeyStr);
        return privateKeyStr;
    }

    public static String getPublicKeyStr(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return encryptBASE64(key.getEncoded());
    }

    public static String getPrivateKeyStr(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return encryptBASE64(key.getEncoded());
    }

    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes = decryptBASE64(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes = decryptBASE64(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static byte[] decryptBASE64(String key) {
        return Base64.getMimeDecoder().decode(key);
    }

    public static String encryptBASE64(byte[] key) {
        return Base64.getMimeEncoder().encodeToString(key);
    }

    public static String sign(byte[] data, String privateKeyStr) throws Exception {
        PrivateKey priK = getPrivateKey(new String(hexToBytes(privateKeyStr)));
        Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
        sig.initSign(priK);
        sig.update(data);
        return bytesToHex(sig.sign());
    }

    public static boolean verify(byte[] data, String sign, String publicKeyStr) throws Exception {
        PublicKey pubK = getPublicKey(new String(hexToBytes(publicKeyStr)));
        Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
        sig.initVerify(pubK);
        sig.update(data);
        return sig.verify(hexToBytes(sign));
    }

    public static String encrypt(byte[] plainText, String publicKeyStr) throws Exception {
        byte[] doFinal;
        PublicKey publicKey = getPublicKey(publicKeyStr);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(1, publicKey);
        int inputLen = plainText.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                doFinal = cipher.doFinal(plainText, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                doFinal = cipher.doFinal(plainText, offSet, inputLen - offSet);
            }
            byte[] cache = doFinal;
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptText = out.toByteArray();
        out.close();
        return bytesToHex(encryptText);
    }

    public static String decrypt(String encryptTextHex, String privateKeyStr) throws Exception {
        byte[] doFinal;
        byte[] encryptText = hexToBytes(encryptTextHex);
        PrivateKey privateKey = getPrivateKey(privateKeyStr);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(2, privateKey);
        int inputLen = encryptText.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                doFinal = cipher.doFinal(encryptText, offSet, MAX_DECRYPT_BLOCK);
            } else {
                doFinal = cipher.doFinal(encryptText, offSet, inputLen - offSet);
            }
            byte[] cache = doFinal;
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] plainText = out.toByteArray();
        out.close();
        return new String(plainText);
    }

    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(KEY_PAIR_INIT_SIZE);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        Object publicKey = (RSAPublicKey) keyPair.getPublic();
        Object privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    public static String bytesToHex(byte[] bytes) {
        int i;
        char[] buf = new char[bytes.length * 2];
        int index = 0;
        for (byte b : bytes) {
            if (b < 0) {
                i = MAX_DECRYPT_BLOCK + b;
            } else {
                i = b;
            }
            int a = i;
            int i2 = index;
            int index2 = index + 1;
            buf[i2] = HEX_CHAR[a / 16];
            index = index2 + 1;
            buf[index2] = HEX_CHAR[a % 16];
        }
        return new String(buf);
    }

    public static byte[] hexToBytes(String str) {
        if (str == null || "".equals(str.trim())) {
            return new byte[0];
        }
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, (i * 2) + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }

    public static void main(String[] args) throws Exception {
        /*String publicKey = readLocalKey(PUBLIC_KEY);
        String privateKey = readLocalKey(PRIVATE_KEY);
        System.out.println("公钥：（填充方式：PKCS1_PADDING，输出类型：base64，字符集：utf8编码）");
        System.out.println("-----BEGIN PUBLIC KEY-----");
        System.out.println(publicKey);
        System.out.println("-----END PUBLIC KEY-----");
        System.out.println("\n");
        System.out.println("私钥：（填充方式：PKCS1_PADDING，输出类型：base64，字符集：utf8编码）");
        System.out.println("-----BEGIN RSA PRIVATE KEY-----");
        System.out.println(privateKey);
        System.out.println("-----END RSA PRIVATE KEY-----");
        System.out.println("\n");
        String encrypt = encrypt("admin123".getBytes(), publicKey);
        System.out.println("密文：（填充方式：PKCS1_PADDING，输出类型：hex，字符集：utf8编码）");
        System.out.println(encrypt);
        System.out.println("\n");
        String decrypt = decrypt(encrypt, privateKey);
        System.out.println("解密后明文: ");
        System.out.println(decrypt);*/
        //此handle为校验作用，不是实际生成的标识，主要校验标识中的前缀是否合规
        String handle = "88.389.00054/Z241114448";
        //秘钥加密的公钥
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4ARQRQC/KEkc9GTNaolkn6kJocvR9VVHRi7Z1hgJMlnQWpvsFkXrry4jX6gYAn3OlPLAhJmsSfjRYYFsvebTNWvb+1d+tKJvHSGfcUouz9EnsIGVSpBf1BgDG3PoHNrn2nlvA8RFW6MoiprmHg6w0q4A1GfDSUhr+vXJ0a7l5odNQx961ILciaEIPRy94GEooOkQH8jVyJQ/mCXLGYDib1zFHj62tYfITNw4ulNlWD/5iGGCnl3ItxsOPSmim3T5n2YlAdXUGFbtt5G968jTgOqCQfDqipL+CXeCzN+WkoYBLHYWDzHcqxanGx19pa0cvO9KFvz1WtyrG8/AMeGdRwIDAQAB\n";
        //将handle进行加密
        String signEncrypted = RSAUtil.encrypt(handle.getBytes(), publicKey);
        System.out.println(signEncrypted);
    }
}
