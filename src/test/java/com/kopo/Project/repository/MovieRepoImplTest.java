package com.kopo.Project.repository;

import com.kopo.Project.Domain.Movie;
import com.kopo.Project.Repository.Movie.MovieRepoImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MovieRepoImplTest {

    @Autowired
    MovieRepoImpl repository;

    @Test
    public void save() {
        //given
        Movie movie = new Movie();
        movie.setMovieTitle("inception");
        //when : member 저장하면 sava메소드에서 sequence로 id를 자동 저장한다
        repository.save(movie);
        //then : findByIda메소드로 member의 id를 찾아와서 result에 저장
        Movie result = repository.findById(movie.getId()).get();
        // member와 result가 같은지 비교
        assertThat(movie).isEqualTo(result);
    }
    @Test
    public void findByMovieTitle() {
        //given
        Movie movie1 = new Movie();
        movie1.setMovieTitle("tenet");
        repository.save(movie1);
        Movie movie2 = new Movie();
        movie2.setMovieTitle("tenet2");
        repository.save(movie2);

        //when
        Movie result = repository.findByMovieTitle("tenet").get();
        //then
        assertThat(result.getMovieTitle()).isEqualTo(movie1.getMovieTitle());
    }
    @Test
    public void findAll() {
        //given
        Movie movie1 = new Movie();
        movie1.setMovieTitle("tenet");
        repository.save(movie1);
        Movie movie2 = new Movie();
        movie2.setMovieTitle("tenet2");
        repository.save(movie2);
        //when
        List<Movie> result = repository.findAll();
        //then
        assertThat(result.size()).isEqualTo(2);
    }
}