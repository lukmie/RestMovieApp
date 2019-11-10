package com.lukmie.restmovieapp.controller;

import com.lukmie.restmovieapp.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private MovieService movieService;


}
