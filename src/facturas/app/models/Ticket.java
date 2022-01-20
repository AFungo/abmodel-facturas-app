/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.models;

import java.util.Hashtable;
import java.sql.Date;

/**
 *
 * @author ABMODEL
 */
public class Ticket {   
    private Provider provider; 
    private int noTicket;
    private Date date;
    private Float iva;
    private float totalAmount;
    private float exchangeType;
    private String ticketType;
    private String exchangeMoney;
    private Float netAmountWI;//with iva
    private Float netAmountWOI;//without iva
    private Float amountImpEx;//imp. op. Exentas ver que es
    private Integer numberTo;
    private String authCode;

   public Ticket(Hashtable<String, String> data){
        this.date = dateGen(data.get("date"));
        this.ticketType = data.get("ticketType");
        this.noTicket = Integer.parseInt(data.get("noTicket"));
        this.numberTo = data.get("numberTo") != null ? Integer.parseInt(data.get("numberTo")) : null ;
        this.authCode =  data.get("authCode");
        this.provider = new Provider(data.get("providerDocType"), data.get("providerCuit"), data.get("providerName"));
        this.exchangeType = Float.parseFloat(data.get("exchangeType"));
        this.exchangeMoney = data.get("exchangeMoney");
        this.netAmountWI = data.get("netAmountWI") != null ? Float.parseFloat(data.get("netAmountWI")) : null ;
        this.netAmountWOI = data.get("netAmountWOI") != null ? Float.parseFloat(data.get("netAmountWOI")) : null ;
        this.amountImpEx = data.get("amountImpEx") != null ? Float.parseFloat(data.get("amountImpEx")) : null ;
        this.iva = data.get("iva") != null ? Float.parseFloat(data.get("iva")) : null ;
        this.totalAmount = Float.parseFloat(data.get("totalAmount"));
    }

    public Provider getProvider() {
        return provider;
    }
    
    public Hashtable<String, Object> getValues() {
        Hashtable<String, Object> dict = new Hashtable<>();
        dict.put("date", date);
        dict.put("ticketType", ticketType);
        dict.put("noTicket", noTicket);
        if (numberTo != null) dict.put("numberTo", numberTo);
        dict.put("authCode", authCode);
        dict.put("provider", provider);
        dict.put("exchangeType", exchangeType);
        dict.put("exchangeMoney", exchangeMoney);
        if (netAmountWI != null) dict.put("netAmountWI", netAmountWI);
        if (netAmountWOI != null) dict.put("netAmountWOI", netAmountWOI);
        if (amountImpEx != null) dict.put("amountImpEx", amountImpEx);
        if (iva != null) dict.put("iva", iva);
        dict.put("totalAmount", totalAmount);
        return dict;
    }
    
    @Override
    public String toString() {
        return "Razon Social" +  provider + "\n" + "Num Fact" + noTicket + "\n" + "Fecha" + date + "\n" + "Tipo" + ticketType + "\n" + "Iva" + iva + "\n" + "Total" + totalAmount + "\n"; 
    }
    
    //this method takes a String and returns a Date.
    private Date dateGen(String dateStr){
        String[] fields = dateStr.split("/"); //d-m-y
        if (fields.length != 3) {
            dateStr = fields[0];
            fields = dateStr.split("-");
            String formatedDate = fields[0] + "-" + fields[1] + "-" + fields[2]; //y-m-d
            return Date.valueOf(formatedDate);
        }
        String formatedDate = fields[2] + "-" + fields[1] + "-" + fields[0]; //y-m-d
        return Date.valueOf(formatedDate);
    }
}
