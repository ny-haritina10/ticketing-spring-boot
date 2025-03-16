package mg.itu.controller;

import mg.itu.model.Reservation;
import mg.itu.model.ReservationDetail;
import mg.itu.repository.ClientRepository;
import mg.itu.repository.FlightRepository;
import mg.itu.service.FileStorageService;
import mg.itu.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;
import java.math.BigDecimal;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    
    @Autowired
    private ReservationService reservationService;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private FlightRepository flightRepository;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("flights", flightRepository.findAll());
        return "reservation/create";
    }
    
    @PostMapping("/create")
    public String createReservation(@Valid Reservation reservation, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("clients", clientRepository.findAll());
            model.addAttribute("flights", flightRepository.findAll());
            return "reservation/create";
        }
        
        // Validate the number of tickets
        if (reservation.getChildTickets() + reservation.getAdultTickets() != reservation.getTotalTickets()) {
            result.rejectValue("totalTickets", "error.reservation", "Total tickets must equal the sum of child and adult tickets");
            model.addAttribute("clients", clientRepository.findAll());
            model.addAttribute("flights", flightRepository.findAll());
            return "reservation/create";
        }
        
        // Save the reservation
        Reservation savedReservation = reservationService.saveReservation(reservation);
        
        redirectAttributes.addFlashAttribute("reservationId", savedReservation.getId());
        redirectAttributes.addFlashAttribute("totalTickets", savedReservation.getTotalTickets());
        
        return "redirect:/reservations/details";
    }
    
    @GetMapping("/details")
    public String showDetailsForm(Model model, @ModelAttribute("reservationId") Integer reservationId, @ModelAttribute("totalTickets") Integer totalTickets) {
        if (reservationId == null) {
            return "redirect:/reservations/create";
        }
        
        List<ReservationDetail> details = new ArrayList<>();
        for (int i = 0; i < totalTickets; i++) {
            details.add(new ReservationDetail());
        }
        
        model.addAttribute("reservationId", reservationId);
        model.addAttribute("details", details);
        
        return "reservation/add-details";
    }
    
    @PostMapping("/details")
    public String saveDetails(@RequestParam("reservationId") Integer reservationId,
                             @RequestParam("passengerName") List<String> passengerNames,
                             @RequestParam("seatCategory") List<String> seatCategories,
                             @RequestParam("passengerBirthDate") List<String> birthDates,
                             @RequestParam("price") List<String> prices,
                             @RequestParam("promotional") List<Boolean> promotionals,
                             @RequestParam("passportImage") List<MultipartFile> passportImages,
                             RedirectAttributes redirectAttributes) throws IOException {
        
        Reservation reservation = reservationService.findReservationById(reservationId);
        if (reservation == null) {
            redirectAttributes.addFlashAttribute("error", "Reservation not found");
            return "redirect:/reservations/create";
        }
        
        for (int i = 0; i < passengerNames.size(); i++) {
            ReservationDetail detail = new ReservationDetail();
            detail.setPassengerName(passengerNames.get(i));
            detail.setSeatCategory(seatCategories.get(i));
            detail.setPassengerBirthDate(LocalDate.parse(birthDates.get(i)));
            detail.setPrice(new BigDecimal(prices.get(i)));
            detail.setPromotional(promotionals.get(i));
            detail.setCancelled(false);
            
            // Handle passport image upload
            MultipartFile passportImage = passportImages.get(i);
            if (!passportImage.isEmpty()) {
                String fileName = fileStorageService.storeFile(passportImage);
                detail.setPassportImage(fileName);
            } else {
                // Handle error - passport image is required
                redirectAttributes.addFlashAttribute("error", "Passport image is required for all passengers");
                redirectAttributes.addFlashAttribute("reservationId", reservationId);
                redirectAttributes.addFlashAttribute("totalTickets", reservation.getTotalTickets());
                return "redirect:/reservations/details";
            }
            
            reservation.addDetail(detail);
        }
        
        reservationService.saveReservation(reservation);
        
        redirectAttributes.addFlashAttribute("success", "Reservation and details saved successfully");
        return "redirect:/reservations/success";
    }
    
    @GetMapping("/success")
    public String showSuccessPage() {
        return "reservation/success";
    }
}