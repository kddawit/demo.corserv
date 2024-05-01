package com.corserv.tests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.corserv.TestBase;
import com.corserv.pageObjects.ProductPage;

public class BusinessCardsTest extends TestBase {
    ProductPage productPage;

    @Before
    public void BeforeAll(){
        Init();
    }

    @After
    public void AfterAll(){
        driver.close();
        driver.quit();
    }

    @Test
    public void cashAdvanceAPRValueTest() {
        Double expectedCashAdvAPR = 24.00;
        driver.get("https://pnfp.myapexcard.com/info");
        ProductPage productPage = new ProductPage(driver);
        productPage.NavigateToBusinessCardLink();
        productPage.ClickTermsConditions("Mastercard® Business Platinum Rewards");
        Double cashAdvanceAPR = productPage.GetCashAdvanceAPR();

        assertTrue("Cash advance apr is less than expected value",  cashAdvanceAPR < expectedCashAdvAPR);
    }
}
