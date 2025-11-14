package com.Angelh0.stayhub.exception;

import com.Angelh0.stayhub.exception.AccommodationException.AccommodationContainsRoom;
import com.Angelh0.stayhub.exception.RoomException.RoomContainsReservation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        List<FieldError> fileError = e.getBindingResult().getFieldErrors();

        String message = "";

        for (int i = 0; i < fileError.size(); i++) {
            FieldError fieldError = fileError.get(i);
            message += fieldError.getField() + ": " + fieldError.getDefaultMessage();
            if (i < fileError.size() - 1) {
                message += "; ";
            }

        }
        ErrorResponse errorResponse = new ErrorResponse("VALIDATION_ERROR", message, HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccommodationContainsRoom.class)
    public ResponseEntity<ErrorResponse> containsRoom(AccommodationContainsRoom e) {
        ErrorResponse errorResponse = new ErrorResponse("CONTAINS_ROOM",
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(NotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("NOT_FOUND_EXCEPTION",
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoomContainsReservation.class)
    public ResponseEntity<ErrorResponse> containsReservation(RoomContainsReservation e) {
        ErrorResponse errorResponse = new ErrorResponse("CONTAINS_RESERVATION",
                e.getMessage(),
                HttpStatus.CONFLICT,
                LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

}
