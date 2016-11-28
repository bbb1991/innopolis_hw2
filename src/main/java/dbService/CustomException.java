package dbService;

/**
 * Created by bbb1991 on 11/23/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class CustomException extends Exception {

    private String message;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
