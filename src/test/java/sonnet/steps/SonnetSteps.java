package sonnet.steps;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.*;
import sonnet.dto.DTOMap;
import sonnet.dto.Interests;
import sonnet.po.TestPage;

import java.util.List;
import java.util.Map;


public class SonnetSteps {
    TestPage testPage = new TestPage();

    @DataTableType
    public Interests decodeInterests(Map<String, String> row) {
        return Interests.decode(new DTOMap(row));
    }

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

    @Then("I verify the submission works")
    public void iVerifyTheLoginWorks() {
        testPage.verifyLogin();
    }

    @And("I select the following interests:")
    public void iSelectTheFollowingInterests(List<Interests> interestList) {
            testPage.selectInterests(interestList.getFirst());
        }
    }
