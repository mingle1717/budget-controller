package learn.budget.controllers;

import learn.budget.domain.Result;
import learn.budget.domain.ResultType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorResponse {



    private final String s;
    public ErrorResponse(String s) {
        this.s = s;
    }

    public static <T> ResponseEntity<Object> build(Result<T> result) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (result.getType() == null) {
            status = HttpStatus.BAD_REQUEST;
        } else if (result.getType() == ResultType.NOT_FOUND) {
            status = HttpStatus.NOT_FOUND;
        } else if (result.getType() == ResultType.FORBIDDEN){
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<>(result.getMessages(), status);
    }

}

