package sonnet;

import static sonnet.Enum.Type.*;


public class TargetFactory {
    public Target id(String id) {
        return new Target("#" + id, ID);
    }

    public Target classname(String classname) {
        return new Target("." + classname, CLASS);
    }

    public Target classnames(String... classnames) {
        StringBuilder sb = new StringBuilder();
        sb.append(".");
        for (String cls : classnames) {
            sb.append(cls);
            sb.append(".");
        }
        sb.deleteCharAt(sb.length() - 1);
        return new Target(sb.toString(), CLASSES);
    }

    public Target name(String name) {
        return new Target("[name='" + name + "']", NAME);
        }

    public Target tagWithText(Enum.HTMLTag tag, String text) {
        return new Target("//" + tag.name().toLowerCase() + "[contains(normalize-space(text()), '" + text + "')]", TAG_WITH_TEXT);
    }

    public Target placeholder(String placeholder) {
        return new Target(placeholder, PLACEHOLDER);
    }

    public Target linkText(String text) {
        return new Target("a:text('" + text + "')", LINK_TEXT);
    }
}

