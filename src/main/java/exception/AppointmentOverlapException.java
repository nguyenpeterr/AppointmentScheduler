package exception;

/**
 * AppointmentOverlapException is an exception class I created to handle appointments that overlap each other and extends
 * from the RuntimeException
 */
public class AppointmentOverlapException extends RuntimeException {
    public AppointmentOverlapException(Throwable cause) {
        super(cause);
    }
}
