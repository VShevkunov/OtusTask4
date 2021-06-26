import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class YandexTest {

    //By блока с информацией о товаре из результатов поиска
    static By productSnippetBy = By.xpath("//*[@data-autotest-id=('product-snippet')] | //*[@data-autotest-id=('offer-snippet')]");

    // By к кнопке сравнения ВНУТРИ БЛОКА товара из результатов поиска
    static By compareBy = By.xpath(".//following-sibling::div/child::div/following-sibling::div/child::div");

    //By плашки сообщающей что товар добавлен (подобие аллерта)
    static By addedToCompareBy = By.xpath("//*[contains(text(), 'добавлен к сравнению')]");

    //By сегментов хранящих 1 товар в списке сравнения
    static By allInComparisonBy = By.xpath("//*[contains(text(), 'Смартфон ')]");

    TestConfig cfg = ConfigFactory.create(TestConfig.class);
    protected WebDriver driver;
    private Logger logger = LogManager.getLogger(YandexTest.class);

    @Before
    public void startUp() {
        int timeout = 3; //переменная для неявного ожидания
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        logger.info("Драйвер запущен, установлено неявное ожидание = {} сек", timeout);

    }

    @Test
    public void test() {

        //By кнопки перехода в сравнение
        By compareBy = By.xpath("//span[text()='Сравнить']/parent::a/parent::div");

        //Открыть в Chrome сайт Яндекс.Маркет
        driver.get(cfg.hostnameYM());
        logger.info("Сайт {} открыт", cfg.hostnameYM());

        //Электроника
        By electronika = By.xpath("//span[text()='Электроника']");
        driver.findElement(electronika).click();
        logger.info("Выбран раздел Электроника");

        //Смартфоны
        By smartphones = By.xpath("//a[text()='Смартфоны']");
        driver.findElement(smartphones).click();
        logger.info("Выбран раздел Смартфоны");

        //Отфильтровать по "Samsung"
        filterBy("Samsung");

        //Отфильтровать по "Xiaomi"
        filterBy("Xiaomi");

        //Отсортировать список товаров по цене (от меньшей к большей)
        sort(true); //true - 1 раз. Во возрастанию.

        //добавить первый Самсунг
        addToCompare(Goods.SAMSUNG.getTitle());

        //добавить первый Xioami
        addToCompare(Goods.XIAOMI.getTitle());

        //Переход в сравнение
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(compareBy));
        driver.findElement(compareBy).click();
//      driver.get(cfg.hostnameYM() + "/my/compare-lists");
        logger.info("Вошли в сравнение");

        //список товаров в равнении
        List<WebElement> goodsInComparison = driver.findElements(allInComparisonBy);
        logger.info("В сравнении {} товара", goodsInComparison.size());

        //проверяем что товара 2
        Assert.assertTrue(goodsInComparison.size()==2);

    }

    public void filterBy (String filter) {
        //принимает на вход имя модели (фильтр), который на этой странице является частью локатора xPath

        WebDriverWait wait = new WebDriverWait(driver, 20);
        driver.findElement(By.xpath("//span[text()='" + filter + "']")).click();
        wait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(productSnippetBy))); //ожидаем что резултат поиска погаснет
        wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(productSnippetBy))); //ожидаем что результат появился после обновления и скалдываем их в productSegments
        logger.info("Раздел загружен и отфильрован по признаку {}", filter);
    }

    public void addToCompare(String productName) {
        //Если аргумент метода (productName)содержится в заголовке блока товара, то добавляем его ПЕРВОГО ПРЕДСТАВИТЕЛЯ в корзину и останавливаем поиск

        WebDriverWait wait = new WebDriverWait(driver, 20); //неявное ожидание
        List<WebElement> productSegments = driver.findElements(productSnippetBy);  //парасинг сегментов товаров

        for (WebElement productSegment : productSegments) {

            if (productSegment.getText().toUpperCase().contains(productName.toUpperCase())) {

                //перемещение внимания к товару
                Actions actions = new Actions(driver);
                actions.moveToElement(productSegment);
                logger.info("Проскролил к товару: {}", productName);

                //добавляем товар к сравнению
                productSegment.findElement(compareBy).click();
                logger.info("Добавил к сравнению: {}", productName);

                //ждём сообщение о том что добавлено
                wait.until(ExpectedConditions.visibilityOfElementLocated(addedToCompareBy));
                Assert.assertTrue(driver.findElement(addedToCompareBy).isDisplayed());
                logger.info("Отобразилась плашка: {}", driver.findElement(addedToCompareBy).getText());

                //Если нашли, то цикл завершается
                break;
            }

            //Если все элементы просмотрены, но ничего не найдено и текущий=последний, логируем и кидаем исключнение
            if (productSegment==productSegments.get(productSegments.size()-1)){
                logger.info("\"{}\" не найден среди результатов поиска", productName);
                Assert.assertTrue(false);
            }
        }
    }

    public void sort(Boolean argument){
        //Метод сортировки товаров на странице
        //True - 1 раз. Во возрастанию.
        //False - 2 раза. По убыванию.

        WebDriverWait wait = new WebDriverWait(driver, 20);

        if (argument) {
            driver.findElement(By.xpath("//button[text()='по цене']")).click();
            wait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(productSnippetBy))); //ожимаем что резултат поиска погаснет
            wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(productSnippetBy))); //ожидаем что результат появился после обновления и скалдываем их в productSegments
            logger.info("Раздел загружен и отсортирован по цене");
        }else{
            sort(true);
            sort(true);
            logger.info("Раздел загружен и отсортирован по цене от большей к меньшей)");
        }
    }


    @After
    public void end(){
        if (driver!=null){
            driver.quit();
            logger.info("Драйвер закрыт");
        }

    }


}