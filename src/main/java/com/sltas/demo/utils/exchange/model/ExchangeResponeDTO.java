package com.sltas.demo.utils.exchange.model;
/**
 *@Author cuitong
 *@Date: 2020/5/28 15:21
 *@Email: cuitong_sl@163.com
 *@Description: 响应体
 */
public class ExchangeResponeDTO {
    /**
     *  @Description: 业务数据实体
     *  @Explain: 业务数据json序列化后并进行base64加密
     */
    private String data;
    /**
     *  @Description: 处理结果编码
     *  @Explain: 详见接口文档第3章错误码说明
     */
    private String resultCode;
    /**
     *  @Description: 处理结果描述
     *  @Explain: 详见接口文档第3章错误码说明
     */
    private String resultMsg;

    public ExchangeResponeDTO() {
    	
    }


    public ExchangeResponeDTO(String data, String resultCode, String resultMsg) {
        this.data = data;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

	@Override
	public String toString() {
		return "ExchangeResponeDTO [data=" + data + ", resultCode=" + resultCode + ", resultMsg=" + resultMsg + "]";
	}

}
