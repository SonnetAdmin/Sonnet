package sonnet.po;

import sonnet.CommonPageObject;
import sonnet.Target;

import static sonnet.Enum.HTMLTag.H1;

public class Common extends CommonPageObject {
    public void openBrowser() {
        open();
    }

    public void navigate(String url) {
        go(url);
    }

}
