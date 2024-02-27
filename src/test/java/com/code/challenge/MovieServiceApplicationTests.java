package com.code.challenge;

import com.code.challenge.domain.Movie;
import com.code.challenge.domain.dto.VoteDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class MovieServiceApplicationTests {
    @Autowired
    private WebTestClient webTestClient;
    @Test
    void whenGetRequestWithIdThenMovieReturned() {
        var movieEidr = "2231281731";
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
        var expectedMovie = Movie.of("2231281232", "Rambo 999",
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
        var movieEidr = "2231836283";
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
        var movieEidr = "2241231894";
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

    @Test
    void whenVoteRequestUpIncreaseUpVoteCount() {
        var movieEidr = "2231836285";
        var movieToCreate = Movie.of(movieEidr, "Rambo 999",
                "Ted Kotcheff", 1982,"studiocanal",
                "In the film, Rambo is a troubled ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg");
        var voteType = VoteDTO.of("UP");
        Movie createdMovie = webTestClient
                .post()
                .uri("/movies")
                .bodyValue(movieToCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Movie.class).value(movie -> assertThat(movie).isNotNull())
                .returnResult().getResponseBody();

        webTestClient
                .put()
                .uri("/movies/" + movieEidr + "/vote")
                .bodyValue(voteType)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Movie.class).value(actualMovie -> {
                    assertThat(actualMovie).isNotNull();
                    assertThat(actualMovie.upVoteCount()).isEqualTo(createdMovie.upVoteCount() + 1);
                });
    }

    @Test
    void whenVoteRequestUpIncreaseDownVoteCount() {
        var movieEidr = "2231836286";
        var movieToCreate = Movie.of(movieEidr, "Rambo 999",
                "Ted Kotcheff", 1982,"studiocanal",
                "In the film, Rambo is a troubled ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg");
        var voteType = VoteDTO.of("DOWN");
        Movie createdMovie = webTestClient
                .post()
                .uri("/movies")
                .bodyValue(movieToCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Movie.class).value(movie -> assertThat(movie).isNotNull())
                .returnResult().getResponseBody();

        webTestClient
                .put()
                .uri("/movies/" + movieEidr + "/vote")
                .bodyValue(voteType)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Movie.class).value(actualMovie -> {
                    assertThat(actualMovie).isNotNull();
                    assertThat(actualMovie.downVoteCount()).isEqualTo(createdMovie.downVoteCount() + 1);
                });
    }

    @Test
    void whenVoteRequestUpIncreaseFavoriteVoteCount() {
        var movieEidr = "2231836287";
        var movieToCreate = Movie.of(movieEidr, "Rambo 999",
                "Ted Kotcheff", 1982,"studiocanal",
                "In the film, Rambo is a troubled ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg");
        var voteType = VoteDTO.of("FAVORITE");
        Movie createdMovie = webTestClient
                .post()
                .uri("/movies")
                .bodyValue(movieToCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Movie.class).value(movie -> assertThat(movie).isNotNull())
                .returnResult().getResponseBody();

        webTestClient
                .put()
                .uri("/movies/" + movieEidr + "/vote")
                .bodyValue(voteType)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Movie.class).value(actualMovie -> {
                    assertThat(actualMovie).isNotNull();
                    assertThat(actualMovie.favoriteCount()).isEqualTo(createdMovie.favoriteCount() + 1);
                });
    }

    @Test
    void getAllMoviesList() {
        webTestClient
                .get()
                .uri("/movies")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Movie.class).value(moviesList -> {
                    assertThat(moviesList).isNotNull();
                });
    }

    @Test
    void getAllMoviesListGroupedBy() {
        webTestClient
                .get()
                .uri("/movies/years")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Movie.class).value(moviesList -> {
                    assertThat(moviesList).isNotNull();
                });
    }

    @Test
    void getAllMoviesByReleaseYear() {
        var movieEidr = "2931836288";
        var releaseYear = 2000;
        var movieToCreate = Movie.of(movieEidr, "Rambo 2000",
                "Ted Kotcheff", releaseYear,"studiocanal",
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
        webTestClient
                .get()
                .uri("/movies/years/"+releaseYear)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Movie.class).value(moviesList -> {
                    assertThat(moviesList).isNotNull();
                    assertThat(moviesList.size()).isEqualTo(1);
                });
    }
}
