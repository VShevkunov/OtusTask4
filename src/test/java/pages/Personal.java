package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.security.Permission;

public class Personal extends AbstractPage {

    private By name = By.xpath("//input[@name='fname']");
    private By latName = By.xpath("//input[@name='fname_latin']");
    private By surname = By.xpath("//input[@name='lname']");
    private By latSurname = By.xpath("//input[@name='lname_latin']");
    private By blogName = By.xpath("//input[@name='blog_name']");
    private By birthDate = By.xpath("//input[@name='date_of_birth']");
    private By country = By.xpath("//input[@name='country']/following-sibling::*/*");
    private By city = By.xpath("//input[@name='city']/following-sibling::*/*");
    private By englishLevel = By.xpath("//input[@name='english_level']/following-sibling::*/*");
    private By readyToRelocate = By.xpath("//input[@name='ready_to_relocate' and @value='True']//following-sibling::*");
    private By workRegime = By.xpath("//span[contains(text(), 'Гибкий график')]");
    private By contactOne = By.xpath("//input[@name='contact-0-service']/following-sibling::*");
    private By contactTwo = By.xpath("//input[@name='contact-1-service']/following-sibling::*");
    private By gender = By.xpath("//select[@name='gender']");
    private By company = By.xpath("//input[@name='company']");
    private By work = By.xpath("//input[@name='work']");
    private By save = By.xpath("//button[@title='Сохранить и продолжить']");





    public Personal(WebDriver driver) {
        super(driver);
    }

    public Personal putAll() throws InterruptedException {

        driver.findElement(this.name).clear();
        driver.findElement(this.name).sendKeys("Тестимя");
        logger.info("Заполнено имя");

        driver.findElement(this.latName).clear();
        driver.findElement(this.latName).sendKeys("Testname");
        logger.info("Заполнено имя на латинице");

        driver.findElement(this.surname).clear();
        driver.findElement(this.surname).sendKeys("Тестфамилия");
        logger.info("Заполнена фамилия");

        driver.findElement(this.latSurname).clear();
        driver.findElement(this.latSurname).sendKeys("Testsurname");
        logger.info("Заполнена фамилия на латинице");

        driver.findElement(this.blogName).clear();
        driver.findElement(this.blogName).sendKeys("blogname999");
        logger.info("Заполнено имя в блоге");

        driver.findElement(this.birthDate).clear();
        driver.findElement(this.birthDate).sendKeys("23.04.1990");
        driver.findElement(this.birthDate).sendKeys(Keys.ENTER);

        driver.findElement(this.country).click();
        driver.findElement(By.xpath("//button[@title='Польша']")).click();

        Thread.sleep(1000);

        driver.findElement(this.city).click();
        driver.findElement(By.xpath("//button[@title='Познань']")).click();

        driver.findElement(this.englishLevel).click();
        driver.findElement(By.xpath("//button[@title='Средний (Intermediate)']"));

        driver.findElement(this.readyToRelocate).click();

        driver.findElement(this.workRegime).click();

        driver.findElement(this.contactOne).click();
        driver.findElement(By.xpath("//button[@title='Facebook']"));
        driver.findElement(By.xpath("//input[@name='contact-0-value']")).sendKeys("facebook.com");

        driver.findElement(By.xpath("//button[text()='Добавить']")).click();

        driver.findElement(this.contactTwo).click();
        driver.findElement(By.xpath("//button[@title='VK']"));
        driver.findElement(By.xpath("//input[@name='contact-1-value']")).sendKeys("vk.com");

        driver.findElement(this.gender).click();
        driver.findElement(By.xpath("//option[@value='m']"));

        driver.findElement(company).sendKeys("Пятёрочка");

        driver.findElement(work).sendKeys("Охранник");

        driver.findElement(save).click();



        return this;


    }










}
