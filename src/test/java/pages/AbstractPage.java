package pages;

import org.aeonbits.owner.ConfigFactory;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import scripts.TestConfig;

//Предок всех страниц проекта
public abstract class AbstractPage {

    private By avatar = By.xpath("//div[@class='header2-menu__icon-img ic-blog-default-avatar']");
    protected WebDriver driver;
    protected Logger logger = LogManager.getLogger(this.getClass());

    TestConfig cfg = ConfigFactory.create(TestConfig.class);

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isLoggedIn(AbstractPage page) {
        WebDriverWait wait = new WebDriverWait(this.driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(avatar));
        return driver.findElement(avatar).isDisplayed();
    }

    public MyMenu getMyMenu() {
        Assert.assertTrue(isLoggedIn(this));
        driver.findElement(avatar).click();
        return new MyMenu(driver);
    }

}
