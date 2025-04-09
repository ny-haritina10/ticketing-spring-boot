package mg.itu.dto;

public class ReservationDetailDTO {
    
    private String seatCategory;
    private String passengerName;
    private String passengerBirthDate;
    private String price;

    public ReservationDetailDTO()
    { }

    
    public ReservationDetailDTO(String seatCategory, String passengerName, String passengerBirthDate, String price) {
        this.seatCategory = seatCategory;
        this.passengerName = passengerName;
        this.passengerBirthDate = passengerBirthDate;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ReservationDetailDTO{" +
            "seatCategory='" + seatCategory + '\'' +
            ", passengerName='" + passengerName + '\'' +
            ", passengerBirthDate='" + passengerBirthDate + '\'' +
            ", price='" + price + '\'' +
        '}';
    }

    public String getSeatCategory() 
    { return seatCategory; }

    public void setSeatCategory(String seatCategory) 
    { this.seatCategory = seatCategory; }

    public String getPassengerName() 
    { return passengerName; }

    public void setPassengerName(String passengerName) 
    { this.passengerName = passengerName; }

    public String getPassengerBirthDate() 
    { return passengerBirthDate; }

    public void setPassengerBirthDate(String passengerBirthDate) 
    { this.passengerBirthDate = passengerBirthDate; }

    public String getPrice() 
    { return price; }

    public void setPrice(String price) 
    { this.price = price; }
}