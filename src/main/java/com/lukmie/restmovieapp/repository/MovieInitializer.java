package com.lukmie.restmovieapp.repository;

import com.lukmie.restmovieapp.entity.Movie;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Component
public class MovieInitializer {

    private MovieRepository movieRepository;

    @EventListener
    @Transactional
    public void init(ApplicationReadyEvent applicationReadyEvent) {
        Movie movie1 = Movie.builder().title("The Shawshank Redemption").releaseYear(1994).director("Frank Darabont").genres("drama").build();
        Movie movie2 = Movie.builder().title("The Godfather").releaseYear(1972).director("Francis Ford Coppola").genres("drama").build();
        Movie movie3 = Movie.builder().title("The Godfather II").releaseYear(1974).director("Francis Ford Coppola").genres("drama").build();
    }
}
