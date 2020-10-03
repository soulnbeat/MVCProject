package com.kopo.Project.Repository.Movie;

import com.kopo.Project.Domain.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepo {
    Movie save(Movie movie);
    Optional<Movie> findById(Long id);
    Optional<Movie> findByMovieTitle(String movieTitle);
    List<Movie> findAll();
}