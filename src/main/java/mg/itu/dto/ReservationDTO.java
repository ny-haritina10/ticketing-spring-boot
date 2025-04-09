package mg.itu.dto;

import java.util.List;

public class ReservationDTO {
    
    private String client;
    private String flightId;
    private String flightNumber;
    private String planeName;
    private String originCity;
    private String destinationCity;
    private String departureTime;
    private String arrivalTime;
    private String reservationTime;
    private String totalTickets;
    private String childTickets;
    private String adultTickets;
    private List<ReservationDetailDTO> reservationDetails;

    public ReservationDTO()
    { }

    
    public ReservationDTO(String client, String flightId, String flightNumber, String planeName, String originCity,
            String destinationCity, String departureTime, String arrivalTime, String reservationTime,
            String totalTickets, String childTickets, String adultTickets, List<ReservationDetailDTO> reservationDetails) {
        this.client = client;
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.planeName = planeName;
        this.originCity = originCity;
        this.destinationCity = destinationCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.reservationTime = reservationTime;
        this.totalTickets = totalTickets;
        this.childTickets = childTickets;
        this.adultTickets = adultTickets;
        this.reservationDetails = reservationDetails;
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
            "client='" + client + '\'' +
            ", flightId='" + flightId + '\'' +
            ", flightNumber='" + flightNumber + '\'' +
            ", planeName='" + planeName + '\'' +
            ", originCity='" + originCity + '\'' +
            ", destinationCity='" + destinationCity + '\'' +
            ", departureTime='" + departureTime + '\'' +
            ", arrivalTime='" + arrivalTime + '\'' +
            ", reservationTime='" + reservationTime + '\'' +
            ", totalTickets='" + totalTickets + '\'' +
            ", childTickets='" + childTickets + '\'' +
            ", adultTickets='" + adultTickets + '\'' +
            ", reservationDetails=" + reservationDetails +
        '}';
    }

    public String getClient() 
    { return client; }

    public void setClient(String client) 
    { this.client = client; }

    public String getFlightId() 
    { return flightId; }

    public void setFlightId(String flightId) 
    { this.flightId = flightId; }

    public String getFlightNumber() 
    { return flightNumber; }

    public void setFlightNumber(String flightNumber) 
    { this.flightNumber = flightNumber; }

    public String getPlaneName() 
    { return planeName; }

    public void setPlaneName(String planeName) 
    { this.planeName = planeName; }

    public String getOriginCity() 
    { return originCity; }

    public void setOriginCity(String originCity) 
    { this.originCity = originCity; }

    public String getDestinationCity() 
    { return destinationCity; }

    public void setDestinationCity(String destinationCity) 
    { this.destinationCity = destinationCity; }

    public String getDepartureTime() 
    { return departureTime; }

    public void setDepartureTime(String departureTime) 
    { this.departureTime = departureTime; }

    public String getArrivalTime() 
    { return arrivalTime; }

    public void setArrivalTime(String arrivalTime) 
    { this.arrivalTime = arrivalTime; }

    public String getReservationTime() 
    { return reservationTime; }

    public void setReservationTime(String reservationTime) 
    { this.reservationTime = reservationTime; }

    public String getTotalTickets() 
    { return totalTickets; }

    public void setTotalTickets(String totalTickets) 
    { this.totalTickets = totalTickets; }

    public String getChildTickets() 
    { return childTickets; }

    public void setChildTickets(String childTickets) 
    { this.childTickets = childTickets; }

    public String getAdultTickets() 
    { return adultTickets; }

    public void setAdultTickets(String adultTickets) 
    { this.adultTickets = adultTickets; }

    public List<ReservationDetailDTO> getReservationDetails() 
    { return reservationDetails; }

    public void setReservationDetails(List<ReservationDetailDTO> reservationDetails) 
    { this.reservationDetails = reservationDetails; }
}