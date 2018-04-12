package com.card.data.ExcelDataDeal;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import com.card.sign.InterfaceSign;
import com.card.utils.AppUtil;
import com.card.utils.GobalConstant;

/*********
 * 处理数字签名
 * @author dy08
 *
 */
public class DataSignDeal{
	private static Integer size = 0;
	private static InterfaceSign interfacesign;
	private static List<String> headers = new ArrayList<String>();
	public static String WriteSign(XSSFRow cuurentmyRow, XSSFSheet inputSheet,int interfaceFactory)
	{
		HashMap<String, String> myList;
		XSSFRow myRow = null;
		String Signbody = null;
		//inputSheet = wb.getSheet("Input");
		//XSSFSheet sheet = wb.getSheetAt(0);
		myRow = inputSheet.getRow(0);
		size=1;
		for(Cell cell: myRow) {
			headers.add(cell.getStringCellValue());
		}
		//for(; (myRow = inputSheet.getRow(size)) != null; size++ ) {
			myList = new HashMap<String, String>();
			for(int col = 0; col < headers.size(); col++ ) {
				if(col < myRow.getLastCellNum()) {
					if(headers.get(col).equals("Body"))
						//body里需要修改签名
					{
						String body=getSheetCellValue(cuurentmyRow.getCell(col));
						//System.out.println(body);
						 Signbody = Cardsign(body,interfaceFactory);
						 break;
						//myList.put(headers.get(col), Signbody);
					}
					else myList.put(headers.get(col), getSheetCellValue(cuurentmyRow.getCell(col))); // myRow.getCell(col).getStringCellValue());
		            } else {
		              myList.put(headers.get(col), "");
		            }
			//}
		}
		return Signbody;
	}
	/****
	 * 卡系统签名处理，参数放在map里,返回签名
	 * @param body
	 * @return
	 * @throws JSONException 
	 */
	  private static String Cardsign(String body,int interfaceFactory){
		  Map map=new HashMap();
		  interfacesign=new InterfaceSign();
		// TODO Auto-generated method stub
		  
		  //公共参数
		  	String commondata[]=body.replace("}", "").split(",");
		  	String bodydata=StringUtils.substringBetween(body,"body","}").replace(": {", "").replace("\"","");
		  	if(bodydata.trim().equals("")){return "";}//处理body为空的情况
		  	if(commondata.length==1){return "";}//处理body为空的情况
		  	else
		  	{
			  	for(int i=0;i<commondata.length;i++)
			  	{
			  			String key=commondata[i].split(":")[0].replace("\\n", "").trim();
			  			if(key.contains("note")){
			  				String value=commondata[i].split(":")[1].replace("\"","").trim();
			  				map.put("note", value);}
			  			if(key.contains("timestamp")){
			  				String value=commondata[i].split(":")[1].replace("\"","").trim();
			  				map.put("timestamp", value);}
			  	}
		  	}
		  	
		    //业务参数
		  	//String bodydata=StringUtils.substringBetween(body,"body","}").replace(": {", "").replace("\"","");
		  	String a[]=bodydata.split(",");
		  	for(int i=0;i<a.length;i++)
		  	{
		  		String key=a[i].split(":")[0].replace("\\n", "").trim();
		  		String value=a[i].split(":")[1].trim();
		  		map.put(key, value);
		  	}
		  	//String note[]=body.replaceAll("\\n ", "").trim().split(":");
		  	String sign=interfacesign.sign(interfaceFactory, map);
		return sign;
	}
	  /****
	   * 换卡签名
	 * @return 
	   */
	private static String ChangCardSign(Map map)
	{
		//Iterator<?> entries = map.entrySet().iterator();
		String oldAccountNo=null;
		String newAccountNo=null;
		String memberNo=null;
		String placeNo=null;
		String merchantNo=null;
		String cardType=null;
		String editorId=null;
		String note=null;
		String timestamp=null;
		
		for (Object s : map.keySet()) {
			if(s.toString().contains("oldAccountNo"))
			{
				oldAccountNo=(String) map.get(s);
			}
			if(s.toString().contains("newAccountNo"))
			{
				newAccountNo=(String) map.get(s);
			}
			if(s.toString().contains("memberNo"))
			{
				memberNo=(String) map.get(s);
			}
			if(s.toString().contains("placeNo"))
			{
				placeNo=(String) map.get(s);
			}
			if(s.toString().contains("merchantNo"))
			{
				merchantNo=(String) map.get(s);
			}
			if(s.toString().contains("cardType"))
			{
				cardType=(String) map.get(s);
			}
			if(s.toString().contains("editorId"))
			{
				editorId=(String) map.get(s);
			}
			if(s.toString().contains("note"))
			{
				note=(String) map.get(s);
			}
			if(s.toString().contains("timestamp"))
			{
				timestamp=(String) map.get(s);
			}
				//System.out.println("key = " + s + "; value = " + map.get(s));
			}

		StringBuffer sbSign = new StringBuffer(GobalConstant.SIGN_PRE); //public static final String SIGN_PRE = "gznb.member"; 
        sbSign.append(note);
        System.out.println(String.valueOf(System.currentTimeMillis()));
       // sbSign.append(String.valueOf(System.currentTimeMillis()));
        sbSign.append(timestamp);
        //Body业务参数

        sbSign.append(signFilter(oldAccountNo));
        sbSign.append(signFilter(newAccountNo));
        sbSign.append(signFilter(merchantNo));
        sbSign.append(signFilter(placeNo));
        sbSign.append(signFilter(cardType));
        sbSign.append(signFilter(editorId)); 
        sbSign.append(GobalConstant.SIGN_LAST); //public static final String SIGN_LAST = "gznb@com";
        
        //System.out.println(sbSign.toString());
        
        String strParamSign = AppUtil.md5Hex(sbSign.toString());
        System.out.println(strParamSign);
 		return strParamSign;
        
	}
	
	  /****
	   * 会员开通签名
	 * @return 
	   */
	private static String memberOpenAccount(Map map)
	{
		String note=null;
		String timestamp=null;
		String memberName=null;
		String certNo=null;
		String mobileNo=null;
		String email=null;
		String address=null;
		String placeNo=null;
		String merchantNo=null;	
		String accountNo=null;
		String editorId=null;
		String cardType=null;
		String remark = null;
		
		for (Object s : map.keySet()) {
			if(s.toString().contains("accountNo"))
			{
				accountNo=(String) map.get(s);
			}
			if(s.toString().contains("memberName"))
			{
				memberName=(String) map.get(s);
			}
			if(s.toString().contains("certNo"))
			{
				certNo=(String) map.get(s);
			}
			if(s.toString().contains("remark"))
			{
				remark=(String) map.get(s);
			}
			if(s.toString().contains("email"))
			{
				email=(String) map.get(s);
			}
			if(s.toString().contains("address"))
			{
				address=(String) map.get(s);
			}
			if(s.toString().contains("merchantNo"))
			{
				merchantNo=(String) map.get(s);
			}
			if(s.toString().contains("mobileNo"))
			{
				mobileNo=(String) map.get(s);
			}
			if(s.toString().contains("placeNo"))
			{
				placeNo=(String) map.get(s);
			}
			if(s.toString().contains("merchantNo"))
			{
				merchantNo=(String) map.get(s);
			}
			if(s.toString().contains("cardType"))
			{
				cardType=(String) map.get(s);
			}
			if(s.toString().contains("editorId"))
			{
				editorId=(String) map.get(s);
			}
			if(s.toString().contains("note"))
			{
				note=(String) map.get(s);
			}
			if(s.toString().contains("timestamp"))
			{
				timestamp=(String) map.get(s);
			}
				//System.out.println("key = " + s + "; value = " + map.get(s));
			}
		
		

		StringBuffer sbSign = new StringBuffer(GobalConstant.SIGN_PRE); //public static final String SIGN_PRE = "gznb.member"; 
      sbSign.append(note);
      System.out.println(String.valueOf(System.currentTimeMillis()));
     // sbSign.append(String.valueOf(System.currentTimeMillis()));
      sbSign.append(timestamp);
      //Body业务参数
      sbSign.append(signFilter(memberName));
      sbSign.append(signFilter(certNo));
      sbSign.append(signFilter(mobileNo));
      sbSign.append(signFilter(email));
      sbSign.append(signFilter(address));
      sbSign.append(signFilter(placeNo));
      sbSign.append(signFilter(merchantNo));
      sbSign.append(signFilter(accountNo));
      sbSign.append(signFilter(editorId));
      sbSign.append(signFilter(cardType));
      sbSign.append(signFilter(remark));
      sbSign.append(GobalConstant.SIGN_LAST); //public static final String SIGN_LAST = "gznb@com";
      
      System.out.println(sbSign.toString());
      
      String strParamSign = AppUtil.md5Hex(sbSign.toString());
      System.out.println(strParamSign);
		return strParamSign;
      
	}
	
	private static String getSheetCellValue(XSSFCell cell) {

		    String value = "";

		    try {
		      cell.setCellType(Cell.CELL_TYPE_STRING);
		      value = cell.getStringCellValue();
		    } catch(NullPointerException npe) {
		      return "";
		    }
		    return value;
		  }
	public static String signFilter(String strSrc) {
        if (null == strSrc) {
            return "";
        }
        //考虑是否还有其他的过滤规则，需要与使用者确认如"null"字符串等。
        return strSrc;
}
}
