package web.com.performanceTest.work;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadExcelTest {
	ReadExcel r = new ReadExcel();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		r.read("D:\\360安全浏览器下载\\20140818 160000_172.21.200.162_交易所系统VMATCH_LINUX版.xls");
	}

}
