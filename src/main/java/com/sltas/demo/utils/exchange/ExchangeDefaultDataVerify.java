package com.sltas.demo.utils.exchange;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.sltas.demo.utils.Base64Util;
import com.sltas.demo.utils.MD5Tools;


public class ExchangeDefaultDataVerify{
    private static Logger log = LoggerFactory.getLogger(ExchangeDefaultDataVerify.class);

    public String encrypt(String param) {
        return Base64Util.encode(param);
    }


    public String decrypt(String param) throws Exception{
        return Base64Util.decode(param);
    }

    /**
     * @param headerMap  header
     * @param bodyDataMap  body
     * @param secret
     * @return sign 签名
     */
    public static String generateSign(Map<String,String> headerMap, Map<String,String> bodyDataMap, String secret) {
        String result = "";
        Map<String,String> allDataMap = getSignParam(headerMap,bodyDataMap);
        //1.加密参数进行自然排序
        TreeMap<String, Object> reqParams =sortMap(allDataMap);
        //2、排序后的map转换成json串
        String paramsJson = new Gson().toJson(reqParams);
        //3.对第3步得的json串进行md5加密
        result = MD5Tools.getStrMD5(paramsJson);
        //4. 拼接secret与第4步得到的加密串
        result = secret+result;
        //5.对第5步得到的串进行md5加密
        result = MD5Tools.getStrMD5(result);
        return result;
    }


    public static boolean checkSign(Map<String,String> headerMap, Map<String,String> bodyDataMap,String sign,String secret) {
        Map<String, String> allData = new HashMap<>();

        allData  = getSignParam(headerMap,bodyDataMap);

        String dataSign = "";
        try {
            dataSign = generateSign(headerMap,bodyDataMap,secret);
        } catch (Exception e) {
            log.error("签名生成异常：{}",e.getMessage());
        }
        return dataSign.equals(sign);
    }


    /**
     * map合并
     * @param headerMap
     * @param bodyMap
     * @return
     */
    private static Map<String, String> getSignParam(Map<String,String> headerMap,Map<String,String> bodyMap){
        // 合并
        Map<String, String> result = new HashMap<String, String>();
        if(headerMap!=null){
            result.putAll(headerMap);
        }
        result.remove("sign");//sign不参与签名
        if(bodyMap!=null){
            result.putAll(bodyMap);
        }
        return result;
    }


    private static TreeMap<String, Object> sortMap(Map map){
        TreeMap<String, Object> reqParams = new TreeMap<String, Object>(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                //如果有空值，直接返回0
                if (o1 == null || o2 == null){
                    return 0;
                }
                return String.valueOf(o1).compareTo(String.valueOf(o2));
            }
        });
        reqParams.putAll(map);
        return reqParams;
    }
}
