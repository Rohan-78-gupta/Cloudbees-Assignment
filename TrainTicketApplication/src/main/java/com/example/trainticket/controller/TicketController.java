// src/main/java/com/example/trainticket/controller/TicketController.java
package com.example.trainticket.controller;

import com.example.trainticket.model.Ticket;
import com.example.trainticket.model.User;
import com.example.trainticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/purchase")
    public ResponseEntity<Ticket> purchaseTicket(@RequestBody User user) {
        Ticket ticket = ticketService.purchaseTicket(user);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/receipt")
    public ResponseEntity<Ticket> getTicketByEmail(@RequestParam String email) {
        Ticket ticket = ticketService.getTicketByEmail(email);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }

    @GetMapping("/section/{section}")
    public ResponseEntity<List<Ticket>> getUsersBySection(@PathVariable String section) {
        List<Ticket> tickets = ticketService.getUsersBySection(section);
        return ResponseEntity.ok(tickets);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeUser(@RequestParam String email) {
        boolean removed = ticketService.removeUser(email);
        return removed ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/modify")
    public ResponseEntity<Ticket> modifyUserSeat(@RequestParam String email, @RequestParam String newSeat) {
        Ticket ticket = ticketService.modifyUserSeat(email, newSeat);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }
}
