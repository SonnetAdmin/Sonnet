package sonnet.steps;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import sonnet.po.Common;

import static sonnet.utils.Time.pause;

public class CommonSteps {
    Common common = new Common();

    @Given("I open a browser")
    public void iOpenABrowser() {
        common.openBrowser();
    }

    @Given("I navigate to {string}")
    public void iGoTo(String url) {
        common.navigate(url);
    }

    @Given("I wait for {int} seconds")
    public void iWaitForSeconds(int seconds) {
        pause(seconds);
    }

}
