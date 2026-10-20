package apap.ti._6.sikopdes2406425805be.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends AppException {

    public DuplicateResourceException(String message) {
        super(message, HttpStatus.CONFLICT);
    }

    public DuplicateResourceException(String resource, String field, Object value) {
        super(resource + " dengan " + field + " " + value + " sudah digunakan", HttpStatus.CONFLICT);
    }
}