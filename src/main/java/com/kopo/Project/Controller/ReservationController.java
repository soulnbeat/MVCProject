package com.kopo.Project.Controller;

import com.kopo.Project.Domain.Reservation;
import com.kopo.Project.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(value = "/reservations/new")
    public String create() {
        return "reservations/createReservationForm";
    }

    @PostMapping(value = "/reservations/new")
    public String create2() {
        return "reservations/reservation";
    }

    @PostMapping(value = "/reservations/new")
    public String createForm(ReservationForm form) {
        Reservation reservation = new Reservation();
        reservation.setId(form.getId());
        reservation.setReservation_code(form.getReservation_code());
        reservation.setMember_code(form.getMember_code());
        reservationService.reservation(reservation);
        return "redirect:/";
    }

    @GetMapping(value = "/reservations/tmp")
    public String list(Model model) {
        List<Reservation> reservation = reservationService.findReservation();
        model.addAttribute("reservations", reservation);
        return "reservations/reservation";
    }
}