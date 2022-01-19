/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app;

import facturas.app.database.TicketDAO;
import facturas.app.models.Ticket;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nacho
 */
public class Controller {
    
    public void loadTickets(String fileName) {
        List<String> stringTickets = new ArrayList<>();
        try {
            stringTickets = Files.readAllLines(new File(fileName).toPath(), Charset.defaultCharset());
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        stringTickets.remove(0); //Remove the first row of the file
        List<Ticket> tickets = new ArrayList<>();
        for (String strTicket : stringTickets) {
            String[ ] ticketAttributes = strTicket.replace("\"", "").split(",");
            tickets.add(new Ticket(ticketAttributes));
        }
        
        tickets.forEach((ticket) -> {
            TicketDAO.addTicket(ticket);
        });
    }
    
    public void createTicket (String ticketData) {
        String[ ] ticketAttributes = ticketData.replace("\"", "").split(",");
        Ticket ticket = new Ticket(ticketAttributes);
        TicketDAO.addTicket(ticket);
    }
    
    public List<Ticket> getTickets(Date initialDate, Date finalDate) {
        List<Ticket> ticketsList = TicketDAO.ticketsBetween(initialDate, finalDate);
        return ticketsList;
    }

    public List<Ticket> getTickets() {
        List<Ticket> ticketsList = TicketDAO.getTickets();
        return ticketsList;
    }
}
