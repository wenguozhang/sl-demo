package com.sltas.demo.utils.exchange;

import java.util.HashMap;
import java.util.Map;

import com.sltas.demo.utils.exchange.model.HeaderBO;


/**
 *@Author cuitong
 *@Date: 2020/5/28 14:29
 *@Email: cuitong_sl@163.com
 *@Description: 实体类型转换map
 */
public class RequestUtil {

    /**
     *接口版本号
     */
    public static final String VERSION = "version";
    /**
     *商户编号
     */
    public static final String MCH_ID = "mch_id";
    /**
     *三方平台号
     */
    public static final String UNION_ID = "union_id";
    /**
     *流水号
     */
    public static final String TRADE_NO = "trade_no";
    /**
     *请求时间
     */
    public static final String TRADE_TIME = "trade_time";
    /**
     *签名
     */
    public static final String SIGN = "sign";

    /**
     * header 实体类转换 map
     * @param headerDemo header实体对象
     */
    public static Map<String,String> getHeaderMap(HeaderBO headerDemo){
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(VERSION,headerDemo.getVersion());
        headerMap.put(MCH_ID,headerDemo.getMchId());
        headerMap.put(UNION_ID,headerDemo.getUnionId());
        headerMap.put(TRADE_NO,headerDemo.getTradeNo());
        headerMap.put(TRADE_TIME,headerDemo.getTradeTime());
        headerMap.put(SIGN,headerDemo.getSign());
        return headerMap;

    }
}
