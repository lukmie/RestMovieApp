package com.lukmie.restmovieapp.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private int releaseYear;
    @Column(nullable = false)
    private String director;
    @Column(nullable = false)
    private String genres;
}
