package sonnet.reference.selenium.logger;

import sonnet.reference.selenium.utils.Colours;

public interface LoggerInterface {

    void log(String ... args );
    void log(Colours colour, String text);
    void warn( String text);
}
