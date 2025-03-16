package mg.itu.service;

import mg.itu.model.Reservation;
import mg.itu.model.ReservationDetail;
import mg.itu.repository.ClientRepository;
import mg.itu.repository.FlightRepository;
import mg.itu.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ReservationService {
    
    @Autowired
    private ReservationRepository reservationRepository;
    
    @Transactional
    public Reservation saveReservation(Reservation reservation) {
        // Set reservation time to current time if not set
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
}