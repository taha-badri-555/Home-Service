package ir.maktabsharif.final_project_taha_badri.util;

import ir.maktabsharif.final_project_taha_badri.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ExpertUpdateBlockedDueToActiveOrdersException.class)
    public ResponseEntity<String> handleExpertUpdateBlockedDueToActiveOrdersException
            (ExpertUpdateBlockedDueToActiveOrdersException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(ImageLengthOutOfBoundException.class)
    public ResponseEntity<String> handleImageLengthOutOfBoundException
            (ImageLengthOutOfBoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<String> handleInsufficientBalanceException
            (InsufficientBalanceException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<String> handleInvalidRequestException
            (InvalidRequestException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserWithSameEmailExistsException.class)
    public ResponseEntity<String> handleExpertUpdateBlockedDueToActiveOrdersException
            (UserWithSameEmailExistsException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnverifiedExpertException.class)
    public ResponseEntity<String> UnverifiedExpertException
            (UnverifiedExpertException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException
            (Exception e){
        return new ResponseEntity<>(
                "Unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
