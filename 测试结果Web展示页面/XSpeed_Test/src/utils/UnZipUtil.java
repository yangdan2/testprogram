package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class UnZipUtil {
	public void unZip(String zipFilePath,String desExcelPath,String zipName){
		try {
			 org.apache.tools.zip.ZipFile zipFile = new  org.apache.tools.zip.ZipFile(zipFilePath);
			 org.apache.tools.zip.ZipEntry zipEntry = zipFile.getEntry(zipName);
			if(zipEntry == null){
				System.out.println("is null");
			}else{
				File file = new File(desExcelPath);
				InputStream input = zipFile.getInputStream(zipEntry);
				FileOutputStream out = new FileOutputStream(file);
				byte[] by = new byte[1024];
				int c;
				while ((c = input.read(by)) != -1) {
					out.write(by, 0, c);
				}
				out.close();
				input.close();
			}
			
			
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
