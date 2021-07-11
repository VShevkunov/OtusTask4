package scripts;

import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.MainPage;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

public class OtusTest {

    protected static String email;
    protected static String password;
    protected WebDriver driver;
    private Logger logger = LogManager.getLogger(OtusTest.class);

    @Before
    public void startUp() {

        email = System.getProperty("email");
        logger.info("Считан из консоли логин: {}", email);

        password = System.getProperty("password");
        logger.info("Считан из консоли пароль: {}", password);

        int timeout = 3; //переменная для неявного ожидания
        driver = WebDriverFactory.create(WebDriverName.CHROME);
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        logger.info("Драйвер запущен, установлено неявное ожидание = {} сек", timeout);

    }

    @Test
    public void test() throws InterruptedException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException {

        new MainPage(driver)
                .open()
                .logIn()
                .getMyMenu()
                .getLK()
                .getBiography()
                .getPersonal()
                .putAll(); //заполнить все поля (почти все) в соответствии с конфигом

        newInstanceBrowser(WebDriverName.CHROME); //открыть чистый браузер

        new MainPage(driver)
                .open()
                .logIn()
                .getMyMenu()
                .getLK()
                .getBiography()
                .getPersonal()
                .checkFields(); //проверить все поля в соответсвии с ТЕМ ЖЕ конфигом


    }

    public void newInstanceBrowser(WebDriverName webDriverName){

        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
        driver.close();
        driver = WebDriverFactory.create(webDriverName);
        driver.manage().window().maximize();

    }


    @After
    public void end() {
        if (driver!=null){
            driver.quit();
            logger.info("Драйвер закрыт");
        }
    }


}