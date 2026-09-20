package sonnet.po;

import sonnet.CommonPageObject;
import sonnet.Target;
import sonnet.dto.Interests;
import sonnet.testenum.TestEnum;
import sonnet.utils.Logger;

import static sonnet.CommonPageInterface.Trait.*;
import static sonnet.Enum.HTMLTag.OPTION;
import static sonnet.Enum.HTMLTag.SELECT;
import static sonnet.utils.StringUtils.isTrue;

public class UltimateQA extends CommonPageObject {
    private final Target CAR_SELECT = tag(SELECT);
    public UltimateQA() {}

    public void chooseCar(String car) {
        focus(CAR_SELECT).click();
        collect(tag(OPTION));
        choose(car).click();
        log("");
    }
}