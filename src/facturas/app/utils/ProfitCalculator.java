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
    
    private Transaction purchases;
    private Transaction sales;
    
    public ProfitCalculator() {
        purchases = new Transaction(0.0, 0.0);
        sales = new Transaction(0.0, 0.0);
    }
    
    public void addTicket(Ticket t) {
        Double ta = (Double) t.getValues().get("totalAmount");
        Double i = (Double) t.getValues().get("iva");
        if(t.isIncome()) sales.addTransaction(ta, i);
        else purchases.addTransaction(ta, i);
    }
    
    public Double getProfit(){
        return (sales.getTransactions().getFst() - purchases.getTransactions().getFst());
    }
    
    public Double getIva(){
        return (sales.getTransactions().getSnd() - purchases.getTransactions().getSnd());
    }
}
