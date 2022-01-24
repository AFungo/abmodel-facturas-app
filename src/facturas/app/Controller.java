/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app;

import facturas.app.database.ProviderDAO;
import facturas.app.database.SQLFilter;
import facturas.app.database.TicketDAO;
import facturas.app.models.Provider;
import facturas.app.models.Ticket;
import facturas.app.utils.Formater;
import facturas.app.utils.ProfitCalculator;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nacho
 */
public class Controller {
    
    public void loadTickets(File f) {
        if (f == null) {
            System.out.println("File at " + f.getPath() + " doesn't exists");
            return ;
        }
        
        List<String> stringTickets = new ArrayList<>();
        try {
            stringTickets = Files.readAllLines(f.toPath(), Charset.defaultCharset());
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String initialLine = stringTickets.remove(0); //Remove the first row of the file for checking
        if (!validFormat(initialLine)) {
            System.out.println("File does not have a valid format to be loaded\nFile: " + f.getPath());
            return;
        }
            
        List<Ticket> tickets = new ArrayList<>();
        for (String strTicket : stringTickets) {
            String[ ] ticketAttributes = strTicket.replace("\"", "").split(",");
            tickets.add(new Ticket(Formater.ticketCsvToDict(ticketAttributes)));
        }
        
        tickets.forEach((ticket) -> {
            TicketDAO.addTicket(ticket);
        });
    }
    
    public ProfitCalculator getProfit() {
        ProfitCalculator profit = new ProfitCalculator();
        for(Ticket t : getTickets())
            profit.addTicket(t);
        
        return profit;
    }
    
    public void createTicket(String ticketData) {
        String[ ] ticketAttributes = ticketData.replace("\"", "").split(",");
        Ticket ticket = new Ticket(Formater.ticketCsvToDict(ticketAttributes));
        TicketDAO.addTicket(ticket);
    }
    
    public List<Ticket> getTickets() {
        return TicketDAO.getTickets();
    }
    
    public List<Ticket> getTickets(SQLFilter filters) {
        if (filters.isEmpty())
            return getTickets();
        else
            return TicketDAO.getTickets(filters);
    }
    
    public List<Provider> getProviders() {
        return ProviderDAO.getProviders();
    }
    
    private boolean validFormat(String initialLine) {
        String expectedLine = "\"Fecha\",\"Tipo\",\"Punto de Venta\",\"Número Desde\",\"Número Hasta\",\"Cód. Autorización\",\"Tipo Doc. Emisor\",\"Nro. Doc. Emisor\",\"Denominación Emisor\",\"Tipo Cambio\",\"Moneda\",\"Imp. Neto Gravado\",\"Imp. Neto No Gravado\",\"Imp. Op. Exentas\",\"IVA\",\"Imp. Total\"";
        char initialChar = initialLine.charAt(0);
        if ((int)initialChar == 65279)  //special char that may come with utf-8 files
            initialLine = initialLine.substring(1); //remove special char
        
        return initialLine.contentEquals(expectedLine);
    }
}