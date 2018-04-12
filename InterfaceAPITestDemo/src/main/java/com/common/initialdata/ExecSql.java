package com.common.initialdata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.apache.tools.ant.types.EnumeratedAttribute;

public class ExecSql {
	
	private static final String dbDriver= "com.mysql.jdbc.Driver";
	public void ExecOpr(String excelPath,String uri) throws IOException {
		ReadExcel xlsMain = new ReadExcel();
		SQLExec sqlExec = new SQLExec();
		List<DBConnectionUtil> dbCon = new ArrayList<DBConnectionUtil>();
		String fileName = null ;
		xlsMain.setExcelPath(excelPath);
		List<DBConnectionUtil> listCon = xlsMain.readXls(uri);
		for (int i = 0; i < listCon.size(); i++) {
			List<String> fileNames = GetFoldFileNames.getFileName(listCon.get(i).getDir());
		    for (int j = 0; j < fileNames.size(); j++) {
		    	fileName=fileNames.get(j);
				//dbCon=listCon.get(i);
				sqlExec.setDriver(dbDriver); 
				sqlExec.setUrl("jdbc:mysql://"+listCon.get(i).getIp()+":"+listCon.get(i).getPort()+ "/"+listCon.get(i).getDb()+"?useUnicode=true&characterEncoding=UTF-8");
				sqlExec.setUserid(listCon.get(i).getUid());  
				///System.out.println("密码："+listCon.get(i).getPwd());
			    sqlExec.setPassword(listCon.get(i).getPwd());
			    sqlExec.setSrc(new File(listCon.get(i).getDir()+fileName));  
			    sqlExec.setOnerror((SQLExec.OnError)(EnumeratedAttribute.getInstance(SQLExec.OnError.class, "abort")));  
			    sqlExec.setPrint(true);    
			    sqlExec.setProject(new Project());  
			    sqlExec.execute();  
		    }		    
		}
//		    sqlExec.setOnerror((SQLExec.OnError)(EnumeratedAttribute.getInstance(SQLExec.OnError.class, "abort")));  
//		    sqlExec.setPrint(true);    
//		    sqlExec.setProject(new Project());  
//		    sqlExec.execute();   			
		
	}

}
