package web.com.junit.getResultList;

import java.io.File;

import org.springframework.stereotype.Component;


public class DescriptionFactory {
public ReadDescription getDescription(File description , File result){
	if(description.getName().endsWith(".txt"))
	{
		return new DescriptionFromTxt(description , result);
	}
	return new DescriptionFromTxt(description , result);
	
}
}
