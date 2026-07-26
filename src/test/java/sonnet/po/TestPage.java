package sonnet.po;

import sonnet.CommonPage;
import sonnet.Target;
import sonnet.dto.Interests;
import sonnet.utils.Logger;
import static sonnet.utils.StringUtils.isTrue;
import static sonnet.CommonPageInterface.Trait.*;

public class TestPage extends CommonPage {
    private final Target COOKIES = id("ez-accept-all");
    private final Target EMAIL = id("email");
    private final Target USERNAME = id("username");
    private final Target PASSWORD = id("password");
    private final Target SPORTS = id("sports");
    private final Target MUSIC = id("music");
    private final Target READING = id("reading");
    private final Target TRAVEL = id("travel");
    private final Target SUBMIT = id("submit-btn");
    private final Target SUBMITTED = id("form-result");

    Logger logger = new Logger();
    public TestPage() {}

    public void openBrowser() {
        open();
    }

    public void navigate(String url) {
        go(url);
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
        focus(id("country")).click();
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
