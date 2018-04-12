package web.com.performanceTest.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

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
		 
		String currentPath = "E:\\test\\\\performance_test_result\\";
		//System.out.println(currentPath);
		List<String[]> performanceTestList =  detailsDao.getAllProgram(currentPath);//System.out.println(performanceTestList.get(0)[0]);
		ModelAndView mav = new ModelAndView();
		mav.addObject("performanceTestList", performanceTestList);
		mav.setViewName("stress");
		return mav;
	}
	@RequestMapping("/onePerformanceTestDetail")
	public void getOneDetail(String path, HttpServletRequest request , HttpServletResponse response){
		try {
			path = URLDecoder.decode(path, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
	        
		 //如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解  
	        //如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解  
	        //并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是myfiles，否则参数里的myfiles无法获取到所有上传的文件  
		 //String realPathdes = request.getRealPath("\\performance_test_result"); //解压目标路径
		String realPathdes = "E:\\test\\performance_test_result";
		String realPath = null ; //源路径  
		 String name = null;
		 File targetfile = null;
		 String fileName = null;
		 for(MultipartFile myfile : myfiles){  
	            if(myfile.isEmpty()){  
	                System.out.println("文件未上传");  
	            }else{  
	                name=myfile.getOriginalFilename();
	                //realPath = request.getRealPath("\\performance_test_result_zip"); 
	                realPath = "E:\\test\\performance_test_result_zip";
	                fileName = myfile.getOriginalFilename();
	                targetfile = new File(realPath,fileName);	                
	                myfile.transferTo(targetfile);        
	            }  
	      }
		 //解压文件
		String zipFilePath = realPath+"\\"+name;
		String[] sname = name.split(".zip");
		String desExcelName = sname[0];
		
		String desExcelPath = realPathdes+"\\"+desExcelName+".xlsx";
		String zipName = desExcelName+".xlsx";
		UnZipUtil uzip = new UnZipUtil();
		
		uzip.unZip(zipFilePath, desExcelPath, zipName);
		
	          return "redirect:/performance/ReportList.do";
	   }   
	 

	    @RequestMapping(value="/download")  
	    public void download(String path, String time,HttpServletResponse res,HttpServletRequest request) { 
	    	
	    	try {
				path = URLDecoder.decode(path, "utf-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	//System.out.println("PATH:"+path);
	    	OutputStream os = null;
	        try {  
	            os = res.getOutputStream(); 
	            res.reset();  
	            res.setHeader("Content-Disposition", "attachment; filename=test.zip");  
	            res.setContentType("application/zip; charset=utf-8"); 
	            //File file = new File(request.getRealPath("performance_test_result_zip")+"/"+path);
	            File file = new File("E:\\test\\performance_test_result_zip\\"+path);
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
