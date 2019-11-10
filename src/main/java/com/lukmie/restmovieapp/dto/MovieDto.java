package com.lukmie.restmovieapp.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class MovieDto {

    @NotEmpty
    private String title;
    @NotNull
    private Integer releaseYear;
    @NotEmpty
    private String director;
    @NotEmpty
    private String genres;
}
