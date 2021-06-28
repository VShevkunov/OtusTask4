package scripts;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class OtusTest {

    TestConfig cfg = ConfigFactory.create(TestConfig.class);
    protected WebDriver driver;
    private Logger logger = LogManager.getLogger(OtusTest.class);

    @Before
    public void startUp() {
        int timeout = 3; //переменная для неявного ожидания
        driver = WebDriverFactory.create(WebDriverName.CHROME);
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        logger.info("Драйвер запущен, установлено неявное ожидание = {} сек", timeout);

    }

    @Test
    public void test() throws InterruptedException {

        new MainPage(driver)
                .open()
                .logIn()
                .openMyMenu()
                .getLK()
                .biography()
                .personal()
                .putAll();



    }



    @After
    public void end(){
        if (driver!=null){
            driver.quit();
            logger.info("Драйвер закрыт");
        }

    }


}