package sonnet.performables;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import sonnet.Performable;

import static sonnet.Enum.Action.Enter;
import static sonnet.Enum.HTMLTag.DIV;

public class SearchGoogle extends Performable {
    private final String URL = "https://www.google.com";
    private String criteria;

    public SearchGoogle() {}

    public SearchGoogle(String criteria) {
        this.criteria = criteria;
    }

    @Override
    public void run() {
        open();
        Page page = getPage();
        page.navigate(URL);
        focus(tagWithText(DIV, "Accept all"));
        click();
        end();
        focus(ariaRole(AriaRole.COMBOBOX));
        click();
        compose(criteria);          //TODO CHECK COMMON-PAGE
        compose(Enter);             //TODO CHECK COMMON-PAGE-INTERFACE
        System.out.println("");
    }

    @Override
    public String getDescription() {
        return "Navigating to " + URL;
    }
}
