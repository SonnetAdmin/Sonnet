package sonnet.steps;

import io.cucumber.java.DataTableType;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.*;
import sonnet.dto.DTOMap;
import sonnet.dto.SonnetSoftContactInfo;
import sonnet.dto.SonnetSoftInfo;
import sonnet.po.SonnetSoft;

import java.util.List;
import java.util.Map;

public class SonnetSoftSteps {
    SonnetSoft sonnetSoft = new SonnetSoft();

    @DataTableType
    public SonnetSoftInfo decodeSonnetInfo(Map<String, String> row) {
        return SonnetSoftInfo.decode(new DTOMap(row));
    }

    @DataTableType
    public SonnetSoftContactInfo decodeSonnetContactInfo(Map<String, String> row) {
        return SonnetSoftContactInfo.decode(new DTOMap(row));
    }

    @When("I check the title is {string}")
    public void iCheckTheTitleIs(String title) {
        sonnetSoft.checkTitle(title);
    }

    @And("I input the following username and password:")
    public void iInputTheFollowingUsernameAndPassword(List<SonnetSoftInfo> sonnetInfo) {
        sonnetSoft.inputInfo(sonnetInfo.getFirst());
    }

    @And("I click login")
    public void iClickLogin() {
        sonnetSoft.clickLogin();
    }

    @And("I click Contact Us")
    public void iClickContactUs() {
        sonnetSoft.clickContactUs();
    }

    @And("I input the following contact details:")
    public void iInputTheFollowingContactDetails(List<SonnetSoftContactInfo> sonnetContactInfo) {
        sonnetSoft.inputContactInfo(sonnetContactInfo.getFirst());
    }

    @And("I click the contact by phone checkbox")
    public void iClickTheContactByPhoneCheckbox() {
        sonnetSoft.clickContactCheckbox();
    }

    @And("I input the phone number {string}")
    public void iInputThePhoneNumber(String number) {
        sonnetSoft.inputPhoneNumber(number);
    }

    @And("I click Submit")
    public void iClickSubmit() {
        sonnetSoft.clickSubmit();
    }

}
