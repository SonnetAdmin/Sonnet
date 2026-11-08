package sonnet.reference.playwright.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {

    public static void pause(int seconds) {
        pause((long) seconds * 1000);
    }

    public static void pause(long milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getDateAndTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yy - HH:mm");
        return simpleDateFormat.format(new Date());
    }

    public static String getTimeAndDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm - dd:MM:yy");
        return simpleDateFormat.format(new Date());
    }

    public static String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yy");
        return simpleDateFormat.format(new Date());
    }

    public static String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(new Date());
    }

    public static String getTimeWithSeconds() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }
}
