package com.code.challenge.domain.dto;

import com.code.challenge.domain.Movie;

public record Vote(String voteType) {
    public static Vote of(String voteType) {
        return new Vote(voteType);
    }
}
