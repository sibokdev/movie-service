package com.code.challenge.domain;

public class MovieAlreadyExistsException extends RuntimeException{
    public MovieAlreadyExistsException(String eidr) {
        super("A movie with EIDR " + eidr + " already exists.");
    }
}
