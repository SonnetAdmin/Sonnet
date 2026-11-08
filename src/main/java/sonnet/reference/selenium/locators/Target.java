package sonnet.reference.selenium.locators;

import lombok.Getter;
import org.openqa.selenium.By;
@Getter
public class Target {

    public enum Type {
        ID,
        TEXT,
        PARTIAL_TEXT,
        TAG_WITH_TEXT,
        TAG_CONTAINS_TEXT,
        TAG_WITH_ID,
        TAG_WITH_CLASS,
        TAG_WITH_TITLE,
        TAG_WITH_NAME,
        TAG_WITH_VALUE,
        CLASS,
        VALUE,
        CLASSES,
        CSS,
        TAG,
        NAME,
        PLACEHOLDER,
        LINK_TEXT,
        PARTIAL_LINK_TEXT,
        DATA_ATTRIBUTE,
        XPATH,
        ROOT,
        LEAF,
        CHILDREN,
        SIBLINGS,
        ATTRIBUTE,
        ROLE,
        TITLE,
        HREF,
        SRC,
        STYLE,
        ALT,
        TYPE,
        DATA_ENDPOINT,
        DATA_FIELD,
        DATA_TEST_ID,
        DATA_ICON_NAME,
        SPLICE;

    }

    public enum AlertAction {
        ACCEPT,
        DISMISS;
    }

    private By by;
    private String key;
    private String value;
    private Type type;

    // Constructor for locators with By
    public Target(By by, String value, Type type) {
        this.by = by;
        this.value = value;
        this.type = type;
    }

    // Constructor for locators with By + K/V pair
    public Target(By by, String key, String value, Type type) {
        this.by = by;
        this.key = key;
        this.value = value;
        this.type = type;
    }
}
