package com.lukmie.restmovieapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukmie.restmovieapp.dto.MovieDto;
import com.lukmie.restmovieapp.entity.Movie;
import com.lukmie.restmovieapp.exception.MovieNotFoundException;
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

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                .andExpect(status().isCreated()) //201
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/api/movies/1"));

        assertThat(argumentCaptor.getValue().getTitle(), is("Intouchables"));
        assertThat(argumentCaptor.getValue().getReleaseYear(), is(2011));
        assertThat(argumentCaptor.getValue().getDirector(), is("Olivier Nakache"));
        assertThat(argumentCaptor.getValue().getGenres(), is("drama"));
    }

    @Test
    public void getAllMoviesShouldReturnTwoMovies() throws Exception {

        when(movieService.getAllMovies()).thenReturn(List.of(
                createMovie(1L, "12 Angry Men", 1957, "Sidney Lumet", "drama"),
                createMovie(2L, "Forrest Gump", 1994, "Robert Zemeckis", "comedy")));

        this.mockMvc.perform(get("/api/movies"))
                .andExpect(status().isOk()) //200
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("12 Angry Men")))
                .andExpect(jsonPath("$[0].releaseYear", is(1957)))
                .andExpect(jsonPath("$[0].director", is("Sidney Lumet")))
                .andExpect(jsonPath("$[0].genres", is("drama")));
    }

    @Test
    public void getMovieWithIdOneShouldReturnMovieWithThisId() throws Exception {

        when(movieService.getMovie(1L)).thenReturn(
                createMovie(1L, "12 Angry Men", 1957, "Sidney Lumet", "drama"));

        this.mockMvc.perform(get("/api/movies/1"))
                .andExpect(status().isOk()) //200
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("12 Angry Men")))
                .andExpect(jsonPath("$.releaseYear", is(1957)))
                .andExpect(jsonPath("$.director", is("Sidney Lumet")))
                .andExpect(jsonPath("$.genres", is("drama")));
    }

    @Test
    public void getMovieWithIdSixShouldReturnError() throws Exception {

        when(movieService.getMovie(6L)).thenThrow(new MovieNotFoundException("Movie with '6' not found"));

        this.mockMvc.perform(get("/api/movies/6"))
                .andExpect(status().isNotFound()); //404
    }

    private Movie createMovie(long id, String title, int releaseYear, String director, String genres) {
        Movie movie = Movie.builder().id(id).title(title).releaseYear(releaseYear).director(director).genres(genres).build();
        return movie;
    }
}