package web.com.junit.getResultList;

import java.io.File;
import java.io.FilenameFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component("GetDescriptionParameters")
public class GetParameters {
public List<List<String>> getParameters(File root){
	
	if(root.listFiles().length==0){
		return null;
	}
	
	List<List<String>> all = new ArrayList<List<String>>();
	
	File[] results = root.listFiles();
   
	for(File file : results){
		
		File[] oneResult = file.listFiles();
		File description = null;
		File result = null;
		for(File oneResultFile: oneResult){
			if(oneResultFile.getName().equalsIgnoreCase("description.txt")){
				description = oneResultFile;
			}
			if(oneResultFile.isDirectory()&&oneResultFile.getName().equalsIgnoreCase("report")){
				result = oneResultFile;
			}
		}
		//System.out.println(description.getAbsolutePath());
		//System.out.println(result.getAbsolutePath());
		all.add(getOneDescription(description,result));
	}
	
	return seqence(all);
}

public List<String> getOneDescription(File description,File result){
	DescriptionFactory df = new DescriptionFactory();
    DescriptionFromTxt rd = (DescriptionFromTxt) df.getDescription(description , result);
    rd.beforeRead();
    rd.read();
    rd.afterRead();
    return rd.getParameters();
}

public List<List<String>> seqence(List<List<String>> list){
	/*for(int i =0 ; i<list.size() ; i++){
		List<String> row = list.get(i);
		String date = row.get(0);
		long time = getSecond(date);
	}*/
	Collections.sort(list, new Comparator<List<String>>() {

		@Override
		public int compare(List<String> row1, List<String> row2) {
			long time1 = getSecond(row1.get(0));
			long time2 = getSecond(row2.get(0));
			if(time1>time2){
				return -1;
			}else if(time1==time2){
			return 0;
			}else{
				return 1;
			}
		}
		
	});
	
	return list;
}

public final long getSecond(String date){
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
	long time = -1L;
	try {
		time = sdf.parse(date).getTime();
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return time;
}
}
