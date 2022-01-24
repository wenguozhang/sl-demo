package com.sltas.demo.common.excel;

import com.google.gson.Gson;

public class SelectInfo {
	
	/**
	 * 选择框所在列定义
	 */
	private Integer colNum;
	
	/**
	 * 选择框初始化类型
	 * 1 根据导出记录数初始化
	 * 2 默认设置
	 * 3 自定义
	 */
	private Integer initRowType;
	
	/**
	 * 选择框初始化行数
	 */
	private Integer initRowNum;
	
	/**
	 * 选择列表初始化值
	 */
	private String[] selectValue;
	
	
	public SelectInfo(Integer colNum, String[] selectValue) {
		this.colNum = colNum;
		this.selectValue = selectValue;
	}
	
	public SelectInfo(Integer colNum, String[] selectValue, Integer initRowType, Integer initRowNum) {
		this.colNum = colNum;
		this.selectValue = selectValue;
		this.initRowType = initRowType;
		this.initRowNum = initRowNum;
	}

	public Integer getColNum() {
		return colNum;
	}

	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	public int getInitRowNum() {
		return initRowNum;
	}

	public void setInitRowNum(Integer initRowNum) {
		this.initRowNum = initRowNum;
	}

	public Integer getInitRowType() {
		return initRowType;
	}

	public void setInitRowType(Integer initRowType) {
		this.initRowType = initRowType;
	}

	public String[] getSelectValue() {
		return selectValue;
	}

	public void setSelectValue(String[] selectValue) {
		this.selectValue = selectValue;
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	  
}
