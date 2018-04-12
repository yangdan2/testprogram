package com.card.sign;

import java.util.Map;
import com.card.utils.AppUtil;
import com.card.utils.GobalConstant;

public class SignFactory {
	  /****
	   * 接口签名01
	 * @return 
	   */
	static String memberOpenAccount(Map map)
	{
		String username=null;
		String timestamp=null;
		String age = null;
		String sex = null;
		
		for (Object s : map.keySet()) {
			if(s.toString().contains("username"))
			{
				accountNo=(String) map.get(s);
			}
			
			if(s.toString().contains("age"))
			{
				note=(String) map.get(s);
			}
			if(s.toString().contains("sex"))
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
    sbSign.append(signFilter(username));
    sbSign.append(signFilter(age));
    sbSign.append(signFilter(sex));
    sbSign.append(GobalConstant.SIGN_LAST); 
    
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
