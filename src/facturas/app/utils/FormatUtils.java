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
        attributes += "id, exchangeType, exchangeMoney, issuedByMe, type, totalAmount";
        values += dict.get("id") + ", " + dict.get("exchangeType") + ", '" + dict.get("exchangeMoney") 
                + "', " + dict.get("issuedByMe") + ", '" + dict.get("type") + "', " + dict.get("totalAmount");

        Pair<String, String> optionals = addOptionalAttributes(dict, new String[] {"ivaTax", "netAmountWI", 
            "netAmountWOI", "numberTo", "amountImpEx"}, new String[] {"authCode"});
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
            sector = provider.getValues().get("sector");
        }
        Boolean delivered = (Boolean) (dict.get("delivered"));
        String buyNSell = (boolean)dict.get("issuedByMe") ? "VENTA" : "COMPRA";
        
        Object[] values = {dict.get("id"), dict.get("date"), dict.get("type"), dict.get("number"), dict.get("numberTo"), dict.get("authCode"), 
            ((Provider)dict.get("provider")).getValues().get("docNo"), provider.getValues().get("name"), dict.get("exchangeType"), dict.get("netAmountWI"), 
            dict.get("netAmountWOI"), dict.get("amountImpEx"), dict.get("ivaTax"), dict.get("totalAmount"), sector, 
            buyNSell, delivered ? "SI" : "NO"};

        return values;
    }
    
    public static Map<String, String> ticketCsvToDict(String strTicket, boolean issuedByMe) {
        String[] data = strTicket.replace("\"", "").split(",");
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
        if (!ivaVar.isEmpty()) dict.put("ivaTax", ivaVar);
        dict.put("totalAmount", data[15]);
        dict.put("issuedByMe", issuedByMe ? "true" : "false");
        
        return dict;
    }
    
    public static Map<String, String> ticketCsvBackupToDict(String s) {
        String[] data = s.split(";");
        Map<String, String> dict = new HashMap<>();
        
        dict.put("date", data[0]);
        dict.put("number", data[1]+data[2]);
        dict.put("providerDoc", data[3]);
        String iva = data[4];
        if (!iva.isEmpty()) dict.put("iva", iva);
        String profits = data[5];
        if (!profits.isEmpty()) dict.put("profits", profits);
        dict.put("delivered", data[6]);
        String sector = data[7];
        if (!sector.isEmpty()) dict.put("sector", sector);
        dict.put("type", data[8]);
        String numberTo = data[9];
        if (!numberTo.isEmpty()) dict.put("numberTo", numberTo);
        String authCode = data[10];
        if (!authCode.isEmpty()) dict.put("authCode", authCode);
        dict.put("exchangeType", data[11]);
        dict.put("exchangeMoney", data[12]);
        String netAmountWI = data[13];
        if (!netAmountWI.isEmpty()) dict.put("netAmountWI", netAmountWI);
        String netAmountWIO = data[14];
        if (!netAmountWIO.isEmpty()) dict.put("netAmountWOI", netAmountWIO);
        String amountImpEx = data[15];
        if (!amountImpEx.isEmpty()) dict.put("amountImpEx", amountImpEx);
        String ivaTax = data[16];
        if (!ivaTax.isEmpty()) dict.put("ivaTax", ivaTax);
        dict.put("totalAmount", data[17]);
        dict.put("issuedByMe", data[18]);
        
        return dict;
    }
    
    public static String ticketToCsv(Ticket t) {
        Map<String, Object> dict = t.getValues();
        Provider provider = (Provider)dict.get("provider");
        String result = "";
        
        result += dict.get("date") + ";" + dict.get("number") + ";" + provider.getValues().get("docNo") + ";" + dict.get("iva") 
                + ";" + dict.get("profits") + ";" + dict.get("delivered") + ";" + dict.get("sector") + ";" + dict.get("type") + ";" 
                + dict.get("numberTo") + ";" + dict.get("authCode") + ";" + dict.get("exchangeType") + ";" 
                + dict.get("exchangeMoney") + ";" + dict.get("netAmountWI") + ";" + dict.get("netAmountWOI") + ";" 
                + dict.get("amountImpEx") + ";" + dict.get("ivaTax") + ";" + dict.get("totalAmount") + ";" + dict.get("issuedByMe");
        
        result = result.replace("null", "");
        return result;
    }
    
    public static Pair<String, String> withholdingToSQL(Withholding w) { 
        Map<String, Object> dict = w.getValues();
        String attributes = "", values = "";
        attributes += "number, date, providerDoc";
        values += "'" + dict.get("number") + "', '" + ((Date)dict.get("date")).toString() + "', '" 
                + ((Provider)dict.get("provider")).getValues().get("docNo") + "'";

        Pair<String, String> optionals = addOptionalAttributes(dict, new String[] {"delivered", "iva", "profits"}, new String[] {"sector"});
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
        
        //so we should check if iva and/or profits have a value and then return the type properly, if both present we should make 2 rows
        Object[] values = {dict.get("id"), dict.get("date"), null, dict.get("number"), null, null, provider.getValues().get("docNo"), 
        provider.getValues().get("name"), null, null, null, null, null, null, sector, null, delivered ? "SI" : "NO"};
        //null values are necessary so the array fits in the table of the view
        return values;
    }

    public static Pair<Object[],Object[]> retrieveInternalWithholdingsToForm(Withholding w) {
        Map<String, Object> dict = w.getValues();
        Provider provider = (Provider)dict.get("provider");
        String sector = (String)dict.get("sector");
        if (sector == null) {   //in case ticket doesn't has a modified sector, we use provider sector
            sector = provider.getValues().get("provSector");
        }
        Boolean delivered = (Boolean) (dict.get("delivered"));
        
        Pair<Object[],Object[]> values = new Pair();
        Object iva = dict.get("iva");
        if (iva != null && (Float) iva != 0.0f) {
            Object[] ivaWithholding = {dict.get("id"), dict.get("date"), "Retencion Iva", dict.get("number"), null, null, 
                provider.getValues().get("docNo"), provider.getValues().get("name"), null, null, null, null, null, iva, sector,
                null, delivered ? "SI" : "NO"};
            
            values.setFst(ivaWithholding);
        }
        
        Object profits = dict.get("profits");
        if (profits != null && (Float) profits != 0.0f) {
            Object[] profitsWithholding = {dict.get("id"), dict.get("date"), "Retencion Ganancias", dict.get("number"), null, 
                null, provider.getValues().get("docNo"), provider.getValues().get("name"), null, null, null, null, null, profits,
                sector, null, delivered ? "SI" : "NO"};
            
            values.setSnd(profitsWithholding);
        }
        //null values are necessary so the array fits in the table of the view
        return values;
    }
    
    public static String withholdingToCsv(Withholding w) { 
        Map<String, Object> dict = w.getValues();
        Provider provider = (Provider)dict.get("provider");
        String result = "";
        
        result += dict.get("date") + ";" + dict.get("number") + ";" + provider.getValues().get("docNo") + ";" + dict.get("iva") 
                + ";" + dict.get("profits") + ";" + dict.get("delivered") + ";" + dict.get("sector") + ";;;;;;;;;;;";
        
        result = result.replace("null", "");
        return result;
    }
    
    public static Map<String, String> dollarPriceCsvToDict(String priceStr) {
        String[] data = priceStr.replace(",", ".").split(";");
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
    
    public static String dollarPriceToCsv(DollarPrice p) {
        Map<String, Object> dict = p.getValues();
        String result = dict.get("date") + ";" + dict.get("buy") + ";" + dict.get("sell");
        
        result = result.replace("null", "");
        return result;
    }

    public static Pair<String, String> providerToSQL(Provider p) {
        Map<String, String> dict = p.getValues();
        String attributes = "", values = "";
        attributes += "docNo, name, documentType";
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
    
    public static String providerToCsv(Provider p) {
        Map<String, String> dict = p.getValues();
        String result = "";
        
        result += dict.get("docNo") + ";" + dict.get("name") + ";" + dict.get("documentType") + ";" + dict.get("direction") 
                + ";" + dict.get("sector") + ";" + dict.get("alias");
        
        result = result.replace("null", "");
        return result;
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
        char initialChar = initialLine.charAt(0);
        if ((int)initialChar == 65279) {  //special char that may come with utf-8 files
            initialLine = initialLine.substring(1); //remove special char
        }
        
        boolean valid;
        switch (mode) {
            case "ticket": String expectedLineEmitter = FixedData.getTicketEmitterFileFormat();
                            String expectedLineReceptor = FixedData.getTicketReceptorFileFormat();
                            valid = initialLine.contentEquals(expectedLineEmitter) || initialLine.contentEquals(expectedLineReceptor);
                            break;
           
            case "price": String expectedLine = FixedData.getDollarPriceFileFormat();
                            valid = initialLine.contentEquals(expectedLine);
                            break;
                            
            case "ticketBackup": expectedLine = FixedData.getTicketAppFormat();
                            valid = initialLine.contentEquals(expectedLine);
                            break;
                            
            case "withholdingBackup": expectedLine = FixedData.getWithholdingAppFormat();
                            valid = initialLine.contentEquals(expectedLine);
                            break;
                            
            case "providerBackup": expectedLine = FixedData.getProviderAppFormat();
                            valid = initialLine.contentEquals(expectedLine);
                            break;
                            
            case "sectorBackup": expectedLine = FixedData.getSectorAppFormat();
                            valid = initialLine.contentEquals(expectedLine);
                            break;
                            
            default: throw new IllegalArgumentException("invalid mode: " + mode);
        }
        
        return valid;
    }

    public static <E> Vector<E> listToVector(List<E> list) {
        Vector<E> vector = new Vector<>();
        for (E e : list) {
            vector.addElement(e);
        }
        return vector;
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
        public static Map<String, String> objectToStringMap(Map<String, Object> values){
        Map<String, String> v = new HashMap();
        Provider provider = (Provider) values.get("provider");
        v.putAll(provider.getValues());
        v.put("id", values.get("id").toString());
        v.put("date", values.get("date").toString());
        v.put("type", values.get("type").toString());
        if(values.get("iva")!=null) v.put("iva", values.get("iva").toString());
        if(values.get("profits")!=null) v.put("profits", values.get("profits").toString());
        v.put("totalAmount", values.get("totalAmount").toString());
        v.put("number", values.get("number").toString());
        if(values.get("sector")!=null) v.put("sector", values.get("sector").toString());
        if (values.get("delivered").toString() != null) v.put("delivered", values.get("delivered").toString());
        if(values.get("numberTo")!=null) v.put("numberTo", values.get("numberTo").toString());
        if(values.get("authCode")!=null) v.put("authCode", values.get("authCode").toString());
        v.put("provider", values.get("provider").toString());
        v.put("exchangeType", values.get("exchangeType").toString());
        v.put("exchangeMoney", values.get("exchangeMoney").toString());
        v.put("issuedByMe", values.get("issuedByMe").toString());
        if (values.get("netAmountWI") != null) v.put("netAmountWI", values.get("netAmountWI").toString());
        if (values.get("netAmountWOI") != null) v.put("netAmountWOI", values.get("netAmountWOI").toString());
        if (values.get("amountImpEx") != null) v.put("amountImpEx", values.get("amountImpEx").toString());
        if (values.get("ivaTax") != null) v.put("ivaTax", values.get("ivaTax").toString());
        if (values.get("sector") != null) v.put("sector", values.get("sector").toString());
        return v;
    }
}
