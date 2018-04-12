package web.com.performanceTest.work;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	
	public List< List<Object> > read(String filePath) {
		

		List< List<Object> > list = new ArrayList<List<Object>>();

		XSSFWorkbook wbook = null;
		FileInputStream input = null;
		
		try {
			input = new FileInputStream(filePath);
			
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		try {
			wbook = new XSSFWorkbook(input);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			try {
				
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		int sheetNumber = wbook.getNumberOfSheets();
		if (sheetNumber < 1) {
			try {
				throw new Exception(filePath + "错误！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		XSSFSheet sheet = (XSSFSheet) wbook.getSheet("sheet1");
		int lastRowNum = sheet.getLastRowNum();
		XSSFRow firstRow = sheet.getRow(0);
		if (firstRow == null) {
			try {
				throw new Exception(filePath + "sheet1为null！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
		for (int iRow = 0; iRow < lastRowNum; iRow++) {
			int keyNum = 0;
			if(sheet.getRow(iRow)==null)
				continue;
			
			String key;
			String value;
			if (iRow == 0) {
				List testTime = new ArrayList();
				Map<String,String> m = new HashMap<String,String>();
				sheet.getRow(iRow).getCell(1).setCellType(1);
				m.put("time",  sheet.getRow(iRow).getCell(1).getStringCellValue());
				testTime.add("测试时间");
				testTime.add(m);
				list.add(testTime);
				
				continue;
			}
			
			//增加场景说明 
			 
			if (iRow == 1) {
				List cont = new ArrayList();
				Map<String,String> m = new HashMap<String,String>();
				sheet.getRow(iRow).getCell(1).setCellType(1);
				m.put("content",  sheet.getRow(iRow).getCell(1).getStringCellValue());
				cont.add("场景说明");
				cont.add(m);
				list.add(cont);
				
				continue;
			}
            //
			//
			//
			if (sheet.getRow(iRow).getCell(0) == null
					|| sheet.getRow(iRow).getCell(0).getStringCellValue() == ""
					|| sheet.getRow(iRow).getCell(0).getStringCellValue() == null
					|| sheet.getRow(iRow).getCell(0).getCellType() == XSSFCell.CELL_TYPE_BLANK)
				continue;
			sheet.getRow(iRow).getCell(0).setCellType(1);
			key = sheet.getRow(iRow).getCell(0).getStringCellValue();
			
			if(key.equalsIgnoreCase("服务器信息")){
				String k;
				String v;
				Map<String,String> m = new HashMap<String,String>();
				for(int i=1; i<7 ; i++){
					sheet.getRow(iRow+i).getCell(0).setCellType(1);
					k = sheet.getRow(iRow+i).getCell(0).getStringCellValue();
					sheet.getRow(iRow+i).getCell(1).setCellType(1);
					v = sheet.getRow(iRow+i).getCell(1).getStringCellValue();
					switch(i){
					case 1:
						k="ip";
					    break;
					case 2:
						k="system";
					    break;
					case 3:
						k="cpu";
					    break;    
					case 4:
						k="memory";
					    break;
					case 5:
						k="environment";
					    break;
					case 6:
						k="spdVersion";
					    break;
					}
					
					m.put(k, v);
				}
                List serverInfo = new ArrayList();
                serverInfo.add("服务器信息");
                serverInfo.add(m);
                list.add(serverInfo);
				iRow = iRow+7;
				continue;
			}
			
			//相关测试信息
			if (key != "" && (sheet.getRow(iRow).getCell(1) == null
					|| sheet.getRow(iRow).getCell(1).getStringCellValue() == ""
					|| sheet.getRow(iRow).getCell(1).getStringCellValue() == null
					|| sheet.getRow(iRow).getCell(1).getCellType() == HSSFCell.CELL_TYPE_BLANK) ){

				
				String k;
				String v;
				Map<String,String> m = new HashMap<String,String>();
                for(int i=1; i<4 ;i++){
                	
                	sheet.getRow(iRow+i).getCell(0).setCellType(1);
                	k = sheet.getRow(iRow+i).getCell(0).getStringCellValue();
                	sheet.getRow(iRow+i).getCell(1).setCellType(1);
					v = sheet.getRow(iRow+i).getCell(1).getStringCellValue(); 
					
					switch(i){
					case 1:
						k="avgTime";
					    break;
					case 2:
						k="maxTime";
					    break;
					case 3:
						k="minTime";
					    break;    
					}
					
					m.put(k, v);
					
                }
                List test = new ArrayList();
                test.add(key);
                test.add(m);
                
                list.add(test);
				iRow = iRow+3;
				continue;	
			}
		}
   return list;
	}
}
