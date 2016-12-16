package me.bbb1991.helpers;

import org.springframework.http.HttpStatus;

/**
 * Created by bbb1991 on 12/16/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

public class CustomException extends RuntimeException {

    private HttpStatus httpStatus;

    public CustomException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
