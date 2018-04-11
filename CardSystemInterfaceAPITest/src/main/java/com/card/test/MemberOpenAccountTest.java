package com.card.test;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.card.common.AfterTestDeal;
import com.card.common.BeforTestDeal;
import com.card.common.SuperClass;
import com.card.common.TestAction;
import com.card.common.init;
import com.card.utils.GobalConstant;
import com.common.initialdata.AntExecSql;


public class MemberOpenAccountTest extends SuperClass implements ITest{
	private BeforTestDeal beforetest;
	private init init;
	private TestAction testaction;
	private AfterTestDeal after;
	String filePath;
    public String getTestName() {
        return "CardApi Test";
    }
    
    @BeforeTest
    @Parameters("workBook001")
    public void setup(String path) throws IOException {
    	AntExecSql sqlinit=new AntExecSql();	
    	sqlinit.SqlExe(GobalConstant.initpath);
    	testaction=new TestAction();
    	filePath=path;
    	init=new init();
    	wb=init.init(filePath);
    	super.setWb(wb);
    	super.setTemplate(init.template());
    	init.BeforeSetupDataDeal(path, wb);
    }
    @DataProvider(name = "WorkBookData")
    protected Iterator<Object[]> testProvider(ITestContext context)
    {
    	beforetest=new BeforTestDeal();
    	Iterator<Object[]> test_IDs = beforetest.testProvider(context,init.getInputSheet(),init.getBaselineSheet());
		return test_IDs ;	
    }

    @Test(dataProvider = "WorkBookData", description = "ReqGenTest")
    public void api_test(String ID, String test_case) {
    	System.out.println("hello");
    	testaction.TestDeal(ID, test_case, super.getTemplate());
    }

	@AfterTest
    public void teardown() throws IOException {
		after=new AfterTestDeal();
		try {
			after.AfterDeal(wb,filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AntExecSql sqlrollback=new AntExecSql();
        sqlrollback.SqlExe(GobalConstant.rollbackpath);
    }
}