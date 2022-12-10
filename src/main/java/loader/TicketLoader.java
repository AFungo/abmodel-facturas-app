package loader;

import database.TicketDAO;
import models.Ticket;

import java.util.Optional;

public class TicketLoader implements Loader<Ticket>{

    /*
     * this method take a ticket, and try to save it, if its already exist return db value;
     * and return it
     */
    public Ticket load(Ticket ticket){
        if (!TicketDAO.getInstance().save(ticket)) {
            Optional<Ticket> ticketOptional = TicketDAO.getInstance().getAll().stream()
                    .filter(p -> p.getID().equals(ticket.getID()))
                    .findFirst();
            if (ticketOptional.isPresent()) {
                return ticketOptional.get();
            } else {
                throw new IllegalStateException("The ticket could not be saved but also not obtained");
            }
        }
        return ticket;
    }
}
