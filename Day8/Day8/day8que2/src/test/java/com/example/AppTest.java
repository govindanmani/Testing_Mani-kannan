package com.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AppTest 
{
    public WebDriver driver;
    public XSSFWorkbook workbook;
    public String username;
    public String password;
    @BeforeTest
    public void beforeTest() throws IOException
    {
        driver = new ChromeDriver();
        FileInputStream fis = new FileInputStream("D:\\Testing\\Day8\\day8que2\\src\\Excel\\Data.xlsx");
        workbook = new XSSFWorkbook(fis);
        
        XSSFSheet sheet = workbook.getSheet("Sheet1");
        
        username = sheet.getRow(1).getCell(0).getStringCellValue();
        password = sheet.getRow(1).getCell(1).getStringCellValue();
    }
    @Test
    public void TestCase1() throws InterruptedException
    {
        
		driver.get("https://www.demoblaze.com/");
		
		
		Thread.sleep(10000);
		driver.findElement(By.linkText("Laptops")).click();
		Thread.sleep(3000);
		driver.findElement(By.linkText("MacBook air")).click();
		Thread.sleep(3000);
		driver.findElement(By.linkText("Add to cart")).click();
		Thread.sleep(3000);
		driver.switchTo().alert().accept();
		Thread.sleep(3000);
		driver.findElement(By.linkText("Cart")).click();
		Thread.sleep(5000);
		List<WebElement>list = driver.findElements(By.tagName("td"));
		if(list.isEmpty())
		{
			System.out.print("Empty");
		}
		else
		{
            for(WebElement i:list)
            {
                if(i.getText().contains("MacBook air"))
                {
                    System.out.println("MacBook air is Added to cart");
                }
            }
		}
        Thread.sleep(4000);
    }
    @Test
    public void TestCase2() throws InterruptedException
    {
        driver.get("https://www.demoblaze.com/");
		Thread.sleep(5000);
        
        driver.findElement(By.xpath("//*[@id='login2']")).click();
		Thread.sleep(3000);
        
        driver.findElement(By.id("loginusername")).sendKeys(username);
        driver.findElement(By.id("loginpassword")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id='logInModal']/div/div/div[3]/button[2]")).click();
		Thread.sleep(4000);
        
        String check = driver.findElement(By.id("nameofuser")).getText();
        if(check.equals("Welcome Testalpha"))
        {
            System.out.println("Login Successful with message Testalpha");
        }
        
    }
    @AfterTest
    public void afterTest() throws InterruptedException
    {
        Thread.sleep(4000);
        driver.quit();
    }
}

