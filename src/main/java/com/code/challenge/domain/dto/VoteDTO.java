package com.code.challenge.domain.dto;

public record VoteDTO(String voteType) {
    public static VoteDTO of(String voteType) {
        return new VoteDTO(voteType);
    }
}
