package com.salvatore.dipalo.movies.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateResourseException extends RuntimeException {

    public DuplicateResourseException() {
        super();
    }

    public DuplicateResourseException(String msg) {
        super(msg);
    }

}
