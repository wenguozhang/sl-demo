package com.sltas.demo.bill;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class BillDemo {
	private String priCer="D:\\cert\\2.pfx";
	private String pubCer="D:\\cert\\1.crt";
	private String certPass = "12345678";
	private String strKey = "12345678";
	private String signKey = "";
	private String targetDomain = "http://192.168.0.1:8080";
	
	public static void main(String[] args) {
		BillDemo ti = new BillDemo();
		
		ti.testSaveOrUpdateBill(); // 账单同步
		ti.testOnOffBill();
	}

	/**
	 * 账单同步
	 * @创建人 李志峰
	 * @创建时间 2021年5月31日下午2:31:06
	 * @说明 
	 */
	public void testSaveOrUpdateBill(){
		String version = "V1.0";
		String merId = "111261";
		String interId = "saveOrUpdateBill";
		String platNo = "standard";
		
		List<Map<String,Object>> listUser = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> listBill = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> reqdata = new HashMap<String, Object>();
     	Map<String,Object> reqHead = new HashMap<String,Object>();
     	Map<String,Object> users = new HashMap<String,Object>();
     	Map<String,Object> bills = new HashMap<String,Object>();
     	reqHead.put("version", version);//版本
     	reqHead.put("charset", "UTF-8");//字符集
     	reqHead.put("tradeNo", "Bill"+(new Date()).getTime());//交易流水号
     	reqHead.put("merId", merId);//商户编码
     	reqHead.put("intId", interId);//接口编码
     	reqHead.put("platNo", platNo);//平台编码

    	reqdata.put("tradeTime", "20211018152355");// 交易时间
    	reqdata.put("userTypeNo", "33");// 人员类型
    	reqdata.put("templetNo", "M0001");// 模板编码
    	reqdata.put("editType", "0");// 操作类型	
    	reqdata.put("ifPublish", "0");// 交易说明
    	reqdata.put("batchTransNo", "按文档中规则生成");// 批次号
    	
    	users.put("userNumber", "2199");
    	users.put("userName", "2199");
    	
    	bills.put("otherBillTransNo", "BT2021060300003");//UtilDate.getLongDate()
    	bills.put("expenseItemNumber", "BDXXF");
    	bills.put("periodNumber", "20201101");
    	bills.put("billAmount", "200.00");
    	bills.put("billInstructions", "测试");
    	bills.put("overdueTime", "");
    	listBill.add(bills);
    	
    	users.put("bills", listBill);
    	listUser.add(users);
    	reqdata.put("users", listUser);
    	
    	System.out.println("请求明文：\n"+JSONObject.fromObject(reqdata).toString());
    	
    	Map<String,String> strSign = FrontCrypt.encryptWithKey(reqdata, priCer, certPass, strKey, signKey);
    	
    	reqHead.put("strDes", strSign.get("strDes"));
    	reqHead.put("signMsg", strSign.get("signMsg"));
    	
    	JSONObject sendObj = JSONObject.fromObject(reqHead);
    	String targetUrl = targetDomain+"/interface/saveBill";
    	
    	try{
    		JSONObject responseJson = HttpSender.sendAndReciveJSON(targetUrl, sendObj.toString());
    		System.out.println("返回报文：\n"+responseJson.toString());
    		String strDes = responseJson.getString("strDes");
    		String signMsg = responseJson.getString("signMsg");
    		System.out.println(strDes);
    		
    		JSONObject result = FrontCrypt.decryptWithKey(strDes, signMsg, pubCer, strKey, "");
    		System.out.println("结果明文：\n"+result.toString());
    		// TODO 解析返回报文，做后续业务处理
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
	}
	
	/**
	 * 账单启停
	 * @创建人 李志峰
	 * @创建时间 2021年5月31日下午2:30:42
	 * @说明 
	 */
	public void testOnOffBill(){
		String version = "V1.0";
		String merId = "111261";
		String interId = "onOffBill";
		String platNo = "standard";
		
		Map<String, Object> reqdata = new HashMap<String, Object>();
     	Map<String,Object> reqHead = new HashMap<String,Object>();
     	
     	reqHead.put("version", version);//版本
     	reqHead.put("charset", "UTF-8");//字符集
     	reqHead.put("tradeNo", "ofBill"+(new Date()).getTime());//交易流水号
     	reqHead.put("merId", merId);//商户编码
     	reqHead.put("intId", interId);//接口编码
     	reqHead.put("platNo", platNo);//平台编码

    	reqdata.put("tradeTime", "20211018154055");// 交易时间
    	reqdata.put("editType", "0");// 商户人员编码
    	reqdata.put("totalCount", "2");//操作类型
    	
    	List<String> listBills = new ArrayList<String>();
    	listBills.add("123456899");
    	listBills.add("968498462196");
    	reqdata.put("bills", listBills);
    	
    	System.out.println("请求明文：\n"+JSONObject.fromObject(reqdata).toString());
    	
    	Map<String,String> strSign = FrontCrypt.encryptWithKey(reqdata, priCer, certPass, strKey, signKey);
    	
    	reqHead.put("strDes", strSign.get("strDes"));
    	reqHead.put("signMsg", strSign.get("signMsg"));
    	
    	JSONObject sendObj = JSONObject.fromObject(reqHead);
    	String targetUrl = targetDomain+"/interface/onOffBill";
    	
    	try{
    		JSONObject responseJson = HttpSender.sendAndReciveJSON(targetUrl, sendObj.toString());
    		System.out.println("返回报文：\n"+responseJson.toString());
    		String strDes = responseJson.getString("strDes");
    		String signMsg = responseJson.getString("signMsg");
    		System.out.println(strDes);
    		JSONObject result = FrontCrypt.decryptWithKey(strDes, signMsg, pubCer, strKey, signKey);
    		System.out.println("结果明文：\n"+result.toString());
    		// TODO 解析返回报文，做后续业务处理
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
	}
	
	/**
	 * 账单启停
	 * @创建人 李志峰
	 * @创建时间 2021年5月31日下午2:30:42
	 * @说明 
	 */
	public void testSaveOrUpdateQuery(){
		String version = "V1.0";
		String merId = "111261";
		String interId = "saveOrUpdateQuery";
		String platNo = "standard";
		
		Map<String, Object> reqdata = new HashMap<String, Object>();
     	Map<String,Object> reqHead = new HashMap<String,Object>();
     	
     	reqHead.put("version", version);//版本
     	reqHead.put("charset", "UTF-8");//字符集
     	reqHead.put("tradeNo", "SOUQ"+(new Date()).getTime());//交易流水号
     	reqHead.put("merId", merId);//商户编码
     	reqHead.put("intId", interId);//接口编码
     	reqHead.put("platNo", platNo);//平台编码

    	reqdata.put("tradeTime", "20211018154055");// 交易时间
    	reqdata.put("batchTransNo", "1232242342523523423");// 待查询的批次号
    	
    	System.out.println("请求明文：\n"+JSONObject.fromObject(reqdata).toString());
    	
    	Map<String,String> strSign = FrontCrypt.encryptWithKey(reqdata, priCer, certPass, strKey, signKey);
    	
    	reqHead.put("strDes", strSign.get("strDes"));
    	reqHead.put("signMsg", strSign.get("signMsg"));
    	
    	JSONObject sendObj = JSONObject.fromObject(reqHead);
    	String targetUrl = targetDomain+"/interface/saveOrUpdateQuery";
    	
    	try{
    		JSONObject responseJson = HttpSender.sendAndReciveJSON(targetUrl, sendObj.toString());
    		System.out.println("返回报文：\n"+responseJson.toString());
    		String strDes = responseJson.getString("strDes");
    		String signMsg = responseJson.getString("signMsg");
    		System.out.println(strDes);
    		JSONObject result = FrontCrypt.decryptWithKey(strDes, signMsg, pubCer, strKey, signKey);
    		System.out.println("结果明文：\n"+result.toString());
    		// TODO 解析返回报文，做后续业务处理
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
	}
}
