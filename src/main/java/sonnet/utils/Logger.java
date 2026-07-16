package sonnet.utils;


public class Logger {

    LoggerImpl loggerImpl = LoggerImpl.getInstance();

    public void log(String ... args ) {
        loggerImpl.log(args);
    }

    public void log(Colours colour, String text) {
        loggerImpl.log(colour, text);
    }

    public void warn( String text) {
        loggerImpl.warn(text);
    }
}
