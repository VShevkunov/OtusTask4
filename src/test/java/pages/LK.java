package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LK extends AbstractPage{

    private By personal = By.xpath("//a[@title='О себе']");

    public LK(WebDriver driver) {
        super(driver);
    }

    public Biography getBiography() {

        driver.findElement(personal).click();
        logger.info("Вызван разде О СЕБЕ");
        return new Biography(driver);

    }

}
