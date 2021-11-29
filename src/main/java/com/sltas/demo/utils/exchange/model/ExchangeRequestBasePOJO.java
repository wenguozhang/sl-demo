package com.sltas.demo.utils.exchange.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.sltas.demo.utils.Base64Util;
import com.sltas.demo.utils.HttpSender;
import com.sltas.demo.utils.exchange.ExchangeDefaultDataVerify;
import com.sltas.demo.utils.exchange.RequestUtil;
/**
 *@Author cuitong
 *@Date: 2020/5/28 15:03
 *@Email: cuitong_sl@163.com
 *@Description: 三方请求示例
 */
public class ExchangeRequestBasePOJO {

    /**
     * @param bodyMap 请求体
     * @return Map<String,String> header请求体
     */
    public static <T> String signGenerate(Map<String,String> headerMap, Map<String,String> bodyMap,String secret){
        // 获取 sign
        String sign = ExchangeDefaultDataVerify.generateSign(headerMap, bodyMap, secret);
        // 返回签名
        return sign;
    }

    /**
     * 封装header
     */
    public static Map<String,String> getHeader(String unionId, String mchId){
        // 日期格式化
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(new Date());
        // 封装header体
        HeaderBO header = new HeaderBO();
        header.setUnionId(unionId);
        header.setMchId(mchId);
        
        String uuid = UUID.randomUUID().toString().replace("-", "");
        header.setTradeNo(uuid);
        header.setTradeTime(date);
        // 获取 header map
        Map<String, String> headerMap = RequestUtil.getHeaderMap(header);
        return headerMap;
    }

    /**
     * @return Map<String,String> 请求body体
     */
    public static <T> Map<String,String> getBodyMap(T bodyBo){
        // json 化 body
        String bodyJson = getJson(bodyBo);
        Map<String, String> bodyMap = new HashMap<>();
        // base64 加密 body
        bodyMap.put("data", Base64Util.encode(bodyJson));
        // 返回body map
        return bodyMap;
    }
    /**
     * @param t
     * @return  String body json
     */
    public static <T> String getJson(T t){
        // json 转化
        Gson gson = new Gson();
        return gson.toJson(t);
    }

    /**
     * 返回体转换
     * @param result 响应map
     */
    public static String getResponeBodyBo(Map<String,String> result){
//        ResponeBodyBo bodyBo = new ResponeBodyBo();
//        // 获取响应码  1 请求成功 返回 body  2 请求失败 返回  null
//        String status = result.get("status");
//        switch (status){
//            case "1":
//                bodyBo.setData(Base64Util.encode(result.get("body")));
//                break;
//            default: bodyBo.setData(null);
//        }
//        bodyBo.setResultCode(status);
//        bodyBo.setResultMsg(result.get("resultMsg"));

        return result.get("body");
    }

    /**
     * @param url 请求地址
     * @param headerMap headerMap
     * @return  responeBodyBo 响应体
     */
    public static String SendUrlReq(String url,Map<String, String> headerMap,Map<String, String> bodyMap){
        //获取 body 体
        String bodyJson = getJson(bodyMap);
        //发送请求
        System.out.println("请求地址=" + url);
        System.out.println("请求header=" + getJson(headerMap));
        System.out.println("请求body=" + getJson(bodyMap));
        Map<String,String> result = HttpSender.sendPost(url,headerMap,bodyJson);
        return getResponeBodyBo(result);
    }
}
