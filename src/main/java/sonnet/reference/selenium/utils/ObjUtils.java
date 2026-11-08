package sonnet.reference.selenium.utils;


import sonnet.reference.selenium.CPOException;

/**
 * Utility class for common object operations, such as null checks.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     boolean isNull = ObjUtils.isNull(null);
 * </pre>
 */
public class ObjUtils {

    public static boolean isNull(Object obj) {
        return obj == null ? true : false;
    }

    public static void throwOnNull(Object obj) {
        if(obj == null) {
            throw new CPOException("Null Object");
        }
    }
}
