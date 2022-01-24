package com.sltas.demo.common;

import java.lang.reflect.Method;

import org.springframework.util.ReflectionUtils;

public class BasicBusinessException extends RuntimeException{
	
	private static final long serialVersionUID = -987772383005945208L;
	
	private static final String getReturnType = "getReturnType";
	
	private static final String getReturnCode = "getReturnCode";

	private static final String getReturnMsg = "getReturnMsg";

	
	
    /**
     * 返回类型
     */
    private String returnType;
    
    /**
     * 处理结果编码
     */
    private String returnCode;
    
    /**
     * 处理结果描述
     */
    private String returnMsg;
    
    
    /**
     * 支持传参数构造专递参数
     * @param returnType 返回类型
     * @param returnCode 处理结果编码
     * @param returnMsg 处理结果描述
     * @author cxt-longsebo
     */
    public BasicBusinessException(String returnType, String returnCode, String returnMsg) {
        this.returnType = returnType;
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }
    
    
    
    public BasicBusinessException(Object object) {
    	if(object != null){
    		try {
    			Class<? extends Object> class1 = object.getClass();
    			Method returnTypeMethod = ReflectionUtils.findMethod(class1, getReturnType);
    			Method returnCodeMethod = ReflectionUtils.findMethod(class1, getReturnCode);
    			Method returnMsgMethod =  ReflectionUtils.findMethod(class1, getReturnMsg);
    			
    			this.returnType = ReflectionUtils.invokeMethod(returnTypeMethod, object).toString();
    			this.returnCode = ReflectionUtils.invokeMethod(returnCodeMethod, object).toString();
    			this.returnMsg  = ReflectionUtils.invokeMethod(returnMsgMethod, object).toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }
    
  
    
    public String getMessage(){
    	return "returnType = "+this.returnType+", returnCode = "+this.returnCode+", returnMsg = "+this.returnMsg;
    }


	public String getReturnType() {
		return returnType;
	}


	public String getReturnCode() {
		return returnCode;
	}


	public String getReturnMsg() {
		return returnMsg;
	}
    
}
