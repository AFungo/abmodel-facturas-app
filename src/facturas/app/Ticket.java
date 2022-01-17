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
    private int noTicket;
    private Date date;
    private float iva;
    private float totalAmount;
    private float exchangeValue;
    private String ticketType;
    private String exchangeMoney;
    private int netAmountWI;//whit iva
    private int netAmountWOI;//whit out iva
    private int amountImpEx;//imp. op. Exentas ver que es
    private int numberTo;
    private int autCode;

    public Ticket(Provider provider, int noTicket, Date date, float iva,float totalAmount, float exchangeValue, String ticketType, String documentType, String exchangeMoney, int netAmountWI, int netAmountWOI, int amountImpEx, int numberTo, int autCode){
        provider = provider;
        this.noTicket = noTicket;
        this.date = date;
        this.iva = iva;
        this.totalAmount = totalAmount;
        this.exchangeValue = exchangeValue;
        this.ticketType = ticketType;
        this.exchangeMoney = exchangeMoney;
        this.netAmountWI = netAmountWI;//whit iva 
        this.netAmountWOI = netAmountWOI;//whit out iva
        this.amountImpEx = amountImpEx;//imp. op. Exentas ver que es
        this.numberTo = numberTo;
        this.autCode = autCode;
        }
    
    public Ticket(Object[] data){
        this.date = dateGen(data[0]);  
        this.ticketType = (String)data[1];        
        this.noTicket = Integer.parseInt((String)data[2]+data[3]);               
        this.numberTo = Integer.parseInt((String)data[4]);        
        this.autCode = Integer.parseInt((String) data[5]);
        this.provider = new Provider((String)data[6], Integer.parseInt((String)data[7]), (String)data[8]);
        this.exchangeValue = Integer.parseInt((String) data[9]);
        this.exchangeMoney = (String)data[10];
        this.netAmountWI = Integer.parseInt((String)data[11]);//whit iva
        this.netAmountWOI = Integer.parseInt((String)data[12]);//whit out iva
        this.amountImpEx = Integer.parseInt((String)data[13]);//imp. op. Exentas ver que es
        this.iva = Integer.parseInt((String)data[14]);
        this.totalAmount = Integer.parseInt((String)data[15]);
        }

    public Provider getProvider(){
        return provider;
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
    public float getExchangeValue(){
        return exchangeValue;
    }
    public String getTicketType(){
        return ticketType;
    }
    public String getExchangeMoney(){
        return exchangeMoney;
    }
    public int getNetAmountWI(){
        return netAmountWI;
    }
    public int getNetAmountWOI(){
        return netAmountWOI;
    }
    public int getAmountImpEx(){
        return amountImpEx;
    }
    public int getNumberTo(){
        return numberTo;
    }
    public int getAutCode(){
        return autCode;
    }
    @Override
    public String toString(){
        return "Razon Social" +  provider + "\n" + "Num Fact" + noTicket + "\n" + "Fecha" + date + "\n" + "Tipo" + ticketType + "\n" + "Iva" + iva + "\n" + "Total" + totalAmount + "\n"; 
    }
    //this method take a object and returns a Date.
    private Date dateGen(Object o){
        String str = (String)o;
        String [] fields = str.split("/");
        return new Date(Integer.parseInt((String)fields[2]), Integer.parseInt((String)fields[1]), Integer.parseInt((String)fields[0]));
    }
}