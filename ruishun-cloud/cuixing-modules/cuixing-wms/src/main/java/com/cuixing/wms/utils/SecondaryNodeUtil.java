package com.cuixing.wms.utils;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import com.cuixing.common.core.utils.SpringUtils;
import com.cuixing.wms.secondarynode.vo.SNBaseInfo;
import org.apache.commons.lang3.StringUtils;

public class SecondaryNodeUtil {
    public static final String TEMPLATE_PRODUCT = "出入库产品信息";
    public static final String TEMPLATE_BOX = "出入库箱信息";
    public static final String TEMPLATE_ITEMRECPT = "入库单信息";
    public static final String TEMPLATE_ISSUE = "出库单信息";
    private static final int expireTime = 28;
    private static RedisCache redisCache;
    public static Map<String, String> tokenMap = new HashMap();
    private static final String username= "ruishun";
    private static final String password="9be382686bfd4f97444fb411d878072da6a72dfb6106ea3bacd7731dbd048d0fa0c166d0bc3ab4f0f7a5794b5fc9b6c3d3f4778182a3a482413210d1c5ec8708356f37b9c6bac8d7cd8ded21afbae49a14dd6ade035d9e3c48283b9681d01ff8e43f46583c47551ef37bab7e1254f9e114e342b0c650d5f17b61800dbdb8c60fcf40244633bd53897d6795329da119171c7fc31affaf54ed6476e37449eced264d6aeb9b221d287842892c447070a4dc68286c5fc5ce46ad32f731877e3dd6e8106de1ece832c59ee5dc508e97d82d861ba9ef2d8d91157a15324f785e9433236ff985be3062f6d4ba3802c26e75395b192210a9d158ac0ea35a445918471589";
    private static final String snServerUrl= "http://182.109.52.2:32000/api";
    private static final String companyId= "40";
    private static final String publicKey= "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4ARQRQC/KEkc9GTNaolkn6kJocvR9VVHRi7Z1hgJMlnQWpvsFkXrry4jX6gYAn3OlPLAhJmsSfjRYYFsvebTNWvb+1d+tKJvHSGfcUouz9EnsIGVSpBf1BgDG3PoHNrn2nlvA8RFW6MoiprmHg6w0q4A1GfDSUhr+vXJ0a7l5odNQx961ILciaEIPRy94GEooOkQH8jVyJQ/mCXLGYDib1zFHj62tYfITNw4ulNlWD/5iGGCnl3ItxsOPSmim3T5n2YlAdXUGFbtt5G968jTgOqCQfDqipL+CXeCzN+WkoYBLHYWDzHcqxanGx19pa0cvO9KFvz1WtyrG8/AMeGdRwIDAQAB";
    private static final String snidPrefix= "88.389.00054";

    public static void analysis(String handleCode) {
        List codeList = new ArrayList();
        codeList.add(handleCode);
        analysis((List)codeList);
    }

    public static void analysis(List<String> codeList) {
        if (redisCache == null) {
            redisCache = (RedisCache) SpringUtils.getBean(RedisCache.class);
        }
        if (!StringUtils.isBlank(username) && !StringUtils.isBlank(password) && !StringUtils.isBlank(publicKey) && !StringUtils.isBlank(snidPrefix)) {
            StringBuilder handleStr = new StringBuilder();

            String code;
            for(Iterator var6 = codeList.iterator(); var6.hasNext(); handleStr.append(snidPrefix + "/" + code)) {
                code = (String)var6.next();
                if (handleStr.length() > 0) {
                    handleStr.append(",");
                }
            }

            final String mainJson = "{\"handle\":\"" + handleStr + "\"}";
            System.out.println("解析标识-mainMap=" + mainJson);
            (new Thread(new Runnable() {
                public void run() {
                    String result = "";

                    try {
                        String token = SecondaryNodeUtil.getToken(Long.valueOf(companyId), username, password);
                        result = SecondaryNodeUtil.sendPost(snServerUrl + "/jxcbdp/handle/analysis", mainJson, token);
                        System.out.println("result=" + result);
                    } catch (Exception var3) {
                        var3.printStackTrace();
                    }

                }
            })).start();
        }
    }

    public static void upload(final String method, final SNBaseInfo entity, String template, final CallBack callBack) {
        if (redisCache == null) {
            redisCache = (RedisCache)SpringUtils.getBean(RedisCache.class);
        }
        if (!StringUtils.isBlank(username) && !StringUtils.isBlank(password) && !StringUtils.isBlank(publicKey) && !StringUtils.isBlank(snidPrefix)) {
            String handle = snidPrefix + "/" + entity.getHandle();
            Map<String, Object> entityMap = BeanUtil.beanToMap(entity);
            StringBuilder valueListStr = new StringBuilder();
            Iterator var11 = entityMap.keySet().iterator();

            String key;
            while(var11.hasNext()) {
                key = (String)var11.next();
                Object value = entityMap.get(key);
                if (value != null && !value.equals("")) {
                    if (valueListStr.length() > 0) {
                        valueListStr.append(",");
                    }

                    valueListStr.append("{");
                    valueListStr.append("\"data\"").append(":").append("{");
                    valueListStr.append("\"type\"").append(":").append("\"").append(key).append("\"");
                    valueListStr.append(",");
                    valueListStr.append("\"value\"").append(":").append("\"").append(value).append("\"");
                    valueListStr.append("}");
                    valueListStr.append("}");
                }
            }

            String infoString = "[" + valueListStr + "]";
            key = "";

            try {
                key = RSAUtil.encrypt(handle.getBytes(), publicKey);
            } catch (Exception var15) {
                var15.printStackTrace();
            }

            final String mainJson = "{\"template\":\"" + template + "\",\"handle\":\"" + handle + "\",\"sign\":\"" + key + "\",\"value\":" + infoString + "}";
            System.out.println("mainMap=" + mainJson);

            (new Thread(new Runnable() {
                public void run() {
                    String result = "";

                    try {
                        String token = SecondaryNodeUtil.getToken(Long.valueOf(companyId), username, password);
                        result = SecondaryNodeUtil.sendPost(snServerUrl + "/jxcbdp/handle/" + method, mainJson, token);
                        SecondaryNodeUtil.analysis(entity.getHandle());
                    } catch (Exception var3) {
                        var3.printStackTrace();
                    }

                    if (callBack != null) {
                        callBack.execute(snidPrefix, result);
                    }

                }
            })).start();
        }
    }

    public static void add(SNBaseInfo entity, String template, CallBack callBack) {
        upload("addData", entity, template, callBack);
    }

    public static void update(SNBaseInfo entity, String template, CallBack callBack) {
        upload("updateData", entity, template, callBack);
    }

    public static void delete(List handleList, CallBack callBack) {
    }

    public static String sendPost(String url, String requestBody, String token) throws Exception {
        System.out.println("url=" + url);
        System.out.println("requestBody=" + requestBody);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection)obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        if (null != token) {
            con.setRequestProperty("Authorization", "Bearer " + token);
        }

        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(requestBody.getBytes());
        os.flush();
        os.close();
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuffer response = new StringBuffer();

        String inputLine;
        while((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();
        System.out.println("Response: " + response.toString());
        return response.toString();
    }

    public static String getToken(Long companyId, String userName, String password) throws Exception {
        String snToken = (String)redisCache.getCacheObject("sn_" + companyId + "_token");
        if (StringUtils.isNotBlank(snToken)) {
            return snToken;
        } else {
            String url = snServerUrl + "/jxcbdp/identity/token";
            JSONObject getTokenParam = new JSONObject();
            getTokenParam.put("username", userName);
            getTokenParam.put("password", password);
            String tokenResult = sendPost(url, getTokenParam.toJSONString(), (String)null);
            JSONObject jsonObject = JSON.parseObject(tokenResult);
            JSONObject data = jsonObject.getJSONObject("data");
            String token = data.getString("token");
            redisCache.setCacheObject("sn_" + companyId + "_token", token, 28, TimeUnit.MINUTES);
            return token;
        }
    }

    public static void main(String[] args) throws Exception {
        String token = getToken(40L, "ruishun", "9be382686bfd4f97444fb411d878072da6a72dfb6106ea3bacd7731dbd048d0fa0c166d0bc3ab4f0f7a5794b5fc9b6c3d3f4778182a3a482413210d1c5ec8708356f37b9c6bac8d7cd8ded21afbae49a14dd6ade035d9e3c48283b9681d01ff8e43f46583c47551ef37bab7e1254f9e114e342b0c650d5f17b61800dbdb8c60fcf40244633bd53897d6795329da119171c7fc31affaf54ed6476e37449eced264d6aeb9b221d287842892c447070a4dc68286c5fc5ce46ad32f731877e3dd6e8106de1ece832c59ee5dc508e97d82d861ba9ef2d8d91157a15324f785e9433236ff985be3062f6d4ba3802c26e75395b192210a9d158ac0ea35a445918471589");
        System.out.println(token);
    }
}
