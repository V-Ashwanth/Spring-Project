package com.demo.Service;

import com.demo.Model.Ticket;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private final Map<String, Ticket> ticketStore = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        bookTicket(new Ticket("TKT-001", "Inception", "John Doe", "A12", 12.50));
        bookTicket(new Ticket("TKT-002", "Interstellar", "Jane Smith", "B04", 15.00));
        bookTicket(new Ticket("TKT-003", "The Matrix", "Alice Brown", "C09", 10.00));
    }

    public Ticket bookTicket(Ticket ticket) {
        if (ticket.getTicketId() == null || ticket.getTicketId().trim().isEmpty()) {
            ticket.setTicketId("TKT-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        }
        ticketStore.put(ticket.getTicketId(), ticket);
        return ticket;
    }

    public Optional<Ticket> searchTicketById(String ticketId) {
        return Optional.ofNullable(ticketStore.get(ticketId));
    }

    public List<Ticket> searchTicketsByMovieName(String movieName) {
        String lowerCaseMovie = movieName.toLowerCase();
        return ticketStore.values().stream()
                .filter(t -> t.getMovieName() != null && t.getMovieName().toLowerCase().contains(lowerCaseMovie))
                .collect(Collectors.toList());
    }

    public List<Ticket> searchTicketsByCustomerName(String customerName) {
        String lowerCaseCustomer = customerName.toLowerCase();
        return ticketStore.values().stream()
                .filter(t -> t.getCustomerName() != null && t.getCustomerName().toLowerCase().contains(lowerCaseCustomer))
                .collect(Collectors.toList());
    }

    public long cancelTicketsByMovieName(String movieName) {
        long initialSize = ticketStore.size();
        ticketStore.values().removeIf(t -> t.getMovieName() != null && t.getMovieName().equalsIgnoreCase(movieName));
        return initialSize - ticketStore.size();
    }
    public List<Ticket> searchTicketsByKeyword(String keyword) {
        String lowerCaseKeyword = keyword.toLowerCase();
        return ticketStore.values().stream()
                .filter(t -> (t.getMovieName() != null && t.getMovieName().toLowerCase().contains(lowerCaseKeyword)) ||
                             (t.getCustomerName() != null && t.getCustomerName().toLowerCase().contains(lowerCaseKeyword)) ||
                             (t.getSeatNumber() != null && t.getSeatNumber().toLowerCase().contains(lowerCaseKeyword)))
                .collect(Collectors.toList());
    }

    public boolean cancelTicket(String ticketId) {
        return ticketStore.remove(ticketId) != null;
    }

    public List<Ticket> getAllTickets() {
        return new ArrayList<>(ticketStore.values());
    }
}