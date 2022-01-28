/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.utils;

import facturas.app.models.DollarPrice;
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
    
    public void addTicket(Ticket t, boolean dollars) {
        Map<String, Object> values = t.getValues();
        Float exchangeType = (Float) t.getValues().get("exchangeType");
        Float totalAmount = (Float) values.get("totalAmount") * exchangeType;
        Float iva = (values.get("iva") != null ? (Float) values.get("iva") : 0.0f) * exchangeType;
        Float netAmountWI = (values.get("netAmountWI") != null ? (Float) values.get("netAmountWI") : 0.0f) * exchangeType;
        
        if (dollars && exchangeType == 1.0f) {  //if dollars are required and exchange type is pesos
            DollarPrice price = t.getDollarPrice();
            if (price != null) {    //if price was loaded (it may not be in db)
                Float sellPrice = (Float)price.getValues().get("sell");
                totalAmount /= sellPrice;
                iva /= sellPrice;
                netAmountWI /= sellPrice;
            }
        }
        
        if ((boolean) values.get("myTicket")) {
            if (t.isIncome())
                sales.addTransaction(-totalAmount, -iva, -netAmountWI);
            else
                sales.addTransaction(totalAmount, iva, netAmountWI);
        } else {
            if (t.isIncome())
                purchases.addTransaction(totalAmount, iva, netAmountWI);
            else
                purchases.addTransaction(-totalAmount, -iva, -netAmountWI);
        }
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
        return ((Float)sales.getTransactions().get("netAmountWI")- (Float)purchases.getTransactions().get("netAmountWI") - (Float) retentionGan.getTransactions().get("totalAmount"));
    }
    
}