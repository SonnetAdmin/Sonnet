package sonnet.reference.selenium;

import static java.util.Objects.isNull;

/**
 * Simple utility class for measuring elapsed time between events.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     StopWatch sw = new StopWatch();
 *     sw.start();
 *     // ... code to time ...
 *     sw.stop();
 *     System.out.println(sw.printElapsed());
 * </pre>
 */
public class StopWatch {
    private long startTime = 0L;
    private long endTime = 0L;

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        endTime = System.currentTimeMillis();
    }

    public String printElapsed() {
        if (isNull(startTime) || isNull(endTime)) {
            throw new IllegalStateException("StopWatch : Both start() and stop() must be called and in that order");
        }
        return (endTime - startTime) + " milliseconds.";
    }
}
