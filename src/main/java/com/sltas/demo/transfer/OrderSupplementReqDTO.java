package com.sltas.demo.transfer;

import java.util.Map;

import com.sltas.demo.utils.Base64Util;
import com.sltas.demo.utils.GsonBUILDERUtil;
import com.sltas.demo.utils.exchange.RequestUtil;
import com.sltas.demo.utils.exchange.model.ExchangeRequestBasePOJO;
import com.sltas.demo.utils.exchange.model.ExchangeResponeDTO;


public class OrderSupplementReqDTO extends ExchangeRequestBasePOJO{
	public static void main(String[] args) {
		String url = "https://esb.mer.shang-lian.com/equipmentOrder/orderSupplement.do";
		
		/*
         * 准备技术参数
         */
	    //平台号。商联分配，一个第三方平台一个
	    String unionId = "200001";
	    //平台密钥。商联分配，对应平台号
	    String secret = "99nVc7e8Iv";
	    
	    //商户id，商联分配，每个商户不同
        String mchId = "111261";

        /*
         * 准备业务数据
         */
        OrderSupplementReqDTO orderSupplementReqDTO = new OrderSupplementReqDTO();
        orderSupplementReqDTO.setTxType("01");
        orderSupplementReqDTO.setUserCode("2017020832");
        orderSupplementReqDTO.setDeviceInfo("159773545633519");
        orderSupplementReqDTO.setThirdUserId("1234");
        orderSupplementReqDTO.setItemId("804");
        orderSupplementReqDTO.setMerchantorderId("2021092616095846224462");
        orderSupplementReqDTO.setOrderFee("5.00");
        orderSupplementReqDTO.setPayFee("5.00");
        orderSupplementReqDTO.setMergeFee("0");
        orderSupplementReqDTO.setMerchantFee("0");
        orderSupplementReqDTO.setTransFee("0");
        orderSupplementReqDTO.setOtherFee("0");
        orderSupplementReqDTO.setMyFee("0");
        orderSupplementReqDTO.setBankfee("0");
        orderSupplementReqDTO.setPayChannel("150017");
        orderSupplementReqDTO.setPaydate("2021-11-07");
        orderSupplementReqDTO.setAccountDate("2021-11-07");
        orderSupplementReqDTO.setPaytime("2021-11-07 17:42:38");
        orderSupplementReqDTO.setReserved(null);
        
        /*
         * 签名及封装请求技术数据
         */
        //封装header和body
        Map<String, String> headerMap = getHeader(unionId, mchId);
        Map<String, String> bodyMap = getBodyMap(orderSupplementReqDTO);
        //获取签名，并添加到header里
        String sign = signGenerate(headerMap, bodyMap, secret);
        headerMap.put(RequestUtil.SIGN, sign);
        
        /*
         * 发送请求
         */
        String result = SendUrlReq(url, headerMap,bodyMap);
        
        /*
         * 解析返回结果
         */
        //转换返回json，获取错误码等信息
        ExchangeResponeDTO exchangeResponeDTO = GsonBUILDERUtil.GSON_BUILDER_COMMON.fromJson(result, ExchangeResponeDTO.class);
        System.out.println("exchangeResponeDTO=" + exchangeResponeDTO);
        String responseDataJsonStr = exchangeResponeDTO.getData();
        if(responseDataJsonStr != null) {
        	//解析返回data数据
        	String decodeResponseDataJsonStr = Base64Util.decode(responseDataJsonStr);
        	System.out.println("decodeResponseDataJson=" + decodeResponseDataJsonStr);
        }
        
	}
	
	private String txType;
	private String userCode;
	private String deviceInfo;
	private String thirdUserId;
	private String itemId;
	private String merchantorderId;
	private String orderFee;
	private String payFee;
	private String mergeFee;
	private String merchantFee;
	private String transFee;
	private String otherFee;
	private String myFee;
	private String bankfee;
	private String payChannel;
	private String paydate;
	private String accountDate;
	private String paytime;
	private String reserved;
	
	public String getTxType() {
		return txType;
	}
	public void setTxType(String txType) {
		this.txType = txType;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getDeviceInfo() {
		return deviceInfo;
	}
	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	public String getThirdUserId() {
		return thirdUserId;
	}
	public void setThirdUserId(String thirdUserId) {
		this.thirdUserId = thirdUserId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getMerchantorderId() {
		return merchantorderId;
	}
	public void setMerchantorderId(String merchantorderId) {
		this.merchantorderId = merchantorderId;
	}
	public String getOrderFee() {
		return orderFee;
	}
	public void setOrderFee(String orderFee) {
		this.orderFee = orderFee;
	}
	public String getPayFee() {
		return payFee;
	}
	public void setPayFee(String payFee) {
		this.payFee = payFee;
	}
	public String getMergeFee() {
		return mergeFee;
	}
	public void setMergeFee(String mergeFee) {
		this.mergeFee = mergeFee;
	}
	public String getMerchantFee() {
		return merchantFee;
	}
	public void setMerchantFee(String merchantFee) {
		this.merchantFee = merchantFee;
	}
	public String getTransFee() {
		return transFee;
	}
	public void setTransFee(String transFee) {
		this.transFee = transFee;
	}
	public String getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(String otherFee) {
		this.otherFee = otherFee;
	}
	public String getMyFee() {
		return myFee;
	}
	public void setMyFee(String myFee) {
		this.myFee = myFee;
	}
	public String getBankfee() {
		return bankfee;
	}
	public void setBankfee(String bankfee) {
		this.bankfee = bankfee;
	}
	public String getPayChannel() {
		return payChannel;
	}
	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}
	public String getPaydate() {
		return paydate;
	}
	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}
	public String getAccountDate() {
		return accountDate;
	}
	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}
	public String getPaytime() {
		return paytime;
	}
	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	@Override
	public String toString() {
		return "OrderSupplementReqDTO [txType=" + txType + ", userCode=" + userCode + ", deviceInfo=" + deviceInfo
				+ ", thirdUserId=" + thirdUserId + ", itemId=" + itemId + ", merchantorderId=" + merchantorderId
				+ ", orderFee=" + orderFee + ", payFee=" + payFee + ", mergeFee=" + mergeFee + ", merchantFee="
				+ merchantFee + ", transFee=" + transFee + ", otherFee=" + otherFee + ", myFee=" + myFee + ", bankfee="
				+ bankfee + ", payChannel=" + payChannel + ", paydate=" + paydate + ", accountDate=" + accountDate
				+ ", paytime=" + paytime + ", reserved=" + reserved + "]";
	}
	
	
}
