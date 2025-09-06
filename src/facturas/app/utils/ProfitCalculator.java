/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.utils;

import facturas.app.models.DollarPrice;
import facturas.app.models.Ticket;
import facturas.app.models.Withholding;
import java.util.HashMap;
import java.util.Map;

/**
 *It does all the calculations that it shows in the view.
 * 
 */
public class ProfitCalculator {
    
    private Transaction purchases;
    private Transaction sales;
    private Float withholdingIva;
    private Float withholdingProfits;
   
        /**
     * Constructor
     */
    public ProfitCalculator() {
        purchases = new Transaction();
        sales = new Transaction();
        withholdingIva = 0.0f;
        withholdingProfits = 0.0f;
    }
    
    /**
     * Add new ticket to transactions
     * @param t Ticket to be added
     */
    public void addTicket(Ticket t) {
        Map<String, Object> values = t.getValues();
        String ticketType = values.get("type").toString();
        int notaDeCredito = ticketType.toLowerCase().contains("credito") ? -1 : 1;

        Float exchangeType = 1.0f;
        String exchangeMoney = (String)t.getValues().get("exchangeMoney");
       if (exchangeMoney.equals("USD")) {
            exchangeType = (Float) t.getValues().get("exchangeType");
        }
       
        Float totalAmount = (Float) values.get("totalAmount") * exchangeType * notaDeCredito;   //Total * exchange type
        Float ivaTax = (values.get("ivaTax") != null ? (Float) values.get("ivaTax") : 0.0f) * exchangeType * notaDeCredito;
        Float netAmountWI = (values.get("netAmountWI") != null ? (Float) values.get("netAmountWI") : totalAmount) * exchangeType * notaDeCredito;
        addTransaction(t.isIncome(), (boolean)values.get("issuedByMe"), totalAmount, ivaTax, netAmountWI);
    }
  
    /**
    *Add new ticket to transaction but the values are in dollars 
    * @param t ticket to be added
    */
    public void addTicketInDollars(Ticket t) {
        Map<String, Object> values = t.getValues();
        Float sellPrice = 1.0f;
        
        Float exchangeType = (Float) t.getValues().get("exchangeType");
        String exchangeMoney = (String)t.getValues().get("exchangeMoney");
        if (exchangeMoney == "USD") {   //we already have the price in dollars so we don't want to modify it
            exchangeType = 1.0f;    
        } else if (exchangeType == 1.0f) {  //money are pesos and we don't have the exchange type
            sellPrice = inDollars(true, exchangeType, t.getDollarPrice());
        }
        //in case we are in pesos and we have the exchange type, we just divide by it
        Float totalAmount = ((Float) values.get("totalAmount") / exchangeType) / sellPrice; //Total / (exchange type or 1) / (dollar price of this day or 1)
        Float ivaTax = ((values.get("ivaTax") != null ? (Float) values.get("ivaTax") : 0.0f) / exchangeType)/sellPrice;
        Float netAmountWI = ((values.get("netAmountWI") != null ? (Float) values.get("netAmountWI") : totalAmount) / exchangeType)/sellPrice;        

        addTransaction((boolean)t.isIncome(), (boolean)values.get("issuedByMe"), totalAmount, ivaTax, netAmountWI);
    }
    
    /**
     * Add new withholding to transactions
     * @param r whithholding to be added 
     * @param dollars boolean if the values is need to be in dollars or not
     */
    public void addRetention(Withholding r, boolean dollars){
        Map<String, Object> dict = r.getValues();
        Float iva = (Float) dict.get("iva");
        Float profits = (Float) dict.get("profits");
        if (dollars) {
            Float sellPrice = inDollars(dollars, 1.0f, r.getDollarPrice());//le pongo 1 en exchangetype pq no se carga en ret
            if (iva != null)
                iva /= sellPrice;
            if (profits != null)
                profits /= sellPrice;
        }
        
        if (iva != null && (Float) iva != 0.0f) {
            withholdingIva += (Float) iva;
        }
        if (profits != null && (Float) profits != 0.0f) {
            withholdingProfits += (Float) profits;
        }
    }
    
    /**
     * retun the values of all data and calculus 
     * @return return Map<> who have all the data 
     */
    public Map<String, Float> getValues(){
        Map<String, Float> values = new HashMap();
        //totals
        values.put("profitWOTax", this.getProfitWOTax());
        values.put("profitWTax", this.getProfitWTax());
        values.put("totlSell",(Float) sales.getTransactions().get("totalAmount"));
        values.put("totalBuy",(Float)purchases.getTransactions().get("totalAmount"));
        values.put("grossMargin", this.getGrossMargin());
        
        //iva
        values.put("issuedIva",(Float)sales.getTransactions().get("iva"));
        values.put("receivedIva", (Float)purchases.getTransactions().get("iva"));
        values.put("withheldIva", withholdingIva);
        values.put("totalIva", this.getIva());
        
        //profitTax
        values.put("issuedNetAmount",(Float)sales.getTransactions().get("netAmountWI"));
        values.put("receivedNetAmount",(Float)purchases.getTransactions().get("netAmountWI"));
        values.put("withheldProfit", withholdingProfits);
        values.put("totalProfitTax", this.getGanancia());
        
        return values;
    }
    
    /**
     * calculate the profit with out taxes
     * @return a float of the profit with out tax
     */
    public Float getProfitWOTax(){//calculate the profit with out taxes
        return ((Float)sales.getTransactions().get("totalAmount") - (Float)purchases.getTransactions().get("totalAmount"));    
    }

    /**
     * calculate the profit with taxes
     * @return return the profit with taxes
     */
    public Float getProfitWTax(){//calculate the profit with taxes
        return ((Float)sales.getTransactions().get("totalAmount") - (Float)purchases.getTransactions().get("totalAmount") - withholdingIva - withholdingProfits);
    }
    
    /**
     * Calculate the difference of iva between sales, purchases and withholding
     * @return float with iva tax 
     */
    public Float getIva(){
        return (-(Float)sales.getTransactions().get("iva") + (Float)purchases.getTransactions().get("iva") + withholdingIva);
    }
    
    /**
     * Calculate the difference of net total amounts between sales, purchases and withholding
     * @return float whit the profit tax
     */
    public Float getGanancia(){//falta restarle las retenciones para que te quede el numero.
        return (-(Float)sales.getTransactions().get("netAmountWI") + (Float)purchases.getTransactions().get("netAmountWI") + withholdingProfits);
    }
    
    /*
     * get a ticket or withholding and add to transactions
     */
    private void addTransaction(boolean isIncome, boolean issuedByMe, Float totalAmount, Float iva, Float netAmountWI){
        
        if (issuedByMe) {
            if (isIncome){
                sales.addTransaction(totalAmount, iva, netAmountWI);
            }else{          
                sales.addTransaction(-totalAmount, -iva, -netAmountWI);
            }
        } else {
            if (isIncome){
                purchases.addTransaction(-totalAmount, -iva, -netAmountWI);
            }else{
                purchases.addTransaction(totalAmount, iva, netAmountWI);
            }
        }
    }
    
   /*
    *return the the dollar proce who need the ticket
    */
    private Float inDollars(boolean dollars, Float exchangeType, DollarPrice price){
        if (dollars && exchangeType == 1.0f) {  //if dollars are required and exchange type is pesos          
            if (price != null) {    //if price was loaded (it may not be in db)
                return (Float)price.getValues().get("sell");               
            }
        }
        return 1.0f;
    }            

    /*
    *Caculates the gross margin
    */
    private Float getGrossMargin() {
        return  (Float)sales.getTransactions().get("netAmountWI") - (Float)purchases.getTransactions().get("netAmountWI");
    }
}