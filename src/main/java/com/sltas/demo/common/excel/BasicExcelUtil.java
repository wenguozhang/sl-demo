package com.sltas.demo.common.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;


public class BasicExcelUtil extends ExcelWriter{
	
	private ExcelWriter writer = null;
	
	private String filePath = null;
	
	private String sheetName = null;
	
	private List<String[]> headers = null;
	
	private String[] headerAliasName = null;
	
	private String[] headerAliasKey = null;

	private List<?> rows = null;
	
	private List<SelectInfo> select = null;
	
	private boolean genErrorMsg = false;
	
	private HttpServletResponse response = null;

	private List<String[]> sumMap = null;
	
	private static String[] selectCell = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	
	/**
	 * 
	 * @创建人 	迟启龙
	 * @创建时间 	2020-12-1 18:19:53
	 * @param 	headersKey 导出Excel表头对应的KEY(必填)
	 * @param 	data 导出Excel的记录集合 支持List<Bean>,List<Map>(非必填)
	 * @param 	selectInfo 导出Excel的选择框设置(非必填)
	 * @param 	HttpServletResponse response
	 * @return	
	 * @说明  	BasicExcelUtil: Excel模板下载初始化,response流下载,不直接生成本地文件
	 * @throws	 
	 */
	public BasicExcelUtil(String fileName, String headersKey, List<?> data, List<SelectInfo> selectInfo, HttpServletResponse response) {
		this.filePath = fileName;
		this.writer = ExcelUtil.getWriter();
		List<String[]> list = new ArrayList<String[]>();
		list.add(new String[] {"aa"});
		this.headers = list;
		this.rows = data;
		this.headerAliasName = headers.get(headers.size() - 2);
		this.headerAliasKey = headers.get(headers.size() - 1);
		this.select = selectInfo;
		this.response = response;
	}
	
	/**
	 * 
	 * @创建人 	迟启龙
	 * @创建时间 	2020-12-1 18:19:53
	 * @param 	headers 导出Excel的表头集合(必填)
	 * @param 	data 导出Excel的记录集合 支持List<Bean>,List<Map>(非必填)
	 * @param 	selectInfo 导出Excel的选择框设置(非必填)
	 * @param 	HttpServletResponse response
	 * @return	
	 * @说明  	BasicExcelUtil: Excel模板下载初始化,response流下载,不直接生成本地文件
	 * @throws	 
	 */
	public BasicExcelUtil(String fileName, List<String[]> headers, List<?> data, List<SelectInfo> selectInfo, HttpServletResponse response) {
		this.filePath = fileName;
		this.writer = ExcelUtil.getWriter();
		this.headers = headers;
		this.rows = data;
		this.headerAliasName = headers.get(headers.size() - 2);
		this.headerAliasKey = headers.get(headers.size() - 1);
		this.select = selectInfo;
		this.response = response;
	}

	/**
	 * 
	 * @创建人 	迟启龙
	 * @创建时间 	2020-12-1 18:19:53
	 * @param 	filePath 文件生成路径(必填)
	 * @param 	sheetName 导出Excel的sheet名称(非必填)
	 * @param 	headersKey 导出Excel表头对应的KEY(必填)
	 * @param 	data 导出Excel的记录集合 支持List<Bean>,List<Map>(非必填)
	 * @return	
	 * @说明  	BasicExcelUtil: Excel导出设置初始化，直接导出文件，目前只支持单个sheet导出
	 * @throws	 
	 */
	public BasicExcelUtil(String filePath, String sheetName, String headersKey, List<?> data) {
		this(filePath, sheetName, headersKey, data, null, false);
	}
	
	/**
	 * 
	 * @创建人 	迟启龙
	 * @创建时间 	2020-12-1 18:19:53
	 * @param 	filePath 文件生成路径(必填)
	 * @param 	sheetName 导出Excel的sheet名称(非必填)
	 * @param 	headers 导出Excel的表头集合(必填)
	 * @param 	data 导出Excel的记录集合 支持List<Bean>,List<Map>(非必填)
	 * @return	
	 * @说明  	BasicExcelUtil: Excel导出设置初始化，直接导出文件，目前只支持单个sheet导出
	 * @throws	 
	 */
	public BasicExcelUtil(String filePath, String sheetName, List<String[]> headers, List<?> data) {
		this(filePath, sheetName, headers, data, null, false);
	}
	
	/**
	 * 
	 * @创建人 	迟启龙
	 * @创建时间 	2020-12-1 18:19:53
	 * @param 	filePath 文件生成路径(必填)
	 * @param 	sheetName 导出Excel的sheet名称(非必填)
	 * @param 	headersKey 导出Excel表头对应的KEY(必填)
	 * @param 	data 导出Excel的记录集合 支持List<Bean>,List<Map>(非必填)
	 * @param 	selectInfo 导出Excel的选择框设置(非必填)
	 * @param 	genErrorMsg 错误信息生成标识(必填)
	 * @return	
	 * @说明  	BasicExcelUtil: Excel导出设置初始化，直接导出文件，目前只支持单个sheet导出
	 * @throws	 
	 */
	public BasicExcelUtil(String filePath, String sheetName, String headersKey, List<?> data, List<SelectInfo> selectInfo, boolean genErrorMsg) {
		this.filePath = filePath;
		this.writer = ExcelUtil.getBigWriter(this.filePath, sheetName);
		this.headers = BasicExcelConstant.getHeaderList(headersKey);
		this.rows = data;
		this.headerAliasName = headers.get(headers.size() - 2);
		this.headerAliasKey = headers.get(headers.size() - 1);
		this.select = selectInfo;
		this.genErrorMsg = genErrorMsg;
		this.sumMap = BasicExcelConstant.getSumMapList(headersKey);
		if(genErrorMsg) {
			this.headerAliasName = insertElement(headerAliasName,BasicExcelConstant.ERROR_MSG_NAME,0);
			this.headerAliasKey = insertElement(this.headerAliasKey,BasicExcelConstant.ERROR_MSG_KEY,0);
		}
	}
	
	/**
	 * 
	 * @创建人 	迟启龙
	 * @创建时间 	2020-12-1 18:19:53
	 * @param 	filePath 文件生成路径(必填)
	 * @param 	sheetName 导出Excel的sheet名称(非必填)
	 * @param 	headers 导出Excel的表头集合(必填)
	 * @param 	data 导出Excel的记录集合 支持List<Bean>,List<Map>(非必填)
	 * @param 	selectInfo 导出Excel的选择框设置(非必填)
	 * @param 	genErrorMsg 错误信息生成标识(必填)
	 * @return	
	 * @说明  	BasicExcelUtil: Excel导出设置初始化，直接导出文件，目前只支持单个sheet导出
	 * @throws	 
	 */
	public BasicExcelUtil(String filePath, String sheetName, List<String[]> headers, List<?> data, List<SelectInfo> selectInfo, boolean genErrorMsg) {
		this.filePath = filePath.replace(".", (LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))).concat("."));
		this.writer = ExcelUtil.getBigWriter(this.filePath, sheetName);
    	this.headers = headers;
    	this.rows = data;
    	this.headerAliasName = headers.get(headers.size() - 2);
    	this.headerAliasKey = headers.get(headers.size() - 1);
    	this.select = selectInfo;
    	this.genErrorMsg = genErrorMsg;
    	if(genErrorMsg) {
    		this.headerAliasName = insertElement(headerAliasName,BasicExcelConstant.ERROR_MSG_NAME,0);
			this.headerAliasKey = insertElement(this.headerAliasKey,BasicExcelConstant.ERROR_MSG_KEY,0);
		}
	}
	
	/**
	 * 
	 * @创建人 	迟启龙
	 * @创建时间 	2020-12-1 18:19:53
	 * @param 	filePath 文件生成路径(必填)
	 * @param 	sheetName 导出Excel的sheet名称(必填)
	 * @param 	headersKey 导出Excel表头对应的KEY(必填)
	 * @return	BasicExcelUtil
	 * @说明  	BasicExcelUtil: Excel文件读取设置初始化
	 * @throws	 
	 */
	public BasicExcelUtil(String filePath, String sheetName, String headersKey) {
		this.filePath = filePath;
		this.writer = ExcelUtil.getWriter();
		this.sheetName = sheetName;
		this.headers = BasicExcelConstant.getHeaderList(headersKey);
		this.headerAliasName = this.headers.get(this.headers.size() - 2);
		this.headerAliasKey = headers.get(headers.size() - 1);
	}
	
	/**
	 * 
	 * @创建人 	迟启龙
	 * @创建时间 	2020-12-1 18:19:53
	 * @param 	filePath 文件生成路径(必填)
	 * @param 	sheetName 导出Excel的sheet名称(必填)
	 * @param 	headers Excel的表头集合(必填)
	 * @return	BasicExcelUtil
	 * @说明  	BasicExcelUtil: Excel文件读取设置初始化
	 * @throws	 
	 */
	public BasicExcelUtil(String filePath, String sheetName, List<String[]> headers) {
		this.filePath = filePath;
		this.writer = ExcelUtil.getWriter();
		this.sheetName = sheetName;
		this.headers = headers;
		this.headerAliasKey = this.headers.get(this.headers.size() - 1);
		this.headerAliasName = this.headers.get(this.headers.size() - 2);
	}
	
	/**
	 * 
	 * @创建人 	迟启龙
	 * @创建时间 	2020-12-1 18:19:53
	 * @return	ResponseResultDto<String> fileSize 生成文件大小
	 * @说明  	exportExcel: Excel导出，直接导出文件，目前只支持单个sheet导出
	 * @throws	 
	 */
    public String exportExcel(){
    	try {
			setHeaders();
			writerExcel();
			String fileSize = getFileSize(this.filePath);
			return fileSize;
		} catch (Exception e) {
			e.printStackTrace();
			if(this.writer != null) {
				this.writer.close();
			}
			return "";
		} 
    }
    
    /**
	 * 
	 * @创建人 	迟启龙
	 * @创建时间 	2020-12-1 18:19:53
	 * @return	ResponseResultDto<Boolean> 是否成功
	 * @说明  	exportExcel: Excel导出，直接导出文件，目前只支持单个sheet导出
	 * @throws	 
	 */
    public Boolean downloadExcelTemplate(){
    	try {
			setHeaders();
			writerResponse();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if(this.writer != null) {
				this.writer.close();
			}
			return false;
		} 
    }
    
    
    /**
	 * 
	 * @创建人 	迟启龙
	 * @创建时间 	2020-12-1 18:19:53
	 * @return	ResponseResultDto<List<Map>> 文件数据
	 * @说明  	readerExcel: 读Excel，验证表头，并返回读取的数据
	 * @throws	 
	 */
    @SuppressWarnings("rawtypes")
	public List<Map> readerExcel(){
    	InputStream inputStream = null;
    	List<Map> read = null;
    	try {
    		// 1.获取上传文件输入流
    		inputStream = this.getInputStream();
    		// 2.应用HUtool ExcelUtil获取ExcelReader指定输入流和sheet
        	ExcelReader excelReader = ExcelUtil.getReader(inputStream, this.sheetName);
        	// 3.表头验证(只验证最后一级)
        	validHeaders(excelReader);
        	// 4.读取数据
        	excelReader.setCellEditor(new BasicCellEditor());
        	excelReader.setHeaderAlias(this.getHeaderAlias());
        	read = excelReader.read(this.headers.size() - 2, this.headers.size() - 1, excelReader.getRowCount(), Map.class);
        	
//        	for (Map map : read) {
//        		for(Object key : map.keySet()){
//        			System.out.println(key+":"+map.get(key));
//        		}
//        	}
		} catch (Exception e) {
			e.printStackTrace();			
			return null;
		} 
    	return read;
    }
    
    /**
	 * 
	 * @创建人 迟启龙
	 * @创建时间 2020年12月14日下午6:45:09
	 * @说明  
	 * @param 
	 * @return
	 * @throws  
	 *
	 */
	private Map<String, String> getHeaderAlias() {
		Map<String, String> headerAlias = new HashMap<>();
		for (int i = 0; i < headerAliasKey.length; i++) {
			headerAlias.put(headerAliasName[i], headerAliasKey[i]);
		}
		return headerAlias;
	}

	public List<List<Object>> readerExcel1(){
    	InputStream inputStream = null;
    	List<List<Object>> read = null;
    	try {
    		// 1.获取上传文件输入流
    		inputStream = this.getInputStream();
    		// 2.应用HUtool ExcelUtil获取ExcelReader指定输入流和sheet
        	ExcelReader excelReader = ExcelUtil.getReader(inputStream, this.sheetName);
        	// 3.表头验证(只验证最后一级)
        	validHeaders(excelReader);
        	// 4.读取数据
        	read = excelReader.read(this.headers.size() - 1, excelReader.getRowCount());
//        	read = excelReader.read(-1, this.headers.size() - 1, excelReader.getRowCount(), Map.c);
		} catch (Exception e) {
			e.printStackTrace();			
			return null;
		} 
    	return read;
    }
	
	public BasicExcelUtil(String filePath, String sheetName) {
		this.filePath = filePath;
		this.writer = ExcelUtil.getWriter();
		this.sheetName = sheetName;
//		this.headers = BasicExcelConstant.getHeaderList(headersKey);
//		this.headerAliasName = this.headers.get(this.headers.size() - 2);
//		this.headerAliasKey = headers.get(headers.size() - 1);
	}
	
	public BasicExcelUtil(String headersKey) {
		this.headers = BasicExcelConstant.getHeaderList(headersKey);
		this.headerAliasName = this.headers.get(this.headers.size() - 2);
		this.headerAliasKey = headers.get(headers.size() - 1);
	}
	
	public List<List<Object>> readerExcel2(){
    	InputStream inputStream = null;
    	List<List<Object>> read = null;
    	try {
    		// 1.获取上传文件输入流
    		inputStream = this.getInputStream();
    		// 2.应用HUtool ExcelUtil获取ExcelReader指定输入流和sheet
        	ExcelReader excelReader = ExcelUtil.getReader(inputStream, this.sheetName);
        	// 3.表头验证(只验证最后一级)
//        	validHeaders(excelReader);
        	// 4.读取数据
        	read = excelReader.read(0, excelReader.getRowCount());
//        	read = excelReader.read(-1, this.headers.size() - 1, excelReader.getRowCount(), Map.c);
		} catch (Exception e) {
			e.printStackTrace();			
			return null;
		} 
    	return read;
    }
	
	
    /**
	 * 
	 * @创建人 	迟启龙
	 * @创建时间 	2020年12月14日下午4:31:11
	 * @说明  
	 * @param 
	 * @return
	 *
	 */
	private void validHeaders(ExcelReader excelReader) {
		int rowIndex = this.headers.size() - 2;
		List<List<Object>> read = excelReader.read(rowIndex, rowIndex);
		List<Object> objects = read.get(0);
		for (int i = 0; i < this.headerAliasName.length; i++) {
			String headerAliasName = this.headerAliasName[i];
			if(!String.valueOf(objects.get(i)).equals(headerAliasName)) {
				System.out.println(false);
			}
		}
	}

	/**
	 * 
	 * @创建人 	迟启龙
	 * @创建时间 	2020年12月14日下午4:25:43
	 * @说明  
	 * @param 
	 * @return
     * @throws 	IOException 
	 *
	 */
	private InputStream getInputStream() throws IOException {
		File file = new File(this.filePath);
		FileInputStream fileInputStream = new FileInputStream(file);
		MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
		InputStream inputStream = multipartFile.getInputStream();
		return inputStream;
	}
	
	/**
	 * 
	 * @创建人 迟启龙
	 * @创建时间 2020年12月9日下午12:30:16
	 * @说明  
	 * @param 
	 * @return
	 * @throws  
	 *
	 */
	private void setHeaders() {
		if(this.headers.size() > 2) {
    		setMergeHeaders();
    	}
		setBasicHeaders();
		setHeaderAlias();
	}

	/**
	 * 
	 * @创建人 迟启龙
	 * @创建时间 2020年12月9日下午12:56:32
	 * @说明  
	 * @param 
	 * @return
	 * @throws  
	 *
	 */
	private void writerResponse() {
		if(this.rows != null && !this.rows.isEmpty()) {
			// 一次性写出内容，使用默认样式，强制输出标题
			StyleSet styleSet = this.writer.getStyleSet();
			styleSet.getCellStyleForNumber().setDataFormat((short) BuiltinFormats.getBuiltinFormat("#,##0.0000"));
			this.writer.write(this.rows, false);
		}
		if(this.select != null && !this.select.isEmpty()) {
			addSelect();
		}
		
		this.response.setContentType("application/vnd.ms-excel;charset=utf-8");
		String name = urlEncodeUTF8(this.filePath);
		this.response.setHeader("Content-Disposition","attachment;filename="+name+".xlsx");
		ServletOutputStream out= null;
		try {
			out = response.getOutputStream();
			writer.flush(out, true);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			// 关闭writer，释放内存
			this.writer.close();
		}
	}
	
	/**
	 * 
	 * @创建人 迟启龙
	 * @创建时间 2020年12月9日下午12:56:32
	 * @说明  
	 * @param 
	 * @return
	 * @throws  
	 *
	 */
	private void writerExcel() {
		if(this.rows != null && !this.rows.isEmpty()) {
			// 一次性写出内容，使用默认样式，强制输出标题
			StyleSet styleSet = this.writer.getStyleSet();
			styleSet.getCellStyleForNumber().setDataFormat((short) BuiltinFormats.getBuiltinFormat("#,##0.0000"));
			this.writer.write(this.rows, false);

			if(null != sumMap) {
				String[] sum = this.sumMap.get(this.sumMap.size()-1);
				int firstColumn = 0;
				for (int j = 0; j < sum.length; j += 2) {
					String headersName =sum[j];
					int lastColumn = firstColumn + Integer.valueOf(sum[j+1]) - 1;
					
					this.writer.merge(this.writer.getCurrentRow()-1, this.writer.getCurrentRow()-1, firstColumn, lastColumn, headersName, true);
					firstColumn = lastColumn + 1;
				}
			}
		}
		if(this.select != null && !this.select.isEmpty()) {
			addSelect();
		}
    	// 关闭writer，释放内存
		this.writer.close();
	}
	
	/**
	 * 
	 * @创建人 迟启龙
	 * @创建时间 2020年12月9日下午2:49:32
	 * @说明  
	 * @param 
	 * @return
	 * @throws  
	 *
	 */
	private void addSelect() {
		String selectList = ""; //Sheet2第A1到A(N)作为下拉列表来源数据 
		Sheet sheet = this.writer.getSheet();
		this.writer.setSheet("selectSheet");
		
		for (int i = 0; i < this.select.size(); i++) {
			SelectInfo selectInfo = this.select.get(i);
			selectList = "selectSheet!$" + selectCell[i] + "$1:$" + selectCell[i] + "$" + selectInfo.getSelectValue().length; //Sheet2第A1到A(N)作为下拉列表来源数据 
			
			int firstRow = this.headers.size() - 1;
			int lastRow = BasicExcelConstant.INIT_ROW_DEFAULT_NUM;
			if(BasicExcelConstant.INIT_ROW_TYPE_ROWS.equals(selectInfo.getInitRowType())) {
				lastRow = firstRow + this.rows.size() - 1;
			}else if(BasicExcelConstant.INIT_ROW_TYPE_CUSTOM.equals(selectInfo.getInitRowType())) {
				lastRow = selectInfo.getInitRowNum();
			}
			int firstCol = selectInfo.getColNum() - 1;
			int lastCol = selectInfo.getColNum() - 1;
			
			CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
			final DataValidationHelper validationHelper = sheet.getDataValidationHelper();
			final DataValidationConstraint constraint = validationHelper.createFormulaListConstraint(selectList);

			//设置下拉框数据
			final DataValidation dataValidation = validationHelper.createValidation(constraint, regions);

			//处理Excel兼容性问题
			if (dataValidation instanceof XSSFDataValidation) {
				dataValidation.setSuppressDropDownArrow(true);
				dataValidation.setShowErrorBox(true);
			} else {
				dataValidation.setSuppressDropDownArrow(false);
			}

			sheet.addValidationData(dataValidation);
			
			String[] selectValue = selectInfo.getSelectValue();
			for (int j = 0; j < selectValue.length; j++) {
				this.writer.writeCellValue(i, this.writer.getCurrentRow(), selectInfo.getSelectValue()[j]);
				this.writer.passCurrentRow();
			}
			
			this.writer.resetRow();
		}
		
	}

	/**
	 * 
	 * @创建人 迟启龙
	 * @创建时间 2020年12月9日下午12:32:35
	 * @说明  
	 * @param 
	 * @return
	 * @throws  
	 *
	 */
	private void setMergeHeaders() {
		for (int i = 0; i < this.headers.size() - 2; i++) {
    		String[] header = this.headers.get(i);
    		int firstColumn = 0;
    		for (int j = 0; j < header.length; j += 2) {
    			String headersName = header[j];
    			int lastColumn = firstColumn + Integer.valueOf(header[j+1]) - 1;
    			if(this.genErrorMsg && j == 0) {
    				lastColumn = firstColumn + Integer.valueOf(header[j+1]);
    			}
    			
    			if(firstColumn == lastColumn) {
//    				Cell cell = getOrCreateCell(firstColumn, this.writer.getCurrentRow());
//    				StyleSet styleSet = this.writer.getStyleSet();
//    				CellUtil.setCellValue(cell, headersName, styleSet, true);
    				this.writer.writeCellValue(firstColumn, this.writer.getCurrentRow(), headersName);
    			} else {
    				this.writer.merge(this.writer.getCurrentRow(), this.writer.getCurrentRow(), firstColumn, lastColumn, headersName, true);
    			}
    			firstColumn = lastColumn + 1;
			}
    		this.writer.passCurrentRow();
		}
	}
	
	/**
	 * 
	 * @创建人 迟启龙
	 * @创建时间 2020年12月9日下午12:32:19
	 * @说明  
	 * @param 
	 * @return
	 * @throws  
	 *
	 */
	private void setBasicHeaders() {
		this.writer.writeHeadRow(Arrays.asList(headerAliasName));
	}

	/**
	 * 
	 * @创建人 迟启龙
	 * @创建时间 2020年12月9日下午12:32:22
	 * @说明  
	 * @param 
	 * @return
	 * @throws  
	 *
	 */
	private void setHeaderAlias() {
		Map<String, String> headerAliasMap = new LinkedHashMap<String, String>();
		for (int i = 0; i < this.headerAliasKey.length; i++) {
			headerAliasMap.put(this.headerAliasKey[i], this.headerAliasName[i]);
			this.writer.getSheet().setColumnWidth(i, 4000); //  设置宽度
			StyleSet styleSet = this.writer.getStyleSet();
			styleSet.setBackgroundColor(IndexedColors.WHITE, true);	// 设置背景颜色
		}
    	//自定义标题别名
		this.writer.setHeaderAlias(headerAliasMap);
		this.writer.setOnlyAlias(true);
	}
	
	private static String[] insertElement(String original[], String element, int index) {
		return (String[]) ArrayUtils.add(original, index, element);
//		int length = original.length;
//		String destination[] = new String[length + 1];
//		System.arraycopy(original, 0, destination, 0, index);
//		destination[index] = element;
//		System.arraycopy(original, index, destination, index + 1, length - index);
//		return destination;
	}
	
	/**
	 * 
	 * @创建人 迟启龙
	 * @创建时间 2020年12月16日下午2:14:06
	 * @说明  
	 * @param 
	 * @return
	 * @throws IOException 
	 *
	 */
	private String getFileSize(String filePath) {
		String fileSize = "0KB";
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(filePath);
			FileChannel channel = fileInputStream.getChannel();
			long size = channel.size();
			size = size / 1024 + 1;
			if (size > 1024) {
				fileSize = size / 1024 + "M";
			} else {
				fileSize = size + "KB";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return fileSize;
	}

	 /**
     * @说明:  字符串编码转换
     * @param str
     * @return
     */
    public static String urlEncodeUTF8(String str){
  		if(StringUtils.isBlank(str)){
  			return "";
  		}
  		try {
  			str = URLEncoder.encode(str, "utf-8");
  		} catch (UnsupportedEncodingException e) {
  			throw new RuntimeException(e);
  		}
  		return str;
  	}
    
    public String[] getHeaderAliasName() {
		return headerAliasName;
	}

	
	public String[] getHeaderAliasKey() {
		return headerAliasKey;
	}
	
	/**
	 * <p>Title: readerExcel2</p>
	 * <p>Description: excel导入解析</p>
	 * @author jp.zhang
	 * @date 2021年3月9日
	 * @return
	 */
	public static List<List<Object>> readerExcel3(InputStream inputStream, String sheetName){
    	List<List<Object>> read = null;
    	try {
    		// 2.应用HUtool ExcelUtil获取ExcelReader指定输入流和sheet
        	ExcelReader excelReader = ExcelUtil.getReader(inputStream, sheetName);
        	// 3.表头验证(只验证最后一级)
//        	validHeaders(excelReader);
        	// 4.读取数据
        	read = excelReader.read(0, excelReader.getRowCount());
//        	read = excelReader.read(-1, this.headers.size() - 1, excelReader.getRowCount(), Map.c);
		} catch (Exception e) {
			e.printStackTrace();			
			return null;
		} 
    	return read;
    }
}
