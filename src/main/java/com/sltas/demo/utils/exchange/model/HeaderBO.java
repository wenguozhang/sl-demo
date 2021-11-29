package com.sltas.demo.utils.exchange.model;

/**
 *@Author cuitong
 *@Date: 2020/5/28 14:09
 *@Email: cuitong_sl@163.com
 *@Description: headers 头实体类
 */
public class HeaderBO {
    /**
     * @Description: 接口版本号
     * @Explain: 固定值：v2.0；注意为小写字
     * @Type: 非空
     */
    private String version = "V1.1";
    /**
     *  @Description: 商户编号
     *  @Explain: 商户号
     *  @Type: 非空
     */
    private String mchId;
    /**
     *  @Description: 三方平台号
     *  @Explain: 6位数100000起始值
     *  @Type: 非空
     */
    private String unionId;
    /**
     *  @Description: 流水号
     *  @Explain: 合作平台唯一请求流水号
     *  @Type: 非空
     */
    private String tradeNo;
    private String contentType = "application/json";
    private String charset = "utf_8";
    /**
     *  @Description: 请求时间
     *  @Explain: 合作平台发起交易的请求时间
     *  @Type: 非空
     */
    private String tradeTime;
    /**
     *  @Description: 签名
     *  @Type: 非空
     */
    private String sign;

    public HeaderBO() {

    }

    public HeaderBO(String version, String mchId, String unionId, String tradeNo, String contentType, String charset, String tradeTime, String sign) {
        this.version = version;
        this.mchId = mchId;
        this.unionId = unionId;
        this.tradeNo = tradeNo;
        this.contentType = contentType;
        this.charset = charset;
        this.tradeTime = tradeTime;
        this.sign = sign;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
