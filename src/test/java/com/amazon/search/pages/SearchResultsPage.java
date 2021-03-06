package com.amazon.search.pages;

import com.amazon.search.framework.CustomExpectedConditions;
import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResultsPage extends BasePage {

    private static Logger LOG = Logger.getLogger(HomePage.class);
    private WebDriver driver;

    private WebDriverWait wait;

    private final String searchIdLocator = "search";
    private final String brandXpathLocator = ".//span[contains(@class,'a-checkbox-label')]/span[contains(.,'%s')]";
    private final String searchItemsXpathLocator = "//div[contains(@class, 's-result-list')]/div[contains(@class, 's-result-item')]";
    private final String filteredSearchResultsIdLocator = "s-skipLinkTargetForMainSearchResults";

    public SearchResultsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }


    @FindBy(id = searchIdLocator)
    private WebElement searchResultsPage;

    @FindBy(id = "brandsRefinements")
    private WebElement brandsSection;

    @Step
    public int getSearchResultsItemsSize() {
        return searchResultsPage.findElements(By.xpath(searchItemsXpathLocator)).size();
    }

    @Step
    public void selectBrand(String brand) {
        LOG.info(String.format("Selecting checkbox with brand %s", brand));
        WebElement el = brandsSection.findElement(By.xpath(String.format(brandXpathLocator, brand)));
        scrollIntoView(el);
        el.click();
        waitUntilFilteredSearchResultsAppeared();
    }

    @Step
    public void waitUntilSearchResultsAppeared() {
        wait = new WebDriverWait(driver, 5);
        LOG.info("Waiting until search results appeared");
        wait.until(new CustomExpectedConditions.ElementPresent(By.id(searchIdLocator)));
    }

    @Step
    public void waitUntilFilteredSearchResultsAppeared() {
        wait = new WebDriverWait(driver, 5);
        LOG.info("Waiting until Filtered search results appeared");
        wait.until(new CustomExpectedConditions.ElementPresent(By.id(filteredSearchResultsIdLocator)));
    }

}
