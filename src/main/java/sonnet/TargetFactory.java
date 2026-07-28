package sonnet;

import static sonnet.Enum.Type.*;


public class TargetFactory {
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
}

