package dz.me.dashboard.utils;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dz.me.dashboard.models.ErrorDetails;

/**
 *
 * @author ABDELLATIF ESSELAMI
 */
public class ResponseEntityUtils {

    public static ResponseEntity<?> ExceptionResponseEntity(String message, int status) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setError(HttpStatus.valueOf(status).name());
        errorDetails
                .setMessage(message);
        errorDetails.setStatus(status);
        errorDetails.setTimestamp(new Date());
        return new ResponseEntity<>(errorDetails, HttpStatus.valueOf(status));

    }

}
