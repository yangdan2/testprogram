package com.card.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import com.card.data.ExcelDataDeal.SheetUtils;
import com.card.utils.DataReader;

/*********
 * 主要是excel的初始化
 * @author dy08
 *
 */
public class init extends SuperClass{
    private String template;
    private String userDir = System.getProperty("user.dir");
    String templatePath =  userDir + File.separator + "http_request_template.txt";
    //XSSFWorkbook wb = null;
	public static DataReader myInputData;
	public static DataReader myBaselineData;
    private String startTime = "";
    public XSSFWorkbook init(String filePath)
    {
    	try {
			wb = new XSSFWorkbook(new FileInputStream(filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return wb;
    }
    public void BeforeSetupDataDeal(String path,XSSFWorkbook wb)
    {
    	SheetUtils.removeSheetByName(wb, "Output");
        SheetUtils.removeSheetByName(wb, "Comparison");
        SheetUtils.removeSheetByName(wb, "Result");    
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        startTime = sf.format(new Date());
        super.setBaselineSheet(wb.getSheet("Baseline"));
        super.setInputSheet(wb.getSheet("Input"));
        super.setOutputSheet(wb.createSheet("Output"));
        super.setComparsionSheet(wb.createSheet("Comparison"));   
        super.setResultSheet(wb.createSheet("Result"));
        super.setComparisonRemove(wb.getSheet("ComparisonRemove"));
    }
    public String template()
    {
        try {    	   
            FileInputStream fis = new FileInputStream(new File(templatePath));
     	   	template = IOUtils.toString(fis, Charset.defaultCharset());
        } catch (Exception e) {
         Assert.fail("Problem fetching data from input file:" + e.getMessage());
        }
		return template;
        
    }
    public DataReader getMyInputData() {
		return myInputData;
	}
    public void setMyInputData(DataReader myInputData) {
		this.myInputData = myInputData;
	}
    public DataReader getMyBaselineData() {
		return myBaselineData;
	}
    public void setMyBaselineData(DataReader myBaselineData) {
		this.myBaselineData = myBaselineData;
	}
    
}
