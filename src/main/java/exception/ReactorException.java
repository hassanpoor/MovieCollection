package exception;

public class ReactorException extends Throwable {
    private Throwable exception;
    private String message;

    public ReactorException(Throwable exception) {
        this.exception = exception;
        this.message = exception.getMessage();
    }
}
