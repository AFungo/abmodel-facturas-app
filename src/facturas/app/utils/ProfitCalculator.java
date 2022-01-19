/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.utils;

import facturas.app.models.Ticket;

/**
 *
 * @author nacho
 */
public class ProfitCalculator {
    
    private Purchase purchases;
    private Sale sales;
    
    public ProfitCalculator() {
        purchases = new Purchase(0, 0);
        sales = new Sale(0, 0, 0, 0);
    }
    
    public void addTicket(Ticket t) {
        throw new UnsupportedOperationException("to do");
    }
    
}
