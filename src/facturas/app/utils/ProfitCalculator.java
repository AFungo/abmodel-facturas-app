/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.utils;

import facturas.app.models.Ticket;
import facturas.app.models.Retenciones;
/**
 *
 * @author nacho
 */
public class ProfitCalculator {
    
    private Transaction purchases;
    private Transaction sales;
    private Transaction retentionIva;
    private Transaction retentionGan;
    
    public ProfitCalculator() {
        purchases = new Transaction(0.0f, 0.0f, 0.0f);
        sales = new Transaction(0.0f, 0.0f, 0.0f);
        retentionIva = new Transaction(0.0f, 0.0f, 0.0f);
        retentionGan = new Transaction(0.0f, 0.0f, 0.0f);
    }
    
    public void addTicket(Ticket t) {
        Float ta = (Float) t.getValues().get("totalAmount");
        Float i = (Float) t.getValues().get("iva");
        Float nw = (Float) t.getValues().get("netAmountWOI");
        if(t.isIncome()) sales.addTransaction(ta, i, nw);
        else purchases.addTransaction(ta, i, nw);
    }
    public void addRetention(Retenciones r){
        Float ta = (Float) r.getValues().get("totalAmount");
        if(r.getValues().get("type").equals("retIva")) retentionIva.addTransaction(ta, 0.0f, 0.0f);
        if(r.getValues().get("type").equals("retGan")) retentionGan.addTransaction(ta, 0.0f, 0.0f);
    }
    
    public Float getProfit(){
        return ((Float)sales.getTransactions().get("totalAmount") - (Float)purchases.getTransactions().get("totalAmount"));
    }
    
    public Float getIva(){
        return ((Float)sales.getTransactions().get("iva") - (Float)purchases.getTransactions().get("iva") - (Float) retentionIva.getTransactions().get("totalAmount"));
    }
    public Float getGanancia(){//falta restarle las retenciones para que te quede el numero.
        return ((Float)sales.getTransactions().get("netAmountWOI")- (Float)purchases.getTransactions().get("netAmountWOI") - (Float) retentionGan.getTransactions().get("totalAmount"));
    }
   
}



















