package com.code.challenge.repository;

import com.code.challenge.domain.Movie;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MovieRepository extends CrudRepository<Movie,Long> {
    Optional<Movie> findByEidr(String eidr);
    boolean existsByEidr(String eidr);
    @Modifying
    @Transactional
    @Query("delete from Movie where eidr = :eidr")
    void deleteByEidr(String eidr);
}
