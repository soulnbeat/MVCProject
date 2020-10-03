package com.kopo.Project.Repository.Reservation;

import com.kopo.Project.Domain.Reservation;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class ReservationRepoImpl implements ReservationRepo {
    private final EntityManager em;

    public ReservationRepoImpl(EntityManager em) {
        this.em = em;
    }

    public Reservation save(Reservation reservation) {
        em.persist(reservation);
        return reservation;
    }

    public Optional<Reservation> findByReservationCode(String reservation_code) {
        List<Reservation> result = em.createQuery("select resv from Reservation resv where resv.reservation_code = :reservation_code", Reservation.class)
                .setParameter("reservation_code", reservation_code)
                .getResultList();
        return result.stream().findAny();
    }

    public Optional<Reservation> findByMemberCode(Long member_code) {
        List<Reservation> result = em.createQuery("select resv from Reservation resv where resv.member_code = :member_code", Reservation.class)
                .setParameter("member_code", member_code)
                .getResultList();
        return result.stream().findAny();
    }

    public List<Reservation> findAll() {
        return em.createQuery("select resv from Reservation resv", Reservation.class)
                .getResultList();
    }
}
