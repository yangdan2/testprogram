package com.card.sign;
import java.util.Map;

import com.card.utils.InterfaceTypeFacctory;

public class InterfaceSign {
	private SignFactory signfactory;
	//InterfaceTypeFacctory IntType =new InterfaceTypeFacctory();
	/****
	 * 卡系统换卡接口
	 */
	private static final int ChangeCard = 1;
	/****
	 * 卡系统会员开通接口
	 */
	private static final int MemberOpenAccount = 2;
	String sign=null;

	public String sign(int IntType, Map map)
	{
		 switch(IntType){
         case ChangeCard: 
         {
        	 sign=signfactory.ChangCardSign(map);
        	 break;
         }
         case MemberOpenAccount: 
         {
        	 sign=signfactory.memberOpenAccount(map);
        	 break;
         }  
		 }
		return sign;
	}

}
