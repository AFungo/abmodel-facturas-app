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
        purchases = new Transaction();
        sales = new Transaction();
        retentionIva = new Transaction();
        retentionGan = new Transaction();
    }
    
    public void addTicket(Ticket t, boolean dollars) {
        Map<String, Object> values = t.getValues();
        
        Float exchangeType = (Float) t.getValues().get("exchangeType");
        Float sellPrice = inDollars(dollars, exchangeType, t.getDollarPrice());
        
       
        Float totalAmount = ((Float) values.get("totalAmount") * exchangeType) / sellPrice;//Total * (exchange type who coming in the file) / (dollar price of this day or 1)
        Float iva = ((values.get("iva") != null ? (Float) values.get("iva") : 0.0f) * exchangeType)/sellPrice;
        Float netAmountWI = ((values.get("netAmountWI") != null ? (Float) values.get("netAmountWI") : totalAmount) * exchangeType)/sellPrice;        

        addTransaction((boolean)t.isIncome(), (boolean)values.get("issuedByMe"), totalAmount, iva, netAmountWI);
    }
  

    public void addRetention(Withholding r){
        Float ta = (Float) r.getValues().get("totalAmount");
        if(r.getValues().get("type").equals("retIva")) retentionIva.addTransaction(ta, 0.0f, 0.0f);
        if(r.getValues().get("type").equals("retGan")) retentionGan.addTransaction(ta, 0.0f, 0.0f);
    }
    
    public Float getProfit(){
        return ((Float)sales.getTransactions().get("totalAmount") + (Float)purchases.getTransactions().get("totalAmount"));
        
    }
    
    public Float getIva(){
        return ((Float)sales.getTransactions().get("iva") + (Float)purchases.getTransactions().get("iva") - (Float) retentionIva.getTransactions().get("totalAmount"));
    }
    public Float getGanancia(){//falta restarle las retenciones para que te quede el numero.
        return ((Float)sales.getTransactions().get("netAmountWI") + (Float)purchases.getTransactions().get("netAmountWI") - (Float) retentionGan.getTransactions().get("totalAmount"));
    }
    
    private void addTransaction(boolean isIncome, boolean issuedByMe, Float totalAmount, Float iva, Float netAmountWI){
        
        if (issuedByMe) {
            if (isIncome){
                sales.addTransaction(-totalAmount, -iva, -netAmountWI);
            }else{          
                sales.addTransaction(totalAmount, iva, netAmountWI);
            }
        } else {
            if (isIncome){
                purchases.addTransaction(totalAmount, iva, netAmountWI);
            }else{
                purchases.addTransaction(-totalAmount, -iva, -netAmountWI);
            }
        }
    }
    private Float inDollars(boolean dollars, Float exchangeType, DollarPrice price){
        if (dollars && exchangeType == 1.0f) {  //if dollars are required and exchange type is pesos          
            if (price != null) {    //if price was loaded (it may not be in db)
                return (Float)price.getValues().get("sell");               
            }
        }
        return 1.0f;
    }            
}