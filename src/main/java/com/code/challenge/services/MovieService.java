package com.code.challenge.services;

import com.code.challenge.domain.*;
import com.code.challenge.domain.dto.Vote;
import com.code.challenge.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public List<Movie> viewMovieList(){
        return movieRepository.getMoviesSorted();
    }

    public List<MovieGroupedByYear> viewMoviesGrouped(){
        return movieRepository.getMoviesGroupedByYear();
    }

    public List<Movie> viewMoviesByReleaseYear(Integer releaseYear){
        return movieRepository.findByReleaseYear(releaseYear);
    }

    public Movie viewMovieDetails(String eidr) {
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

    public Movie vote(String eidr, Vote vote) {
        return movieRepository.findByEidr(eidr)
                .map(existingMovie -> {
                    int upCount = 0, downCount = 0, favoriteCount = 0;

                    switch (vote.voteType()) {
                        case "UP" -> {
                            upCount = existingMovie.upVoteCount() + 1;
                        }
                        case "DOWN" -> {
                            downCount = existingMovie.downVoteCount() + 1;
                        }
                        case "FAVORITE" -> favoriteCount = existingMovie.favoriteCount() + 1;
                    }

                    var movieToUpdate = new Movie(
                            existingMovie.id(),
                            existingMovie.eidr(),
                            existingMovie.title(),
                            existingMovie.director(),
                            existingMovie.releaseYear(),
                            existingMovie.publisher(),
                            existingMovie.sinopsis(),
                            existingMovie.imageURl(),
                            upCount,
                            downCount,
                            favoriteCount,
                            existingMovie.createdDate(),
                            existingMovie.lastModifiedDate(),
                            existingMovie.version());
                    return movieRepository.save(movieToUpdate);
                }).orElse(null);
    }
}
