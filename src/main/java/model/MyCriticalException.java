package model;

public class MyCriticalException extends Exception {
    public MyCriticalException(String message, Throwable cause) {
        super(message + " Please restart the app.", cause);
    }
}
