package com.code.challenge.domain;

import java.util.Optional;

import com.code.challenge.repository.MovieRepository;
import com.code.challenge.services.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    void whenMovieToCreateAlreadyExistsThenThrows() {
        var movieIsbn = "1234561232";
        var movieToCreate = Movie.of(movieIsbn, "Rambo",
                "Ted Kotcheff", 1982,"studiocanal",
                "In the film, Rambo is a troubled ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg");
        when(movieRepository.existsByEidr(movieIsbn)).thenReturn(true);
        assertThatThrownBy(() -> movieService.addMovieToCatalog(movieToCreate))
                .isInstanceOf(MovieAlreadyExistsException.class)
                .hasMessage("A movie with EIDR " + movieIsbn + " already exists.");
    }

    @Test
    void whenMovieToReadDoesNotExistThenThrows() {
        var movieEidr = "1234561232";
        when(movieRepository.findByEidr(movieEidr)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> movieService.viewMovieDetails(movieEidr))
                .isInstanceOf(MovieNotFoundException.class)
                .hasMessage("The movie with EIDR " + movieEidr + " was not found.");
    }

}
