package com.lukmie.restmovieapp.service;

import com.lukmie.restmovieapp.dto.MovieDto;
import com.lukmie.restmovieapp.entity.Movie;
import com.lukmie.restmovieapp.exception.MovieNotFoundException;
import com.lukmie.restmovieapp.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        return movieRepository.findAll();
    }

    public Movie getMovie(Long id) {
        Movie movie = movieRepository
                .findById(id).orElseThrow(() -> new MovieNotFoundException(String.format("Movie with '%s' not found", id)));
        return movie;
    }

    @Transactional
    public Movie updateMovie(Long id, MovieDto movieDto) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(String.format("Movie with '%s' not found", id)));
        movie.setTitle(movieDto.getTitle());
        movie.setReleaseYear(movieDto.getReleaseYear());
        movie.setDirector(movieDto.getDirector());
        movie.setGenres(movieDto.getGenres());

        return movie;
    }

    public void deleteMovie(Long id) {
        Movie movie = movieRepository
                .findById(id).orElseThrow(() -> new MovieNotFoundException(String.format("Movie with '%s' not found", id)));
        movieRepository.delete(movie);
    }
}
