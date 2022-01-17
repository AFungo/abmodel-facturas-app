/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        
        List<Ticket> tickets = new ArrayList<>();
        for (String strTicket : stringTickets) {
            String[ ] ticketAttributes = strTicket.replace("\"", "").split(",");
            tickets.add(new Ticket(ticketAttributes));
        }
        
        for (Ticket ticket : tickets)
            TicketDAO.createTicket(ticket);
    }
    
    public void createTicket (String ticketData) {
        String[ ] ticketAttributes = ticketData.replace("\"", "").split(",");
        Ticket ticket = new Ticket(ticketAttributes);
        TicketDAO.createTicket(ticket);
    }
    
    public List<Ticket> getTickets(Date initialDate, Date finalDate) {
        ResultSet result = TicketDAO.ticketsBetween(initialDate, finalDate);
        
        List<Ticket> ticketsList = new ArrayList<>();
        try {
            while(result.next()) {
                int len = result.getMetaData().getColumnCount();
                String [ ] ticketAttributes;
                for(int i = 1; i <= len; i++)
                    ticketAttributes[i-1] = result.getString(i);
                
                ticketsList.add(new Ticket(ticketAttributes));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ticketsList;
    }
}
