package sonnet.reference.selenium.utils;


import sonnet.reference.selenium.CPOException;

import java.util.List;

import static sonnet.reference.selenium.utils.Core.throwOnNullOrEmpty;


/**
 * Utility class for common list operations, such as null or empty checks.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     boolean empty = ListUtils.isNullOrEmpty(myList);
 * </pre>
 */
public class ListUtils {
    /**
     * Checks if a list is null or empty.
     *
     * @param list the list to check
     * @return true if the list is null or empty, false otherwise
     *
     * <b>Example usage:</b>
     * <pre>
     *     boolean empty = ListUtils.isNullOrEmpty(myList);
     * </pre>
     */
    public static boolean isNullOrEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * Aggregate a list where entries contain numeric data
     *
     * @param list  The list of strings
     * @return The aggregate as a double
     */
    public static double aggregate(List<String> list) {
        throwOnNullOrEmpty(list);
        double result = 0.0;
        try {
            for (String entry : list) {
                if(StringUtils.isNullOrEmpty(entry))
                    continue;
                result += Double.parseDouble(entry);
            }
        } catch (NumberFormatException nfe) {
            throw new CPOException("Unable to aggregate");
        }
        return result;
    }


}
