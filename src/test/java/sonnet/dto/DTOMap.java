package dto;

import com.risknarrative.qa.automation.playwright.CommonPageObject;

import java.util.Map;
import java.util.Objects;

import static utils.StringUtils.*;

public class DTOMap {

    CommonPageObject cpo = new CommonPageObject();

    private Map<String, String> map;
    private final int ALPHA_GEN_START_INDEX = 10;
    private final int NUM_GEN_START_INDEX = 8;

    // These are accessed in the Feature test
    public final static int     DEFAULT_GEN_LEN = 8;
    public final static String  DEFAULT_ALPHA_GEN = "GEN_ALPHA";
    public final static String  DEFAULT_NUM_GEN = "GEN_NUM";
    public final static String  USR_ALPHA_GEN = "GEN_ALPHA_";
    public final static String  USR_NUM_GEN = "GEN_NUM_";
    public final static int     NUM_GEN_MAX = 255;


    public DTOMap(Map map) {
        this.map = map;
    }

    public String get(String key) {
        String value;
        value = map.get(key);
        return Objects.requireNonNullElse(generateOnRequest(value), "");
    }

    private String generateOnRequest(String value) {
        if(isNull(value))
            return value;

        if (value.equalsIgnoreCase(DEFAULT_ALPHA_GEN)) {
            return randomAlphabetic(DEFAULT_GEN_LEN);
        }
        if (value.equalsIgnoreCase("#USERNAME#")) {
            return Objects.requireNonNull(cpo.retrieve("USERNAME"), "USERNAME is not set in the runtime store. Ensure login has occurred before using #USERNAME# in feature files");
        }
        if (value.equalsIgnoreCase(DEFAULT_NUM_GEN)) {
            return randomNumeric(DEFAULT_GEN_LEN);
        }
        try {
            if (value.toUpperCase().startsWith(USR_ALPHA_GEN)) {
                int length = Integer.parseInt(value.substring(ALPHA_GEN_START_INDEX, value.length()));
                return (randomAlphabetic(length > NUM_GEN_MAX ? NUM_GEN_MAX : length ));
            }
            if (value.toUpperCase().startsWith(USR_NUM_GEN)) {
                int length = Integer.parseInt(value.substring(NUM_GEN_START_INDEX, value.length()));
                return (randomNumeric(length > NUM_GEN_MAX ?  NUM_GEN_MAX : length ));
            }
        }
        catch(NumberFormatException nfe) {
            cpo.log("DTOMap", "Feature GEN value is not a number. ", "Format is :");
            cpo.log("Alpha", "GEN_ALPHA_[n]");
            cpo.log("Numeric", "GEN_NUM_[n]");
            cpo.log("Where [n] is a three digit number.  Values over "+NUM_GEN_MAX+" will be defaulted to "+NUM_GEN_MAX+" characters");
            throw new NumberFormatException(nfe.getMessage());
        }
        return value;  // Unchanged
    }
}
