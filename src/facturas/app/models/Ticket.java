/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.models;

import facturas.app.utils.TicketFormater;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

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

   public Ticket(Map<String, String> data){
        this.date = TicketFormater.dateGen(data.get("date"));
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
    
    public Map<String, Object> getValues() {
        Map<String, Object> dict = new HashMap<>();
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
    
    public boolean isIncome(){
        return !(this.ticketType.contains("Factura") || this.ticketType.equals("Débito"));
    }

    @Override
    public String toString() {
        return "Razon Social" +  provider + "\n" + "Num Fact" + noTicket + "\n" + "Fecha" + date + "\n" + "Tipo" + ticketType + "\n" + "Iva" + iva + "\n" + "Total" + totalAmount + "\n"; 
    }
}
