package mg.itu.controller;

import mg.itu.model.Client;
import mg.itu.model.Reservation;
import mg.itu.model.ReservationDetail;
import mg.itu.repository.FlightRepository;
import mg.itu.service.FileStorageService;
import mg.itu.service.PriceCalculationService;
import mg.itu.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import java.time.LocalDate;
import java.math.BigDecimal;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    
    @Autowired
    private ReservationService reservationService;
    
    @Autowired
    private FlightRepository flightRepository;
    
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private PriceCalculationService priceService;
    
    @GetMapping("/create")
    public String showCreateForm(Model model, HttpSession session) {
        Client client = (Client) session.getAttribute("client");
        
        if (client == null) {
            return "redirect:/client-login";
        }

        System.out.println("id client: " + client.getId());

        Reservation reservation = new Reservation();
        reservation.setClient(client);

        model.addAttribute("reservation", reservation);
        model.addAttribute("flights", flightRepository.findAll());
        return "reservation/create";
    }
    
    @PostMapping("/create")
    public String createReservation(@Valid Reservation reservation, BindingResult result, Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        Client client = (Client) session.getAttribute("client");
        if (client == null) {
            return "redirect:/client-login";
        }

        // set client
        reservation.setClient(client);
        
        if (result.hasErrors()) {
            model.addAttribute("flights", flightRepository.findAll());
            return "reservation/create";
        }
        
        if (reservation.getChildTickets() + reservation.getAdultTickets() != reservation.getTotalTickets()) {
            result.rejectValue("totalTickets", "error.reservation", "Total tickets must equal the sum of child and adult tickets");
            model.addAttribute("flights", flightRepository.findAll());
            return "reservation/create";
        }
        
        Reservation savedReservation = reservationService.saveReservation(reservation);
        
        redirectAttributes.addFlashAttribute("reservationId", savedReservation.getId());
        redirectAttributes.addFlashAttribute("totalTickets", savedReservation.getTotalTickets());
        redirectAttributes.addFlashAttribute("flightId", savedReservation.getFlight().getId());
        
        return "redirect:/reservations/details";
    }
    
    @GetMapping("/details")
    public String showDetailsForm(Model model, 
                                 @ModelAttribute("reservationId") Integer reservationId, 
                                 @ModelAttribute("totalTickets") Integer totalTickets,
                                 @ModelAttribute("flightId") Integer flightId) {
        if (reservationId == null) {
            return "redirect:/reservations/create";
        }
        
        List<ReservationDetail> details = new ArrayList<>();
        for (int i = 0; i < totalTickets; i++) {
            details.add(new ReservationDetail());
        }
        
        model.addAttribute("reservationId", reservationId);
        model.addAttribute("flightId", flightId);
        model.addAttribute("details", details);
        
        return "reservation/add-details";
    }
    
    @PostMapping("/details")
    public String saveDetails(@RequestParam("reservationId") Integer reservationId,
                             @RequestParam("flightId") Integer flightId,
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
            
            LocalDate birthDate = LocalDate.parse(birthDates.get(i));
            detail.setPassengerBirthDate(birthDate);
            
            boolean isPromotional = promotionals.get(i);
            detail.setPromotional(isPromotional);
            
            // Calculate price using the PriceCalculationService
            BigDecimal calculatedPrice = priceService.calculateSeatPrice(
                flightId, 
                seatCategories.get(i), 
                birthDate, 
                isPromotional
            );
            detail.setPrice(calculatedPrice);
            
            detail.setCancelled(false);
            
            // Update promotional seats availability if applicable
            if (isPromotional) {
                priceService.updatePromotionalSeatsAvailability(flightId, seatCategories.get(i));
            }
            
            MultipartFile passportImage = passportImages.get(i);
            if (!passportImage.isEmpty()) {
                String fileName = fileStorageService.storeFile(passportImage);
                detail.setPassportImage(fileName);
            } else {
                redirectAttributes.addFlashAttribute("error", "Passport image is required for all passengers");
                redirectAttributes.addFlashAttribute("reservationId", reservationId);
                redirectAttributes.addFlashAttribute("totalTickets", reservation.getTotalTickets());
                redirectAttributes.addFlashAttribute("flightId", flightId);
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

    @GetMapping("/list")
    public String showReservationsList(Model model, HttpSession session) {
        Client client = (Client) session.getAttribute("client");
        
        if (client == null) {
            return "redirect:/client-login";
        }

        List<Reservation> reservations = reservationService.findReservationsByClient(client);
        model.addAttribute("reservations", reservations);
        return "reservation/reservations-list";
    }

    @GetMapping("/api/price-calculation")
    @ResponseBody
    public Map<String, Object> calculatePrice(@RequestParam("flightId") Integer flightId,
                                             @RequestParam("seatCategory") String seatCategory,
                                             @RequestParam("birthDate") String birthDate,
                                             @RequestParam("promotional") boolean promotional) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            LocalDate passengerBirthDate = LocalDate.parse(birthDate);
            BigDecimal price = priceService.calculateSeatPrice(flightId, seatCategory, passengerBirthDate, promotional);
            
            response.put("price", price);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        
        return response;
    }
}