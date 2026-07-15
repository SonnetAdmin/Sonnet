package org.sonnet.steps;

import io.cucumber.java.en.*;
import org.sonnet.po.TestPage;


public class SonnetSteps {
    TestPage testPage = new TestPage();

    @Given("I open a browser")
    public void iOpenABrowser() {
       testPage.openBrowser();
    }

    @Given("I navigate to {string}")
    public void iGoTo(String url) {
        testPage.navigate(url);
    }

    @When("I type the name {string}")
    public void iTypeTheName(String name) {
        testPage.enterName(name);
    }

    @And("I type the email {string}")
    public void iTypeTheEmail(String email) {
        testPage.enterEmail(email);
    }

    @When("I type the password {string}")
    public void iTypeThePassword(String password) {
        testPage.enterPassword(password);
    }
// TODO printf to truncate
    @When("I get the element details")
    public void checkGet() {
        testPage.checkGet();
    }

    @When("I select the country {string}")
    public void iSelectTheCountry(String country) {
        testPage.selectCountry(country);
    }

    @And("I submit the form")
    public void iSubmitTheForm() {
        testPage.clickSubmit();
    }

    @Then("I verify the login works")
    public void iVerifyTheLoginWorks() {
        testPage.verifyLogin();
    }

}
