package com.card.data.ExcelDataDeal;
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.CellStyle;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;  
public class POIExcelUtil {
	 /** 
     *  
     * @param row 
     * @param column 
     * @param sheet 
     * @param content 
     * @param cellTypes 
     * @param cellStyle 
     * @throws Exception 
     */  
    public static void writeDataToExcel(int row, int column, Sheet sheet,  
            Object content, int cellType, CellStyle cellStyle) throws Exception {  
        Row r1 = sheet.getRow(row);  
        Cell c1 = r1.getCell(column);  
        if (null != cellStyle) {  
            c1.setCellStyle(cellStyle);  
        }  
        switch (cellType) {  
        case Cell.CELL_TYPE_NUMERIC:  
            c1.setCellValue((Double) content);  
            break;  
        case Cell.CELL_TYPE_STRING:  
            c1.setCellValue((String) content);  
            break;  
        case Cell.CELL_TYPE_FORMULA:  
            c1.setCellFormula((String) content);  
            break;  
        default:  
            c1.setCellValue((String) content);//默认的先暂时全用这个  
            System.out.println("未匹配到东西!");  
            break;  
        }  
    }  
}
