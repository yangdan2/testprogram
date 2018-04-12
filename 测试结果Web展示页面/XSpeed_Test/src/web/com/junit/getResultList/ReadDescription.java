package web.com.junit.getResultList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public abstract class ReadDescription{

	protected File description;
	protected File result;
	protected FileReader fr = null;
	protected BufferedReader br = null; 
	
	public abstract void read();
	
	public abstract void setParameters(String data , List<String> parameters);
	
	public void beforeRead(){
		try {
			fr = new FileReader(description);
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void afterRead(){
	 try {
		fr.close();
		br.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	}
	
	
}
