package com.demo.Controller;

import com.demo.Exception.ResourceNotFoundException;
import com.demo.Model.Ticket;
import com.demo.Service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getTickets(@RequestParam(required = false) String search) {
        if (search != null && !search.isEmpty()) {
            return ResponseEntity.ok(ticketService.searchTicketsByKeyword(search));
        }
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @PostMapping
    public ResponseEntity<Ticket> bookTicket(@Valid @RequestBody Ticket ticket) {
        Ticket savedTicket = ticketService.bookTicket(ticket);
        return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> searchTicketById(@PathVariable String ticketId) {
        Ticket ticket = ticketService.searchTicketById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with ID: " + ticketId));
        return ResponseEntity.ok(ticket);
    }
    
    @GetMapping("/movie/{movieName}")
    public ResponseEntity<List<Ticket>> searchTicketsByMovieName(@PathVariable String movieName) {
        List<Ticket> tickets = ticketService.searchTicketsByMovieName(movieName);
        if (!tickets.isEmpty()) {
            return ResponseEntity.ok(tickets);
        }
        throw new ResourceNotFoundException("No tickets found for movie: " + movieName);
    }

    @GetMapping("/customer/{customerName}")
    public ResponseEntity<List<Ticket>> searchTicketsByCustomerName(@PathVariable String customerName) {
        List<Ticket> tickets = ticketService.searchTicketsByCustomerName(customerName);
        if (!tickets.isEmpty()) {
            return ResponseEntity.ok(tickets);
        }
        throw new ResourceNotFoundException("No tickets found for customer: " + customerName);
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> cancelTicket(@PathVariable String ticketId) {
        boolean cancelled = ticketService.cancelTicket(ticketId);
        if (!cancelled) {
            throw new ResourceNotFoundException("Cancellation failed. Ticket not found with ID: " + ticketId);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/movie/{movieName}")
    public ResponseEntity<Void> cancelTicketsByMovieName(@PathVariable String movieName) {
        long deletedCount = ticketService.cancelTicketsByMovieName(movieName);
        if (deletedCount > 0) {
            return ResponseEntity.noContent().build();
        }
        throw new ResourceNotFoundException("No tickets found to cancel for movie: " + movieName);
    }
}