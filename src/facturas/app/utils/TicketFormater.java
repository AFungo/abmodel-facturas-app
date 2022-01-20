/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.utils;

import facturas.app.models.Provider;
import facturas.app.models.Ticket;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nacho
 */
public class TicketFormater {
    
     public static String[] ticketToSQL(Ticket t) {
        Map<String, Object> dict = t.getValues();
        String attributes = "", values = "";
        attributes += "noTicket, totalAmount, date, exchangeType, ticketType, exchangeMoney, authCode, providerCuit";
        values += dict.get("noTicket") + ", " + dict.get("totalAmount") + ", '" + ((Date)dict.get("date")).toString() + "', " 
                + dict.get("exchangeType") + ", '" + dict.get("ticketType") + "', '" + dict.get("exchangeMoney") + "', '" 
                + dict.get("authCode") + "', '" + ((Provider)dict.get("provider")).getCuit() + "'";

        if (dict.get("iva") != null) { attributes += ", iva"; values += ", " + dict.get("iva");}
        if (dict.get("netAmountWI") != null) { attributes += ", netAmountWI"; values += ", " + dict.get("netAmountWI");}
        if (dict.get("netAmountWOI") != null) { attributes += ", netAmountWOI"; values += ", " + dict.get("netAmountWOI");}
        if (dict.get("numberTo") != null) { attributes += ", numberTo"; values += ", " + dict.get("numberTo");}
        if (dict.get("amountImpEx") != null) { attributes += ", amountImpEx"; values += ", " + dict.get("amountImpEx");}

        String[] result = {attributes, values};
        return result;
    }
    
    public static Object[] ticketToForm(Ticket t) {
        Map<String, Object> dict = t.getValues();
        Object[] values = {dict.get("date"), dict.get("ticketType"), dict.get("noTicket"), dict.get("numberTo"), dict.get("authCode"), 
            ((Provider)dict.get("provider")).getCuit(), ((Provider)dict.get("provider")).getName(), dict.get("exchangeType"), 
            dict.get("netAmountWI"), dict.get("netAmountWOI"), dict.get("amountImpEx"), dict.get("iva"), dict.get("totalAmount")};

        return values;
    }
    
    public static Map<String, String> csvToDict(String[] data) {
        Map<String, String> dict = new HashMap<>();
        
        dict.put("date", data[0]);
        dict.put("ticketType", data[1]);
        dict.put("noTicket", data[2]+data[3]);
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
