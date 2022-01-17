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

    public Ticket(Provider provider, int noTicket, Date date, Float iva, float totalAmount, float exchangeType, String ticketType, String documentType, String exchangeMoney, Float netAmountWI, Float netAmountWOI, Float amountImpEx, int numberTo, String authCode){
        this.provider = provider;
        this.noTicket = noTicket;
        this.date = date;
        this.iva = iva;
        this.totalAmount = totalAmount;
        this.exchangeType = exchangeType;
        this.ticketType = ticketType;
        this.exchangeMoney = exchangeMoney;
        this.netAmountWI = netAmountWI;//whit iva 
        this.netAmountWOI = netAmountWOI;//whit out iva
        this.amountImpEx = amountImpEx;//imp. op. Exentas ver que es
        this.numberTo = numberTo;
        this.authCode = authCode;
    }
    
    public Ticket(Object[] data){
        this.date = dateGen(data[0]);  
        this.ticketType = (String)data[1];        
        this.noTicket = Integer.parseInt((String)data[2]+data[3]);
        String numberToVar = (String)data[4];
        this.numberTo = numberToVar.isEmpty() ? null : Integer.parseInt(numberToVar);
        this.authCode = (String) data[5];
        this.provider = new Provider((String)data[6], (String)data[7], (String)data[8]);
        this.exchangeType = Float.parseFloat((String) data[9]);
        this.exchangeMoney = (String)data[10];
        String netAmountWIVar = (String)data[11];
        this.netAmountWI = netAmountWIVar.isEmpty() ? null : Float.parseFloat(netAmountWIVar);//with iva
        String netAmountWIOVar = (String)data[12];
        this.netAmountWOI = netAmountWIOVar.isEmpty() ? null : Float.parseFloat(netAmountWIOVar);//without iva
        String amountImpExVar = (String)data[13];
        this.amountImpEx = amountImpExVar.isEmpty() ? null : Float.parseFloat(amountImpExVar);//imp. op. Exentas ver que es
        String ivaVar = (String)data[14];
        this.iva = ivaVar.isEmpty() ? null : Float.parseFloat(ivaVar);
        this.totalAmount = Float.parseFloat((String)data[15]);
        }

        public String getSQLValues() {
            String values = "";
            values += noTicket + iva + totalAmount + date.toString() ;
            values += exchangeType + ticketType + provider.getCuit();
            return values;
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
    
    public float getexchangeType(){
        return exchangeType;
    }
    
    public String getTicketType(){
        return ticketType;
    }
    
    public String getExchangeMoney(){
        return exchangeMoney;
    }
    
    public float getNetAmountWI(){
        return netAmountWI;
    }
    
    public float getNetAmountWOI(){
        return netAmountWOI;
    }
    
    public float getAmountImpEx(){
        return amountImpEx;
    }
    
    public int getNumberTo(){
        return numberTo;
    }
    
    public String getAuthCode(){
        return authCode;
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