package com.code.challenge.demodata;


import com.code.challenge.config.Generated;
import com.code.challenge.domain.Movie;
import com.code.challenge.repository.MovieRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Generated
@Component
@Profile("testdata")
public class MovieDataLoader {
    private final MovieRepository movieRepository;

    public MovieDataLoader(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData() {
        movieRepository.deleteAll();
        var movie1 = Movie.of("1234567891", "Rambo",
                "Ted Kotcheff", 1982,"studiocanal",
                "In the film, Rambo is a troubled and misunderstood Vietnam veteran who must rely on his combat and survival skills when a series of brutal events results in him having to survive a massive manhunt by police and government troops near the fictional small town of Hope, Washington",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg",0,1,1);
        var movie2 = Movie.of("1234567892", "Rocky",
                "John G. Avildsen", 1976,"MGM",
                "Rocky Balboa, a small-time boxer from working-class Philadelphia, is arbitrarily chosen to take on the reigning world heavyweight champion, Apollo Creed, when the undefeated fighter's scheduled opponent is injured.",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/zxctX2pCucniDkS83TTCqRnKS9e.jpg",1,1,2);
        var movie3 = Movie.of("1234567893", "Rocky 3",
                "John G. Avildsen", 1976,"MGM",
                "Rocky Balboa, returns ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/zxctX2pCucniDkS83TTCqRnKS9e.jpg",1,2,3);

        movieRepository.saveAll(List.of(movie1,movie2, movie3));
    }

}
