package web.com.performanceTest.work.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import utils.Formats;
import web.com.performanceTest.work.ReadExcel;
import web.com.performanceTest.work.dao.GetPerformanceTestDetailsDao;

@Component("GetPerformanceDetailsImpl")
public class GetPerformanceTestDetailsImpl implements GetPerformanceTestDetailsDao{

	@Override
	public List<String[]> getAllProgram(String path) {
		List<String[]> list = new ArrayList<String[]>();
		File[] files = getExcelName(path);
		
		if(files==null)
		return null;	
		
		for(File file : files){
			String filename = file.getName();
			String filepath = file.getAbsolutePath();
			String[] sName = filepath.split("\\\\");
			String lastName = sName[sName.length-1];
			String name = (lastName.split(".xlsx"))[0];
			String[] strs = filename.split(".xlsx");
			String newStr = strs[0];
			String[] content = newStr.split("_");
			content[0] = Formats.formatStr(content[0]);
			
			String[] returnStr = new String[7];
			returnStr[0] = content[0];
			returnStr[1] = content[1];
			returnStr[2] = content[2];
			returnStr[3] = content[3];
			returnStr[4] = content[4];
			returnStr[5] = filepath;
			returnStr[6] = name+".zip";
			list.add(returnStr);
		}
		
		// ±º‰µπ–Ú≈≈¡–
		
		
		return swap(list);
	}

	@Override
	public List< List<Object> > getDetails(String path) {
		ReadExcel readExcel = new ReadExcel();
		return readExcel.read(path);
	}

	@Override
	public File[] getExcelName(String path) {
		
		File file = new File(path);
		File[] files = file.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if(name.endsWith(".xlsx"))
				return true;	
					
				return false;
			}
		});
		
		if(files.length==0)
		files = null;	
			
		return files;
	}

	public List<String[]> swap(List<String[]> _list){
		
		Collections.sort(_list, new Comparator<String[]>() {

			@Override
			public int compare(String[] o1, String[] o2) {
				long l1 = 0;
				long l2 = 0;
				try {
					l1 = Formats.parseDateBy_(o1[0]).getTime();
					l2 = Formats.parseDateBy_(o2[0]).getTime();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(l1 > l2){
					return -1;
					
				}else if(l1 < l2){
					return 1;
				}else{
				return 0;
				}
			}
	
		});
		
		return _list;
	}
}
