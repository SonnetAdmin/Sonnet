package sonnet;

public class SonnetException extends RuntimeException {
    String message;
    public SonnetException(String message) {
        super(this.message = message);
    }
    public String getMessage(){
        return message;
    }
}
