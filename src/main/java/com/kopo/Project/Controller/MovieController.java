package com.kopo.Project.Controller;

import com.kopo.Project.Domain.Movie;
import com.kopo.Project.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(value = "/movies/new")
    public String createForm() {
        return "movies/createMovieForm";
    }


    @PostMapping(value = "/movies/new")
    public String create(MovieForm form) {
        Movie movies = new Movie();
        movies.setMovieTitle(form.getMovieTitle());
        movieService.join(movies);
        return "redirect:/";
    }

    @GetMapping(value = "/movies")
    public String list(Model model) {
        List<Movie> movies = movieService.findMovies();
        model.addAttribute("movies", movies);
        return "movies/movieList";
    }
}