package web.com.junit.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import web.com.junit.getResultList.GetParameters;


@Controller
@RequestMapping("/JunitReport")
public class JunitReportController {
	private GetParameters gp;

	public GetParameters getGp() {
		return gp;
	}
    @Resource(name="GetDescriptionParameters")
	public void setGp(GetParameters gp) {
		this.gp = gp;
	}
     
	@SuppressWarnings("deprecation")
	@RequestMapping("/ReportList")
    public ModelAndView getReportList(HttpServletRequest request){
    	//System.out.println("haha!!!");
    	ModelAndView mav = new ModelAndView();
    	String uploadFolder = request.getRealPath("\\reports\\");
    	//System.out.println(uploadFolder);
    	File rootFile = new File(uploadFolder);    
    	List<List<String>> reports = this.gp.getParameters(rootFile);
    	mav.addObject("reports", reports);
    	mav.setViewName("auto");
    	return mav;
    }
    
    @RequestMapping("getReport")
    public ModelAndView getReport(String date, String version , String environment , String result, String src){
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("date",date);
    	mav.addObject("version",version);
    	mav.addObject("environment",environment);
    	mav.addObject("result",result);
    	mav.addObject("src",src);
    	mav.setViewName("report");
    	return mav;
    }
    
   
}
