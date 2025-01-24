package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    // Wrapper method to find element
    public static WebElement find_Element(By by, WebDriver driver) {
        WebElement field = driver.findElement(by);
        return field;
    }

    // Wrapper method to send value in a field
    public static void sen_Keys(WebElement element, String value) throws InterruptedException {
        element.clear();
        Thread.sleep(1000);
        element.sendKeys(value);
        Thread.sleep(1000);
    }

    // Wrapper method to click on an element
    public static void click_On(WebElement element) throws InterruptedException {
        element.click();
        Thread.sleep(2000);
    }
}
