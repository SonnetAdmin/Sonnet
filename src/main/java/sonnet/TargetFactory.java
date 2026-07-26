package sonnet;

import java.util.List;

public class TargetFactory {
    public Target id(String id) {
        return new Target("#" + id, Enum.Type.ID);
    }
    public Target classname(String classname) {
        return new Target("." + classname, Enum.Type.CLASS);
    }
    public Target classnames(List<String> classnames) {
        StringBuilder sb = new StringBuilder();
        sb.append(".");
        for (String cls : classnames) {
            sb.append(cls);
            sb.append(".");
        }
        sb.deleteCharAt(sb.length() - 1);
        return new Target(sb.toString(), Enum.Type.CLASS);
    }
}
