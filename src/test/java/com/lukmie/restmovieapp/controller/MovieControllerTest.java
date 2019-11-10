package com.lukmie.restmovieapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukmie.restmovieapp.dto.MovieDto;
import com.lukmie.restmovieapp.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;
    // for serializing java object to json
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MovieService movieService;
    // to capture the passed obj. of a method and use for assertion
    @Captor
    private ArgumentCaptor<MovieDto> argumentCaptor;

    @Test
    public void postingNewMovieShouldAddMovieToDB() throws Exception {
        MovieDto movieDto = MovieDto.builder()
                .title("Intouchables")
                .releaseYear(2011)
                .director("Olivier Nakache")
                .genres("drama")
                .build();

        when(movieService.createMovie(argumentCaptor.capture())).thenReturn(1L);

        this.mockMvc.perform(post("/api/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/api/movies/1"));

        assertThat(argumentCaptor.getValue().getTitle(), is("Intouchables"));
        assertThat(argumentCaptor.getValue().getReleaseYear(), is(2011));
        assertThat(argumentCaptor.getValue().getDirector(), is("Olivier Nakache"));
        assertThat(argumentCaptor.getValue().getGenres(), is("drama"));

    }
}