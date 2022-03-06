/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.models;

import facturas.app.utils.FormatUtils;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nacho
 */
public class DollarPrice {
    
    private Date date;
    private Float buy;
    private Float sell;
    
    public DollarPrice(Map<String, String> data) {
        this.date = FormatUtils.dateGen(data.get("date"));
        this.buy = Float.parseFloat(data.get("buy"));
        this.sell = Float.parseFloat(data.get("sell"));
    }
    
    public Map<String, Object> getValues() {
        Map<String, Object> dict = new HashMap<>();
        dict.put("date", date);
        dict.put("buy", buy);
        dict.put("sell", sell);
        return dict;
    }
    
    public Date getDate() {
        return date;
    }
    
    public String toString() {
        return "Fecha: " + date + "\n" + "Compra: " + buy + "\n" + "Venta: " + sell + "\n"; 
    }
}
