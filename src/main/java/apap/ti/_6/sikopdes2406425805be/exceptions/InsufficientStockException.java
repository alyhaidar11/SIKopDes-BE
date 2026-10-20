package apap.ti._6.sikopdes2406425805be.exceptions;

import org.springframework.http.HttpStatus;

public class InsufficientStockException extends AppException {

    public InsufficientStockException(String namaProduk, int tersedia, int diminta) {
        super("Stok " + namaProduk + " tidak mencukupi (tersedia: "
                + tersedia + ", diminta: " + diminta + ")", HttpStatus.CONFLICT);
    }

    public InsufficientStockException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}