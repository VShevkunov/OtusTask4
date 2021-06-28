package scripts;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

import static scripts.WebDriverName.*;

public class WebDriverFactory {

    public static WebDriver create(WebDriverName webDriverName){
        switch (webDriverName){
            case CHROME:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            case OPERA:
                WebDriverManager.operadriver().setup();
                return new OperaDriver();
            default:
                return null;
        }
    }

    public static WebDriver create(WebDriverName webDriverName, MutableCapabilities options){
        switch (webDriverName){
            case CHROME:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver( (ChromeOptions) options);
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver((FirefoxOptions) options);
            case OPERA:
                WebDriverManager.operadriver().setup();
                return new OperaDriver((OperaOptions) options);
            default:
                return null;
        }
    }


}
