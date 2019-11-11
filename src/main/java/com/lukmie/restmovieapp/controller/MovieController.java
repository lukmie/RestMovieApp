package com.lukmie.restmovieapp.controller;

import com.lukmie.restmovieapp.dto.MovieDto;
import com.lukmie.restmovieapp.entity.Movie;
import com.lukmie.restmovieapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<Void> createMovie(@Valid @RequestBody MovieDto movieDto, UriComponentsBuilder uriComponentsBuilder) {

        Long id = movieService.createMovie(movieDto);
        UriComponents uriComponents = uriComponentsBuilder.path("/api/movies/{id}").buildAndExpand(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> allMovies = movieService.getAllMovies();
        return new ResponseEntity<>(allMovies, HttpStatus.OK);
        // in one line
//        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") Long id) {
        Movie movie = movieService.getMovie(id);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

}
