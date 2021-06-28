package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MyMenu extends AbstractPage {

    private By lk = By.xpath("//a[@title='Личный кабинет']");

    public MyMenu(WebDriver driver) {
        super(driver);
    }

    public LK getLK() {
        driver.findElement(lk).click();
        logger.info("Вызван раздел ЛИЧНЫЙ КАБИНЕТ");
        return new LK(driver);
    }

}
