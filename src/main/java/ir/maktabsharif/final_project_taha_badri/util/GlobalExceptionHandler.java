package ir.maktabsharif.final_project_taha_badri.util;

import ir.maktabsharif.final_project_taha_badri.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    public record ApiError(String message, HttpStatus status, LocalDateTime timestamp) {
        public ApiError(Exception e, HttpStatus status) {
            this(e.getMessage(), status, LocalDateTime.now());
        }
    }
    public record ApiError2(String message, LocalDateTime timestamp) {
        public ApiError2(Exception e) {
            this(e.getMessage(), LocalDateTime.now());
        }
    }

    private ResponseEntity<ApiError> handleException(Exception exception, HttpStatus status) {
        return new ResponseEntity<>(new ApiError(exception, status), status);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleExpertUpdateBlockedDueToActiveOrdersException
            (ExpertUpdateBlockedDueToActiveOrdersException e) {
        return handleException(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError2 handleImageLengthOutOfBoundException
            (ImageLengthOutOfBoundException e) {
        return new ApiError2(e);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleInsufficientBalanceException
            (InsufficientBalanceException e) {
        return handleException(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleInvalidRequestException
            (InvalidRequestException e) {
        return handleException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleExpertUpdateBlockedDueToActiveOrdersException
            (UserWithSameEmailExistsException e) {
        return handleException(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> UnverifiedExpertException
            (UnverifiedExpertException e) {
        return handleException(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> SuggestPriceIsLow
            (SuggestPriceIsLow e) {
        return handleException(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> EmailNotFoundException
            (EmailNotFoundException e) {
        return handleException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleException
            (Exception e) {
        return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
