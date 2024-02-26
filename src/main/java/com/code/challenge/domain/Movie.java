package com.code.challenge.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.time.Instant;
import java.util.Date;

public record Movie(
        @Id
        Long id,
        @NotBlank(message = "The movie EIDR must be defined.")
        @Pattern(
                regexp = "^([0-9]{10}|[0-9]{13})$",
                message = "The EIDR format must be valid."
        )
        String eidr,
        @NotBlank(
                message = "The Movie title must be defined."
        )
        String title,
        @NotBlank(message = "The Movie Director must be defined.")
        String director,
        @NotNull(message = "The Movie year must be defined.")
        @Pattern(
                regexp = "^([0-9][0-9]{3})$",
                message = "The year format must be valid."
        )
        Integer releaseYear,
        String publisher,
        String sinopsis,
        String imageURl,
        Integer upVoteCount,
        Integer downVoteCount,
        Integer favoriteCount,
        @CreatedDate
        Instant createdDate,
        @LastModifiedDate
        Instant lastModifiedDate,
        @Version
        int version
) {

    public static Movie of(String eidr, String title, String director, Integer releaseYear, String publisher, String sinopsis, String imageURL) {
        return new Movie(null, eidr, title, director, releaseYear, publisher, sinopsis, imageURL,0,0, 0, null, null, 0);
    }
}
