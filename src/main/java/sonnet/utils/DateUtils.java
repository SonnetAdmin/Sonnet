package sonnet.utils;

import java.util.Date;

public class DateUtils {

    public static boolean compareDates(Date date1, Date date2){
        return  date1.equals(date2);
    }
    public static int diffSeconds(Date date1, Date date2) {
        int seconds1;
        int seconds2;
        seconds1 = date1.getSeconds();
        seconds2 = date2.getSeconds();
        return seconds2 - seconds1;
    }

    public static int diffSeconds(Date date) {
        int seconds1;
        int seconds2;
        Date timeNow = new Date();
        seconds1 = date.getSeconds();
        seconds2 = timeNow.getSeconds();
        return seconds2 - seconds1;
    }

    public static int diffMinutes(Date date) {
        int minutes1;
        int minutes2;
        Date timeNow = new Date();
        minutes1 = date.getMinutes();
        minutes2 = timeNow.getMinutes();
        return minutes2 - minutes1;
    }

}
