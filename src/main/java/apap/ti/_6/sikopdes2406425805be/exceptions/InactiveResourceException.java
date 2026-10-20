package apap.ti._6.sikopdes2406425805be.exceptions;

import org.springframework.http.HttpStatus;

public class InactiveResourceException extends AppException {

    public InactiveResourceException(String message) {
        super(message, HttpStatus.CONFLICT);
    }

    public InactiveResourceException(String resource, Object identifier) {
        super(resource + " " + identifier + " sudah nonaktif", HttpStatus.CONFLICT);
    }
}