package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;
import org.apache.log4j.Logger;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class App {
    public WebDriver driver;
    final static Logger logger = Logger.getLogger(App.class);
    @Before
    public void setupDriver() {
        System.setProperty("webserver.chrome.driver", "../chromedriver.exe");
        driver = new ChromeDriver();
        String url = "https://www.gittigidiyor.com.tr/";
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void TestHome() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement signBtn = driver.findElement(By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[3]/div/div[1]/div/div[2]"));
        signBtn.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebElement loginBtn = driver.findElement(By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[3]/div/div[1]/div[2]/div/div/div/a"));
        loginBtn.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        WebElement mailBox = driver.findElement(By.id("L-UserNameField"));
        mailBox.click();
        mailBox.sendKeys("your e-mail");

        WebElement password = driver.findElement(By.id("L-PasswordField"));
        password.click();
        password.sendKeys("your password");
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
        driver.findElement(By.id("gg-login-enter")).click();

        /* Arama çubuğunda 'Bilgisayar' ifadesinin aranması */
        WebElement searchBox = driver.findElement(By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[2]/form/div/div[1]/div[2]/input"));
        searchBox.click();
        searchBox.sendKeys("Bilgisayar");
        driver.findElement(By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[2]/form/div/div[2]/button")).click();
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);

        /* Pagination elemenlerinin görünmesi için sayfanın altına inilmesi ve arama sonuç sayfalarında 2. sayfanın açılması */
        WebElement pagination = driver.findElement(By.xpath("//*[@id=\"best-match-right\"]/div[5]/ul/li[2]/a"));
        js.executeScript("arguments[0].scrollIntoView();", pagination);
        pagination.click();

        /* Rastgele bir ürünün açılması */
        Random rand = new Random();
        int randomOrder = rand.nextInt((47) + 1) + 1;

        driver.findElement(By.xpath("//*[@product-index=\"" + randomOrder + "\"]")).click();

        WebElement price = driver.findElement(By.id("sp-price-highPrice"));
        String priceText = price.getText();
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);

        /* Açılan ürün sayfasında ürünün sepete eklenmesi*/
        WebElement quantityBox = driver.findElement(By.id("buyitnow_adet"));
        quantityBox.click();
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);

        WebElement basketBtn = driver.findElement(By.id("add-to-basket"));
        js.executeScript("arguments[0].scrollIntoView();", basketBtn);
        basketBtn.click();
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id=\"header_wrapper\"]/div[4]/div[3]/a/div[1]")).click();
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id=\"header_wrapper\"]/div[4]/div[3]/div/div/div/div[2]/div[4]/div[1]/a")).click();

        WebElement PBasketPrice = driver.findElement(By.xpath("//*[@id=\"cart-price-container\"]/div[3]/p"));
        String BasketPrice = PBasketPrice.getText();
        logger.info("Sepet Fiyatı : " + BasketPrice);

//        Actions action = new Actions(driver);
//        WebElement elem = driver.findElement(By.xpath("//*[@class='amount']"));
//
//        action.moveToElement(elem).build().perform();
//        action.contextClick(elem).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();

        /* Sepetin boşaltılması */

        driver.findElement(By.cssSelector(".btn-delete.btn-update-item.hidden-m")).click();
        logger.info("Sepet Boşaltıldı");

        WebElement BasketCheck = driver.findElement(By.xpath("//*[@id=\"cart-price-container\"]/div[3]/p"));
        String Check = BasketCheck.getText();

        if (Check.equals("")) {
            logger.info("Sepetin boş olduğu kontrolü");
        }}

    @After
    public void quitDriver() {
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
        driver.quit();
    }
}
