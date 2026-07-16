package sonnet.utils;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static sonnet.utils.Colours.*;

public class LoggerImpl {
    private static LoggerImpl instance = null;

    private static Logger logger = null;

    public static synchronized LoggerImpl getInstance() {
        if (instance == null) {
            instance = new LoggerImpl();
        }
        return instance;
    }

    public LoggerImpl() {
        super();
        logger = Logger.getLogger(this.getClass().getName());
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
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
        if (args != null && args.length > 0) {
            logger.info(GREEN + args[0] + RESET + TAB +
                    (args.length > 1 ? MAGENTA + args[1] : "") + RESET +
                    (args.length > 2 ? BLUE + args[2] : "") + RESET +
                    (args.length > 3 ? YELLOW + args[3] : "" + RESET));
        }
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
