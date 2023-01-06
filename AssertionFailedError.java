

public class AssertionFailedError extends AssertionError {
    String m;

    public AssertionFailedError(String message) {
        super(message);
    }
    public AssertionFailedError(String message, Throwable th) {
        super(message, th);
    }
    public AssertionFailedError(Exception e) {
        super(e);
    }
}

