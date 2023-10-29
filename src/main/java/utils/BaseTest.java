package utils;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import pages.BrokerPage;

public class BaseTest {

    protected String baseUrl;
    protected WebDriver driver;
    protected BrokerPage brokerPage;
    protected SoftAssert softAssert;

    @BeforeClass
    protected void beforeClass() {
        System.out.println("Before Class");
        baseUrl = "https://www.yavlena.com/broker/";
        driver = new ChromeDriver();
        driver.get(baseUrl);
        driver.manage().window().maximize();
        brokerPage = new BrokerPage(driver);
    }

    @BeforeMethod(alwaysRun = true)
    protected void beforeMethod() throws IOException {
        System.out.println("Before Method has started");
        softAssert = new SoftAssert();
    }

    @AfterMethod(alwaysRun = true)
    protected void afterMethod() {
        System.out.println("After Method");
        softAssert.assertAll();
    }

    @AfterClass(alwaysRun = true)
    protected void afterClass() {
        System.out.println("After Class");
        driver.close();
        driver.quit();
    }

}
