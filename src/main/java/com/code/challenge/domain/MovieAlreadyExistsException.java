package com.code.challenge.domain;

public class MovieAlreadyExistsException extends RuntimeException{
    public MovieAlreadyExistsException(String eidr) {
        super("A book with EIDR " + eidr + " already exists.");
    }
}
