/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.models;

import facturas.app.database.ProviderDAO;
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
    protected int number;
    protected Date date;
    protected Float totalAmount;
    protected String type;
    protected DollarPrice dollarPrice = null;
    
    public Withholding(Map<String, String> data) {
        date = FormatUtils.dateGen(data.get("date"));
        type = data.get("type");
        number = Integer.parseInt(data.get("number"));
        provider = ProviderDAO.getProvider(data.get("docNo"));
        if (provider == null) { //if this provider was not in database
            provider = new Provider(data);
        }
        totalAmount = Float.parseFloat(data.get("totalAmount"));
    }

    public Withholding(Provider provider, int number, Date date, Float totalAmount, String type) {
        this.provider = provider;
        this.number = number;
        this.date = date;
        this.type = type;
        this.totalAmount = totalAmount;
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
        dict.put("date", date);
        dict.put("type", type);
        dict.put("totalAmount", totalAmount);
        dict.put("provider", provider);
        dict.put("number", number);
        return dict;
    }                  
    
    public DollarPrice getDollarPrice() {
        return dollarPrice;
    }
 }