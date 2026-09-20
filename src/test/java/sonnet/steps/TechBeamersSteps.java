package sonnet.steps;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.*;
import sonnet.dto.Interests;
import sonnet.testenum.TestEnum;
import sonnet.po.TechBeamers;

import java.util.List;
import java.util.Map;
import sonnet.dto.DTOMap;
import sonnet.utils.Time;

import static sonnet.utils.StringUtils.*;

public class TechBeamersSteps {
    TechBeamers techBeamers = new TechBeamers();

    @DataTableType
    public Interests decodeInterests(Map<String, String> row) {
        return Interests.decode(new DTOMap(row));
    }

    @Given("I accept the cookies")
    public void iAcceptTheCookies() {
        techBeamers.acceptCookies();
    }

    @When("I type the name {string}")
    public void iTypeTheName(String name) {
        techBeamers.enterName(name);
    }

    @And("I type the email {string}")
    public void iTypeTheEmail(String email) {
        techBeamers.enterEmail(email);
    }

    @When("I type the password {string}")
    public void iTypeThePassword(String password) {
        techBeamers.enterPassword(password);
    }

    @When("I get the element details")
    public void checkGet() {
        techBeamers.checkGet();
    }

    @When("I select the country {string}")
    public void iSelectTheCountry(String country) {
        techBeamers.selectCountry(country);
    }

    @And("I select the language {string}")
    public void iSelectTheLanguage(String language) {
        techBeamers.selectLanguage(language);
    }

    @And("I submit the form")
    public void iSubmitTheForm() {
        techBeamers.clickSubmit();
    }

    @Then("I verify the submission works")
    public void iVerifyTheLoginWorks() {
        techBeamers.verifyLogin();
    }

    @And("I select the following interests:")
    public void iSelectTheFollowingInterests(List<Interests> interestList) {
            techBeamers.selectInterests(interestList.getFirst());
        }

    @And("I have the following gender:")
    public void iHaveTheFollowingGender(String gender) {
        String genderCamel = toCamelCase(gender);
        techBeamers.selectGender(TestEnum.Gender.valueOf(genderCamel));
    }
}
