package sonnet;

import static sonnet.Enum.Type.*;
import com.microsoft.playwright.options.AriaRole;


public abstract class TargetFactory {
    public Target id(String id) {
        return new Target("#" + id ,ID ,id);
    }

    public Target classname(String classname) {
        return new Target("." + classname ,CLASS ,classname);
    }

    public Target classnames(String... classnames) {
        StringBuilder sb = new StringBuilder();
        sb.append(".");
        for (String cls : classnames) {
            sb.append(cls);
            sb.append(".");
        }
        sb.deleteCharAt(sb.length() - 1);
        return new Target(sb.toString() ,CLASSES ,sb.toString());
    }

    public Target name(String name) {
        return new Target("[name='" + name + "']",NAME ,name);
        }

    public Target tagWithText(Enum.HTMLTag tag, String text) {
        return new Target("//" + tag.name().toLowerCase() + "[contains(normalize-space(text()), '" + text + "')]" ,TAG_WITH_TEXT ,text);
    }

    public static Target splice(String prefix, String suffix) {
        return new Target("//*[starts-with(text(), '" + prefix + "') and contains(text(), '" + suffix + "')]", prefix, suffix, SPLICE);
    }

    public Target placeholder(String placeholder) {
        return new Target(placeholder ,PLACEHOLDER ,placeholder);
    }

    public Target linkText(String text) {
        return new Target("a:text('" + text + "')" ,LINK_TEXT ,text);
    }

    public Target label(String label) {
        return new Target(label ,LABEL ,label);
    }

    public Target alt(String altText) {
        return new Target(altText ,ALT ,altText);
    }

    public Target title(String title) {
        return new Target(title ,TITLE ,title);
    }

    public Target tag(Enum.HTMLTag tag) {
        return new Target(tag.name().toLowerCase() ,TAG ,tag.name().toLowerCase());
    }

    protected static Target ariaRole(AriaRole ariaRole) {
        return new Target(ariaRole, ROLE);
    }

    protected static Target ariaRole(AriaRole ariaRole, String label) {
        return new Target(ariaRole, label, ROLE_AND_LABEL);
    }


}

