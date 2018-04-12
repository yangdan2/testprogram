package com.card.common;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.card.utils.DataReader;
/***
 * 建立父类，为初始化后的全局变量赋值
 * @author dy08
 *
 */
public class SuperClass {
		private String template;
		protected XSSFWorkbook wb = null;
		XSSFSheet inputSheet = null;
	    XSSFSheet baselineSheet = null;
		public static XSSFSheet ComparisonRemove = null;
		public static XSSFSheet outputSheet = null;
		public static XSSFSheet comparsionSheet = null;
		public static XSSFSheet resultSheet = null;
	    public XSSFWorkbook getWb() {
			return wb;
		}

		public void setWb(XSSFWorkbook wb) {
			this.wb = wb;
		}
		
		public XSSFSheet getInputSheet() {
			//System.out.println(inputSheet);
			return inputSheet;
		}
		public void setInputSheet(XSSFSheet inputSheet) {
			this.inputSheet = inputSheet;
		}
		public XSSFSheet getBaselineSheet() {
			return baselineSheet;
		}
		public void setBaselineSheet(XSSFSheet baselineSheet) {
			this.baselineSheet = baselineSheet;
		}
		public XSSFSheet getOutputSheet() {
			return outputSheet;
		}
		public void setOutputSheet(XSSFSheet outputSheet) {
			this.outputSheet = outputSheet;
		}
		public XSSFSheet getComparsionSheet() {
			return comparsionSheet;
		}
		public void setComparsionSheet(XSSFSheet comparsionSheet) {
			this.comparsionSheet = comparsionSheet;
		}
		public XSSFSheet getResultSheet() {
			return resultSheet;
		}
		public void setResultSheet(XSSFSheet resultSheet) {
			this.resultSheet = resultSheet;
		}
		public String getTemplate() {
			return template;
		}

		public void setTemplate(String template) {
			this.template = template;
		}
		public XSSFSheet getComparisonRemove() {
			System.out.println(ComparisonRemove);
			return ComparisonRemove;
		}

		public void setComparisonRemove(XSSFSheet comparisonRemove) {
			ComparisonRemove = comparisonRemove;
			System.out.println(ComparisonRemove);
		}
}
