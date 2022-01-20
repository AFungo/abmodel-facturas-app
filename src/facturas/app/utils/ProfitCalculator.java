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
        purchases = new Transaction(0.0f, 0.0f);
        sales = new Transaction(0.0f, 0.0f);
    }
    
    public void addTicket(Ticket t) {
        Float ta = (Float) t.getValues().get("totalAmount");
        Float i = (Float) t.getValues().get("iva");
        if(t.isIncome()) sales.addTransaction(ta, i);
        else purchases.addTransaction(ta, i);
    }
    
    public Float getProfit(){
        return (sales.getTransactions().getFst() - purchases.getTransactions().getFst());
    }
    
    public Float getIva(){
        return (sales.getTransactions().getSnd() - purchases.getTransactions().getSnd());
    }
}
