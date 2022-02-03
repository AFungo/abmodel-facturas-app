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
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author nacho
 */
public class FormatUtils {
    
     public static Pair<String, String> ticketToSQL(Ticket t) {
        Map<String, Object> dict = t.getValues();
        String attributes = "", values = "";
        attributes += "number, totalAmount, date, exchangeType, type, exchangeMoney, authCode, providerCuit, issuedByMe";
        values += dict.get("number") + ", " + dict.get("totalAmount") + ", '" + ((Date)dict.get("date")).toString() + "', " 
                + dict.get("exchangeType") + ", '" + dict.get("type") + "', '" + dict.get("exchangeMoney") + "', '" 
                + dict.get("authCode") + "', '" + ((Provider)dict.get("provider")).getDocNo() + "', " + dict.get("issuedByMe");

        if (dict.get("iva") != null) { attributes += ", iva"; values += ", " + dict.get("iva");}
        if (dict.get("netAmountWI") != null) { attributes += ", netAmountWI"; values += ", " + dict.get("netAmountWI");}
        if (dict.get("netAmountWOI") != null) { attributes += ", netAmountWOI"; values += ", " + dict.get("netAmountWOI");}
        if (dict.get("numberTo") != null) { attributes += ", numberTo"; values += ", " + dict.get("numberTo");}
        if (dict.get("amountImpEx") != null) { attributes += ", amountImpEx"; values += ", " + dict.get("amountImpEx");}
        if (dict.get("sector") != null) { attributes += ", sector"; values += ", '" + dict.get("sector") + "'"; }

        return new Pair<>(attributes, values);
    }
     
    public static Object[] ticketToForm(Ticket t) {
        Map<String, Object> dict = t.getValues();
        Provider provider = (Provider)dict.get("provider");
        String sector = (String)dict.get("sector");
        if (sector == null) {   //in case ticket doesn't has a modified sector, we use provider sector
            sector = provider.getSector();
        }
        
        Object[] values = {dict.get("date"), dict.get("type"), dict.get("number"), dict.get("numberTo"), dict.get("authCode"), 
            ((Provider)dict.get("provider")).getDocNo(), provider.getName(), dict.get("exchangeType"), dict.get("netAmountWI"), 
            dict.get("netAmountWOI"), dict.get("amountImpEx"), dict.get("iva"), dict.get("totalAmount"), sector};

        return values;
    }
    
    public static Map<String, String> ticketCsvToDict(String strTicket, boolean issuedByMe) {
        String[ ] data = strTicket.replace("\"", "").split(",");
        Map<String, String> dict = new HashMap<>();
        
        dict.put("date", data[0]);
        dict.put("type", data[1]);
        dict.put("number", data[2]+data[3]);
        String numberToVar = data[4];
        if (!numberToVar.isEmpty()) dict.put("numberTo", numberToVar);
        dict.put("authCode", data[5]);
        dict.put("docType", data[6]);
        dict.put("docNo", data[7]);
        dict.put("name", data[8]);
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
        dict.put("issuedByMe", issuedByMe ? "true" : "false");
        
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
        values += "'" + dict.get("date") + "', " + dict.get("buy") + ", " + dict.get("sell") + "";

        return new Pair<>(attributes, values);
    }
    
    public static Pair<String, String> providerToSQL(Provider p) {
        Map<String, Object> dict = p.getValues();
        String attributes = "", values = "";
        attributes += "cuit, name, documentType";
        values += "'" + dict.get("docNo") + "', '" + dict.get("name") + "', '" + dict.get("docType") + "'";
        
        if (dict.get("direction") != null) { attributes += ", direction"; values += ", " + dict.get("direction");}
        if (dict.get("sector") != null) { attributes += ", sector"; values += ", " + dict.get("sector");}

        return new Pair<>(attributes, values);
    }
    
    public static Object[] providerToForm(Provider prov) {
        Map<String, Object> dict = prov.getValues();
        Object[] values = new Object[5];
        values[0] = dict.get("docNo");
        values[1] = dict.get("name");
        values[2] = dict.get("docType");
        if (dict.get("direction") != null)
            values[3] = dict.get("direction");
        if (dict.get("sector") != null)
            values[4] = dict.get("sector");

        return values;
    }

    public static boolean validFormat(String initialLine, String mode) {
        String expectedLine = "";
        if ("ticket".equals(mode)) {
            expectedLine = "\"Fecha\",\"Tipo\",\"Punto de Venta\",\"Número Desde\",\"Número Hasta\",\"Cód. Autorización\",\"Tipo Doc. Emisor\",\"Nro. Doc. Emisor\",\"Denominación Emisor\",\"Tipo Cambio\",\"Moneda\",\"Imp. Neto Gravado\",\"Imp. Neto No Gravado\",\"Imp. Op. Exentas\",\"IVA\",\"Imp. Total\"";
        } else if ("price".equals(mode)) {
            expectedLine = "Fecha cotizacion;Compra;Venta;";
        }
        
        char initialChar = initialLine.charAt(0);
        if ((int)initialChar == 65279) {  //special char that may come with utf-8 files
            initialLine = initialLine.substring(1); //remove special char
        }

        return initialLine.contentEquals(expectedLine);
    }

    public static Vector<String> listToVector(List<String> list) {
        Vector<String> vector = new Vector<>();
        for (String s : list) {
            vector.addElement(s);
        }
        return vector;
    }
    
    public static boolean validTicketInput(Map<String, String> values) {
        try {
            Float.parseFloat(values.get("amountImpEx"));
            Float.parseFloat(values.get("exchangeType"));
            Float.parseFloat(values.get("iva"));
            Float.parseFloat(values.get("netAmountWI"));
            Float.parseFloat(values.get("netAmountWOI"));
            Integer.parseInt(values.get("number"));
            Float.parseFloat(values.get("providerCuit"));
            Float.parseFloat(values.get("totalAmount"));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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
