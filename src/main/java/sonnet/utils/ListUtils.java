package sonnet.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static sonnet.utils.Checks.throwOnNull;
import static sonnet.utils.Checks.throwOnNullOrEmpty;


public class ListUtils {

    /**
     * Compares two lists of strings.
     *
     * @param expected the expected list
     * @param actual   the actual list
     * @return true if the lists match by content and order
     */
    public static boolean identical(List<String> expected, List<String> actual) {
        throwOnNull(expected, "Expected list is null");
        throwOnNull(actual, "Actual list is null");

        if (expected.size() != actual.size()) {
            return false;
        }
        return expected.equals(actual);
    }

    /**
     * Compares two lists of strings.
     *
     * @param expected the expected list
     * @param actual   the actual list
     * @return true if the lists match by content but not necessarily order
     */
    public static boolean comparable(List<String> expected, List<String> actual) {
        throwOnNull(expected, "Expected list is null");
        throwOnNull(actual, "Actual list is null");

        if (expected.size() != actual.size()) {
            return false;
        }
        List<String> sortedExpected = new ArrayList<>(expected);
        List<String> sortedActual = new ArrayList<>(actual);
        Collections.sort(sortedExpected);
        Collections.sort(sortedActual);
        return sortedExpected.equals(sortedActual);
    }

    /**
     * Compares two lists of strings.
     *
     * @param expected the expected list
     * @param actual   the actual list
     * @return true if the expected list is present in the actual list, regardless of order
     */

    public static boolean subset(List<String> expected, List<String> actual) {
        throwOnNull(expected, "Expected list is null");
        throwOnNull(actual, "Actual list is null");
        boolean subset = false;

        if (expected.size() != actual.size()) {
            return false;
        }
        int expectedListElementCount = 0;
        for(String expectedElement : expected) {
            if (actual.contains(expectedElement)) {
                expectedListElementCount++;
                if(expectedListElementCount == expected.size()) {
                    subset = true;
                }
            }
        }
        return subset;
    }

    /**
     * Compares two lists of strings.
     *
     * @param expected the expected list
     * @param actual   the actual list
     * @return true if the actual list is reverse of the expected list
     */
    public static boolean reversed(List<String> expected, List<String> actual) {
        throwOnNull(expected, "Expected list is null");
        throwOnNull(actual, "Actual list is null");

        if (expected.size() != actual.size()) {
            return false;
        }

        List<String> reversed = expected.reversed();
        return actual.equals(reversed);
    }

    /**
     * Checks whether a string is present in a list.
     *
     * @param item    the string to search for
     * @param list    the list to search
     * @return true if the condition holds
     */
    public static boolean presentInList(String item, List<String> list) {
        throwOnNull(item, "Item is null");
        throwOnNull(list, "List is null");

        return list.contains(item);
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
            throw new IllegalArgumentException("Unable to aggregate");
        }
        return result;
    }

}
