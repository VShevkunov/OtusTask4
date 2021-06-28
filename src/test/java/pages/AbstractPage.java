package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public abstract class AbstractPage {

    private By avatar = By.xpath("//div[@class='header2-menu__icon-img ic-blog-default-avatar']");

    protected WebDriver driver;

    protected Logger logger = LogManager.getLogger(this.getClass());

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isLoggedIn(AbstractPage page) {
        return driver.findElement(avatar).isDisplayed();
    }

    public MyMenu openMyMenu() {
        Assert.assertTrue(isLoggedIn(this));
        driver.findElement(avatar).click();
        return new MyMenu(driver);
    }

}
