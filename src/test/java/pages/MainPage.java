package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import sun.rmi.runtime.Log;

import java.util.ArrayList;

public class MainPage extends AbstractPage {

    private static final String URL = "https://otus.ru";
    private By auth = By.xpath("//button[contains(text(), 'Вход')]");
    private By emailField = By.xpath("//input[@placeholder='Электронная почта' and @type='text']");
    private By passwordField = By.xpath("//input[@placeholder='Введите пароль' and @type='password']");
    private By logIn = By.xpath("//button[@type='submit' and contains(text(), 'Войти')]");

    public MainPage(WebDriver driver) {
        super(driver);
    }


    public MainPage open() {
        driver.get(URL);
        return this;
    }

    public MainPage logIn() {

        driver.findElement(auth).click();
        logger.info("Нажата кнопка ВХОД ИЛИ РЕГИСТРАЦИЯ");

        driver.findElement(emailField).sendKeys("recafa9892@paseacuba.com");
        logger.info("Вставлен логин");

        driver.findElement(passwordField).sendKeys("password3197");
        logger.info("Вставлен пароль");

        driver.findElement(logIn).click();
        logger.info("Нажата кнопка ВОЙТИ");

        Assert.assertTrue(isLoggedIn(this));
        logger.info("Пользователь внутри");

        return this;
    }


}
