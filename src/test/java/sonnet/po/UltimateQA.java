package sonnet.po;

import sonnet.CommonPageObject;
import sonnet.Target;

import static sonnet.Enum.HTMLTag.OPTION;
import static sonnet.Enum.HTMLTag.SELECT;

public class UltimateQA extends CommonPageObject {
    private final Target CAR_SELECT = tag(SELECT);
    public UltimateQA() {}

    public void chooseCar(String car) {
        focus(CAR_SELECT).click();
        assemble(tag(OPTION));
        choose(car).click();
        log("");
    }
}