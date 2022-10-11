package learn.budget.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleException(DataAccessException ex) {
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse("Error: Something went wrong in the database."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleNotReadableException(HttpMessageNotReadableException ex) {
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse("Error: Contents of JSON not readable."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> mediaTypeUnsupportedException(HttpMediaTypeNotSupportedException ex) {
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse("Error: This is not a supported media type."), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorResponse> multiPartException(MultipartException ex) {
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse("Error: multiple parts found."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<String>(
                "Error: Your request was unsuccessful.",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


