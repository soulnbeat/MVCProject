package com.kopo.Project.Service;

import com.kopo.Project.Domain.Reservation;
import com.kopo.Project.Repository.Reservation.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class ReservationService {
    private final ReservationRepo reservationRepo;

    @Autowired
    public ReservationService(ReservationRepo reservationRepo) {
        this.reservationRepo = reservationRepo;
    }
    // C
    public String reservation(Reservation reservation) {
        long start = System.currentTimeMillis();
        try {
            validateDuplicateReservation(reservation); //중복 검증
            reservationRepo.save(reservation);
            return reservation.getReservation_code();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join " + timeMs + "ms");
        }
    }

    private void validateDuplicateReservation(Reservation reservation) {
        reservationRepo.findByReservationCode(reservation.getReservation_code())
                .ifPresent(res -> {
                    throw new IllegalStateException("이미 존재하는 예약입니다.");
                });
    }
    // R
    public List<Reservation> findReservation() {
        long start = System.currentTimeMillis();
        try {
            return reservationRepo.findAll();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("findMembers " + timeMs + "ms");
        }
    }
}
