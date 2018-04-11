package com.card.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.card.data.ExcelDataDeal.DataSignDeal;

/**
 * 读取Excel
 * 
 */
public class DataReader {
  
  protected static final Logger logger = LoggerFactory.getLogger(DataReader.class);

  private HashMap<String, RecordHandler> map = new HashMap<String, RecordHandler>();

  private Boolean byColumnName = false;
  private Boolean byRowKey = false;
  private List<String> headers = new ArrayList<String>();
  private static List<String> NoNeedCompared = new ArrayList<String>();

  private Integer size = 0;
  private String sign="";

  /**
   * Primary constructor. Uses Apache POI XSSF to pull data from given excel workbook sheet. Data is stored in a
   * structure depending on the options from other parameters.
   * 
   * @param sheet Given excel sheet.
   * @param has_headers Boolean used to specify if the data has a header or not. The headers will be used as field keys.
   * @param has_key_column Boolean used to specify if the data has a column that should be used for record keys.
   * @param key_column Integer used to specify the key column for record keys.
 * @param sign 
   */
  public DataReader(XSSFSheet sheet, Boolean has_headers, Boolean has_key_column, Integer key_column,int interfaceFactory) {

    XSSFRow myRow = null;
    HashMap<String, String> myList;
    size = 0;
    String body;
    int testflagcol = 0;// 是否测试标志位

    this.byColumnName = has_headers;
    this.byRowKey = has_key_column;
    //this.sign=sign;
    
    try {
    
      if(byColumnName) {
        myRow = sheet.getRow(0);
        for(Cell cell: myRow) {
          headers.add(cell.getStringCellValue());
        }
        size = 1;
      }
  
      for(; (myRow = sheet.getRow(size)) != null; size++ ) {
    	  for (int col = 0; col <= sheet.getRow(1).getLastCellNum() - 1; col++) {			
      		if(headers.get(col).equals("IsTest"))
          	{
          		testflagcol=col;
          	}
			}
    	  if(myRow.getCell(testflagcol).toString().toLowerCase().trim().equals("no"))
        	{
        		continue;
        	}
        myList = new HashMap<String, String>();
  
        if(byColumnName) {
          for(int col = 0; col < headers.size(); col++) {
        	//添加签名到Body
            if(col < myRow.getLastCellNum()) {   	
				if(headers.get(col).equals("Body"))
				{
            		body=getSheetCellValue(myRow.getCell(col));
            		String sign = DataSignDeal.WriteSign(myRow,sheet,interfaceFactory);
            		if(!sign.equals(""))
            			body=body.replace(findOrderString(body,"sign"), sign);//找出sign后的签名，然后用正确的签名替换
            		myList.put(headers.get(col), body);
				}
            	else myList.put(headers.get(col), getSheetCellValue(myRow.getCell(col))); // myRow.getCell(col).getStringCellValue());
            } else {
              myList.put(headers.get(col), "");
            }
          }
        } else {
          for(int col = 0; col < myRow.getLastCellNum(); col++ ) {
            myList.put(Integer.toString(col), getSheetCellValue(myRow.getCell(col)));
          }
        }
  
        if(byRowKey) {
          if(myList.size() == 2 && key_column == 0) {
            map.put(getSheetCellValue(myRow.getCell(key_column)), new RecordHandler(myList.get(1)));
          } else if(myList.size() == 2 && key_column == 1) {
            map.put(getSheetCellValue(myRow.getCell(key_column)), new RecordHandler(myList.get(0)));
          } else {
            map.put(getSheetCellValue(myRow.getCell(key_column)), new RecordHandler(myList));
          }
        } else {
          map.put(Integer.toString(size), new RecordHandler(myList));
        }
      }
     
    } catch (Exception e) {
      logger.error("Exception while loading data from Excel sheet:"+e.getMessage());
    }
  }
  
  public static List<String> ComparisonRemove(XSSFSheet sheet)
  {
	  XSSFRow myRow = null;
	  HashMap<String, String> myList;	    
	  myRow = sheet.getRow(0);
	  for(Cell cell: myRow) {
		  NoNeedCompared.add(cell.getStringCellValue());
	        }

	return NoNeedCompared;
	  
  }

  public static String findOrderString(String body,String rex) {
	// TODO Auto-generated method stub
	  String sign=null;
	  String commondata[]=body.split(",");
	  	for(int i=0;i<commondata.length;i++)
	  	{
	  			String key=commondata[i].split(":")[0].replace("\\n", "").trim();
	  			if(key.contains(rex)){
	  				sign=commondata[i].split(":")[1].replace("\"","").trim();
	  				break;
	  			}
	  	}
	return sign;
}

/**
   * 取值，Excel单个cell值
   * 
   * @param cell Given excel cell.
   */
  private String getSheetCellValue(XSSFCell cell) {

    String value = "";

    try {
      cell.setCellType(Cell.CELL_TYPE_STRING);
      value = cell.getStringCellValue();
    } catch(NullPointerException npe) {
      return "";
    }

    return value;
  }

  /**
   * Returns entire HashMap containing this class's data.
   * 
   * @return HashMap<String, RecordHandler>, map of ID-Record data.
   */
  public HashMap<String, RecordHandler> get_map(){

    return map;
  }


  /**
   * Gets an entire record.
   * @param record String key value for record to be returned.
   * @return HashMap of key-value pairs representing the specified record.
   */
  public RecordHandler get_record(String record) {

    RecordHandler result = new RecordHandler();

    if(map.containsKey(record)) {
      result = map.get(record);
    }

    return result;
  }

/***
  private static void getStrings(String olddata) {
      Pattern p = Pattern.compile("sign(.*?),");
      Matcher m = p.matcher(str);
      ArrayList<String> strs = new ArrayList<String>();
      while (m.find()) {
          strs.add(m.group(1));            
      } 
      for (String s : strs){
          System.out.println(s);
      }        
  }
 ***/ 
  public static String getSubUtil(String soap){
	  soap="{\"body\":{\"oldAccountNo\":\"8801000000000020007\",\"newAccountNo\":\"8801000000000020015\",\"memberNo\":\"170707000237\",\"placeNo\":\"A0101010\",\"merchantNo\":\"M000001\",\"cardType\":\"2\",\"editorId\":\"232423432\"},\"note\":\"22222222\",\"sign\":\"RYYIUOPPOPOOIIO￥＃＠＃OIOIOO\",\"timestamp\":\"222\"}";
	  Pattern regex = Pattern.compile("(.*)sign\":\"(.*)\",");
		Matcher m = regex.matcher(soap);
		m.find();
		System.out.println(m.group(2));
	/***
      List<String> list = new ArrayList<String>();  
      Pattern pattern = Pattern.compile(rgex);// 匹配的模式  
      Matcher m = pattern.matcher(soap);  
      while (m.find()) {
    	  //System.out.println(m.replace(m.group(), "I love xx"));
          int i = 1;  
          list.add(m.group(i));
          i++;
      }
      **/
      return m.group(2);
  }
}