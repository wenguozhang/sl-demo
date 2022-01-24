package com.sltas.demo.common.excel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 
 * @创建人 	 迟启龙
 * @创建时间 	2020-12-1 18:19:53
 * @说明  	Excel常量枚举实体类
 */
public class BasicExcelConstant {
	
	/***********************选择框初始化类型开始********************/
	
	/**
	 * 1 根据导出记录数初始化
	 */
	public static final Integer INIT_ROW_TYPE_ROWS = 1;
	
	/**
	 * 2 默认设置
	 */
	public static final Integer INIT_ROW_TYPE_DEFAULT = 2;
	
	/**
	 * 3 自定义
	 */
	public static final Integer INIT_ROW_TYPE_CUSTOM = 3;
	
	/**
	 * 选择框初始化行数默认设置
	 */
	public static final Integer INIT_ROW_DEFAULT_NUM = 50000;
	
	/**
	 * 错误信息头名称
	 */
	public static final String ERROR_MSG_NAME = "错误信息";
	
	/**
	 * 错误信息头key
	 */
	public static final String ERROR_MSG_KEY = "errorMsg";
	
	
	
	/***********************选择框初始化类型结束********************/
	
	public static List<String[]> initHeaderList(String[]...headers){
		List<String[]> headerList = GsonUtil.JsonToList(GsonUtil.GsonToString(headers), String[].class);
		return headerList;
	}
	

	
	/***********************各业务导出设置开始*********************/
	
	
	private static final String[] DEMO_HEADERS1 = {"考试信息","5"};
	private static final String[] DEMO_HEADERS2 = {"用户信息","2","成绩信息","3"};
	private static final String[] DEMO_HEADERS_ALIAS = {"姓名","年龄","成绩","是否合格","考试日期"};
	private static final String[] DEMO_HEADERS_ALIAS1 = {"name","age","grade","isQualified","date"};
	

	//策略标准详情导出
	private static final String[] BILL_STRATEGY_STANDARD_HEAD_HEADERS_NAME = {"收费减免策略","收费年度","创建时间","修改时间","操作人"};
	private static final String[] BILL_STRATEGY_STANDARD_HEAD_HEADERS_ALIAS = {"strategyInfoName","chargeYear","createdTimeStr","lastUpdatedTimeStr","operatorName"};

	//策略发布详情导出
	private static final String[] BILL_STRATEGY_PUBLISH_DETAIL_HEADERS_NAME = {"发布流水号","策略标准","账单项目","发布状态","失败原因","用户编号",
	        "用户名称","所属机构","收费金额","发布周期类型","发布周期",
	        "合计分期数","费率浮动比(%)","收费总金额","发布成功时间","创建时间"};
	private static final String[] BILL_STRATEGY_PUBLISH_DETAIL_HEADERS_ALIAS = {"strategyPublishNo","billStrategyStandardHeadName","billItemName","publishStatusName","failMsg","personnelNumber","personnelName",
    		"managerScopeName","tradeAmount","periodTypeName","periodCodes",
    		"periodCodesCount", "rateFloatRatio","publishAmount","publishTimeStr", "createdTimeStr"};

	//集中开票导出
	private static final String[] INVOICE_FOCUS_HEADERS_NAME = {"订单编号","用户编号","用户名称","产品名称","单价","数量","已退金额","可开票金额","开票方式","结算类型","交易日期"};
	private static final String[] INVOICE_FOCUS_HEADERS_ALIAS = {"itemNo","custUserNumber","custUserName","prodProdName","discountPrice","allowRefundMaxCount","refundTotalAmount","allowRefundMaxAmount","invoiceWhetherMayName","settlementName","payTime"};
	private static final String[] INVOICE_FOCUS_HEADERS_SUM = {"合计","5"};
	
	public static final String DEMO_HEADERS_LIST = "demoHeadersList";
	public static final String DEMO_HEADERS_LIST1 = "demoHeadersList1";
	//策略标准详情导出
	public static final String BILL_STRATEGY_STANDARD_HEAD_HEADERS_LIST = "billStrategyStandardHeadHeadersList";
	
	//策略发布详情导出
	public static final String BILL_STRATEGY_PUBLISH_DETAIL_HEADERS_LIST = "billStrategyPublishDetailHeadersList";
	
	//集中开票导出
	public static final String INVOICE_FOCUS_HEADERS_LIST = "invoiceFocusHeadersList";
	
	public static List getHeaderList(String str) {
		List<String[]> list = new ArrayList<String[]>();
		list.add(new String[] {"aa"});
		return list;
	}
	public static List getSumMapList(String str) {
		List<String[]> list = new ArrayList<String[]>();
		list.add(new String[] {"aa"});
		return list;
	}
	
	
	
	/***********************各业务导出设置结束*********************/
	
}
 