package sonnet.utils;

public class Time {
    public static void pause(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        }
        catch (InterruptedException ignored) {}
    }
}