package mg.itu.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mg.itu.service.ReservationService;

@RestController
@RequestMapping("/api/export")
public class ExportPDFController {

    @Autowired
    private ReservationService reservationService;
    
    @Value("${api.security.token}")
    private String apiSecurityToken;

    @GetMapping(value = "/reservation/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadReservationPdf(
            @PathVariable("id") Integer reservationId,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        
        // Verify the token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        
        // Extract the token
        String token = authHeader.substring(7);
        
        // Simple token validation
        if (!apiSecurityToken.equals(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        
        try {
            byte[] pdfBytes = reservationService.generateReservationPdf(reservationId);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "reservation_" + reservationId + ".pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
                    
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}