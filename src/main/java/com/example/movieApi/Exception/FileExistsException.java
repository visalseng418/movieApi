package com.example.movieApi.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class FileExistsException extends RuntimeException{
    public FileExistsException(String message) {
        super(message);
    }
}
