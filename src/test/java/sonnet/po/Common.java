package sonnet.po;

import sonnet.CommonPageObject;

public class Common extends CommonPageObject {
    public void openBrowser() {
        open();
    }

    public void navigate(String url) {
        go(url);
    }
}
