package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import demo.wrappers.Wrappers;
import org.openqa.selenium.logging.LogEntry;

public class TestCases {
    ChromeDriver driver;

    @Test
    public void testCase01() throws InterruptedException {
        driver.get("https:\\www.flipkart.com");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='q']")));
        Wrappers.sen_Keys(search, "Washing Machine" + Keys.ENTER);

        // Sort by Popularity (Click the Sort dropdown and select 'Popularity')
        WebElement popularityOption = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Popularity')]")));
        // sortDropdown.click();
        // WebElement popularityOption =
        // driver.findElement(By.xpath("//li[contains(text(),'Popularity')]"));
        popularityOption.click();

        // Wait for the results to be sorted by Popularity
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(
                        By.xpath("//div[@class='zg-M3Z _0H7xSG' and text()='Popularity']")));

        // Get the list of products
        List<WebElement> products = driver.findElements(By.xpath("//div[@class='KzDlHZ']"));

        // Count the number of items with a rating <= 4 stars
        int count = 0;
        for (WebElement product : products) {
            try {
                WebElement ratingElement = product.findElement(By.xpath(".//div[@class='XQDdHH']"));
                double rating = Double.parseDouble(ratingElement.getText());
                if (rating <= 4) {
                    count++;
                }
            } catch (Exception e) {
                // Skip products without a rating element
                continue;
            }
        }

        // Print the count
        System.out.println("Count of products with rating <= 4 stars: " + count);
    }

    @Test
    public void testCase02() throws InterruptedException {
        driver.get("https:\\www.flipkart.com");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='q']")));
        Wrappers.sen_Keys(search, "iPhone" + Keys.ENTER);

        // Wait for the results to be sorted by Relevance
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(
                        By.xpath("//div[@class='zg-M3Z _0H7xSG' and text()='Relevance']")));

        // Get the list of productPercentages
        List<WebElement> productPercentages = driver.findElements(By.xpath("//div[@class='UkUFwK']"));

        // print the Titles and discount % of items with more than 17% discount
        for (WebElement percentage : productPercentages) {
            try {
                // WebElement ratingElement = percent.findElement(By.xpath("."));
                double percentageValue = Double.parseDouble(percentage.getText().replace("% off", ""));
                if (percentageValue > 17) {
                    WebElement productElement = percentage
                            .findElement(By.xpath("./../../../../div/div[@class='KzDlHZ']"));
                    System.out.println(productElement.getText());
                    System.out.println(percentageValue + "%");
                }
            } catch (Exception e) {
                // Skip products without discount % elements
                continue;
            }
        }
    }

    @Test
    public void testCase03() throws InterruptedException {
        driver.get("https:\\www.flipkart.com");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='q']")));
        Wrappers.sen_Keys(search, "Coffee Mug" + Keys.ENTER);

        // Wait for the results to be sorted by Relevance
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(
                        By.xpath("//div[@class='zg-M3Z _0H7xSG' and text()='Relevance']")));

        WebElement FourStartRating = driver.findElement(By.xpath("//div[@class='_6i1qKy' and text()='4★ & above']"));

        FourStartRating.click();

        //
        // Wait for 4* rated elements will appear
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(
                        By.xpath("//div[@class='_6tw8ju']")));

        // Get the list of NoOfReviews of all products in 1st page
        List<WebElement> productNoOfReviews = driver.findElements(By.xpath("//span[@class='Wphh3N']"));

        int i = 0;
        double[] noOfReviewsValue = new double[productNoOfReviews.size()];

        // store noOfReviews of each product into noOfReviesValue array
        for (WebElement noOfReviews : productNoOfReviews) {

            noOfReviewsValue[i] = Double
                    .parseDouble(noOfReviews.getText().replace("(", "").replace(")", "").replace(",", ""));

            i++;
        }
        Arrays.sort(noOfReviewsValue); // sorts array in ascending order

        // print the Title and image URL of the 5 items with highest number of reviews
        for (WebElement noOfReviews : productNoOfReviews) {

            double noOfReviewsValueTemp = Double
                    .parseDouble(noOfReviews.getText().replace("(", "").replace(")", "").replace(",", ""));

            for (int c = noOfReviewsValue.length - 1; c > noOfReviewsValue.length - 6; c--) {
                if (noOfReviewsValueTemp == noOfReviewsValue[c]) {
                    WebElement TitleOfTheElement = noOfReviews
                            .findElement(By.xpath("./../../a[@class='wjcEIp']"));
                    System.out.println(TitleOfTheElement.getAttribute("title"));
                    WebElement urlOfImageElement = noOfReviews
                            .findElement(By.xpath("./../../a/div/div/div/img"));
                    System.out.println(urlOfImageElement.getAttribute("src"));
                }
            }
        }

    }

    /*
     * Do not change the provided methods unless necessary, they will help in
     * automation and assessment
     */
    @BeforeTest
    public void startBrowser() {
        // No need to manually set the chromedriver path or call SeleniumManager
        // Selenium Manager will handle the driver setup automatically for you

        // Set ChromeOptions
        ChromeOptions options = new ChromeOptions();

        // Set logging preferences (if needed for debugging)
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);

        // Add any desired Chrome options
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        System.setProperty("webdriver.chrome.logfile", ".\\assesment\\assets\\chromedriver.log");
        // Initialize the ChromeDriver with the options
        driver = new ChromeDriver(options);

        // Maximize the window (if not already maximized by the options)
        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest() {
        LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logs) {
            System.out.println(entry.getMessage());
        }
        driver.quit();

    }
}