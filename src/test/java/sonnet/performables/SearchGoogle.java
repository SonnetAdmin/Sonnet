package sonnet.performables;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import sonnet.Enum;
import sonnet.Performable;

import static sonnet.CommonPageInterface.Action.ENTER;

public class SearchGoogle extends Performable {
    private final String URL = "https://www.google.com";
    private String criteria;

    public SearchGoogle(String criteria) {
        this.criteria = criteria;
    }

    @Override
    public void run() {
        open();
        Page page = getPage();
        page.navigate(URL);
        probe(ariaRole(AriaRole.NONE, "Reject all"));
        click();                //TODO FIX LATER
        end();
        focus(ariaRole(AriaRole.COMBOBOX));
        compose(criteria);
        compose(ENTER);
        System.out.println("");
    }

    @Override
    public String getDescription() {
        return "Navigating to " + URL;
    }
}
