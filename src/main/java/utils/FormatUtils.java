/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import database.ProviderDAO;
import models.DollarPrice;
import models.Provider;
import models.Sector;
import models.Ticket;
import models.Withholding;
import java.sql.Date;
import java.util.*;

/**
 * Class used to transform objects into a string with certain format
 */
public class FormatUtils {
    
    /**
     * Transforms ticket data into a SQL format data
     * 
     * @param t ticket with data to transform
     * @return a pair of strings containing the attributes to insert and the actual values
     * to insert to those attributes
     */
    public static Pair<String, String> ticketToSQL(Ticket t) {
        Map<String, Object> dict = t.getValues();
        String attributes = "", values = "";
        attributes += "id, exchangeType, exchangeMoney, issuedByMe, type, totalAmount";
        values += ((Withholding)dict.get("withholding")).getValues().get("id") + ", " + dict.get("exchangeType") + ", '" + dict.get("exchangeMoney")
                + "', " + dict.get("issuedByMe") + ", '" + dict.get("type") + "', " + dict.get("totalAmount");

        Pair<String, String> optionals = addOptionalAttributes(dict, new String[] {"ivaTax", "netAmountWI", 
            "netAmountWOI", "numberTo", "amountImpEx"}, new String[] {"authCode"});
        attributes += optionals.getFst();
        values += optionals.getSnd();
        
        return new Pair<>(attributes, values);
    }
     
    /**
     * Transforms ticket data into a form format data
     * 
     * @param t ticket with data to transform
     * @return an object array containing the attributes to show
     */
    public static Object[] ticketToForm(Withholding t) {
        Map<String, Object> dict = t.getValues();
        Provider provider = (Provider)dict.get("provider");
        String sector = (String)dict.get("sector");
        if (sector == null) {   //in case ticket doesn't has a modified sector, we use provider sector
            sector = (String) provider.getValues().get("sector");
        }
        Boolean delivered = (Boolean) (dict.get("delivered"));
        String buyNSell = (boolean)dict.get("issuedByMe") ? "VENTA" : "COMPRA";

        Object[] values = {dict.get("id"), dict.get("date"), dict.get("type"), dict.get("number"), dict.get("numberTo"), dict.get("authCode"),
            ((Provider)dict.get("provider")).getValues().get("docNo"), provider.getValues().get("name"), dict.get("exchangeType"), dict.get("netAmountWI"),
            dict.get("netAmountWOI"), dict.get("amountImpEx"), dict.get("ivaTax"), dict.get("totalAmount"), sector,
            buyNSell, delivered ? "SI" : "NO"};

        return values;
    }
    
    /**
     * Transforms CSV line containing ticket data into a map
     * 
     * @param strTicket String with CSV format containing ticket data
     * @param issuedByMe boolean representing extra ticketData
     * @return a map from String to String of the ticket data
     */
    public static Map<String, Object> ticketCsvToDict(String strTicket, boolean issuedByMe) {
        strTicket = strTicket.substring(1, strTicket.length() - 2); //remove first and last " symbol
        String[] data = strTicket.split("\",\"");       //split by "," so we don't get in trouble with names containing ,
        Map<String, Object> dict = new HashMap<>();

        /* TODO: from here, we must extract this as a new method that constructor the ticket using the "data" (String[])
            in some util method that can access DAOs, because use a DAO in a format utils class does not seen good.
         */
        dict.put("date", Date.valueOf(FormatUtils.formatDate(data[0])));
        dict.put("type", data[1]);
        dict.put("number", data[2]+data[3]);
        if (!data[4].isEmpty()) dict.put("numberTo", data[4]);
        dict.put("authCode", data[5]);
        /* Use this data for search or create a new provider */
        ProviderDAO dao = ProviderDAO.getInstance();
        String docNo = data[7];
        Optional<Provider> provider = dao.getAll().stream().filter(p -> p.getValues().get("docNo").equals(docNo)).findFirst();
        if (provider.isPresent()) {
            dict.put("provider", provider.get());
        } else {
            dict.put("provider", new HashMap<String, Object>() {{
                            put("docType", data[6]);
                            put("docNo", docNo);
                            put("name", data[8].replace("'", ""));
                        }});
        }
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
    
    /**
     * Transforms CSV line containing ticket backup data into a map
     * 
     * @param s String with CSV format containing ticket data
     * @return a map from String to String of the ticket data
     */
    public static Map<String, Object> ticketCsvBackupToDict(String s) {
        String[] data = s.split(";");
        Map<String, Object> dict = new HashMap<>();
        
        dict.put("date", data[0]);
        dict.put("number", data[1]);
        dict.put("docNo", data[2]);
        String iva = data[3];
        if (!iva.isEmpty()) dict.put("iva", iva);
        String profits = data[4];
        if (!profits.isEmpty()) dict.put("profits", profits);
        dict.put("delivered", data[5]);
        String sector = data[6];
        if (!sector.isEmpty()) dict.put("sector", sector);
        dict.put("type", data[7]);
        String numberTo = data[8];
        if (!numberTo.isEmpty()) dict.put("numberTo", numberTo);
        String authCode = data[9];
        if (!authCode.isEmpty()) dict.put("authCode", authCode);
        dict.put("exchangeType", data[10]);
        dict.put("exchangeMoney", data[11]);
        String netAmountWI = data[12];
        if (!netAmountWI.isEmpty()) dict.put("netAmountWI", netAmountWI);
        String netAmountWIO = data[13];
        if (!netAmountWIO.isEmpty()) dict.put("netAmountWOI", netAmountWIO);
        String amountImpEx = data[14];
        if (!amountImpEx.isEmpty()) dict.put("amountImpEx", amountImpEx);
        String ivaTax = data[15];
        if (!ivaTax.isEmpty()) dict.put("ivaTax", ivaTax);
        dict.put("totalAmount", data[16]);
        dict.put("issuedByMe", data[17]);
        
        return dict;
    }
    
    /**
     * Transforms ticket data into a CSV format
     * 
     * @param t ticket to transform
     * @return a string in CSV format with the ticket data
     */
    public static String ticketToCsv(Ticket t) {
        Map<String, Object> dict = t.getValues();
        Provider provider = (Provider)dict.get("provider");
        String result = "";
        
        result += dict.get("date") + ";" + dict.get("number") + ";" + provider.getValues().get("docNo") + ";" + dict.get("iva") 
                + ";" + dict.get("profits") + ";" + dict.get("delivered") + ";" + dict.get("sector") + ";" + dict.get("type") + ";" 
                + dict.get("numberTo") + ";" + dict.get("authCode") + ";" + dict.get("exchangeType") + ";" 
                + dict.get("exchangeMoney") + ";" + dict.get("netAmountWI") + ";" + dict.get("netAmountWOI") + ";" 
                + dict.get("amountImpEx") + ";" + dict.get("ivaTax") + ";" + dict.get("totalAmount") + ";" 
                + dict.get("issuedByMe") + ";";
        
        result = result.replace("null", "");
        return result;
    }
    
    /**
     * Transforms withholding data into a SQL format data
     * 
     * @param w withholding with data to transform
     * @return a pair of strings containing the attributes to insert and the actual values
     * to insert to those attributes
     */
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
    public static Pair<String, String> sectorToSQL(Sector s){
        Map<String, Object> dict = s.getValues();

        String attributes = "", values = "";
        attributes += "name";
        values += "'" + dict.get("name") + "'";

        return new Pair<>(attributes, values);
    }
    /**
     * Transforms withholding data into a form format data
     * 
     * @param w withholding with data to transform
     * @return a Pair of Object arrays, were first is a row of iva withholding and second
     * is a row of profits withholding
     */
    public static Pair<Object[],Object[]> retrieveInternalWithholdingToForm(Withholding w) {
        Map<String, Object> dict = w.getValues();
        Provider provider = (Provider)dict.get("provider");
        String sector = (String)dict.get("sector");
        if (sector == null) {   //in case ticket doesn't has a modified sector, we use provider sector
            sector = (String) provider.getValues().get("provSector");
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
    
    /**
     * Transforms CSV line containing withholding backup data into a map
     * 
     * @param s String with CSV format containing withholding data
     * @return a map from String to String of the withholding data
     */
    public static Map<String, Object> withholdingCsvBackupToDict(String s) {
        String[] data = s.split(";");
        Map<String, Object> dict = new HashMap<>();
        
        dict.put("date", Date.valueOf(formatDate(data[0])));
        dict.put("number", data[1]);
        dict.put("docNo", data[2]);
        String iva = data[3];
        if (!iva.isEmpty()) dict.put("iva", Float.parseFloat(iva));
        String profits = data[4];
        if (!profits.isEmpty()) dict.put("profits", Float.parseFloat(profits));
        String sector = data[5];
        if (!sector.isEmpty()) dict.put("sector", sector);
        dict.put("delivered", Boolean.valueOf(data[6]));
        
        return dict;
    }
    
    /**
     * Transforms withholding data into a CSV format
     * 
     * @param w withholding to transform
     * @return a string in CSV format with the withholding data
     */
    public static String withholdingToCsv(Withholding w) { 
        Map<String, Object> dict = w.getValues();
        Provider provider = (Provider)dict.get("provider");
        String result = "";
        result += dict.get("date") + ";" + dict.get("number") + ";" + provider.getValues().get("docNo") + ";" + dict.get("iva") 
                + ";" + dict.get("profits") + ";" + dict.get("sector") + ";" + dict.get("delivered") + ";";
        
        result = result.replace("null", "");
        return result;
    }
    
    /**
     * Transforms CSV line containing dollar price data into a map
     * 
     * @param priceStr String with CSV format containing dollar price data
     * @return a map from String to String of the dollar price data
     */
    public static Map<String, String> dollarPriceCsvToDict(String priceStr) {
        String[] data = priceStr.replace(",", ".").split(";");
        Map<String, String> dict = new HashMap<>();
        dict.put("date", data[0]);
        dict.put("buy", data[1]);
        dict.put("sell", data[2]);
        
        return dict;
    }
    
    /**
     * Transforms price data into a SQL format data
     * 
     * @param price price with data to transform
     * @return a pair of strings containing the attributes to insert and the actual values
     * to insert to those attributes
     */
    public static Pair<String, String> dollarPriceToSQL(DollarPrice price) {
        Map<String, Object> dict = price.getValues();
        String attributes = "", values = "";
        attributes += "date, buy, sell";
        values += "'" + dict.get("date") + "', " + dict.get("buy") + ", " + dict.get("sell") + "";

        return new Pair<>(attributes, values);
    }
    
    /**
     * Transforms CSV line containing dollar price backup data into a map
     * 
     * @param s String with CSV format containing dollar price data
     * @return a map from String to String of the dollar price data
     */
    public static Map<String, Object> dollarPriceCsvBackupToDict(String s) {
        String[] data = s.split(";");
        Map<String, Object> dict = new HashMap<>();
        
        dict.put("date", data[0]);
        dict.put("buy", data[1]);
        dict.put("sell", data[2]);
        
        return dict;
    }
    
    /**
     * Transforms dollar price data into a CSV format
     * 
     * @param p dollar price to transform
     * @return a string in CSV format with the dollar price data
     */
    public static String dollarPriceToCsv(DollarPrice p) {
        Map<String, Object> dict = p.getValues();
        String result = dict.get("date") + ";" + dict.get("buy") + ";" + dict.get("sell") + ";";
        
        result = result.replace("null", "");
        return result;
    }
    
    /**
     * Transforms provider data into a SQL format data
     * 
     * @param p provider with data to transform
     * @return a pair of strings containing the attributes to insert and the actual values
     * to insert to those attributes
     */
    public static Pair<String, String> providerToSQL(Provider p) {
        Map<String, Object> dict = p.getValues();
        String attributes = "", values = "";
        attributes += "docNo, name, docType";
        values += "'" + dict.get("docNo") + "', '" + dict.get("name") + "', '" + dict.get("docType") + "'";
        
        Pair<String, String> optionals = addOptionalAttributes(dict, new String[] {}, new String[] {"direction", "sector", "alias"});
        attributes += optionals.getFst();
        values += optionals.getSnd();
        
        return new Pair<>(attributes, values);
    }
    
    /**
     * Transforms provider data into a form format data
     * 
     * @param prov provider with data to transform
     * @return an object array containing the attributes to show
     */
    public static Object[] providerToForm(Provider prov) {
        Map<String, Object> dict = prov.getValues();
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
    
    /**
     * Transforms CSV line with containing provider backup data into a map
     * 
     * @param s String with CSV format containing provider data
     * @return a map from String to String of the provider data
     */
    public static Map<String, Object> providerCsvBackupToDict(String s) {
        String[] data = s.split(";");
        Map<String, Object> dict = new HashMap<>();
        
        dict.put("docNo", data[0]);
        dict.put("name", data[1]);
        String direction = data[2];
        if (!direction.isEmpty()) dict.put("direction", direction);
        String sector = data[3];
        if (!sector.isEmpty()) dict.put("provSector", sector);
        String alias = data[4];
        if (!alias.isEmpty()) dict.put("alias", alias);
        dict.put("docType", data[5]);
        
        return dict;
    }
            
    /**
     * Transforms provider data into a CSV format
     * 
     * @param p provider to transform
     * @return a string in CSV format with the provider data
     */
    public static String providerToCsv(Provider p) {
        Map<String, Object> dict = p.getValues();
        String result = "";
        
        result += dict.get("docNo") + ";" + dict.get("name") + ";" + dict.get("direction") + ";" + dict.get("sector") + ";" 
                + dict.get("alias") + ";" + dict.get("docType") + ";";
        
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
    
    /**
     * Determines if the initial line matches the format of the given mode
     * 
     * @param initialLine first line of a CSV file that contains the format of the file
     * @param mode a string representing the format that the first line should have
     * @return a boolean representing if the format is valid or not
     */
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

    /**
     * Transforms a list into a vector
     * 
     * @param list list of generic type
     * @return a vector of generic type containig all the elements of {@code list}
     */
    public static <E> Vector<E> listToVector(List<E> list) {
        Vector<E> vector = new Vector<>();
        for (E e : list) {
            vector.addElement(e);
        }
        return vector;
    }
    
    /**
     * Transfroms a string with date format into a Date
     * 
     * @param dateStr string with format date
     * @return a Date based on {@code dateStr} data
     */
    public static String formatDate(String dateStr) {
        String[] fields = dateStr.split("/"); //d-m-y
        if (fields.length != 3) {
            dateStr = fields[0];
            fields = dateStr.split("-");
            return fields[0] + "-" + fields[1] + "-" + fields[2]; //y-m-d
        }
        return fields[2] + "-" + fields[1] + "-" + fields[0]; //y-m-d
    }
    
    /** 
     * Transforms a map into a string with a SQL form as follows:
     * attr1 = value1, attr2 = value2 ...
     * 
     * @param params map from attribute names to values
     * @return a string representing the map in a SQL form
     */
    public static String mapToSQLValues(Map<String, Object> params) {
        List<String> values = new LinkedList<>();
        for (String key : params.keySet()) {
            if (params.get(key).getClass() == String.class || params.get(key).getClass() == Date.class) {
                values.add(key + " = '" + params.get(key) + "'");
            } else {
                values.add(key + " = " + params.get(key));
            }
        }

        return String.join(", ", values);
    }

    /**
     * Transforms a map of string to object into a map of string to string
     * 
     * @param values the map to transform
     * @return a map of string to string
     */
    public static Map<String, Object> objectToStringMap(Map<String, Object> values){
        Map<String, Object> v = new HashMap();
        Provider provider = (Provider) values.get("provider");
        v.putAll(provider.getValues());
        v.put("id", values.get("id").toString());
        v.put("date", formatDate(values.get("date").toString()));
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
