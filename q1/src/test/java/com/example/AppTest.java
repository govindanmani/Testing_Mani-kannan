package com.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
// import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    public WebDriver driver;
    public String xpath = "C:\\Users\\USER\\Downloads\\q1\\src\\Excel\\ExSheet.xlsx";
    public String resultPath = "C:\\Users\\USER\\Downloads\\q1\\src\\Reports\\result.html";
    public XSSFWorkbook workbook;
    public WebDriverWait wait;
    public ExtentReports reports;
    public Logger logger;

    @BeforeTest
    public void Initialization() throws IOException {

        // driver connection
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Excel connection
        FileInputStream file = new FileInputStream(xpath);
        workbook = new XSSFWorkbook(file);

        // Extent Report connection
        reports = new ExtentReports();
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(resultPath);
        reports.attachReporter(sparkReporter);

        // logger initialization
        logger = LogManager.getLogger(getClass());
    }

    @Test(priority = 1)
    public void verifyChetan() {
        // get the url
        driver.get("https://www.barnesandnoble.com/");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // click all and select book
        driver.findElement(By.xpath("//*[@id=\"rhf_header_element\"]/nav/div/div[3]/form/div/div[1]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"rhf_header_element\"]/nav/div/div[3]/form/div/div[1]/div/a[2]")).click();

        // take the author name from the excel and place it and search
        XSSFSheet s1 = workbook.getSheetAt(0);
        String author = s1.getRow(1).getCell(0).getStringCellValue();
        driver.findElement(By.xpath("//*[@id=\"rhf_header_element\"]/nav/div/div[3]/form/div/div[2]/div/input[1]"))
                .sendKeys(author);
        driver.findElement(By.xpath("//*[@id=\"rhf_header_element\"]/nav/div/div[3]/form/div/span/button")).click();

        // Test Creation
        ExtentTest AuthorName = reports.createTest("Test Author Name");

        // Verify that it contains Chetan Bhagat
        String res = driver
                .findElement(By.xpath("//*[@id=\"searchGrid\"]/div/section[1]/section[1]/div/div[1]/div[1]/h1/span"))
                .getText();
        if (res.equals("Chetan Bhagat")) {
            System.out.println("Yes is Contains Chetan Bhagat");
            AuthorName.log(Status.PASS, "Yes is Contains Chetan Bhagat");
            logger.info("SuccessFull");
        } else {
            System.out.println("No it does not contain Chetan Bhagat");
            AuthorName.log(Status.PASS, "No it does not contain Chetan Bhagat");
            logger.error("Failed");
        }
        // driver.close();
    }

    @Test(priority = 2)
    public void AudioBooks() {

        // get the url
        driver.get("https://www.barnesandnoble.com/");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // got to the top 100 Autobook
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//*[@id=\"navbarSupportedContent\"]/div/ul/li[5]"))).build()
                .perform();
        driver.findElement(
                By.xpath("//*[@id=\"navbarSupportedContent\"]/div/ul/li[5]/div/div/div[1]/div/div[2]/div[1]/dd/a[1]"))
                .click();

        // Add to Cart
        try {
            driver.findElement(By.xpath("//*[@id=\"addToBagForm_2940159516459\"]/input[11]")).click();
        } catch (Exception e) {
            logger.warn("No Element Found");
        }

        String str = driver.findElement(By.xpath("//*[@id=\"cartIcon\"]/span[2]")).getText();

        // checking the item is added to the cart or not
        ExtentTest addToCart = reports.createTest("Item Add To Cart");

        if (!str.equals("0")) {
            System.out.println(str);
            addToCart.log(Status.FAIL, "No The item Not Added to The cart");
            logger.error("No items in the Cart");
        } else {
            logger.info("This the item is added to the cart");
            addToCart.log(Status.PASS, "The item Added to the cart");
        }
        // driver.close();
    }

    @Test(priority = 3)
    public void BandN() {
        // get the url
        driver.get("https://www.barnesandnoble.com/");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // navigate to n and b
        // driver.findElement(By.linkText("B&N MEMBERSHIP")).click();
        driver.navigate().to("https://www.barnesandnoble.com/membership/");

        // click the rewards
        ExtentTest rewards = reports.createTest("Reward Test");
        try {
            driver.findElement(By.linkText("JOIN REWARDS")).click();
            rewards.log(Status.PASS, "Sucessfull get the page");
            logger.info("Sucess");
        } catch (Exception e) {
            rewards.log(Status.FAIL, "No the Element Not Found");
            logger.error("Not Found");
        }
        driver.close();
    }

    @AfterTest
    public void AfterTest() throws IOException {
        reports.flush();
        workbook.close();
        driver.quit();
    }
}
