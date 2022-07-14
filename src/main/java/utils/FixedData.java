/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

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
    
    /**
     * @return ticket types 
     */
    public static String[] getTicketTypes() {
        return types;
    }

    /**
     * @return document types 
     */
    public static String[] getDocumentTypes() {
        return new String[] {"CUIT", "CUIL"};
    }
    
    /**
     * @return ticket emitter file format 
     */
    public static String getTicketEmitterFileFormat() {
        return "\"Fecha\",\"Tipo\",\"Punto de Venta\",\"Número Desde\",\"Número Hasta\",\"Cód. Autorización\",\"Tipo Doc. Emisor\",\"Nro. Doc. Emisor\",\"Denominación Emisor\",\"Tipo Cambio\",\"Moneda\",\"Imp. Neto Gravado\",\"Imp. Neto No Gravado\",\"Imp. Op. Exentas\",\"IVA\",\"Imp. Total\"";
    }
    
    /**
     * @return ticket receptor file format
     */ 
    public static String getTicketReceptorFileFormat() {
        return "\"Fecha\",\"Tipo\",\"Punto de Venta\",\"Número Desde\",\"Número Hasta\",\"Cód. Autorización\",\"Tipo Doc. Receptor\",\"Nro. Doc. Receptor\",\"Denominación Receptor\",\"Tipo Cambio\",\"Moneda\",\"Imp. Neto Gravado\",\"Imp. Neto No Gravado\",\"Imp. Op. Exentas\",\"IVA\",\"Imp. Total\"";
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
