/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.models;

import java.util.Map;

/**
 *
 * @author ABMODEL
 */
public class Ticket extends Withholding{   
    private Float iva;
    private Float exchangeType;
    private String exchangeMoney;
    private Float netAmountWI;//with iva
    private Float netAmountWOI;//without iva
    private Float amountImpEx;//imp. op. Exentas ver que es
    private Integer numberTo;
    private String authCode;
    private boolean issuedByMe;
    private String sector = null;
    
   public Ticket(Map<String, String> data){
        super(data);
        this.numberTo = data.get("numberTo") != null ? Integer.parseInt(data.get("numberTo")) : null ;
        this.authCode =  data.get("authCode") != null ? data.get("authCode") : null;
        this.exchangeType = Float.parseFloat(data.get("exchangeType"));
        this.exchangeMoney = data.get("exchangeMoney");
        String netAmountWI = data.get("netAmountWI");
        this.netAmountWI = netAmountWI != null && !netAmountWI.isEmpty() ? Float.parseFloat(netAmountWI) : null ;
        String netAmountWOI = data.get("netAmountWOI");
        this.netAmountWOI = netAmountWOI != null && !netAmountWOI.isEmpty() ? Float.parseFloat(netAmountWOI) : null ;
        String amountImpEx = data.get("amountImpEx");
        this.amountImpEx = amountImpEx != null && !amountImpEx.isEmpty() ? Float.parseFloat(amountImpEx) : null ;
        String iva = data.get("iva");
        this.iva = iva != null && !iva.isEmpty() ? Float.parseFloat(iva) : null ;
        this.issuedByMe =  data.get("issuedByMe").equals("true");
  }

    @Override
    public Map<String, Object> getValues() {
        Map<String, Object> dict = super.getValues();
        if (numberTo != null) dict.put("numberTo", numberTo);
        if (numberTo != null) dict.put("authCode", authCode);
        dict.put("provider", provider);
        dict.put("exchangeType", exchangeType);
        dict.put("exchangeMoney", exchangeMoney);
        dict.put("issuedByMe", issuedByMe);
        if (netAmountWI != null) dict.put("netAmountWI", netAmountWI);
        if (netAmountWOI != null) dict.put("netAmountWOI", netAmountWOI);
        if (amountImpEx != null) dict.put("amountImpEx", amountImpEx);
        if (iva != null) dict.put("iva", iva);
        if (sector != null) dict.put("sector", sector);
        return dict;
    }
    
    public void addSector(String sector) {
        this.sector = sector;
    }
    
    public void addId(String id) {
        if (this.id != null)
            throw new IllegalStateException("Id already set, current id: " + this.id + ", new id: " + id);
        
        this.id = Integer.valueOf(id);
    }
    
    public boolean isIncome(){
        if(issuedByMe){
            return !this.type.contains("Crédito");
        }else{ 
            return this.type.contains("Crédito");
        }
    }

    @Override
    public String toString() {
        return "Razon Social" + provider + "\n" + "Num Fact" + number + "\n" + "Fecha" + date + "\n" + "Tipo" + type + "\n" + "Iva" + iva + "\n" + "Total" + totalAmount + "\n"; 
    }
}