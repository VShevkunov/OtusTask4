package pages;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.channels.SelectableChannel;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Personal extends AbstractPage {

    WebDriverWait wait = new WebDriverWait(driver, 20);

    private static By name = By.xpath("//input[@name='fname']");
    private By latName = By.xpath("//input[@name='fname_latin']");
    private By surname = By.xpath("//input[@name='lname']");
    private By latSurname = By.xpath("//input[@name='lname_latin']");
    private By blogName = By.xpath("//input[@name='blog_name']");
    private By birthDate = By.xpath("//input[@name='date_of_birth']");
    private By country = By.xpath("//input[@name='country']/following-sibling::*"); // /*
    private By city = By.xpath("//input[@name='city']/following-sibling::*");
    private By cityList = By.xpath("//input[@name='city']/parent::*/following-sibling::*/child::*/button");
    private By englishLevel = By.xpath("//input[@name='english_level']/following-sibling::*");
    private By workRegime = By.xpath("//input[@name='work_schedule' and @title='" + cfg.workRegime() + "']/parent::*");
    private By readyToRelocate = By.xpath("//input[@type='radio' and @value='" + cfg.readyToRelocate() + "']/parent::*");
    private By contactOneType = By.xpath("//input[@name='contact-0-service']/following-sibling::*");
    private By contactOneLink = By.xpath("//input[@name='contact-0-value']");
    private By contactTwoType = By.xpath("//input[@name='contact-1-service']/following-sibling::*");
    private By contactTwoLink = By.xpath("//input[@name='contact-1-value']");
    private By gender = By.xpath("//select[@name='gender']");
    private By company = By.xpath("//input[@name='company']");
    private By work = By.xpath("//input[@name='work']");
    private By save = By.xpath("//button[@title='Сохранить и продолжить']");


    public Personal(WebDriver driver) {
        super(driver);
    }

    //Метод проверки реализован на основе рефлексии с благой идеей переиспользования конфига несущего тестовые данные
    //"By", содержащие локаторы элементов названы аналогично с переменными конфига несущего тестовые данные для этих полей
    public Personal checkFields() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException {

        //Массив стандартных методов класса Object - создан для исключения из перебора во время проверки
        List<String> basic = Arrays.asList("invoke", "equals", "toString", "hashCode", "getAttributes", "getAttribute", "getMBeanInfo", "setAttribute", "setAttributes");

        for (Method method : cfg.getClass().getDeclaredMethods()) {

            if (!basic.contains(method.getName())) {

                String methodName = method.getName();
                Field field = this.getClass().getDeclaredField(methodName);
                By locator = (By) field.get(this);

                String testData = (String) cfg.getClass().getDeclaredMethod(methodName).invoke(cfg);

                //ниже все проверки разделены на 3 группы по типу элементов, доступные на странице
                switch (method.getName()) {
                    case "name":
                    case "latName":
                    case "surname":
                    case "latSurname":
                    case "blogName":
                    case "birthDate":
                    case "contactOneLink":
                    case "contactTwoLink":
                    case "company":
                    case "work":
                        Assert.assertEquals(testData, driver.findElement(locator).getAttribute("Value"));
                        break;
                    case "country":
                    case "city":
                    case "englishLevel":
                    case "contactOneType":
                    case "contactTwoType":
                        Assert.assertEquals(testData, driver.findElement(locator).getText());
                        break;
                    case "gender":
                        Assert.assertEquals(testData, driver.findElement(locator).findElement(By.xpath("//option[@selected='']")).getText());

            }

            logger.info("Значение поля {} соответвует ожидаемому ({})", method.getName(), testData);

            }
        }

        return this;
    }

    public Personal putAll() throws InterruptedException {

        return this
                .setName(cfg.name())
                .setLatName(cfg.latName())
                .setSurname(cfg.surname())
                .setLatSurname(cfg.latSurname())
                .setBlogName(cfg.blogName())
                .setBirthDate(cfg.birthDate())
                .setCountry(cfg.country())
                .setCity(cfg.city())
                .setEnglishLevel(cfg.englishLevel())
                .setReadyToRelocate(cfg.readyToRelocate())
                .setRegime(cfg.workRegime())
                .setContact(cfg.contactOneType(), cfg.contactOneLink())
                .setAddContact(cfg.contactTwoType(), cfg.contactTwoLink())
                .setGender(cfg.gender())
                .setCompany(cfg.company())
                .setWork(cfg.work())
                .save();

    }

    public Personal setName(String name) {
        driver.findElement(this.name).clear();
        driver.findElement(this.name).sendKeys(name);
        logger.info("Заполнено имя");
        return this;
    }

    public Personal setLatName(String latName) {
        driver.findElement(this.latName).clear();
        driver.findElement(this.latName).sendKeys(latName);
        logger.info("Заполнено имя на латинице");
        return this;
    }

    public Personal setSurname(String surname) {
        driver.findElement(this.surname).clear();
        driver.findElement(this.surname).sendKeys(surname);
        logger.info("Заполнена фамилия");
        return this;
    }

    public Personal setLatSurname(String latSurname) {
        driver.findElement(this.latSurname).clear();
        driver.findElement(this.latSurname).sendKeys(latSurname);
        logger.info("Заполнена фамилия на латинице");
        return this;
    }

    public Personal setBlogName(String blogName) {
        driver.findElement(this.blogName).clear();
        driver.findElement(this.blogName).sendKeys(blogName);
        logger.info("Заполнено имя в блоге");
        return this;
    }

    public Personal setBirthDate(String birthDate) {
        driver.findElement(this.birthDate).clear();
        driver.findElement(this.birthDate).sendKeys(birthDate);
        driver.findElement(this.birthDate).sendKeys(Keys.ENTER);
        logger.info("Выбрана дата рождения");
        return this;
    }


    public Personal setCountry(String country) {
        driver.findElement(this.country).click();
        driver.findElement(By.xpath("//button[@title='" + country + "']")).click();
        logger.info("Заполнена страна");
        return this;
    }

    public Personal setCity(String city) {

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(cityList, 1));
        logger.info("Найдено больше одного элемента в списке городов (кроме \"не выбран\")");

        wait.until(ExpectedConditions.elementToBeClickable(this.city));
        logger.info("element кликабелен");

        driver.findElement(this.city).click();
        logger.info("нажали на поле выбора города");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='" + city + "']"))).click();
        logger.info("Заполнен город");
        return this;
    }

    public Personal setEnglishLevel(String englishLevel) {
        driver.findElement(this.englishLevel).click();
        driver.findElement(By.xpath("//button[@title='" + englishLevel + "']")).click();
        logger.info("Уровень англйского выбран");
        return this;
    }

    public Personal setReadyToRelocate(String relocate) {
        driver.findElement(readyToRelocate).click();
        logger.info("Релокейт выбран");
        return this;
    }


    public Personal setRegime(String regime) {
        for (WebElement x : driver.findElements(By.xpath("//input[@name='work_schedule']"))) {
            if (x.isSelected()) {
                x.findElement(By.xpath("./parent::*")).click();
            }
        }
        logger.info("Старый выбор очищен");

        driver.findElement(workRegime).click();
        logger.info("График выбран");
        return this;
    }

    public Personal setContact(String contactType, String contactLink) {
        driver.findElement(this.contactOneType).click(); //нажать на поле типа контакта
        driver.findElement(By.xpath("//input[@name='contact-0-id']//following-sibling::*//button[@title='" + contactType + "']")).click(); //выбрать контакт по входным данным
        driver.findElement(contactOneLink).clear(); //очистка перед заполненением
        driver.findElement(contactOneLink).sendKeys(contactLink); //вставка линка на контакт
        logger.info("Заполнен 1й контакт");
        return this;
    }


    public Personal setAddContact(String contactType, String contactLink) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Добавить']")));
        driver.findElement(By.xpath("//button[text()='Добавить']")).click(); //взаимодействие с кнопкой добавить
        logger.info("Добавлено поле контакта");


        driver.findElement(this.contactTwoType).click(); //повтор логики setContact
        driver.findElement(By.xpath("//input[@name='contact-1-id']//following-sibling::*//button[@title='" + contactType + "']")).click();
        driver.findElement(contactTwoLink).clear();
        driver.findElement(contactTwoLink).sendKeys(contactLink);
        logger.info("Заполнен 2й контакт");
        return this;
    }

    public Personal setGender(String gender) {
        driver.findElement(this.gender).click();
        new Select(driver.findElement(By.id("id_gender"))).selectByVisibleText(gender);
        logger.info("Указан мужской пол");
        return this;
    }

    public Personal setCompany(String company) {
        driver.findElement(this.company).clear();
        driver.findElement(this.company).sendKeys(company);
        logger.info("Указана компания");
        return this;
    }

    public Personal setWork(String work) {
        driver.findElement(this.work).clear();
        driver.findElement(this.work).sendKeys(work);
        logger.info("Указана должность");
        return this;
    }

    public Personal save() {
        driver.findElement(this.save).click();
        logger.info("Страница сохранена");
        return this;
    }


    public Personal scrollWithOffset(WebElement webElement, int x, int y) {

        String code = "window.scroll(" + (webElement.getLocation().x + x) + ","
                + (webElement.getLocation().y + y) + ");";

        ((JavascriptExecutor)driver).executeScript(code, webElement, x, y);

        return this;

    }


}
