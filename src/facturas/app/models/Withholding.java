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
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        try {
            NumberFormat nf = NumberFormat.getInstance();
            date = FormatUtils.dateGen(data.get("date"));
            number = data.get("number");
            //data can contain name (csv with new tickets) or not (loading backup, provider data is in DB)
            if (data.containsKey("name") && data.containsKey("docType")) provider = new Provider(data);
            else provider = ProviderDAO.get(data.get("docNo"));
            sector = data.get("sector");
            String iva = data.get("iva");
            if (iva != null && !iva.isEmpty()) this.iva = nf.parse(iva).floatValue();
            String profits = data.get("profits");
            if (profits != null && !profits.isEmpty()) this.profits = nf.parse(profits).floatValue();
            if (data.get("id") != null) id = Integer.parseInt(data.get("id"));
            if (data.get("delivered") != null) delivered = Boolean.valueOf(data.get("delivered"));
        } catch (ParseException ex) {
            Logger.getLogger(Withholding.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addDollarPrice(DollarPrice price) {
        if (price == null) {
            System.out.println("No price for date " + date);
            return ;
        }
        
        dollarPrice = price;
    }
    
    public void deleteIva() {
        this.iva = null;
    }
    
    public void deleteProfits() {
        this.profits = null;
    }
    
    public Map<String, Object> getValues() {
        Map<String, Object> dict = new HashMap<>();
        dict.put("date", date);
        dict.put("provider", provider);
        dict.put("number", number);
        dict.put("sector", sector);
        if (id != null) dict.put("id", id);
        if (iva != null) dict.put("iva", iva);
        if (profits != null) dict.put("profits", profits);
        if (delivered != null) dict.put("delivered", delivered);
        return dict;
    }                  
    
    public DollarPrice getDollarPrice() {
        return dollarPrice;
    }
    
    @Override
    public String toString() {
        return "Id" + id + "\nFecha" + date + "\nProveedor" + provider + "\nNum Fact" + number + "\nRubro" + sector 
                + "\nIva" + iva + "\nGanancias" + profits + "\nEnviado al contador" + delivered + "\n"; 
    }
}