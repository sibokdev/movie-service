package com.code.challenge.domain;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;

public record MovieGroupedByYear(
        String title,
        @NotNull(message = "The Movie release year must be defined.")
        @Min(1000)@Max(9999)
        Integer releaseYear) {
    public static MovieGroupedByYear of(String title, Integer releaseYear) {
        return new MovieGroupedByYear(title, releaseYear);
    }
}
