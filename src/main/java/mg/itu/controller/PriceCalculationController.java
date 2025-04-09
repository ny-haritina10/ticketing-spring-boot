package mg.itu.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mg.itu.service.PriceCalculationService;

@RestController
@RequestMapping("/api")
public class PriceCalculationController {

    @Autowired
    private PriceCalculationService priceService;

    @GetMapping("/price-calculation")
    public Map<String, Object> calculatePrice(
            @RequestParam("flightId") Integer flightId,
            @RequestParam("seatCategory") String seatCategory,
            @RequestParam("birthDate") String birthDate,
            @RequestParam("promotional") boolean promotional) {

        Map<String, Object> response = new HashMap<>();
        try {
            LocalDate passengerBirthDate = LocalDate.parse(birthDate);
            BigDecimal price = priceService.calculateSeatPrice(
                flightId,
                seatCategory,
                passengerBirthDate,
                promotional
            );

            response.put("price", price);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }

        return response;
    }
}