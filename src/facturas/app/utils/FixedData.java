/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * ¸this class give the correct structure of the data
 */
public class FixedData {
    
    /*
        names of all tickets types
    */
    private static final String[] types = {"1 - Factura A", "2 - Nota de Debito A", "3 - Nota de Credito A", "4 - Recibo A", 
            "5 - Nota de Venta al Contado A", "6 - Factura B", "7 - Nota de Debito B", "8 - Nota de Credito B", 
            "9 - Recibo B", "10 - Nota de Venta al Contado B", "11 - Factura C", "12 - Nota de Debito C", 
            "13 - Nota de Credito C", "15 - Recibo C", "16 - Nota de Venta al Contado C", 
            "17 - Liquidacion de Servicios Publicos Clase A", "18 - Liquidacion de Servicios Publicos Clase B", 
            "33 - Liquidacion Primaria de Granos", "47 - Nota de Debito Liquidacion Unica Comercial Impositiva Clase C", 
            "48 - Nota de Credito Liquidacion Unica Comercial Impositiva Clase A", 
            "49 - Comprobante de Compra de Bienes no Registrables a Consumidores Finales", 
            "50 - Recibo Factura a Regimen de Factura de Credito ", "51 - Factura M", "52 - Nota de Debito M", 
            "53 - Nota de Credito M", "54 - Recibo M", "55 - Nota de Venta al Contado M", "59 - Liquidacione M", 
            "63 - Liquidacione A", "64 - Liquidacione B", "68 - Liquidacion C", "70 - Recibo Factura de Credito", 
            "81 - Tique Factura A", "82 - Tique Factura B", "83 - Tique", "88 - Remito Electronico", "110 - Tique Nota de Credito", 
            "111 - Tique Factura C", "112 - Tique Nota de Credito A", "113 - Tique Nota de Credito B", "114 - Tique Nota de Credito C", 
            "115 - Tique Nota de Debito A", "116 - Tique Nota de Debito B", "117 - Tique Nota de Debito C", "118 - Tique Factura M", 
            "119 - Tique Nota de Credito M", "120 - Tique Nota de Debito M", "331 - Liquidacion Secundaria de Granos", 
            "332 - Certificacion Electronica (Granos)"};

    private static Map<String, String> ticketTypesMap = new HashMap<>();

    private static void initializeTicketTypesMap() {
        ticketTypesMap.put("1", "1 - Factura A");
        ticketTypesMap.put("2", "2 - Nota de Debito A");
        ticketTypesMap.put("3", "3 - Nota de Credito A");
        ticketTypesMap.put("4", "4 - Recibo A");
        ticketTypesMap.put("5", "5 - Nota de Venta al Contado A");
        ticketTypesMap.put("6", "6 - Factura B");
        ticketTypesMap.put("7", "7 - Nota de Debito B");
        ticketTypesMap.put("8", "8 - Nota de Credito B");
        ticketTypesMap.put("9", "9 - Recibo B");
        ticketTypesMap.put("10", "10 - Nota de Venta al Contado B");
        ticketTypesMap.put("11", "11 - Factura C");
        ticketTypesMap.put("12", "12 - Nota de Debito C");
        ticketTypesMap.put("13", "13 - Nota de Credito C");
        ticketTypesMap.put("15", "15 - Recibo C");
        ticketTypesMap.put("16", "16 - Nota de Venta al Contado C");
        ticketTypesMap.put("17", "17 - Liquidacion de Servicios Publicos Clase A");
        ticketTypesMap.put("18", "18 - Liquidacion de Servicios Publicos Clase B");
        ticketTypesMap.put("33", "33 - Liquidacion Primaria de Granos");
        ticketTypesMap.put("47", "47 - Nota de Debito Liquidacion Unica Comercial Impositiva Clase C");
        ticketTypesMap.put("48", "48 - Nota de Credito Liquidacion Unica Comercial Impositiva Clase A");
        ticketTypesMap.put("49", "49 - Comprobante de Compra de Bienes no Registrables a Consumidores Finales");
        ticketTypesMap.put("50", "50 - Recibo Factura a Regimen de Factura de Credito ");
        ticketTypesMap.put("51", "51 - Factura M");
        ticketTypesMap.put("52", "52 - Nota de Debito M");
        ticketTypesMap.put("53", "53 - Nota de Credito M");
        ticketTypesMap.put("54", "54 - Recibo M");
        ticketTypesMap.put("55", "55 - Nota de Venta al Contado M");
        ticketTypesMap.put("59", "59 - Liquidacione M");
        ticketTypesMap.put("63", "63 - Liquidacione A");
        ticketTypesMap.put("64", "64 - Liquidacione B");
        ticketTypesMap.put("68", "68 - Liquidacion C");
        ticketTypesMap.put("70", "70 - Recibo Factura de Credito");
        ticketTypesMap.put("81", "81 - Tique Factura A");
        ticketTypesMap.put("82", "82 - Tique Factura B");
        ticketTypesMap.put("83", "83 - Tique");
        ticketTypesMap.put("88", "88 - Remito Electronico");
        ticketTypesMap.put("110", "110 - Tique Nota de Credito");
        ticketTypesMap.put("111", "111 - Tique Factura C");
        ticketTypesMap.put("112", "112 - Tique Nota de Credito A");
        ticketTypesMap.put("113", "113 - Tique Nota de Credito B");
        ticketTypesMap.put("114", "114 - Tique Nota de Credito C");
        ticketTypesMap.put("115", "115 - Tique Nota de Debito A");
        ticketTypesMap.put("116", "116 - Tique Nota de Debito B");
        ticketTypesMap.put("117", "117 - Tique Nota de Debito C");
        ticketTypesMap.put("118", "118 - Tique Factura M");
        ticketTypesMap.put("119", "119 - Tique Nota de Credito M");
        ticketTypesMap.put("120", "120 - Tique Nota de Debito M");
        ticketTypesMap.put("331", "331 - Liquidacion Secundaria de Granos");
        ticketTypesMap.put("332", "332 - Certificacion Electronica (Granos)");    
    }
    
    private static Map<String, String> documentTypesMap = new HashMap<>();
    
    private static void initializeDocumentTypesMap() {
        documentTypesMap.put("80", "CUIT");
        documentTypesMap.put("86", "CUIL");
    }
    
    /**
     * @return ticket types 
     */
    public static String[] getTicketTypes() {
        return types;
    }
    
    public static String getTicketTypeByCode(String code) {
        if (ticketTypesMap.isEmpty()) {
            initializeTicketTypesMap();
        }
        String ticketType = ticketTypesMap.get(code);
        return ticketType == null ? code : ticketType;
    }
    
    /**
     * @return document types 
     */
    public static String[] getDocumentTypes() {
        return new String[] {"CUIT", "CUIL"};
    }
    
    public static String getDocumentTypeByCode(String code) {
        if (documentTypesMap.isEmpty()) {
            initializeDocumentTypesMap();
        }
        String docType = documentTypesMap.get(code);
        return docType == null ? code : docType;
    }
    
    public static String getOldExchangeType(String exchange) {
        throw new UnsupportedOperationException("TODO: create a hashmap with the new names of exchange types as keys and as value the old"
                + "values that we still use in the program");
    }
    
    /**
     * @return ticket emitter file format 
     */
    public static String getTicketEmitterFileFormat() {
        return "\"Fecha de Emisión\";\"Tipo de Comprobante\";\"Punto de Venta\";\"Número Desde\";\"Número Hasta\";\"Cód. Autorización\";\"Tipo Doc. Emisor\";\"Nro. Doc. Emisor\";\"Denominación Emisor\";\"Tipo Cambio\";\"Moneda\";\"Imp. Neto Gravado\";\"Imp. Neto No Gravado\";\"Imp. Op. Exentas\";\"Otros Tributos\";\"IVA\";\"Imp. Total\"";
    }
    
    /**
     * @return ticket receptor file format
     */ 
    public static String getTicketReceptorFileFormat() {
        return "\"Fecha de Emisión\";\"Tipo de Comprobante\";\"Punto de Venta\";\"Número Desde\";\"Número Hasta\";\"Cód. Autorización\";\"Tipo Doc. Receptor\";\"Nro. Doc. Receptor\";\"Denominación Receptor\";\"Tipo Cambio\";\"Moneda\";\"Imp. Neto Gravado\";\"Imp. Neto No Gravado\";\"Imp. Op. Exentas\";\"Otros Tributos\";\"IVA\";\"Imp. Total\"";
    }
    
    /**
     * @return dollar price file format
     */
    public static String getDollarPriceFileFormat() {
        return "Fecha cotizacion;Compra;Venta;";
    }
    
    /**
     * @return the format of the ticket 
     */
    public static String getTicketAppFormat() {
        return "date;number;providerDoc;iva;profits;delivered;sector;type;numberTo;authCode;exchangeType;exchangeMoney;netAmountWI;netAmountWOI;amountImpEx;ivaTax;totalAmount;issuedByMe;";
    }
    
    /**
     *  @return the format of the withholdings
     */
    public static String getWithholdingAppFormat() {
        return "date;number;providerDoc;iva;profits;sector;delivered;";
    }
    
    /**
     * @return the format of the provider 
     */
    public static String getProviderAppFormat() {
        return "docNo;name;direction;sector;alias;documentType;";
    }
    
    /**
     * @return the format of the sector
     */
    public static String getSectorAppFormat() {
        return "name;";
    }
    
    /**
     * @param name the name for the backup file
     * @return the backup file name format
     */
    public static String getBackupFolderName(String name) {
        return "\\" + name + "--" + LocalDate.now() + "--" + currentTimeMinutesHours() + "\\";
    }
    
    /**
     * check if the value of a string is a valid ticket type
     * @param type the string who want to check if is a valid type
     * @return a boolean who said is valid or not
     */
    public static boolean validType(String type) {
        boolean value = Arrays.asList(types).contains(type);
        return value;
    }
    
    private static String currentTimeMinutesHours() {
        LocalTime current = LocalTime.now();
        return current.getHour() + "-" + current.getMinute();
    }
    
}
