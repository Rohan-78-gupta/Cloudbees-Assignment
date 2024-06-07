// src/main/java/com/example/trainticket/service/TicketService.java
package com.example.trainticket.service;

import com.example.trainticket.model.Ticket;
import com.example.trainticket.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TicketService {
    private final List<Ticket> tickets = new ArrayList<>();
    private final Map<String, List<Ticket>> seatAllocation = new HashMap<>();
    private static final String[] SECTIONS = {"A", "B"};
    private static final double TICKET_PRICE = 20.0;

    public Ticket purchaseTicket(User user) {
        String seat = allocateSeat();
        Ticket ticket = new Ticket("London", "France", user, TICKET_PRICE, seat);
        tickets.add(ticket);
        seatAllocation.computeIfAbsent(seat, k -> new ArrayList<>()).add(ticket);
        return ticket;
    }

    public Ticket getTicketByEmail(String email) {
        return tickets.stream()
                .filter(ticket -> ticket.getUser().getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public List<Ticket> getUsersBySection(String section) {
        return seatAllocation.getOrDefault(section, new ArrayList<>());
    }

    public boolean removeUser(String email) {
        Ticket ticket = getTicketByEmail(email);
        if (ticket != null) {
            tickets.remove(ticket);
            seatAllocation.get(ticket.getSeat()).remove(ticket);
            return true;
        }
        return false;
    }

    public Ticket modifyUserSeat(String email, String newSeat) {
        Ticket ticket = getTicketByEmail(email);
        if (ticket != null && seatAllocation.containsKey(newSeat)) {
            seatAllocation.get(ticket.getSeat()).remove(ticket);
            ticket.setSeat(newSeat);
            seatAllocation.computeIfAbsent(newSeat, k -> new ArrayList<>()).add(ticket);
            return ticket;
        }
        return null;
    }

    private String allocateSeat() {
        int sectionIndex = tickets.size() % SECTIONS.length;
        return SECTIONS[sectionIndex];
    }
}
