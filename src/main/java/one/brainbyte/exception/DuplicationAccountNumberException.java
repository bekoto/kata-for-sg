package one.brainbyte.exception;

public class DuplicationAccountNumberException extends Exception{
    public DuplicationAccountNumberException() {
    }

    public DuplicationAccountNumberException(String message) {
        super(message);
    }

    public DuplicationAccountNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicationAccountNumberException(Throwable cause) {
        super(cause);
    }

    public DuplicationAccountNumberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
