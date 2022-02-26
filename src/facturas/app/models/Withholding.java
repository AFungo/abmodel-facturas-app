/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.models;

import facturas.app.utils.FormatUtils;
import java.util.Map;
import java.util.HashMap;
import java.sql.Date;

/**
 *
 * @author Fungo
 */
public class Withholding {
    protected Provider provider;
    protected String number;
    protected Date date;
    protected Integer id;
    protected Float iva;
    protected Float profits;
    protected Boolean delivered;
    protected String sector;
    protected DollarPrice dollarPrice = null;

    public Withholding(Map<String, String> data) {
        date = FormatUtils.dateGen(data.get("date"));
        number = data.get("number");
        provider = new Provider(data);
        sector = data.get("sector");
        if (data.get("iva") != null) iva = Float.parseFloat(data.get("iva"));
        if (data.get("profits") != null) profits = Float.parseFloat(data.get("profits"));
        if (data.get("id") != null) id = Integer.parseInt(data.get("id"));
        if (data.get("delivered") != null) delivered = Boolean.valueOf(data.get("delivered"));
    }

    public void addDollarPrice(DollarPrice price) {
        if (price == null) {
            System.out.println("No price for date " + date);
            return ;
        }
        
        dollarPrice = price;
    }
    
    public Map<String, Object> getValues() {
        Map<String, Object> dict = new HashMap<>();
        dict.put("id", id);
        dict.put("date", date);
        dict.put("provider", provider);
        dict.put("number", number);
        dict.put("sector", sector);
        if (iva != null) dict.put("iva", iva);
        if (profits != null) dict.put("profits", profits);
        if (id != null) dict.put("id", id);
        if (delivered != null) dict.put("delivered", delivered);
        return dict;
    }                  
    
    public DollarPrice getDollarPrice() {
        return dollarPrice;
    }
 }