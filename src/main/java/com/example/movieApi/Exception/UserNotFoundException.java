package com.example.movieApi.Exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
      super(message);
    }
}
