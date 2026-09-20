package sonnet.steps;

import io.cucumber.java.DataTableType;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.*;
import sonnet.dto.Interests;
import sonnet.testenum.TestEnum;
import sonnet.po.TestPage;

import java.util.List;
import java.util.Map;
import sonnet.dto.DTOMap;
import sonnet.utils.Time;

import static sonnet.utils.StringUtils.*;

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

    @Given("I wait for {int} seconds")
    public void iWaitForSeconds(int seconds) {
        Time.pause(seconds);
    }

    @Given("I accept the cookies")
    public void iAcceptTheCookies() {
        testPage.acceptCookies();
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

    @And("I select the language {string}")
    public void iSelectTheLanguage(String language) {
        testPage.selectLanguage(language);
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

    @And("I have the following gender:")
    public void iHaveTheFollowingGender(String gender) {
        String genderCamel = toCamelCase(gender);
        testPage.selectGender(TestEnum.Gender.valueOf(genderCamel));
    }
}
