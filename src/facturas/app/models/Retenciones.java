/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.models;

import facturas.app.utils.TicketFormater;
import java.util.Map;
import java.util.HashMap;
import java.sql.Date;

/**
 *
 * @author Fungo
 */
public class Retenciones {
    protected Provider provider; 
    protected int number;
    protected Date date;
    protected Float totalAmount;
    protected String type;
    
   public Retenciones(Map<String, String> data){
        this.date = TicketFormater.dateGen(data.get("date"));
        this.type = data.get("type");
        this.number = Integer.parseInt(data.get("number"));
        this.provider = new Provider(data.get("providerDocType"), data.get("providerCuit"), data.get("providerName"));
        this.totalAmount = Float.parseFloat(data.get("totalAmount"));
   }    
   public Retenciones(Provider provider, int noRetencion, Date date, Float totalAmount, String retType){
       this.provider = provider;
       this.number = number;
       this.date = date;
       this.type = type;
       this.totalAmount = totalAmount;
   }
   public Map<String, Object> getValues(){
       Map<String, Object> dict = new HashMap<>();
       dict.put("date", date);
       dict.put("type", type);
       dict.put("totalAmount", totalAmount);
       dict.put("provider", provider);
       dict.put("number", number);
       return dict;
   }                  
 }