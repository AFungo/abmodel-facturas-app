/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.utils;

import facturas.app.models.Ticket;
import facturas.app.models.Withholding;
import java.util.Map;

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
        Map<String, Object> values = t.getValues();
        Float exchangeType = (Float) t.getValues().get("exchangeType");
        Float totalAmount = (Float) values.get("totalAmount") * exchangeType;
        Float iva = (values.get("iva") != null ? (Float) values.get("iva") : 0.0f) * exchangeType;
        Float netAmountWOI = (values.get("netAmountWOI") != null ? (Float) values.get("netAmountWOI") : 0.0f) * exchangeType;
        if (t.isIncome())
            sales.addTransaction(totalAmount, iva, netAmountWOI);
        else
            purchases.addTransaction(totalAmount, iva, netAmountWOI);
    }

    
    public void addRetention(Withholding r){
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