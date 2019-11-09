package com.lukmie.restmovieapp.repository;

import com.lukmie.restmovieapp.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
