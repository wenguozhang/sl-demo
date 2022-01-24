package com.sltas.demo.common;

import lombok.Data;
import lombok.Getter;

@Data
public class BasicEnumClass {

	/**
	 * @ClassName: StatusEnum
	 * @Description: 0|1 状态
	 * @author liuqinglin
	 * @date 2021年12月17日
	 */
	@Getter
	public enum StatusEnum{
		STATUS_0("0","正常","关闭","否","不支持"),
		
		STATUS_1("1","停用","开启","是","支持");
		
		private String code;
		
		private String msg;
		
		private String isOpen;
		
		private String isYes;
		
		private String isSup;
		
		StatusEnum(String code, String msg,String isOpen,String isYes,String isSup) {
			this.code = code;
			this.msg = msg;
			this.isOpen = isOpen;
			this.isYes = isYes;
			this.isSup = isSup;
		}
	}
	
	/**
	 * @ClassName: PeriodTypeEnum
	 * @Description: 周期类型
	 * @author liuqinglin
	 * @date 2021年12月17日
	 */
	@Getter
	public enum PeriodTypeEnum{
		PERIOD_TYPE_01("01","周"),
		PERIOD_TYPE_02("02","月"),
		PERIOD_TYPE_03("03","季度"),
		PERIOD_TYPE_04("04","半年"),
		PERIOD_TYPE_05("05","年"),
		PERIOD_TYPE_11("11","学年"),
		PERIOD_TYPE_12("12","学期"),
		PERIOD_TYPE_13("13","年度"),
		PERIOD_TYPE_99("99","自由账期");
		
		private String code;
		private String msg;
		PeriodTypeEnum(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
	}
}
