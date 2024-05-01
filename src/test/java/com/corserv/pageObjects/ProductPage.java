package com.corserv.pageObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {
        private WebDriver driver;

    @FindBy(xpath = "//div[text()[contains(.,'View Business Credit Cards')]]")
	WebElement businessCreditCardLink;
	
    @FindBy(xpath = "//*[contains(@class,'productTitle') and contains(., 'Mastercard')]")
	List<WebElement> creditCardList;

    @FindBy(xpath = "//*[text()[contains(.,'+Terms & Conditions')]]")
    List<WebElement> termsConditionsLinkList;

    @FindBy(xpath = "//*[@id=\"divTermsContent\"]/table[1]/tbody/tr[4]/td[2]/b")
    WebElement cashAdvanceAPR;

     public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void NavigateToBusinessCardLink(){
        businessCreditCardLink.click();
    }

    public void ClickTermsConditions(String cardName){
        int count = GetCount(cardName);
         JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", termsConditionsLinkList.get(count));

        // Switch to the new tab
        Set<String> windowHandles = driver.getWindowHandles();
        for (String windowHandle : windowHandles) {
            if (!windowHandle.equals(driver.getWindowHandle())) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }

    public double GetCashAdvanceAPR(){
         WebDriverWait wait = new WebDriverWait(driver, 10);
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"divTermsContent\"]")));
       String percentageString = cashAdvanceAPR.getText();
       return Double.parseDouble(percentageString.replace("%", ""));
    }

    private int GetCount(String cardToFind){
        List<String> creditCardNameList = new ArrayList<>();
        for (WebElement element : creditCardList) {
            creditCardNameList.add(element.getAttribute("innerText"));
        }
        int position = creditCardNameList.indexOf(cardToFind);
        return position;
    }
}
