package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Biography extends AbstractPage {

    private By personal = By.xpath("//div[@class='nav-sidebar']//a[@title='Персональные данные']");

    public Biography(WebDriver driver) {
        super(driver);
    }

    public Personal personal(){
        driver.findElement(personal).click();
        logger.info("Вызван раздел ПЕРСОНАЛЬНЫЕ ДАННЫЕ");
        return new Personal(driver);
    }




}
