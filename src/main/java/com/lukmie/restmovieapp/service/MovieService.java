package com.lukmie.restmovieapp.service;

import com.lukmie.restmovieapp.dto.MovieDto;
import com.lukmie.restmovieapp.entity.Movie;
import com.lukmie.restmovieapp.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public Long createMovie(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setReleaseYear(movieDto.getReleaseYear());
        movie.setDirector(movieDto.getDirector());
        movie.setGenres(movieDto.getGenres());

        return movieRepository.save(movie).getId();
    }

    public List<Movie> getAllMovies() {
        return null;
    }

    public Movie getMovie(Long id) {
        return null;
    }
}
