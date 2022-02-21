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
  

    public void addRetention(Withholding r, boolean dollars){
        Float sellPrice = inDollars(dollars, 1.0f, r.getDollarPrice());//le pongo 1 en exchangetype pq no se carga en ret
        Float ta = (Float) r.getValues().get("totalAmount") / sellPrice;

        if(r.getValues().get("type").equals("Retencion Iva")) retentionIva.addTransaction(ta, 0.0f, 0.0f);
        if(r.getValues().get("type").equals("Retencion Ganancias")) retentionGan.addTransaction(ta, 0.0f, 0.0f);
    }
    
    public Map<String, Float> getValues(){
        Map<String, Float> values= new HashMap();
        values.put("profitWOTax", this.getProfitWOTax());
        values.put("profitWTax", this.getProfitWTax());
        values.put("totlSell",(Float) sales.getTransactions().get("totalAmount"));
        values.put("totalBuy",(Float)purchases.getTransactions().get("totalAmount"));
        
        values.put("issuedIva",(Float)sales.getTransactions().get("iva"));
        values.put("receivedIva", (Float)purchases.getTransactions().get("iva"));
        values.put("totalIva", this.getIva());
        values.put("totalwithoutholdingIva", (Float) retentionGan.getTransactions().get("totalAmount"));
        
        values.put("issuedNetAmount",(Float)sales.getTransactions().get("netAmountWI"));
        values.put("receivedNetAmount",(Float)purchases.getTransactions().get("netAmountWI"));
        values.put("totalProfitTax", this.getGanancia());
        values.put("totalWithoutholdingGanancia",(Float) retentionGan.getTransactions().get("totalAmount"));
        
        return values;
    }
    
    public Float getProfitWOTax(){//calculate the profit with out taxes
        return ((Float)sales.getTransactions().get("totalAmount") - (Float)purchases.getTransactions().get("totalAmount"));    
    }

    public Float getProfitWTax(){//calculate the profit with taxes
        return ((Float)sales.getTransactions().get("totalAmount") - (Float)purchases.getTransactions().get("totalAmount") - (Float) retentionIva.getTransactions().get("totalAmount") - (Float) retentionGan.getTransactions().get("totalAmount"));     
    }
    
    public Float getIva(){
        return ((Float)sales.getTransactions().get("iva") - (Float)purchases.getTransactions().get("iva") - (Float) retentionIva.getTransactions().get("totalAmount"));
    }
    
    public Float getGanancia(){//falta restarle las retenciones para que te quede el numero.
        return ((Float)sales.getTransactions().get("netAmountWI") - (Float)purchases.getTransactions().get("netAmountWI") - (Float) retentionGan.getTransactions().get("totalAmount"));
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
                purchases.addTransaction(-totalAmount, -iva, -netAmountWI);
            }else{
                purchases.addTransaction(totalAmount, iva, netAmountWI);
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