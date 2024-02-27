package com.code.challenge.repository;

import com.code.challenge.domain.Movie;
import com.code.challenge.domain.MovieGroupedByYear;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends CrudRepository<Movie,Long> {
    Optional<Movie> findByEidr(String eidr);
    List<Movie> findByReleaseYear(Integer releaseYear);
    boolean existsByEidr(String eidr);
    @Modifying
    @Transactional
    @Query("delete from Movie where eidr = :eidr")
    void deleteByEidr(String eidr);
    @Query("select m.title, m.release_year from Movie m group by m.release_year, m.title ")
    List<MovieGroupedByYear> getMoviesGroupedByYear();

    @Query("select m.* from Movie m order by favorite_count desc")
    List<Movie> getMoviesSorted();
}
