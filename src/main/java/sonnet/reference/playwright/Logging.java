package sonnet.reference.playwright;


import sonnet.reference.playwright.Enums.LogStatus;
import sonnet.reference.playwright.utils.Colours;
import sonnet.reference.playwright.utils.VMArgs;
import lombok.Getter;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static sonnet.reference.playwright.Enums.LogStatus.OFF;
import static sonnet.reference.playwright.Enums.LogStatus.ON;
import static sonnet.reference.playwright.utils.Checks.throwOnNull;
import static sonnet.reference.playwright.utils.Checks.throwOnNullOrEmpty;
import static sonnet.reference.playwright.utils.Colours.*;
import static sonnet.reference.playwright.utils.Colours.RESET;

public class Logging {
    // Java Util Logger
    private Logger logger = null;

    static Logging instance;
    @Getter
    private LogStatus logStatus;

    public static synchronized Logging getInstance () {
        if (instance == null) {
            instance = new Logging();
        }
        return instance;
    }

    public Logging() {
        if(VMArgs.isLogged()) {
            logStatus = ON;
        } else {
            logStatus = OFF;
        }
        logger = Logger.getLogger(this.getClass().getName());
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
    }

    public void log(String ... args ) {
        if (getLogStatus().equals(OFF)) {
            return;
        }
        info(args);
    }

    public void log(Colours colour, String text) {
        if (getLogStatus().equals(OFF)) {
            return;
        }
        info(colour, text);
    }

    public void warn( String text) {
        logger.warning(text);
    }

    /**
     * Switch ON or OFF logging. Reason is not provided
     * @param logStatus
     */

    public void log(LogStatus logStatus) {
        throwOnNull(logStatus, "Log Status must be defined");
        // Return on setting status to current status : OFF -> OFF or ON -> ON
        if(logStatus.equals(this.logStatus)) {
            return;
        }
        if (logStatus.equals(OFF)) {
            this.logStatus = logStatus;
        } else {
            this.logStatus = logStatus;
        }
    }

    /**
     * Switch ON or OFF logging.  Reason is provided
     * @param logStatus
     * @param reason
     */
    public void log(LogStatus logStatus, String reason) {
        throwOnNull(logStatus, "Log Status must be defined");
        // Return on setting status to current status : OFF -> OFF or ON -> ON
        if (logStatus.equals(this.logStatus)) {
            return;
        }
        throwOnNullOrEmpty(reason, "Log Status change - reason should be specified");

        if (logStatus.equals(OFF)) {
            // Log before switching off
            info("Logger : ", logStatus.name(), reason);
            this.logStatus = logStatus;
        } else {
            this.logStatus = logStatus;
            info("Logger : ", logStatus.name(), reason);
        }
    }

    private void info(String... args) {
        if (args != null && args.length > 0) {
            logger.info(GREEN + args[0] + RESET + TAB +
                    (args.length > 1 ? MAGENTA + args[1] : "") + RESET + TAB +
                    (args.length > 2 ? BLUE + args[2] : "") + RESET + TAB +
                    (args.length > 3 ? YELLOW + args[3] : "" + RESET));
        }
    }

    private void info(Colours color, String text) {
        logger.info(color + text + RESET);
    }

    static class SimpleFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {
            return record.getMessage() + "\n"; // Return only the message, no date
        }
    }
}
