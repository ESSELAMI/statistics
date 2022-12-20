package dz.me.dashboard.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dz.me.dashboard.utils.ResponseEntityUtils;

/**
 *
 * @author ABDELLATIF ESSELAMI
 */
@ControllerAdvice
public class ResourceExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED.value());
    }
}