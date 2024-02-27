package com.code.challenge.web;

import com.code.challenge.domain.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class MovieJsonTests {
    @Autowired
    private JacksonTester<Movie> json;
    @Test
    void testSerialize() throws Exception {
        var now = Instant.now();
        var movie = new Movie(394L,"1234567899", "Rambo 2000",
                "Ted Kotcheff", 1982,"studiocanal",
                "In the film, Rambo is a troubled ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg",
                1,2,3,now,now,21);
        var jsonContent = json.write(movie);
        assertThat(jsonContent).extractingJsonPathNumberValue("@.id")
                .isEqualTo(movie.id().intValue());
        assertThat(jsonContent).extractingJsonPathStringValue("@.eidr")
                .isEqualTo(movie.eidr());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title")
                .isEqualTo(movie.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.director")
                .isEqualTo(movie.director());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.releaseYear")
                .isEqualTo(movie.releaseYear());
        assertThat(jsonContent).extractingJsonPathStringValue("@.publisher")
                .isEqualTo(movie.publisher());
        assertThat(jsonContent).extractingJsonPathStringValue("@.sinopsis")
                .isEqualTo(movie.sinopsis());
        assertThat(jsonContent).extractingJsonPathStringValue("@.imageURl")
                .isEqualTo(movie.imageURl());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.upVoteCount")
                .isEqualTo(movie.upVoteCount());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.downVoteCount")
                .isEqualTo(movie.downVoteCount());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.favoriteCount")
                .isEqualTo(movie.favoriteCount());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.version")
                .isEqualTo(movie.version());
    }
    @Test
    void testDeserialize() throws Exception {
        var instant = Instant.parse("2021-09-07T22:50:37.135029Z");
        var content = """
                {
                    "id":394,
                    "eidr": "1234567899",
                    "title": "Rambo 2000",
                    "director": "Ted Kotcheff",
                    "releaseYear": 1982,
                    "publisher":"studiocanal",
                    "sinopsis":"In the film, Rambo is a troubled ...",
                    "imageURl":"https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg",
                    "upVoteCount": 1,
                    "downVoteCount": 2,
                    "favoriteCount": 3,
                    "createdDate": "2021-09-07T22:50:37.135029Z",
                    "lastModifiedDate": "2021-09-07T22:50:37.135029Z",
                    "version":21
                }
                """;
        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(new Movie(394L,"1234567899", "Rambo 2000",
                        "Ted Kotcheff", 1982,"studiocanal",
                        "In the film, Rambo is a troubled ...",
                        "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg",
                        1,2,3,instant,instant,21));
    }
}