package com.common.initialdata;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class ReadExcel {
	
	private String readMePath;
	
	private static DBConnectionUtil DBConnectionUtil;
	
	public void setExcelPath(String readMePath) {
		this.readMePath=readMePath;
	}
	
	public String getExcelPath() {
		return readMePath;
	}
	
	@SuppressWarnings("static-access")
	public List<DBConnectionUtil> readXls(String execTag) throws IOException {
		System.out.println(readMePath);
        InputStream is = new FileInputStream(getExcelPath());
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        //DBConnectionUtil ConnInfo = null;
        List<DBConnectionUtil> list = new ArrayList<DBConnectionUtil>();
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                	DBConnectionUtil = new DBConnectionUtil();
                    HSSFCell ip = hssfRow.getCell(0);
                    HSSFCell port = hssfRow.getCell(1);
                    port.setCellType(port.CELL_TYPE_STRING);
                    HSSFCell db = hssfRow.getCell(2);
                    HSSFCell uid = hssfRow.getCell(3);
                    HSSFCell pwd = hssfRow.getCell(4);
                    pwd.setCellType(pwd.CELL_TYPE_STRING);
                    HSSFCell dir = hssfRow.getCell(5);
                    HSSFCell uri = hssfRow.getCell(6);
                    DBConnectionUtil.setIp(getValue(ip));
                    DBConnectionUtil.setPort(getValue(port));
                    DBConnectionUtil.setDb(getValue(db));
                    DBConnectionUtil.setUid(getValue(uid));
                    DBConnectionUtil.setPwd(getValue(pwd));
                    DBConnectionUtil.setDir(getValue(dir));
                    String getTag = uri.toString();
                    System.out.println(getTag);
                    System.out.println(execTag);
                    if (getTag.equals(execTag)) {
                    	list.add(DBConnectionUtil); 
                    }
                    else continue;                      
                    System.out.println(list.get(0).getDir());
                }
            }
        }
        System.out.println(list);
        return list;
    }
	

    private String getValue(HSSFCell hssfCell) {
            if (hssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                return String.valueOf(hssfCell.getBooleanCellValue());
            } else if (hssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                return String.valueOf(hssfCell.getNumericCellValue());
            } else {
                return String.valueOf(hssfCell.getStringCellValue());
            }
        }
}

