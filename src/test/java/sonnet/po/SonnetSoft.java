package sonnet.po;

import com.microsoft.playwright.options.AriaRole;
import sonnet.CommonPageObject;
import sonnet.Enum;
import sonnet.Target;
import sonnet.dto.SonnetSoftContactInfo;
import sonnet.dto.SonnetSoftInfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static sonnet.Enum.HTMLTag.*;
import static sonnet.Enum.Trait.TEXT;


public class SonnetSoft extends CommonPageObject {
    private final Target TITLE = (tag(Enum.HTMLTag.TITLE));
    private final Target USERNAME = (label("Username:"));
    private final Target PASSWORD = (label("Password:"));
    private final Target LOGIN = (ariaRole(AriaRole.BUTTON, "Login"));
    private final Target CONTACT_US = (ariaRole(AriaRole.BUTTON, "Contact Us"));
    private final Target FULL_NAME = (label("Full Name:"));
    private final Target EMAIL = (label("Email:"));
    private final Target MESSAGE = (label("Message:"));
    private final Target SUBMIT = (ariaRole(AriaRole.BUTTON, "Submit"));
    private final Target MESSAGE_RECEIVED = tagWithText(H1, "Message Received");



    public SonnetSoft() {}

    public void checkTitle(String title) {
        focus(TITLE);
        assertEquals(title, get(TEXT));
    }

    public void inputInfo(SonnetSoftInfo info) {
        focus(USERNAME).compose(info.getUsername());
        focus(PASSWORD).compose(info.getPassword());
    }

    public void clickLogin() {
        focus(LOGIN).click();
    }

    public void clickContactUs() {
        focus(CONTACT_US).click();
    }

    public void inputContactInfo(SonnetSoftContactInfo info) {
        focus(FULL_NAME).compose(info.getFullName());
        focus(EMAIL).compose(info.getEmail());
        focus(MESSAGE).compose(info.getMessage());
    }

    public void clickSubmit() {
        focus(SUBMIT).click();
    }
}
