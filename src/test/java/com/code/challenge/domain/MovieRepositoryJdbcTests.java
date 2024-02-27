package com.code.challenge.domain;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import com.code.challenge.config.DataConfig;
import com.code.challenge.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Import(DataConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
class MovieRepositoryJdbcTests {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    @Test
    void findAllMovies() {
        var movie1 = Movie.of("1234561235", "Rambo 4",
                "Ted Kotcheff", 1982,"studiocanal",
                "In the film, Rambo is a troubled ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg");
        var movie2 = Movie.of("1234561236", "Rambo 5",
                "Ted Kotcheff", 1982,"studiocanal",
                "In the film, Rambo is a troubled ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg");
        jdbcAggregateTemplate.insert(movie1);
        jdbcAggregateTemplate.insert(movie2);

        Iterable<Movie> actualMovies = movieRepository.findAll();

        assertThat(StreamSupport.stream(actualMovies.spliterator(), true)
                .filter(movie -> movie.eidr().equals(movie1.eidr()) || movie.eidr().equals(movie2.eidr()))
                .collect(Collectors.toList())).hasSize(2);
    }

    @Test
    void findMovieByEidrWhenExisting() {
        var movieEidr = "1234561237";
        var movie = Movie.of(movieEidr, "Rambo 999",
                "Ted Kotcheff", 1982,"studiocanal",
                "In the film, Rambo is a troubled ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg");
        jdbcAggregateTemplate.insert(movie);
        Optional<Movie> actualMovie = movieRepository.findByEidr(movieEidr);
        assertThat(actualMovie).isPresent();
        assertThat(actualMovie.get().eidr()).isEqualTo(movie.eidr());
    }

    @Test
    void findMovieByEidrWhenNotExisting() {
        Optional<Movie> actualMovie = movieRepository.findByEidr("1234561238");
        assertThat(actualMovie).isEmpty();
    }

    @Test
    void existsByEidrWhenExisting() {
        var movieEidr = "1234561239";
        var movieToCreate = Movie.of(movieEidr, "Rambo 999",
                "Ted Kotcheff", 1982,"studiocanal",
                "In the film, Rambo is a troubled ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg");
        jdbcAggregateTemplate.insert(movieToCreate);

        boolean existing = movieRepository.existsByEidr(movieEidr);

        assertThat(existing).isTrue();
    }

    @Test
    void existsByEidrWhenNotExisting() {
        boolean existing = movieRepository.existsByEidr("1234561240");
        assertThat(existing).isFalse();
    }

    @Test
    void deleteByEidr() {
        var movieEidr = "1234561241";
        var movieToCreate = Movie.of(movieEidr, "Rambo 999",
                "Ted Kotcheff", 1982,"studiocanal",
                "In the film, Rambo is a troubled ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg");
        var persistedMovie = jdbcAggregateTemplate.insert(movieToCreate);

        movieRepository.deleteByEidr(movieEidr);

        assertThat(jdbcAggregateTemplate.findById(persistedMovie.id(), Movie.class)).isNull();
    }
}
