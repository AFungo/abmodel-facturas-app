/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.models;

import java.util.Map;

/**
 *
 * @author ABMODEL
 */
public class Ticket extends Withholding{   
    private Float iva;
    private float exchangeType;
    private String exchangeMoney;
    private Float netAmountWI;//with iva
    private Float netAmountWOI;//without iva
    private Float amountImpEx;//imp. op. Exentas ver que es
    private Integer numberTo;
    private String authCode;
    private boolean issuedByMe;
    
   public Ticket(Map<String, String> data){
        super(data);
        this.numberTo = data.get("numberTo") != null ? Integer.parseInt(data.get("numberTo")) : null ;
        this.authCode =  data.get("authCode");
        this.exchangeType = Float.parseFloat(data.get("exchangeType"));
        this.exchangeMoney = data.get("exchangeMoney");
        this.netAmountWI = data.get("netAmountWI") != null ? Float.parseFloat(data.get("netAmountWI")) : null ;
        this.netAmountWOI = data.get("netAmountWOI") != null ? Float.parseFloat(data.get("netAmountWOI")) : null ;
        this.amountImpEx = data.get("amountImpEx") != null ? Float.parseFloat(data.get("amountImpEx")) : null ;
        this.iva = data.get("iva") != null ? Float.parseFloat(data.get("iva")) : null ;
        //this.issuedByMe =  data.get("issuedByMe").equals("true");
  }

    @Override
    public Map<String, Object> getValues() {
        Map<String, Object> dict = super.getValues();
        if (numberTo != null) dict.put("numberTo", numberTo);
        dict.put("authCode", authCode);
        dict.put("provider", provider);
        dict.put("exchangeType", exchangeType);
        dict.put("exchangeMoney", exchangeMoney);
        dict.put("issuedByMe", issuedByMe);
        if (netAmountWI != null) dict.put("netAmountWI", netAmountWI);
        if (netAmountWOI != null) dict.put("netAmountWOI", netAmountWOI);
        if (amountImpEx != null) dict.put("amountImpEx", amountImpEx);
        if (iva != null) dict.put("iva", iva);
        return dict;
    }
    
    public boolean isIncome(){
        return !(this.type.contains("Factura") || this.type.equals("Débito"));
    }

    @Override
    public String toString() {
        return "Razon Social" + provider + "\n" + "Num Fact" + number + "\n" + "Fecha" + date + "\n" + "Tipo" + type + "\n" + "Iva" + iva + "\n" + "Total" + totalAmount + "\n"; 
    }
}