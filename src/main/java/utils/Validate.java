/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import models.Provider;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;

/**
 * this class provides a methods for check if the values are correct 
 * 
 */
public class Validate {
    
    /**
     * Check if the provider input are valid
     * @param values of the provider
     * @param sectorsComboBox combo box with the sector for the provider
     * @return if the inputs are invalid return a string with invalidations or returns a empty string 
     */
    public static String providerInput(Map<String, Object> values, JComboBox<String> sectorsComboBox) {
        String message = "<html>", invalidations = "";
        List<String> sectors = getItemsFromComboBox(sectorsComboBox);
                
        if (((String) values.get("name")).isEmpty()) {
            invalidations += "<br/> Nombre no introducido";
        }
        if (((String) values.get("docNo")).isEmpty()) {
            invalidations += "<br/> Numero documento no introducido";
        } else if (!tryParse(values.get("docNo"), Integer.class, false)) { 
            invalidations += "<br/> Numero documento mal escrito";
        }
        
        if (((String) values.get("docType")).isEmpty()) {
            invalidations += "<br/> Tipo de documento no introdcido";
        }
        
        Object providerSector = values.get("provSector"); //if not null or empty and doesn't exists
        if (providerSector != null && (!((String) providerSector).isEmpty()) && (!sectors.contains(providerSector))) {
            invalidations += "<br/>El rubro del comprobante no existe";
        }
        
        if (invalidations.isEmpty()) {
            SQLFilter filter = new SQLFilter();
            filter.add("docNo", "=", values.get("docNo"), String.class);
            if (ProviderDAO.exist(filter)) {
                invalidations += "<br/>El proveedor " + values.get("name") + " con nro documento " + values.get("docNo") + " ya esta cargado";
            }
        }
        
        if (invalidations.isEmpty()) {
            return null;
        } else {
            invalidations += "</html>";
            return message + invalidations;
        }
    }

    /**
     * Check if the ticket input are valid
     * @param date date of the ticket
     * @param values values of the ticket
     * @return a string with the invalidatios if all values of the ticket are valid string can be empty
     */
    public static String ticketInput(java.util.Date date, Map<String, String> values) {
        
        String invalidations = "";
        if (values.get("type").isEmpty() || !FixedData.validType(values.get("type"))) {
            invalidations += "<br/>No se especifico el tipo de comprobante";
        }
        
        if (values.get("exchangeMoney").isEmpty()) {
            invalidations += "<br/>No se introdujo el tipo de moneda";
        }
        
        boolean[] numerics = validTicketValues(values);
        invalidations += addInvalidTicketMessages(numerics);
        
        return invalidations;
    }

    /**
     * Check if the withholding input are valid
     * @param date date of the withholding
     * @param sectorsComboBox sector of the withholding
     * @param values values of the withholding
     * @param selectedProvider provider of the withholding
     * @param forTicket tells if the validation is for a ticket or a withholding only
     * @return a string with the invalidatios if all values of the withholdings are valid string can be empty
     */    
    public static String withholdingInput(java.util.Date date, JComboBox<String> sectorsComboBox, 
        Map<String, String> values, List<Provider> selectedProvider, boolean forTicket) {
        
        List<String> sectors = getItemsFromComboBox(sectorsComboBox);
        String invalidations = "";
        
        if (date == null) {
            invalidations += "<br/>Fecha no introducida";
        }
        
        if (selectedProvider.isEmpty()) {
            invalidations += "<br/>Proveedor no introducido";
        } else if (selectedProvider.size() > 1) {
            invalidations += "<br/>El proveedor elegido no es unico, especifique su documento";
        }
        
        String selectedSector = values.get("sector"); //if not null or empty and doesn't exists
        if (selectedSector != null && (!selectedSector.isEmpty()) && (!sectors.contains(selectedSector))) {
            invalidations += "<br/>El rubro del comprobante no existe";
        }
        
        boolean[] numerics = validWithholdingValues(values, forTicket);
        invalidations += addInvalidWithholdingMessages(numerics);
        
        if (invalidations.isEmpty()) {
            Provider prov = selectedProvider.get(0);
            SQLFilter filter = FilterUtils.createTicketFilter(values, prov, date);
            if (WithholdingDAO.exist(filter)) { 
                invalidations += "<br/>El comprobante de proveedor " + prov.getValues().get("name") + ", numero " + 
                        values.get("number") + " y fecha " + date + " ya esta cargado";
            }
        }
        
        return invalidations;
    }

    /*
     * return a list of items of the combobox 
     */
    private static List<String> getItemsFromComboBox(JComboBox<String> sectorsComboBox) {
        List<String> items = new LinkedList<>();
        for (int i = 0; i < sectorsComboBox.getItemCount(); i++) {
            items.add(sectorsComboBox.getItemAt(i));
        }
        return items;
    }
 
    /*
    * check if the values of the ticket are valid
    */
    private static boolean[] validTicketValues(Map<String, String> values) {
        boolean[] validations = new boolean[8];

        int i = 0;
        validations[i++] = values.get("amountImpEx").isEmpty() ? true : tryParse(values.get("amountImpEx"), Float.class, false);
        validations[i++] = values.get("exchangeType").isEmpty() ? true : tryParse(values.get("exchangeType"), Float.class, false);
        validations[i++] = values.get("ivaTax").isEmpty() ? true : tryParse(values.get("ivaTax"), Float.class, false);
        validations[i++] = values.get("ivaTax1").isEmpty() ? true : tryParse(values.get("ivaTax1"), Float.class, false);
        validations[i++] = values.get("ivaTax2").isEmpty() ? true : tryParse(values.get("ivaTax2"), Float.class, false);
        validations[i++] = values.get("netAmountWI").isEmpty() ? true : tryParse(values.get("netAmountWI"), Float.class, false);
        validations[i++] = values.get("netAmountWOI").isEmpty() ? true : tryParse(values.get("netAmountWOI"), Float.class, false);
        validations[i++] = tryParse(values.get("totalAmount"), Float.class, false);

        return validations;
    }
    
    /*
    * check if the values of withholding are valid
    */
    private static boolean[] validWithholdingValues(Map<String, String> values, boolean forTicket) {
        boolean[] validations = new boolean[4];
        
        int i = 0;
        String iva = values.get("iva"), profits = values.get("profits");
        
        validations[i++] = tryParse(values.get("number"), Integer.class, false);
        validations[i++] = iva.isEmpty() ? true : tryParse(iva, Float.class, false);
        validations[i++] = profits.isEmpty() ? true : tryParse(profits, Float.class, false);
        
        if (!forTicket) {   //if we are validating for a withholding only, we need at least one of the values present
            validations[i++] = tryParse(iva, Float.class, true) || tryParse(profits, Float.class, true);
        } else {    //if we are validating withholding for a ticket we don't care about iva and profits except they are correctly parsed
            validations[i++] = true;
        }
        return validations;
    }
    
    /*
    * add invalid ticket messages
    */
    private static String addInvalidTicketMessages(boolean[] numerics) {
        String invalidations = "";
        int i = 0;
        if (!numerics[i++])
            invalidations += "<br/>Importe Op. Exentas mal escrito";
        if (!numerics[i++])
            invalidations += "<br/>Tipo de cambio mal escrito";
        if (!numerics[i++] || !numerics[i++] || !numerics[i++])
            invalidations += "<br/>algunos de los Ivas mal escritos";
        if (!numerics[i++])
            invalidations += "<br/>Importe neto gravado mal escrito";
        if (!numerics[i++])
            invalidations += "<br/>Importe neto no gravado mal escrito";
        if (!numerics[i++])
            invalidations += "<br/>Importe total mal escrito";
        
        return invalidations;
    }
    
    /*
    * add invalid withholding messages
    */
    private static String addInvalidWithholdingMessages(boolean[] numerics) {
        String invalidations = "";
        int i = 0;
        if (!numerics[i++])
            invalidations += "<br/>Numero de ticket mal escrito";
        if (!numerics[i++])
            invalidations += "<br/>retencion iva mal escrito";
        if (!numerics[i++])
            invalidations += "<br/>retencion ganancias mal escrito";
        if (!numerics[i++])
            invalidations += "<br/>ningun valor introducido a la retencion";

        return invalidations;
    }
    
    /**
    * check if a value can be pase to a class
    * @param value value want to parse
    * @param expectedClass class who can try to parse the value
    * @param notZero indicates that the value must not be zero, in such case false is returned
    * @return true if the value can be parsed and its not negative
    */
    public static boolean tryParse(Object value, Class<?> expectedClass, boolean notZero) {
        try { 
            if (expectedClass == Float.class) {
                float i = Float.parseFloat((String) value);
                if (notZero) {
                    return i > 0;
                }
                return i >= 0;
            } else if (expectedClass == Integer.class){
                BigInteger i = new BigInteger((String) value);
                if (notZero) {
                    return i.signum() > 0;
                }
                return i.signum() >= 0;
            } else {
                System.out.println("class wrongly passed, " + expectedClass + " is not valid");
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    
}
