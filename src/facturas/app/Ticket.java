/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app;

import java.sql.Date;


/**
 *
 * @author ABMODEL
 */
public class Ticket {   
    private Provider provider; 
    private int id;
    private int noTicket;
    private Date date;
    private float iva;
    private float totalAmount;
    private float exchangeType;
    private String ticketType;
    
    public Ticket(int id, int noTicket, Date date, float iva,float totalAmount, float exchangeType, String ticketType){
        this.id = id;
        this.noTicket = noTicket;
        this.date = date;
        this.iva = iva;
        this.totalAmount = totalAmount;
        this.exchangeType = exchangeType;
        this.ticketType = ticketType;
    }
    
    public Provider getProvider(){
        return provider;
    }
    public int getId(){
        return id;
    }
    public int getNoTicket(){
        return noTicket;
    }
    public Date getDate(){
        return date;
    }
    public float getIva(){
        return iva;
    }
    public float getTotalAmount(){
        return totalAmount;
    }
    public float getExchangeType(){
        return exchangeType;
    }
    public String getTicketType(){
        return ticketType;
    }
    
    public String toString(){
        return "Razon Social" +  provider + "\n" + "Num Fact" + id + "\n" + "Fecha" + date + "\n" + "Tipo" + ticketType + "\n" + "Iva" + iva + "\n" + "Total" + totalAmount + "\n"; 
    }
}
