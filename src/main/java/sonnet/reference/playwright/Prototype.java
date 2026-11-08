package sonnet.reference.playwright;


import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import static org.junit.Assert.assertTrue;

public class Prototype {

    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions().setHeadless(false);
            Browser browser = playwright.chromium().launch(launchOptions);
            Page page = browser.newPage();
            page.navigate("https://qa.trunarrative.cloud/company/search.aspx");
            assertTrue("URL does not contain 'trunarrative'", page.url().contains("trunarrative"));
            assertTrue("Title does not contain 'Login'", page.title().contains("Login"));
            page.getByPlaceholder("Username").fill("user");
            page.getByRole(AriaRole.BUTTON).click();
            page.getByPlaceholder("Password").fill("password");
            Locator l = page.getByRole(AriaRole.BUTTON);
        }
    }
}
