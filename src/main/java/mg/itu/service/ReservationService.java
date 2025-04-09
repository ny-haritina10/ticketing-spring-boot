package mg.itu.service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import mg.itu.dto.ReservationDTO;
import mg.itu.dto.ReservationDetailDTO;
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

        System.out.println("departure time: " + departureTime.toString());
        System.out.println("hoursUntilDeparture: " + hoursUntilDeparture);
        
        return hoursUntilDeparture >= reservation.getFlight().getCancellationDeadlineHours(); 
    }

    public void cancelReservation(Reservation reservation, ZonedDateTime cancellationTime) {
        for (ReservationDetail detail : reservation.getDetails()) {
            detail.setCancelled(true);
            detail.setCancellationTime(cancellationTime);
        }
        reservationRepository.save(reservation);
    }

    public ReservationDTO getReservationInformation(Integer idReservation) 
        throws Exception
    {
        ReservationDTO reservationDTO = new ReservationDTO();
        Reservation reservation = this.reservationRepository.findById(idReservation).get();

        List<ReservationDetailDTO> details = new ArrayList<ReservationDetailDTO>();

        reservationDTO.setClient(reservation.getClient().getName());
        reservationDTO.setFlightId(reservation.getFlight().getId().toString());
        reservationDTO.setFlightNumber(reservation.getFlight().getFlightNumber());
        reservationDTO.setPlaneName(reservation.getFlight().getPlane().getModelName());
        reservationDTO.setOriginCity(reservation.getFlight().getOriginCity().getCountry() + " " + reservation.getFlight().getOriginCity().getCityName());
        reservationDTO.setDestinationCity(reservation.getFlight().getDestinationCity().getCountry() + " " + reservation.getFlight().getDestinationCity().getCityName());
        reservationDTO.setDepartureTime(reservation.getFlight().getDepartureTime().toString());
        reservationDTO.setArrivalTime(reservation.getFlight().getArrivalTime().toString());
        reservationDTO.setReservationTime(reservation.getReservationTime().toString());
        reservationDTO.setTotalTickets(reservation.getTotalTickets().toString());
        reservationDTO.setChildTickets(reservation.getChildTickets().toString());
        reservationDTO.setAdultTickets(reservation.getAdultTickets().toString());

        for(ReservationDetail detail: reservation.getDetails()) {
            ReservationDetailDTO detailDTO = new ReservationDetailDTO();

            detailDTO.setSeatCategory(detail.getSeatCategory());
            detailDTO.setPassengerName(detail.getPassengerName());
            detailDTO.setPassengerBirthDate(detail.getPassengerBirthDate().toString());
            detailDTO.setPrice(detail.getPrice().toString());

            details.add(detailDTO);
        }

        reservationDTO.setReservationDetails(details);
        return reservationDTO;
    }

    public byte[] generateReservationPdf(Integer reservationId) throws Exception {
        ReservationDTO reservation = getReservationInformation(reservationId);
        
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            
            // Add title
            document.add(new Paragraph("Reservation Details"));
            document.add(new Paragraph("-------------------"));
            
            // Add reservation information
            document.add(new Paragraph("Client: " + reservation.getClient()));
            document.add(new Paragraph("Flight Number: " + reservation.getFlightNumber()));
            document.add(new Paragraph("Plane: " + reservation.getPlaneName()));
            document.add(new Paragraph("From: " + reservation.getOriginCity()));
            document.add(new Paragraph("To: " + reservation.getDestinationCity()));
            document.add(new Paragraph("Departure: " + reservation.getDepartureTime()));
            document.add(new Paragraph("Arrival: " + reservation.getArrivalTime()));
            document.add(new Paragraph("Reservation Time: " + reservation.getReservationTime()));
            document.add(new Paragraph("Total Tickets: " + reservation.getTotalTickets()));
            document.add(new Paragraph("Child Tickets: " + reservation.getChildTickets()));
            document.add(new Paragraph("Adult Tickets: " + reservation.getAdultTickets()));
            
            // Add reservation details section
            document.add(new Paragraph("\nPassenger Details"));
            document.add(new Paragraph("-------------------"));
            
            for (ReservationDetailDTO detail : reservation.getReservationDetails()) {
                document.add(new Paragraph("Seat Category: " + detail.getSeatCategory()));
                document.add(new Paragraph("Passenger: " + detail.getPassengerName()));
                document.add(new Paragraph("Birth Date: " + detail.getPassengerBirthDate()));
                document.add(new Paragraph("Price: " + detail.getPrice()));
                document.add(new Paragraph("---"));
            }
            
            document.close();
            return out.toByteArray();
            
        } catch (DocumentException e) {
            throw new Exception("Error generating PDF: " + e.getMessage());
        }
    }

    // Add this method to ReservationService.java
    public void saveReservationPdfToFile(Integer reservationId, String filePath) throws Exception {
        byte[] pdfBytes = generateReservationPdf(reservationId);
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(pdfBytes);
        } catch (IOException e) {
            throw new Exception("Error saving PDF to file: " + e.getMessage());
        }
    }
}