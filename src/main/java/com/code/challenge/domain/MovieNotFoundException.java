package com.code.challenge.domain;

public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException(String eidr) {
        super("The book with ISBN " + eidr + " was not found.");
    }
}
