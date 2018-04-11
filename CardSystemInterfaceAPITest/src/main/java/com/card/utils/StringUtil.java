package com.card.utils;

import java.util.Iterator;
import java.util.List;

public class StringUtil{
	/*****
	 * 移除不需要校验的字段
	 * @param baseline_message
	 * @param NoneedCompare
	 * @return
	 */
	public static String RemoveNoNeedCompared(String baseline_message,List<String> NoneedCompare)
	{
		Iterator<String> it = NoneedCompare.iterator();
		while(it.hasNext())
		{
			baseline_message=baseline_message.replaceAll(DataReader.findOrderString(baseline_message,it.next()), "");
		}
		return baseline_message;
			
	}
	 public static String removeSpaces(String str){
	    	System.out.println(str.replaceAll("[\\s]+", ""));
	    	return str.replaceAll("[\\s]+", "");
	    }
}
