package au.com.goed.starter.demo.stuff;

public class StuffException extends Exception {

    private static final long serialVersionUID = -5388092818465859702L;

    public StuffException(String message) {
        super(message);
    }
    
    public StuffException(String message, Throwable t) {
        super(message, t);
    }
}
