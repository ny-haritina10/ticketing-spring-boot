package mg.itu.service;

import mg.itu.model.*;
import mg.itu.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
public class PriceCalculationService {

    @Autowired
    private FlightPriceRepository flightPriceRepository;
    
    @Autowired
    private FlightPromotionRepository flightPromotionRepository;
    
    @Autowired
    private PercentageDiscountRepository percentageDiscountRepository;

    public BigDecimal calculateSeatPrice(Integer flightId, String seatCategory, LocalDate passengerBirthDate, boolean applyPromotion) {
        
        // 1. Get the base price for the flight and seat category
        Optional<FlightPrice> flightPriceOptional = flightPriceRepository
                .findByFlightIdAndCategory(flightId, seatCategory);
        
        if (!flightPriceOptional.isPresent()) {
            throw new IllegalArgumentException("No price found for flight ID " + flightId 
                    + " and category " + seatCategory);
        }
        
        BigDecimal basePrice = flightPriceOptional.get().getBasePrice();
        BigDecimal finalPrice = basePrice;
        
        // 2. Apply promotion discount if requested and available
        if (applyPromotion) {
            Optional<FlightPromotion> promotionOptional = flightPromotionRepository
                    .findByFlightIdAndCategory(flightId, seatCategory);
            
            if (promotionOptional.isPresent()) {
                FlightPromotion promotion = promotionOptional.get();
                
                // Check if promotion seats are still available
                if (promotion.getSeatsAvailable() > 0) {
                    BigDecimal discountPercentage = promotion.getDiscountPercentage();
                    BigDecimal discountAmount = basePrice.multiply(discountPercentage)
                            .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
                    
                    finalPrice = basePrice.subtract(discountAmount);
                }
            }
        }
        
        // 3. Apply age-based discount if applicable
        if (passengerBirthDate != null) {
            int age = Period.between(passengerBirthDate, LocalDate.now()).getYears();
            
            // Find applicable age category and discount
            Optional<PercentageDiscount> discountOptional = percentageDiscountRepository
                    .findByAgeMax(age);
            
            if (discountOptional.isPresent()) {
                PercentageDiscount discount = discountOptional.get();
                BigDecimal ageDiscountPercentage = BigDecimal.valueOf(discount.getPercentageDiscount());
                BigDecimal ageDiscountAmount = finalPrice.multiply(ageDiscountPercentage)
                        .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
                
                finalPrice = finalPrice.subtract(ageDiscountAmount);
            }
        }
        
        return finalPrice.setScale(2, RoundingMode.HALF_UP);
    }
    
    public boolean updatePromotionalSeatsAvailability(Integer flightId, String seatCategory) {
        Optional<FlightPromotion> promotionOptional = flightPromotionRepository
                .findByFlightIdAndCategory(flightId, seatCategory);
        
        if (!promotionOptional.isPresent() || promotionOptional.get().getSeatsAvailable() <= 0) {
            return false;
        }
        
        FlightPromotion promotion = promotionOptional.get();
        promotion.setSeatsAvailable(promotion.getSeatsAvailable() - 1);
        flightPromotionRepository.save(promotion);
        
        return true;
    }
}