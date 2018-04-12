package web.com.performanceTest.controllers;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import utils.UnZipUtil;
import web.com.performanceTest.work.dao.GetPerformanceTestDetailsDao;

@Controller
@RequestMapping("/performance")
public class PerformanceTestControllers {

	private GetPerformanceTestDetailsDao detailsDao;

	public GetPerformanceTestDetailsDao getDetailsDao() {
		return detailsDao;
	}

	@Resource(name="GetPerformanceDetailsImpl")
	public void setDetailsDao(GetPerformanceTestDetailsDao detailsDao) {
		this.detailsDao = detailsDao;
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping("/ReportList")
	public ModelAndView getAllTests(HttpServletRequest request){
		 
		String currentPath ="/usr/XSpeed_Test_Performance_Results/performance_test_result/";
		//System.out.println(currentPath);
		List<String[]> performanceTestList =  detailsDao.getAllProgram(currentPath);//System.out.println(performanceTestList.get(0)[0]);
		ModelAndView mav = new ModelAndView();
		mav.addObject("performanceTestList", performanceTestList);
		mav.setViewName("stress");
		return mav;
	}
	@RequestMapping("/onePerformanceTestDetail")
	public void getOneDetail(String path, HttpServletRequest request , HttpServletResponse response){
		//System.out.println("detail:"+path);
		try {
			path = URLDecoder.decode(path, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//System.out.println("Path:"+path);
		List< List<Object> > performance = detailsDao.getDetails(path);
		JSONArray json = JSONArray.fromObject(performance);
		PrintWriter out = null;
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		try {
			out = response.getWriter();
			out.println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)  
	 public String upLoad(@RequestParam("btn_file") MultipartFile[] myfiles,String file_name,  HttpServletRequest request,HttpServletResponse response) throws IOException{  
	        
		 String realPathdes = "/usr/XSpeed_Test_Performance_Results/performance_test_result/"; //解压目标路径
		 String realPath = null ;   
		 String name = null;
		 File targetfile = null;
		 String fileName = null;
		 for(MultipartFile myfile : myfiles){  
	            if(myfile.isEmpty()){  
	                System.out.println("文件未上传");  
	            }else{  
	                name=myfile.getOriginalFilename();
	                realPath ="/usr/XSpeed_Test_Performance_Results/performance_test_result_zip/";  
	                fileName = myfile.getOriginalFilename();
	                targetfile = new File(realPath,fileName);
	                myfile.transferTo(targetfile);        
	            }  
	      }
		 
		 //解压文件
		
			String zipFilePath = realPath+"/"+name;
			String[] sname = name.split(".zip");
			String desExcelName = sname[0];
			
			String desExcelPath = realPathdes+"/"+desExcelName+".xlsx";
			String zipName = desExcelName+".xlsx";
			UnZipUtil uzip = new UnZipUtil();
			
			uzip.unZip(zipFilePath, desExcelPath, zipName);
	   
	          return "redirect:/performance/ReportList.do";
	   }   
	 

	    @RequestMapping(value="/download")  
	    public void download(String path, String time,HttpServletResponse res,HttpServletRequest request) { 
	    	
	    	try {
				path = new String(path.getBytes("ISO-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	String rp = "/usr/XSpeed_Test_Performance_Results/performance_test_result_zip/";
	    	System.out.println("download:"+rp+"/"+path);
	    	OutputStream os = null;
	        try {  
	            os = res.getOutputStream(); 
	            res.reset();  
	            res.setHeader("Content-Disposition", "attachment; filename=result.zip");  
	            res.setContentType("application/zip; charset=utf-8"); 
	            File file = new File(rp+"/"+path);
	            os.write(FileUtils.readFileToByteArray(file));  
	             
	        } catch (IOException e) {
	    
				e.printStackTrace();
			} finally {  
	            if (os != null) {  
	                try {	
	                	os.flush(); 
						os.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
	            }  
	        }  
	    }
}
