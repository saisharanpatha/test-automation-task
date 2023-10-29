package pages;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BasePage;

public class BrokerPage extends BasePage {

    public static final By BY_NAME_LOCATOR = By.className("name");
    @FindBy(className = "hide-cookies-message")
    private WebElement hideCookies;

    @FindBy(className = "load-more-results-list")
    private WebElement loadMoreResults;

    @FindBy(id = "brokers-grid-holder")
    private WebElement brokersHolder;

    @FindBy(className = "broker-list-holder")
    private WebElement brokerList;

    @FindBy(className = "tel-group")
    private WebElement telGroup;

    @FindBy(className = "input-search")
    private WebElement searchInput;

    @FindBy(className = "search-btn")
    private WebElement searchButton;

    @FindBy(className = "brokers-loading")
    private WebElement brokersLoading;

    @FindBy(css = ".name > a")
    private WebElement name;

    public BrokerPage(WebDriver driver) {
        super(driver);
    }

    public BrokerPage hideCookiesMessage() {
        waitForElementToBeClickableAndClick(hideCookies);
        return new BrokerPage(getDriver());
    }

    public BrokerPage loadMoreBrokers() {
        waitForElementToBeClickableAndClick(loadMoreResults);
        waitForElementToBeRemoved(loadMoreResults);
        return new BrokerPage(getDriver());
    }


    public List<String> getAllBrokers() {
        List<WebElement> webElements = waitAndFindElements(brokersHolder, BY_NAME_LOCATOR);
        return webElements.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public BrokerPage searchBrokerAndWaitForResults(String name) {
        clearAndSendKeys(searchInput, name);
        waitForElementToBeRemoved(brokersLoading);
        waitForElementToBeClickableAndClick(searchButton);
        return new BrokerPage(getDriver());
    }


    public String getNumberOfBrokersListed() {
        waitForElementToBe(brokerList, "data-total-count", "1");
        return waitAndFindElement(By.className("broker-list-holder")).getAttribute("data-total-count");
    }

    public void waitForSearchedNameToBeShown(String searchedName) {
        waitForElementToBeRemoved(brokersLoading);
        waitAndFindElement(BY_NAME_LOCATOR);
        try {
            waitForElementToBe(name, "title", searchedName);
        } catch (StaleElementReferenceException ignored) {
        }
    }

    public String getName() {
        return waitAndFindElement(BY_NAME_LOCATOR).getText();
    }

    public String getAddress() {
        return waitAndFindElement(By.className("office")).getText();
    }

    public int getNumberOfPhones() {
        return waitAndFindElements(telGroup, By.className("tel")).size();
    }

    public int getNumberOfProperties() {
        return Integer.parseInt(
            waitAndFindElement(By.cssSelector("div.position > a")).getText().replaceAll("[^0-9]+", ""));
    }
}
