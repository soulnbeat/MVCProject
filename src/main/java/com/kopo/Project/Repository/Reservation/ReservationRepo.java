package com.kopo.Project.Repository.Reservation;

import com.kopo.Project.Domain.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepo {
    // C
    Reservation save(Reservation reservation);
    // R
    Optional<Reservation> findByReservationCode(String reservation_code);
    Optional<Reservation> findByMemberCode(Long member_code);
    List<Reservation> findAll();
    // U
    // D
}