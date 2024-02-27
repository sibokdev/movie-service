package com.code.challenge.domain;

import jakarta.validation.constraints.*;
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
        @NotNull(message = "The Movie release year must be defined.")
        @Min(1000)@Max(9999)
        Integer releaseYear,
        @NotBlank(message = "The Movie Publisher must be defined.")
        String publisher,
        @NotBlank(message = "The Movie Sinopsis must be defined.")
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
    public static Movie of(String eidr, String title, String director, Integer releaseYear, String publisher, String sinopsis, String imageURL, Integer upVoteCount, Integer downVoteCount, Integer favoriteCount) {
            return new Movie(null, eidr, title, director, releaseYear, publisher, sinopsis, imageURL,upVoteCount,downVoteCount, favoriteCount, null, null, 0);
    }
}
