/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.utils;

import facturas.app.models.DollarPrice;
import facturas.app.models.Provider;
import facturas.app.models.Ticket;
import facturas.app.models.Withholding;

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
        attributes += "number, totalAmount, date, exchangeType, type, exchangeMoney, authCode, providerDoc, issuedByMe";
        values += dict.get("number") + ", " + dict.get("totalAmount") + ", '" + ((Date)dict.get("date")).toString() + "', " 
                + dict.get("exchangeType") + ", '" + dict.get("type") + "', '" + dict.get("exchangeMoney") + "', '" 
                + dict.get("authCode") + "', '" + ((Provider)dict.get("provider")).getValues().get("docNo") + "', " + dict.get("issuedByMe");

        Pair<String, String> optionals = addOptionalAttributes(dict, new String[] {"iva", "netAmountWI", "netAmountWOI", 
            "numberTo", "amountImpEx"}, new String[] {"sector", "delivered"});
        attributes += optionals.getFst();
        values += optionals.getSnd();
        
        return new Pair<>(attributes, values);
    }
     
    public static Object[] ticketToForm(Withholding t) {
        if (!(t instanceof Ticket))
            return withholdingToForm(t);

        Map<String, Object> dict = t.getValues();
        Provider provider = (Provider)dict.get("provider");
        String sector = (String)dict.get("sector");
        if (sector == null) {   //in case ticket doesn't has a modified sector, we use provider sector
            sector = provider.getValues().get("provSector");
        }
        Boolean delivered = (Boolean) (dict.get("delivered"));
        
        String buyNSell = (boolean)dict.get("issuedByMe") ? "VENTA" : "COMPRA";
        
        Object[] values = {dict.get("date"), dict.get("type"), dict.get("number"), dict.get("numberTo"), dict.get("authCode"), 
            ((Provider)dict.get("provider")).getValues().get("docNo"), provider.getValues().get("name"), dict.get("exchangeType"), dict.get("netAmountWI"), 
            dict.get("netAmountWOI"), dict.get("amountImpEx"), dict.get("iva"), dict.get("totalAmount"), sector, 
            buyNSell, delivered ? "SI" : "NO"};

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
    
    public static Pair<String, String> withholdingToSQL(Withholding w) { 
        Map<String, Object> dict = w.getValues();
        String attributes = "", values = "";
        attributes += "number, totalAmount, date, type, providerDoc";
        values += dict.get("number") + ", " + dict.get("totalAmount") + ", '" + ((Date)dict.get("date")).toString() + "', '" 
        + dict.get("type") + "', '" + ((Provider)dict.get("provider")).getValues().get("docNo") + "'";

        Pair<String, String> optionals = addOptionalAttributes(dict, new String[] {"id", "delivered"}, new String[] {"sector"});
        attributes += optionals.getFst();
        values += optionals.getSnd();

        return new Pair<>(attributes, values);
    }

    private static Object[] withholdingToForm(Withholding w) {
        Map<String, Object> dict = w.getValues();
        Provider provider = (Provider)dict.get("provider");
        String sector = (String)dict.get("sector");
        if (sector == null) {   //in case ticket doesn't has a modified sector, we use provider sector
            sector = provider.getValues().get("provSector");
        }
        Boolean delivered = (Boolean) (dict.get("delivered"));
        
        Object[] values = {dict.get("date"), dict.get("type"), dict.get("number"), null, null, provider.getValues().get("docNo"), 
        provider.getValues().get("name"), null, null, null, null, null, dict.get("totalAmount"), sector, null, delivered ? "SI" : "NO"};
        //null values are necessary so the array fits in the table of the view
        return values;
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
        Map<String, String> dict = p.getValues();
        String attributes = "", values = "";
        attributes += "cuit, name, documentType";
        values += "'" + dict.get("docNo") + "', '" + dict.get("name") + "', '" + dict.get("docType") + "'";
        
        Pair<String, String> optionals = addOptionalAttributes(dict, new String[] {}, new String[] {"direction", "sector", "alias"});
        attributes += optionals.getFst();
        values += optionals.getSnd();
        
        return new Pair<>(attributes, values);
    }
    
    public static Object[] providerToForm(Provider prov) {
        Map<String, String> dict = prov.getValues();
        Object[] values = new Object[6];
        values[0] = dict.get("docNo");
        values[1] = dict.get("name");
        if (dict.get("alias") != null)
            values[2] = dict.get("alias");
        values[3] = dict.get("docType");
        if (dict.get("direction") != null)
            values[4] = dict.get("direction");
        if (dict.get("sector") != null)
            values[5] = dict.get("sector");

        return values;
    }

    private static Pair<String, String> addOptionalAttributes(Map<String, ? extends Object> dict, String[] keys, String[] commaKeys) {
        String attributes = "", values = "";
        for (String key : keys) {
            if (dict.get(key) != null) { 
                attributes += ", " + key;
                values += ", " + dict.get(key);
            }
        }
        
        for (String key : commaKeys) {
            if (dict.get(key) != null) { 
                attributes += ", " + key;
                values += ", '" + dict.get(key) + "'";
            }
        }
        
        return new Pair<String, String> (attributes, values);
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
    
    public static boolean[] validTicketInput(Map<String, String> values, boolean ticket) {
        boolean[] validations;
        if (ticket) 
            validations = new boolean[8];
        else
            validations = new boolean[3];
        
        validations[0] = tryParse(values.get("number"), "Integer");
        validations[1] = values.get("docNo").isEmpty() ? true : tryParse(values.get("docNo"), "Integer"); //maybe user seleceted an existent provider
        validations[2] = tryParse(values.get("totalAmount"), "Float");
        if (ticket) {
            validations[3] = values.get("amountImpEx").isEmpty() ? true : tryParse(values.get("amountImpEx"), "Float");
            validations[4] = values.get("exchangeType").isEmpty() ? true : tryParse(values.get("exchangeType"), "Float");
            validations[5] = values.get("iva").isEmpty() ? true : tryParse(values.get("iva"), "Float");
            validations[6] = values.get("netAmountWI").isEmpty() ? true : tryParse(values.get("netAmountWI"), "Float");
            validations[7] = values.get("netAmountWOI").isEmpty() ? true : tryParse(values.get("netAmountWOI"), "Float");
        }

        return validations;
    }
    
    private static boolean tryParse(String value, String expectedClass) {
        try { 
            if (expectedClass == "Float") {
                Float.parseFloat(value);
            } else if (expectedClass == "Integer") {
                Integer.parseInt(value);
            } else {
                System.out.println("class wrongly passed, " + expectedClass + " is not valid");
                return false;
            }
            return true;
        } catch (Exception e) {
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
