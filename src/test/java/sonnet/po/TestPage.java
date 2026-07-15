package org.sonnet.po;

import org.sonnet.CommonPage;

import static org.sonnet.CommonPageInterface.Trait.*;
import static org.sonnet.utils.Print.print;

public class TestPage extends CommonPage {
    public TestPage() {

    }

    public void openBrowser() {
        open();
    }

    public void navigate(String url) {
        go(url);
        expect("#ez-accept-all").click();
    }

    public void enterName(String name) {
        focus("#username").compose(name);
    }

    public void enterEmail(String email) {
        focus("#email").compose(email);

    }

    public void enterPassword(String password) {
        focus("#password").compose(password);
    }

    public void checkGet() {
        print(get(TEXT));
        print(get(TAG));
        print(get(XPOS));
        print(get(YPOS));
        print(get(WIDTH));
        print(get(HEIGHT));
        print(get(ENABLED));
        print(get(VISIBLE));
    }

    public void selectCountry(String country) {
        focus("#country").click();
        sleep(2);
        collect("option");
        printElements();
        choose(2);
        printElement();
        click();
    }

    public void clickSubmit() {
        focus("#submit-btn").click();
    }

    public void verifyLogin() {

    }


}
