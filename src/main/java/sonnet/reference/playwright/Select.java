package sonnet.reference.playwright;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;
import sonnet.reference.playwright.locators.Target;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Wrapper for HTML5 {@code <select>} elements (single and multi-select dropdowns).
 *
 * <h3>Usage</h3>
 * <pre>{@code
 *   Select select = new Select(id("days"));
 *   select.select();                   // selects the first option
 *   select.select(Enums.Index.SECOND); // selects by Index enum
 * }</pre>
 *
 * @since March 2026
 */
public class Select {

    private final Logging logger = Logging.getInstance();
    private final Locator locator;

    public Select(Target target) {
        Page page = CommonPage.getInstance().getPage();
        this.locator = page.locator(target.getSelector());
    }

    public void select() {
        log("select()", "first option");
        locator.selectOption(new SelectOption().setIndex(0));
    }

    public void select(int... indices) {
        log("select()", "indices: " + java.util.Arrays.toString(indices));
        SelectOption[] options = new SelectOption[indices.length];
        for (int i = 0; i < indices.length; i++) {
            options[i] = new SelectOption().setIndex(indices[i]);
        }
        locator.selectOption(options);
    }

    public void select(Enums.Index... indices) {
        int[] ordinals = new int[indices.length];
        for (int i = 0; i < indices.length; i++) {
            ordinals[i] = indices[i].ordinal();
        }
        log("select()", "Index: " + java.util.Arrays.toString(indices));
        select(ordinals);
    }

    public void select(Target target) {
        log("select()", "Target: " + target.getType().name() + " " + target.getValue());
        String label = locator.locator(target.getSelector()).first().textContent();
        locator.selectOption(new SelectOption().setLabel(label));
    }

    public void select(Target... targets) {
        log("select()", "targets: " + targets.length);
        SelectOption[] options = new SelectOption[targets.length];
        for (int i = 0; i < targets.length; i++) {
            String label = locator.locator(targets[i].getSelector()).first().textContent();
            options[i] = new SelectOption().setLabel(label);
        }
        locator.selectOption(options);
    }

    public List<String> getOptions() {
        log("getOptions()", "retrieving available options");
        return locator.locator("option").allTextContents()
                .stream().map(String::trim).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<String> getSelectedValues() {
        log("getSelectedValues()", "retrieving selected option values");
        Object result = locator.evaluate("el => Array.from(el.selectedOptions).map(o => o.value)");
        return (List<String>) result;
    }

    private void log(String... args) {
        logger.log(args);
    }
}
