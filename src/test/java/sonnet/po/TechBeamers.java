package sonnet.po;

import sonnet.CommonPageObject;
import sonnet.Target;
import sonnet.dto.Interests;
import sonnet.testenum.TestEnum;
import sonnet.utils.Logger;

import static sonnet.Enum.HTMLTag.*;
import static sonnet.utils.StringUtils.isTrue;
import static sonnet.CommonPageInterface.Trait.*;

public class TechBeamers extends CommonPageObject {
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
    private final Target COUNTRY = id("country");
    private final Target LANGUAGES = id("languages");


    public TechBeamers() {}

    public void acceptCookies() {
        probe(COOKIES).click().end();
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
        log(get(TEXT));
        log(get(TAG));
        log(get(XPOS));
        log(get(YPOS));
        log(get(WIDTH));
        log(get(HEIGHT));
        log(get(ENABLED));
        log(get(VISIBLE));
    }

    public void selectCountry(String country) {
        focus(COUNTRY).click();
        collect(tag(OPTION));
        choose(country);
        click();
    }

    public void selectLanguage(String language) {
        focus(LANGUAGES);
        collect(tag(OPTION));
        choose(language);
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

    public void selectGender(TestEnum.Gender gender) {
        focus(label(gender.name())).click();
    }
}
