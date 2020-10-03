package com.kopo.Project.repository;

import com.kopo.Project.Domain.Reservation;
import com.kopo.Project.Repository.Reservation.ReservationRepoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ReservationRepoImplTest {

    @Autowired
    ReservationRepoImpl repository;


    @Test
    public void save() {
        Reservation reservation = new Reservation();
        reservation.setMember_code(System.currentTimeMillis());
        repository.save(reservation);

        Reservation result = repository.findByMemberCode(reservation.getMember_code()).get();
        assertThat(reservation).isEqualTo(result);
    }

    @Test
    public void findByReservationCode() {
        Reservation reservation = new Reservation();
        reservation.setReservation_code("A1");
        repository.save(reservation);
        Reservation reservation1 = new Reservation();
        reservation1.setReservation_code("Z2");
        repository.save(reservation1);

        Reservation result = repository.findByReservationCode("A1").get();
        assertThat(result.getReservation_code()).isEqualTo(reservation.getReservation_code());
    }

    @Test
    public void findByMemberCode() {
        Reservation reservation = new Reservation();
        long tmp = System.currentTimeMillis();
        reservation.setMember_code(tmp);
        repository.save(reservation);
        Reservation reservation1 = new Reservation();
        reservation1.setMember_code(System.currentTimeMillis());
        repository.save(reservation1);

        Reservation result = repository.findByMemberCode(tmp).get();
        assertThat(result.getMember_code()).isEqualTo(reservation.getMember_code());

    }

    @Test
    public void findAll() {
        Reservation reservation = new Reservation();
        reservation.setMember_code(System.currentTimeMillis());
        repository.save(reservation);
        Reservation reservation1 = new Reservation();
        reservation1.setMember_code(System.currentTimeMillis());
        repository.save(reservation1);

        List<Reservation> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}
