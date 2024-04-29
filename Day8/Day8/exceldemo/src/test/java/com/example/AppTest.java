package com.example;



import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;



/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws IOException
    {
        // //io accessing
        // FileInputStream fis = new FileInputStream("D:\\Testing\\Day8\\exceldemo\\src\\Excelsheet\\Data.xlsx");

        // //Object Poi creation
        // XSSFWorkbook workbook = new XSSFWorkbook(fis);
        
        // //number of sheets
        // int sheets = workbook.getNumberOfSheets();
        // System.out.println(sheets);
        
        // XSSFSheet get_sheet = workbook.getSheet("Data");
        
        // String username = get_sheet.getRow(1).getCell(0).getStringCellValue();
        // System.out.println(username);
        
        // String password = get_sheet.getRow(1).getCell(1).getStringCellValue();
        // System.out.println(password);

        // workbook.close();

    }
}
