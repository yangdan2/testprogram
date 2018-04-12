package web.com.performanceTest.work.dao;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
 

@Component("GetPerformanceTestDetails")
public interface GetPerformanceTestDetailsDao {
	 public File[] getExcelName(String path);
	 public List<String[]> getAllProgram(String path);
     public List< List<Object> > getDetails(String path);
}
