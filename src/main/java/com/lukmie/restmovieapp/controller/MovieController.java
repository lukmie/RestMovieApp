package com.lukmie.restmovieapp.controller;

import com.lukmie.restmovieapp.dto.MovieDto;
import com.lukmie.restmovieapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

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


}
