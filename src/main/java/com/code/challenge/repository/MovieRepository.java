package com.code.challenge.repository;

import com.code.challenge.domain.Movie;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MovieRepository extends CrudRepository<Movie,Long> {
    Optional<Movie> findByIEDR(String iedr);
    boolean existsByIEDR(String iedr);
    @Modifying
    @Transactional
    @Query("delete from Movie where iedr = :iedr")
    void deleteByIsbn(String iedr);
}
