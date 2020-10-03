package com.kopo.Project;

import com.kopo.Project.Repository.Member.MemberRepo;
import com.kopo.Project.Repository.Member.MemberRepoImpl;
import com.kopo.Project.Repository.Movie.MovieRepo;
import com.kopo.Project.Repository.Movie.MovieRepoImpl;
import com.kopo.Project.Repository.Reservation.ReservationRepo;
import com.kopo.Project.Repository.Reservation.ReservationRepoImpl;
import com.kopo.Project.Service.MemberService;
import com.kopo.Project.Service.MovieService;
import com.kopo.Project.Service.ReservationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;
    private final EntityManager em;

    public SpringConfig(DataSource dataSource, EntityManager em) {
        this.dataSource = dataSource;
        this.em = em;
    }

    @Bean
    public MovieService movieService() {
        return new MovieService(movieRepo());
    }

    @Bean
    public MovieRepo movieRepo() {
        return new MovieRepoImpl(em);
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepo());
    }

    @Bean
    public MemberRepo memberRepo() {
        return new MemberRepoImpl(em);
    }

    @Bean
    public ReservationService reservationService() {
        return new ReservationService(reservationRepo());
    }

    @Bean
    public ReservationRepo reservationRepo() {
        return new ReservationRepoImpl(em);
    }
}