package com.card.utils;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class DataWriter {

	private static int lastNumoutput=0;
	private static int lastNumcom=0;
	private static int lastNumresult=0;
	public static void writeData(XSSFSheet comparsionSheet, String result, String iD, String test_case) {
		//lastNumoutput = comparsionSheet.getLastRowNum();
		System.out.println("lastNum:"+lastNumoutput);
		if(0 == lastNumoutput){
			writeSheet(comparsionSheet.createRow(lastNumoutput),"comparsionDetail","ID","TestCase");
			//lastNumoutput ++;
		}
		writeSheet(comparsionSheet.createRow(lastNumoutput),result,iD,test_case);
	}
	public static void writeSheet(XSSFRow row, String... data){
		for(int i=0;i<data.length;i++){
			row.createCell(i).setCellValue(data[i]);
		}
		lastNumoutput ++;
	}
	
	public static void writeSheetrest(XSSFRow row, String... data){
		for(int i=0;i<data.length;i++){
			row.createCell(i).setCellValue(data[i]);
		}
		lastNumcom ++;
	}

	public static void writeData(XSSFSheet resultSheet, String string, String iD, String test_case, int i) {
		//lastNumrest = resultSheet.getLastRowNum();
		writeSheetresult(resultSheet.createRow(lastNumresult+1),string,iD,test_case);
	}

	public static void writeData(XSSFSheet comparsionSheet, String string, String iD, String iD2, String test_case) {
		//lastNumrest = comparsionSheet.getLastRowNum();
		writeSheet(comparsionSheet.createRow(lastNumcom+1),string,iD,iD2,test_case);
		
	}
	
	public static void writeData(XSSFSheet resultSheet, double totalcase, double failedcase, String startTime,
			String endTime) {
		
		writeSheet(resultSheet.createRow(1),String.valueOf(totalcase),String.valueOf(failedcase),startTime,endTime);
	}

	public static void writeDatacomparsionSheet(XSSFSheet comparsionSheet, String result, String iD,
			String test_case) {
		// TODO Auto-generated method stub
		//int lastNum = comparsionSheet.getLastRowNum();
		System.out.println("lastNumcom:"+lastNumcom);
		if(0 == lastNumcom){
			writeSheetrest(comparsionSheet.createRow(lastNumcom),"comparsionDetail","ID","TestCase");
			//lastNum ++;
		}
		writeSheetrest(comparsionSheet.createRow(lastNumcom),result,iD,test_case);
		
	}
	public static void writeSheetresult(XSSFRow row, String... data){
		//WritableCellFormat writableCellFormat = new WritableCellFormat();
		for(int i=0;i<data.length;i++){
			row.createCell(i).setCellValue(data[i]);
		}
		lastNumresult ++;
	}
	//设置红色单元格，用例失败的显示为红色
	public static void CellStyle()
	{
		//CellStyle style;
	}
}
