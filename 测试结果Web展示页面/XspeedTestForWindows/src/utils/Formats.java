package utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Formats {

	public static Date parseDate(String str) throws Exception{
	if(str.length()!=20){
		throw new Exception("测试时间格式有误！");
	}
		
	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
	 return sdf.parse(str);
	}
	
	public static Date parseDateBy_(String str) throws Exception{
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 return sdf.parse(str);
		}
	
	public static String format(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
		return sdf.format(date);
	}
	
	public static String formatStr(String str){
		//yyyy-MM-dd HH:mm:ss
		if(str.length()!=14){
			try {
				throw new Exception("excel文件名时间格式有误！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		char[] array = str.toCharArray();
		
		StringBuilder s = new StringBuilder();
		for(int i=0 ; i<4 ;i++){
			s.append(array[i]);
		}
		s.append("-");
		for(int i=4 ; i<6 ;i++){
			s.append(array[i]);
		}
		s.append("-");
		for(int i=6 ; i<8 ;i++){
			s.append(array[i]);
		}
		s.append(" ");
		for(int i=8 ; i<10 ;i++){
			s.append(array[i]);
		}
		s.append(":");
		for(int i=10 ; i<12 ;i++){
			s.append(array[i]);
		}
		s.append(":");
		for(int i=12 ; i<14 ;i++){
			s.append(array[i]);
		}
		
		return s.toString();
	}
}
