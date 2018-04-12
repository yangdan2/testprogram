package com.card.common;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.card.utils.DataWriter;

/*****
 * testcase执行完毕之后需要做的工作,主要执行结果的写操作
 * @author dy08
 *
 */
public class AfterTestDeal extends init{
    private double totalcase = 0;
    private double failedcase = 0;
    private String startTime = "";
    private String endTime = "";
    public  void AfterDeal(XSSFWorkbook wb,String filePath) throws IOException
    {
    	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        endTime = sf.format(new Date());
        DataWriter.writeData(resultSheet, totalcase, failedcase, startTime, endTime);    
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            wb.write(fileOutputStream);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
      /****  
    	FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(filePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        wb.write(fileOutputStream);
        ****/
   //   System.out.println(resultSheet.getSheetName() + "\t\t"+ totalcase + "\t\t" + failedcase + "\t\t" + startTime + "\t\t" + endTime);       
    } 
}
