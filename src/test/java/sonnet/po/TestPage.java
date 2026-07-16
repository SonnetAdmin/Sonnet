package sonnet.po;

import sonnet.CommonPage;
import sonnet.dto.Interests;
import sonnet.utils.Logger;
import static sonnet.utils.StringUtils.isTrue;
import static sonnet.CommonPageInterface.Trait.*;

public class TestPage extends CommonPage {
    private final String COOKIES = "#ez-accept-all";
    private final String EMAIL = "#email";
    private final String USERNAME = "#username";
    private final String PASSWORD = "#password";
    private final String SPORTS = "#sports";
    private final String MUSIC = "#music";
    private final String READING = "#reading";
    private final String TRAVEL = "#travel";
    private final String SUBMIT = "#submit-btn";
    private final String SUBMITTED = "#form-result";

    Logger logger = new Logger();
    public TestPage() {}

    public void openBrowser() {
        open();
    }

    public void navigate(String url) {
        go(url);
        expect(COOKIES).click();
    }

    public void enterName(String name) {
        focus(USERNAME).compose(name);
    }

    public void enterEmail(String email) {
        focus(EMAIL).compose(email);
    }

    public void enterPassword(String password) {
        focus(PASSWORD).compose(password);
    }

    public void checkGet() {
        logger.log(get(TEXT));
        logger.log(get(TAG));
        logger.log(get(XPOS));
        logger.log(get(YPOS));
        logger.log(get(WIDTH));
        logger.log(get(HEIGHT));
        logger.log(get(ENABLED));
        logger.log(get(VISIBLE));
    }

    public void selectCountry(String country) {
        focus("#country").click();
        sleep(2);
        collect("option");
        choose(country);
        click();
    }

    public void clickSubmit() {
        focus(SUBMIT).click();
    }

    public void verifyLogin() {
        focus(SUBMITTED);
    }

    public void selectInterests(Interests interests) {
        if (isTrue(interests.getSports())) {
            focus(SPORTS).click();
        }
        if (isTrue(interests.getMusic())) {
            focus(MUSIC).click();
        }
        if(isTrue(interests.getReading())) {
            focus(READING).click();
        }
        if (isTrue(interests.getTravel())) {
            focus(TRAVEL).click();
        }
    }
}
