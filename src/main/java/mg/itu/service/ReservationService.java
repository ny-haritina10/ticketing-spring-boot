package mg.itu.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mg.itu.model.Client;
import mg.itu.model.Reservation;
import mg.itu.model.ReservationDetail;
import mg.itu.repository.ReservationRepository;

@Service
public class ReservationService {
    
    @Autowired
    private ReservationRepository reservationRepository;
    
    @Transactional
    public Reservation saveReservation(Reservation reservation) {
        if (reservation.getReservationTime() == null) {
            reservation.setReservationTime(ZonedDateTime.now());
        }
        return reservationRepository.save(reservation);
    }
    
    @Transactional
    public Reservation saveReservationWithDetails(Reservation reservation, List<ReservationDetail> details) {
        Reservation savedReservation = saveReservation(reservation);
        for (ReservationDetail detail : details) {
            savedReservation.addDetail(detail);
        }
        return reservationRepository.save(savedReservation);
    }
    
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }
    
    public Reservation findReservationById(Integer id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public List<Reservation> findReservationsByClient(Client client) {
        return reservationRepository.findByClient(client);
    }

    public boolean isReservationCancelable(Reservation reservation, ZonedDateTime now) {
        ZonedDateTime departureTime = reservation.getFlight().getDepartureTime();
        long hoursUntilDeparture = java.time.Duration.between(now, departureTime).toHours();
        return hoursUntilDeparture >= reservation.getFlight().getCancellationDeadlineHours(); 
    }

    public void cancelReservation(Reservation reservation, ZonedDateTime cancellationTime) {
        for (ReservationDetail detail : reservation.getDetails()) {
            detail.setCancelled(true);
            detail.setCancellationTime(cancellationTime);
        }
        reservationRepository.save(reservation);
    }
}