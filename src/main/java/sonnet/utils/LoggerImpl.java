package sonnet.utils;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static sonnet.utils.Colours.*;

public class LoggerImpl {
    private static LoggerImpl instance = null;

    private static Logger logger = null;

    public LoggerImpl() {
        super();
        logger = Logger.getLogger(this.getClass().getName());
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
    }

    public static synchronized LoggerImpl getInstance() {
        if (instance == null) {
            instance = new LoggerImpl();
        }
        return instance;
    }

    public void log(String... args) {
        info(args);
    }

    public void log(Colours colour, String text) {
        info(colour, text);
    }

    public void warn(String text) {
        logger.warning(text);
    }


    private void info(String... args) {
        if (args == null || args.length == 0) {
            return;
        }

        String a0 = args[0] != null ? args[0] : "";
        String a1 = args.length > 1 && args[1] != null ? args[1] : "";
        String a2 = args.length > 2 && args[2] != null ? args[2] : "";
        String a3 = args.length > 3 && args[3] != null ? args[3] : "";
        String a4 = args.length > 4 && args[4] != null ? args[4] : "";

        logger.info(String.format(
                        GREEN        + "%-12s" + RESET +
                        MAGENTA_BOLD + "%-24s" + RESET +
                        BLUE         + "%-12s" + RESET +
                        YELLOW_BOLD  + "%-30s" + RESET +
                        CYAN         + "%s"    + RESET,
                a0, a1, a2, a3, a4
        ));
    }

    private void info(Colours color, String text) {
        logger.info(color + text + RESET);
    }


    class SimpleFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {
            return record.getMessage() + "\n"; // Return only the message, no date
        }
    }
}
