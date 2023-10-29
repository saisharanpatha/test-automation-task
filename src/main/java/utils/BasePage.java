package utils;

import java.time.Duration;
import java.util.List;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public abstract class BasePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final AjaxElementLocatorFactory factory;
    private final Actions actions;
    private final JavascriptExecutor javascriptExecutor;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofMillis(5000L));
        this.factory = new AjaxElementLocatorFactory(driver, 20);
        PageFactory.initElements(factory, this);
        actions = new Actions(driver);
        javascriptExecutor = (JavascriptExecutor) driver;
    }

    protected void moveToElement(WebElement element) {
        actions.moveToElement(element);
        actions.perform();
    }

    protected void waitForElementToBeClickableAndClick(WebElement elem) {
        moveToElement(elem);
        wait.until(ExpectedConditions.elementToBeClickable(elem));
        elem.click();
    }

    protected List<WebElement> waitAndFindElements(WebElement root, By byLocator) {
        wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(root, byLocator));
        return root.findElements(byLocator);
    }

    protected WebElement waitAndFindElement(By byLocator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
    }

    protected void clearAndSendKeys(WebElement element, String text) {
        moveToElement(element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.clear();
        element.sendKeys(text);
    }

    protected void waitForElementToBe(WebElement element, String attribute, String value) {
        wait.until(ExpectedConditions.attributeToBe(element, attribute, value));
    }

    protected void waitForElementToBeRemoved(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }
}
