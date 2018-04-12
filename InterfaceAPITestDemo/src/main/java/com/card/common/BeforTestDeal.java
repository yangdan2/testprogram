package com.card.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.testng.ITestContext;
import com.card.utils.DataReader;
import com.card.utils.InterfaceTypeFacctory;
import com.card.utils.RecordHandler;
/*****
 * 测试执行之前进行的操作，数据准备
 * @author dy08
 *
 */
public class BeforTestDeal extends init{
	private InterfaceTypeFacctory interfaceFactory;
	    private double totalcase = 0;
	    public Iterator<Object[]> testProvider(ITestContext context,XSSFSheet inputSheet,XSSFSheet baselineSheet)
	    {
	    	List<Object[]> test_IDs = new ArrayList<Object[]>();

            DataReader myInputData = new DataReader(inputSheet, true, true, 0,interfaceFactory.MemberOpenAccount);
            super.setMyInputData(myInputData);
            // 根据键值排序，使testcase顺序执行
            Map<String, RecordHandler> sortmap = new TreeMap<String,RecordHandler>(new Comparator<String>(){
				public int compare(String key1, String key2) {
					return key1.compareTo(key2);
				}
            });
           
            sortmap.putAll(myInputData.get_map());
            for (Map.Entry<String, RecordHandler> entry : sortmap.entrySet()) {
                String test_ID = entry.getKey();
                String test_case = entry.getValue().get("TestCase");
                if (!test_ID.equals("") && !test_case.equals("")) {
                    test_IDs.add(new Object[] { test_ID, test_case }); //存储id，casename便于后面用
                }
                totalcase++;
            }
            DataReader myBaselineData = new DataReader(baselineSheet, true, true, 0,interfaceFactory.MemberOpenAccount);
            super.setMyBaselineData(myBaselineData);
           return test_IDs.iterator();	
	    }
}
