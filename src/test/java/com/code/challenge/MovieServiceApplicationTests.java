package com.code.challenge;

import com.code.challenge.domain.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class CatalogServiceApplicationTests {
    @Autowired
    private WebTestClient webTestClient;
    @Test
    void whenGetRequestWithIdThenMovieReturned() {
        var movieEidr = "1231231230";
        var movieToCreate = Movie.of(movieEidr, "Rambo 999",
                "Ted Kotcheff", 1982,"studiocanal",
                "In the film, Rambo is a troubled ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg");
        Movie expectedMovie = webTestClient
                .post()
                .uri("/movies")
                .bodyValue(movieToCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Movie.class).value(movie -> assertThat(movie).isNotNull())
                .returnResult().getResponseBody();

        webTestClient
                .get()
                .uri("/movies/" + movieEidr)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Movie.class).value(actualMovie -> {
                    assertThat(actualMovie).isNotNull();
                    assertThat(actualMovie.eidr()).isEqualTo(expectedMovie.eidr());
                });
    }

    @Test
    void whenPostRequestThenMovieCreated() {
        var expectedMovie = Movie.of("1231231231", "Rambo 999",
                "Ted Kotcheff", 1982,"studiocanal",
                "In the film, Rambo is a troubled ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg");

        webTestClient
                .post()
                .uri("/movies")
                .bodyValue(expectedMovie)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Movie.class).value(actualMovie -> {
                    assertThat(actualMovie).isNotNull();
                    assertThat(actualMovie.eidr()).isEqualTo(expectedMovie.eidr());
                });
    }

    @Test
    void whenPutRequestThenMovieUpdated() {
        var movieEidr = "1231231232";
        var movieToCreate = Movie.of(movieEidr, "Rambo 999",
                "Ted Kotcheff", 1982,"studiocanal",
                "In the film, Rambo is a troubled ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg");
        Movie createdMovie = webTestClient
                .post()
                .uri("/movies")
                .bodyValue(movieToCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Movie.class).value(movie -> assertThat(movie).isNotNull())
                .returnResult().getResponseBody();
        var movieToUpdate = new Movie(createdMovie.id(), createdMovie.eidr(), createdMovie.title(),
                createdMovie.director(), createdMovie.releaseYear(), createdMovie.publisher(),
                createdMovie.sinopsis(), createdMovie.imageURl(), createdMovie.upVoteCount(),
                createdMovie.downVoteCount(), createdMovie.favoriteCount(), createdMovie.createdDate(),
                createdMovie.lastModifiedDate(), createdMovie.version());

        webTestClient
                .put()
                .uri("/movies/" + movieEidr)
                .bodyValue(movieToUpdate)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Movie.class).value(actualMovie -> {
                    assertThat(actualMovie).isNotNull();
                    assertThat(actualMovie.releaseYear()).isEqualTo(movieToUpdate.releaseYear());
                });
    }

    @Test
    void whenDeleteRequestThenMovieDeleted() {
        var movieEidr = "1231231233";
        var movieToCreate = Movie.of(movieEidr, "Rambo 999",
                "Ted Kotcheff", 1982,"studiocanal",
                "In the film, Rambo is a troubled ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg");
        webTestClient
                .post()
                .uri("/movies")
                .bodyValue(movieToCreate)
                .exchange()
                .expectStatus().isCreated();

        webTestClient
                .delete()
                .uri("/movies/" + movieEidr)
                .exchange()
                .expectStatus().isNoContent();

        webTestClient
                .get()
                .uri("/movies/" + movieEidr)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class).value(errorMessage ->
                        assertThat(errorMessage).isEqualTo("The movie with EIDR " + movieEidr + " was not found.")
                );
    }
}
