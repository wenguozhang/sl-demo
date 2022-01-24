package com.sltas.demo.common.excel;

import org.apache.poi.ss.usermodel.Cell;

import cn.hutool.poi.excel.cell.CellEditor;

/**
 * @author sllink
 *
 */
public class BasicCellEditor implements CellEditor{

	BasicCellEditor(){
	}
	/**
	 * 
	 * @创建人 迟启龙
	 * @创建时间 2020年12月14日下午6:58:36
	 * @说明  
	 * @param 
	 * @return
	 * @throws  
	 *
	 */
	@Override
	public Object edit(Cell cell, Object value) {
		return value;
	}
}

