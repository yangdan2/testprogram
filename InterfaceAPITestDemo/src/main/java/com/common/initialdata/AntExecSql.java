package com.common.initialdata;

import java.io.*;
import java.util.List;

import org.apache.tools.ant.*;   
import org.apache.tools.ant.taskdefs.*;   
import org.apache.tools.ant.types.*;  

  
public class AntExecSql {
	private static final String Path = "D:\\workspace\\CardSystemInterfaceAPITest\\sqlReadmebk.xls";
	private static final String uri = "ASDF";
	//private static final String Path = "D:\\workspace\\CardSystemInterfaceAPITest\\initdata.xls";;
/***
	public static void main(String[] args) throws IOException
	{	
		ExecSql Exc=new ExecSql();
		Exc.ExecOpr(Path,uri);
	}
	***/

	public void SqlExe(String path) throws IOException
	{	
		ExecSql Exc=new ExecSql();
		Exc.ExecOpr(Path,uri);
	}
}

//public static void main(String[] args) {   
//      SQLExec sqlExec = new SQLExec();
//      sqlExec.setDriver("com.mysql.jdbc.Driver");   
//      sqlExec.setUrl("jdbc:mysql://10.66.30.142:3306/db_member?useUnicode=true&characterEncoding=UTF-8");  
//      sqlExec.setUserid("root");
//      sqlExec.setPassword("123456");
//      sqlExec.setSrc(new File("D:\\AAA\\INT\\SQL\\rollback\\AutoTest_Rollback.sql")); 
//      sqlExec.setOnerror((SQLExec.OnError)(EnumeratedAttribute.getInstance(SQLExec.OnError.class, "abort")));  
//      sqlExec.setPrint(true);
//      sqlExec.setOutput(new File("D:\\sql.out"));   
//      sqlExec.setProject(new Project());  
//      sqlExec.execute();
//   }   
//}  