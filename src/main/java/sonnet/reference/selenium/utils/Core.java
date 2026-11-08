package sonnet.reference.selenium.utils;

import sonnet.reference.selenium.CPOException;

import java.util.List;

import static sonnet.reference.selenium.utils.StringUtils.isNullOrEmpty;

public class Core {

    public static void throwOnZeroOrMinus(int... indexes) {
        for (int i : indexes) {
            if (i <= 0) {
                throw new CPOException("Invalid Index");
            }
        }
    }


    public static void throwOnZeroOrMinus(int i) {
        if(i <= 0 ) {
            throw new CPOException("Invalid Index");
        }
    }

    public static void throwOnNullOrEmpty(String s) {
        if(isNullOrEmpty(s)) {
            throw new CPOException("Empty String");
        }
    }

    public static void throwOnNullOrEmpty(List list) {
        if(isNullOrEmpty(list)) {
            throw new CPOException("Empty String");
        }
    }

    public static void fling(String message) {
            throw new CPOException(message);
    }
}
