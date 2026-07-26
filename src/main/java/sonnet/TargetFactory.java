package sonnet;

import java.util.List;

import static sonnet.Enum.Type.NAME;

public class TargetFactory {
    public Target id(String id) {
        return new Target("#" + id, Enum.Type.ID);
    }

    public Target classname(String classname) {
        return new Target("." + classname, Enum.Type.CLASS);
    }

    public Target classnames(String... classnames) {
        StringBuilder sb = new StringBuilder();
        sb.append(".");
        for (String cls : classnames) {
            sb.append(cls);
            sb.append(".");
        }
        sb.deleteCharAt(sb.length() - 1);
        return new Target(sb.toString(), Enum.Type.CLASS);
    }


    public Target name(String name) {
        return new Target("[name='" + name + "']", Enum.Type.NAME);
        }



    public Target tagWithText(Enum.HTMLTag tag, String text) {
        return new Target("//" + tag.name().toLowerCase() + "[contains(normalize-space(text()), " + text + ")]", Enum.Type.TAG_WITH_TEXT);
    }
}

