package com.kopo.Project.Service;

import com.kopo.Project.Domain.Movie;
import com.kopo.Project.Repository.Movie.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class MovieService {
    private final MovieRepo movieRepo;

    @Autowired
    public MovieService(MovieRepo movieRepo) {
        this.movieRepo = movieRepo;
    }

    /**
     * 영화등록
     */
    public Long join(Movie movie) {
        long start = System.currentTimeMillis();
        try {
            validateDuplicateMovie(movie); //중복 회원 검증
            movieRepo.save(movie);
            return movie.getId();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join " + timeMs + "ms");
        }
    }

    private void validateDuplicateMovie(Movie movie) {
        movieRepo.findByMovieTitle(movie.getMovie_title())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 영화 조회
     */
    public List<Movie> findMovies() {
        long start = System.currentTimeMillis();
        try {
            return movieRepo.findAll();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("findMembers " + timeMs + "ms");
        }
    }
}