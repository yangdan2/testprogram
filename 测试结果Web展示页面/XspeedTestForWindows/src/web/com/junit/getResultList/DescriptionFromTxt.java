package web.com.junit.getResultList;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class DescriptionFromTxt extends ReadDescription {
	
	private List<String> parameters;

	public DescriptionFromTxt(File description, File result){
		super.description = description;
		super.result = result;
	}
	
	@Override
	public void read() {
		parameters = new ArrayList<String>();
		String data = null;
		
		try {
			while((data = br.readLine())!=null){
				setParameters(data, parameters);
			}
			if(result!=null){
			String str = result.getAbsolutePath()+"\\index.html";
			//System.out.println("Str:"+str);
			String[] strs = str.split("\\\\");
			int index = -1;
			StringBuffer sb = new StringBuffer();
			for(int i=0 ; i <strs.length ; i++){
				if(strs[i].equalsIgnoreCase("reports")){
					index = i;
				}
				if(i>=index && index!=-1){
					if(i==strs.length-1){
					sb.append(strs[i]);
					}else{
					sb.append(strs[i]+"\\");	
					}
				}
			}
			String path = sb.toString();
			//System.out.println(path);
			//path = path.replaceAll("\\\\", "/");
			
			parameters.add(path);
			
			parameters.add(readXML(path));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void setParameters(String data , List<String> parameters) {
		String[] datas = data.split("=");
		if(datas[0].equalsIgnoreCase("result")){
			
			if(datas[1].equalsIgnoreCase("true")){
				
				parameters.add("failed");
			}else{
				
				parameters.add("pass");
			}
		}else if(datas[0].equalsIgnoreCase("date")){
			String str = datas[1];
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
			Date d = null;
			try {
			 d = sdf.parse(str);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd HH:mm");
			String fdate = sdf2.format(d);
			
			parameters.add(fdate);
		}else{
		
			parameters.add(datas[1]);
		}
		
	}

	public List<String> getParameters(){
		return parameters;
	}
	
    public String readXML(String path){
    	
    	String xmlPath = path.replaceAll("index.html", "TESTS-TestSuites.xml");
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = dbf.newDocumentBuilder();
			File f = new File("E:\\apache-tomcat-7.0.11\\webapps\\XspeedTestForWindows\\"+xmlPath);
			Document doc = builder.parse(f); 
			Element root = doc.getDocumentElement();
			NodeList tests =  root.getElementsByTagName("testsuite");
			
				Element node = (Element) tests.item(0);
				String errors = node.getAttribute("errors");
				String fails = node.getAttribute("failures");
				String runs = node.getAttribute("tests");
		return "runs:"+runs+" failures:"+fails+" errors:"+errors;		
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
}
