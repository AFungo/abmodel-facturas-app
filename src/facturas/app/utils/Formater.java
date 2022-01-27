/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.utils;

import facturas.app.models.DollarPrice;
import facturas.app.models.Provider;
import facturas.app.models.Ticket;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nacho
 */
public class Formater {
    
     public static Pair<String, String> ticketToSQL(Ticket t) {
        Map<String, Object> dict = t.getValues();
        String attributes = "", values = "";
        attributes += "number, totalAmount, date, exchangeType, type, exchangeMoney, authCode, providerCuit";
        values += dict.get("number") + ", " + dict.get("totalAmount") + ", '" + ((Date)dict.get("date")).toString() + "', " 
                + dict.get("exchangeType") + ", '" + dict.get("type") + "', '" + dict.get("exchangeMoney") + "', '" 
                + dict.get("authCode") + "', '" + ((Provider)dict.get("provider")).getCuit() + "'";

        if (dict.get("iva") != null) { attributes += ", iva"; values += ", " + dict.get("iva");}
        if (dict.get("netAmountWI") != null) { attributes += ", netAmountWI"; values += ", " + dict.get("netAmountWI");}
        if (dict.get("netAmountWOI") != null) { attributes += ", netAmountWOI"; values += ", " + dict.get("netAmountWOI");}
        if (dict.get("numberTo") != null) { attributes += ", numberTo"; values += ", " + dict.get("numberTo");}
        if (dict.get("amountImpEx") != null) { attributes += ", amountImpEx"; values += ", " + dict.get("amountImpEx");}

        return new Pair<>(attributes, values);
    }
    
    public static Object[] ticketToForm(Ticket t) {
        Map<String, Object> dict = t.getValues();
        Object[] values = {dict.get("date"), dict.get("type"), dict.get("number"), dict.get("numberTo"), dict.get("authCode"), 
            ((Provider)dict.get("provider")).getCuit(), ((Provider)dict.get("provider")).getName(), dict.get("exchangeType"), 
            dict.get("netAmountWI"), dict.get("netAmountWOI"), dict.get("amountImpEx"), dict.get("iva"), dict.get("totalAmount")};

        return values;
    }
    
    public static Map<String, String> ticketCsvToDict(String strTicket) {
        String[ ] data = strTicket.replace("\"", "").split(",");
        Map<String, String> dict = new HashMap<>();
        
        dict.put("date", data[0]);
        dict.put("type", data[1]);
        dict.put("number", data[2]+data[3]);
        String numberToVar = data[4];
        if (!numberToVar.isEmpty()) dict.put("numberTo", numberToVar);
        dict.put("authCode", data[5]);
        dict.put("providerDocType", data[6]);
        dict.put("providerCuit", data[7]);
        dict.put("providerName", data[8]);
        dict.put("exchangeType", data[9]);
        dict.put("exchangeMoney", data[10]);
        String netAmountWIVar = data[11];
        if (!netAmountWIVar.isEmpty()) dict.put("netAmountWI", netAmountWIVar);
        String netAmountWIOVar = data[12];
        if (!netAmountWIOVar.isEmpty()) dict.put("netAmountWOI", netAmountWIOVar);
        String amountImpExVar = data[13];
        if (!amountImpExVar.isEmpty()) dict.put("amountImpEx", amountImpExVar);
        String ivaVar = data[14];
        if (!ivaVar.isEmpty()) dict.put("iva", ivaVar);
        dict.put("totalAmount", data[15]);
        
        return dict;
    }
    
    public static Map<String, String> dollarPriceCsvToDict(String priceStr) {
        String[ ] data = priceStr.replace(",", ".").split(";");
        Map<String, String> dict = new HashMap<>();
        dict.put("date", data[0]);
        dict.put("buy", data[1]);
        dict.put("sell", data[2]);
        
        return dict;
    }
    
    public static Pair<String, String> dollarPriceToSQL(DollarPrice price) {
        Map<String, Object> dict = price.getValues();
        String attributes = "", values = "";
        attributes += "date, buy, sell";
        values += "'" + dict.get("date") + "', '" + dict.get("buy") + "', '" + dict.get("sell") + "'";

        return new Pair<>(attributes, values);
    }
    
    public static Pair<String, String> providerToSQL(Provider p) {
        Map<String, Object> dict = p.getValues();
        String attributes = "", values = "";
        attributes += "cuit, name, documentType";
        values += "'" + dict.get("cuit") + "', '" + dict.get("name") + "', '" + dict.get("documentType") + "'";

        return new Pair<>(attributes, values);
    }
    
    //this method takes a String and returns a Date.
    public static Date dateGen(String dateStr) {
        String[] fields = dateStr.split("/"); //d-m-y
        if (fields.length != 3) {
            dateStr = fields[0];
            fields = dateStr.split("-");
            String formatedDate = fields[0] + "-" + fields[1] + "-" + fields[2]; //y-m-d
            return Date.valueOf(formatedDate);
        }
        String formatedDate = fields[2] + "-" + fields[1] + "-" + fields[0]; //y-m-d
        return Date.valueOf(formatedDate);
    }
}
