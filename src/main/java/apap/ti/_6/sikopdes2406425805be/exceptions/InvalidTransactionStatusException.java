package apap.ti._6.sikopdes2406425805be.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidTransactionStatusException extends AppException {

    public InvalidTransactionStatusException(String message) {
        super(message, HttpStatus.CONFLICT);
    }

    public InvalidTransactionStatusException(Object idTransaksi, Object statusSaatIni) {
        super("Transaksi " + idTransaksi + " berstatus " + statusSaatIni
                + " sehingga tidak dapat dibatalkan. Hanya transaksi berstatus SELESAI yang dapat dibatalkan",
                HttpStatus.CONFLICT);
    }
}