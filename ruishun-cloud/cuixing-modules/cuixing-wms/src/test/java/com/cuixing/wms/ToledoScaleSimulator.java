package com.cuixing.wms;

import java.util.Random;

public class ToledoScaleSimulator {
    public static void main(String[] args) {
        while (true) {
            byte[] data = generateToledoScaleData();
            System.out.println("模拟托利多称数据: " + bytesToHex(data));
            System.out.println("解析后的重量: " + parseWeight(data));

            try {
                Thread.sleep(1000); // 每秒生成一次数据
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] generateToledoScaleData() {
        byte[] data = new byte[17];
        Random random = new Random();

        data[0] = 0x02; // STX

        // 小数点位置 (最后三位)
        int decimalPosition = random.nextInt(3) + 1; // 1, 2, or 3 decimal places
        data[1] = (byte) (0x2C | decimalPosition);

        // 状态字节
        boolean isNegative = random.nextBoolean();
        boolean isStable = random.nextBoolean();
        boolean isKg = true; // 假设总是使用KG
        data[2] = (byte) (0x30 | (isKg ? 0x08 : 0) | (isStable ? 0 : 0x04) | (isNegative ? 0x02 : 0));

        // 重量数据
        int weightInt = random.nextInt(100000); // 0-99999
        String weightStr = String.format("%06d", weightInt);
        for (int i = 0; i < 6; i++) {
            data[i + 3] = (byte) weightStr.charAt(i);
        }

        // 填充空格
        for (int i = 9; i < 15; i++) {
            data[i] = 0x20;
        }

        // 结束字符
        data[15] = 0x30;
        data[16] = 0x0D; // CR

        return data;
    }

    public static String parseWeight(byte[] data) {
        StringBuilder weightBuilder = new StringBuilder();
        for (int i = 3; i < 9; i++) {
            weightBuilder.append((char) data[i]);
        }
        int weightInt = Integer.parseInt(weightBuilder.toString());

        int decimalPosition = data[1] & 0x07;
        double weight = weightInt / Math.pow(10, decimalPosition);

        boolean isNegative = (data[2] & 0x02) != 0;
        boolean isKg = (data[2] & 0x08) != 0;

        return String.format("%s%."+decimalPosition+"f%s", isNegative ? "-" : "", weight, isKg ? "KG" : "lb");
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 3];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 3] = HEX_ARRAY[v >>> 4];
            hexChars[j * 3 + 1] = HEX_ARRAY[v & 0x0F];
            hexChars[j * 3 + 2] = ' ';
        }
        return new String(hexChars);
    }
}
