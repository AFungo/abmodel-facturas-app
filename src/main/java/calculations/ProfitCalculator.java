/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculations;

import models.DollarPrice;
import models.Ticket;
import models.Withholding;

import java.util.*;

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
    *Add new ticket to transaction but the values are in dollars
    * @param t ticket to be added
    */
    public void addTicketInDollars(Ticket t, DollarPrice dollarPrice) {
        //get the values
        Float totalAmount = (Float) t.getValues().get("totalAmount");
        Float ivaTax = (t.getValues().get("ivaTax") != null ? (Float) t.getValues().get("ivaTax") : 0.0f);
        Float netAmountWI = (t.getValues().get("netAmountWI") != null ? (Float) t.getValues().get("netAmountWI") : totalAmount);
        //get the exchange value
        Float sellPrice = 1.0f;
        String exchangeMoney = (String)t.getValues().get("exchangeMoney");
        if (exchangeMoney.equals("$")) {   //ticket price in ARS
            sellPrice = getDollarPrice(t, dollarPrice);
        }
        //transform prices to dollar exchange type
        totalAmount = totalAmount / sellPrice; //Total / (exchange type or 1) / (dollar price of this day or 1)
        ivaTax = ivaTax / sellPrice;
        netAmountWI = netAmountWI / sellPrice;

        addTransaction(t.isIncome(), (boolean)t.getValues().get("issuedByMe"), totalAmount, ivaTax, netAmountWI);
    }

    /**
     * Add new ticket to transactions
     * @param t Ticket to be added
     */
    public void addTicketInPesos(Ticket t) {
        //get the values
        Float totalAmount = (Float) t.getValues().get("totalAmount");
        Float ivaTax = (t.getValues().get("ivaTax") != null ? (Float) t.getValues().get("ivaTax") : 0.0f);
        Float netAmountWI = (t.getValues().get("netAmountWI") != null ? (Float) t.getValues().get("netAmountWI") : totalAmount);
        //get the exchange value
        Float exchangeType = 1.0f;
        String exchangeMoney = (String)t.getValues().get("exchangeMoney");
        if (exchangeMoney.equals("USD")) {  //ticket price in USD
            exchangeType = (Float) t.getValues().get("exchangeType");
        }
        //transform prices to pesos exchange type
        totalAmount *= exchangeType;
        ivaTax *= exchangeType;
        netAmountWI *= exchangeType;

        addTransaction(t.isIncome(), (boolean)t.getValues().get("issuedByMe"), totalAmount, ivaTax, netAmountWI);
    }

    /**
     * Add new withholding to transactions
     * @param r whithholding to be added 
     */
    public void addWithholdingInDollars(Withholding r, DollarPrice dollarPrice){
        Float iva = (Float) r.getValues().get("iva");
        Float profits = (Float) r.getValues().get("profits");

        Float sellPrice = (Float) dollarPrice.getValues().get("sell");
        if (iva != null) {
            iva /= sellPrice;
            withholdingIva += iva;
        }
        if (profits != null) {
            profits /= sellPrice;
            withholdingProfits += profits;
        }
    }
    
    /**
     * Add new withholding to transactions
     * @param r whithholding to be added
     */
    public void addWithholdingInPesos(Withholding r){
        Float iva = (Float) r.getValues().get("iva");
        Float profits = (Float) r.getValues().get("profits");

        if (iva != null) {
            withholdingIva += iva;
        }
        if (profits != null) {
            withholdingProfits += profits;
        }
    }

    /**
     * retun the values of all data and calculus 
     * @return return Map<> who have all the data 
     */
    public Map<String, Float> getValues(){
        Map<String, Float> values = new HashMap<String,Float>();
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
        values.put("totalProfitTax", this.getProfit());
        
        return values;
    }
    
    /**
     * calculate the profit with out taxes
     * @return a float of the profit with out tax
     */
    private Float getProfitWOTax(){//calculate the profit with out taxes
        return ((Float)sales.getTransactions().get("totalAmount") - (Float)purchases.getTransactions().get("totalAmount"));    
    }

    /**
     * calculate the profit with taxes
     * @return return the profit with taxes
     */
    private Float getProfitWTax(){//calculate the profit with taxes
        return ((Float)sales.getTransactions().get("totalAmount") - (Float)purchases.getTransactions().get("totalAmount") - withholdingIva - withholdingProfits);
    }
    
    /**
     * Calculate the difference of iva between sales, purchases and withholding
     * @return float with iva tax 
     */
    private Float getIva(){
        return (-(Float)sales.getTransactions().get("iva") + (Float)purchases.getTransactions().get("iva") + withholdingIva);
    }
    
    /**
     * Calculate the difference of net total amounts between sales, purchases and withholding
     * @return float whit the profit tax
     */
    private Float getProfit(){
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
    * Given a ticket and a dollar price, returns the sell price for it
    * if the ticket has a sell price returns it, otherwise returns the one of dollar price
    */
    private Float getDollarPrice(Ticket ticket, DollarPrice price){
        Float sellPrice = (Float) ticket.getValues().get("exchangeType");
        if (sellPrice == 1.0f) {
            sellPrice = (Float) price.getValues().get("sell");
        }
        return sellPrice;
    }

    /*
    *Caculates the gross margin
    */
    private Float getGrossMargin() {
        return  (Float)sales.getTransactions().get("netAmountWI") - (Float)purchases.getTransactions().get("netAmountWI");
    }
}