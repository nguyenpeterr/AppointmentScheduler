package exception;

public class AppointmentOverlapException extends RuntimeException {
    public AppointmentOverlapException(Throwable cause) {
        super(cause);
    }
}
