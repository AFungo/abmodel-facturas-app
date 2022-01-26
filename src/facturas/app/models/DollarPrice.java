/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.models;

import java.sql.Date;

/**
 *
 * @author nacho
 */
public class DollarPrice {
    
    private Date date;
    private Float buy;
    private Float sell;
    
    public DollarPrice(Date date, Float buy, Float sell) {
        this.date = date;
        this.buy = buy;
        this.sell = sell;
    }
    
    public Date getDate() {
        return date;
    }
    
}
