package com.code.challenge.domain;

public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException(String eidr) {
        super("The movie with EIDR " + eidr + " was not found.");
    }
}
