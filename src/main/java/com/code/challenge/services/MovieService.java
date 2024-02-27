package com.code.challenge.services;

import com.code.challenge.domain.Movie;
import com.code.challenge.domain.MovieAlreadyExistsException;
import com.code.challenge.domain.MovieNotFoundException;
import com.code.challenge.repository.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public Iterable<Movie> viewMoviesList(){
        return movieRepository.findAll();
    }

    public Movie viewBookDetails(String eidr) {
        return movieRepository.findByEidr(eidr)
                .orElseThrow(() -> new MovieNotFoundException(eidr));
    }

    public Movie addMovieToCatalog(Movie movie) {
        if (movieRepository.existsByEidr(movie.eidr())) {
            throw new MovieAlreadyExistsException(movie.eidr());
        }
        return movieRepository.save(movie);
    }

    public void removeMovieFromCatalog(String eidr) {
        movieRepository.deleteByEidr(eidr);
    }

    public Movie editMovieDetails(String eidr, Movie movie) {
        return movieRepository.findByEidr(eidr)
                .map(existingMovie -> {
                    var movieToUpdate = new Movie(
                            existingMovie.id(),
                            existingMovie.eidr(),
                            movie.title(),
                            movie.director(),
                            movie.releaseYear(),
                            movie.publisher(),
                            movie.sinopsis(),
                            movie.imageURl(),
                            movie.upVoteCount(),
                            movie.downVoteCount(),
                            movie.favoriteCount(),
                            movie.createdDate(),
                            movie.lastModifiedDate(),
                            movie.version());
                    return movieRepository.save(movieToUpdate);
                })
                .orElseGet(() -> addMovieToCatalog(movie));
    }
}
