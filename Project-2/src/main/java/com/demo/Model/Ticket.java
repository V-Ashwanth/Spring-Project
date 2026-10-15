package com.demo.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class Ticket {
    
    private String ticketId;
    
    @NotBlank(message = "Movie name is required")
    private String movieName;
    
    @NotBlank(message = "Customer name cannot be empty")
    private String customerName;
    
    @NotBlank(message = "Seat number is required")
    private String seatNumber;
    
    @Positive(message = "Ticket price must be positive")
    private double ticketPrice;

    public Ticket() {}

    public Ticket(String ticketId, String movieName, String customerName, String seatNumber, double ticketPrice) {
        this.ticketId = ticketId;
        this.movieName = movieName;
        this.customerName = customerName;
        this.seatNumber = seatNumber;
        this.ticketPrice = ticketPrice;
    }

    // Getters and Setters
    public String getTicketId() { return ticketId; }
    public void setTicketId(String ticketId) { this.ticketId = ticketId; }

    public String getMovieName() { return movieName; }
    public void setMovieName(String movieName) { this.movieName = movieName; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public double getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(double ticketPrice) { this.ticketPrice = ticketPrice; }

    @Override
    public String toString() {
        return "Ticket [ticketId=" + ticketId + ", movieName=" + movieName + ", customerName=" + customerName 
                + ", seatNumber=" + seatNumber + ", ticketPrice=" + ticketPrice + "]";
    }
}