package apap.ti._6.sikopdes2406425805be.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends AppException {

    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(String resource, Object id) {
        super(resource + " dengan id " + id + " tidak ditemukan", HttpStatus.NOT_FOUND);
    }
}