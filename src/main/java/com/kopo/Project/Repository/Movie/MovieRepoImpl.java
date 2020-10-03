package com.kopo.Project.Repository.Movie;

import com.kopo.Project.Domain.Movie;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class MovieRepoImpl implements MovieRepo {
    private final EntityManager em;

    public MovieRepoImpl(EntityManager em) {
        this.em = em;
    }

    public Movie save(Movie movie) {
        em.persist(movie);
        return movie;
    }

    public Optional<Movie> findById(Long id) {
        Movie movie = em.find(Movie.class, id);
        return Optional.ofNullable(movie);
    }

    public List<Movie> findAll() {
        return em.createQuery("select m from Movie as m", Movie.class)
                .getResultList();
    }

    public Optional<Movie> findByMovieTitle(String movieTile) {
        List<Movie> result = em.createQuery("select m from Movie m where m.movieTitle = :movieTitle", Movie.class)
                .setParameter("movieTitle", movieTile)
                .getResultList();
        return result.stream().findAny();
    }
}