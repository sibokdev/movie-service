package com.code.challenge.controllers;

import com.code.challenge.domain.Movie;
import com.code.challenge.services.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @GetMapping
    public Iterable<Movie> get() {
        return movieService.viewMovieList();
    }

    @GetMapping("{eidr}")
    public Movie getByEidr(@PathVariable String eidr) {
        return movieService.viewMovieDetails(eidr);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movie post(@Valid @RequestBody Movie movie) {
        return movieService.addMovieToCatalog(movie);
    }

    @DeleteMapping("{eidr}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String eidr) {
        movieService.removeMovieFromCatalog(eidr);
    }

    @PutMapping("{eidr}")
    public Movie put(@PathVariable String eidr, @Valid @RequestBody Movie movie) {
        return movieService.editMovieDetails(eidr, movie);
    }

    @PutMapping("{eidr}/vote")
    public Movie voteMovie(@PathVariable String eidr, @Valid @RequestBody Movie movie) {
        return movieService.editMovieDetails(eidr, movie);
    }
}
