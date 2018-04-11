package com.card.sign;

import java.util.Map;
import com.card.utils.AppUtil;
import com.card.utils.GobalConstant;

public class SignFactory {
	  
	/****
	   * 换卡签名
	 * @return 
	   */
	static String ChangCardSign(Map map)
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
	static String memberOpenAccount(Map map)
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
	public static String signFilter(String strSrc) {
        if (null == strSrc) {
            return "";
        }
        //考虑是否还有其他的过滤规则，需要与使用者确认如"null"字符串等。
        return strSrc;
}
}
