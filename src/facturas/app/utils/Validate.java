/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.utils;

import facturas.app.database.ProviderDAO;
import facturas.app.database.SQLFilter;
import facturas.app.database.WithholdingDAO;
import facturas.app.models.Provider;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;

/**
 *
 * @author nacho
 */
public class Validate {
 
    public static String providerInput(Map<String, String> values, JComboBox<String> sectorsComboBox) {
        String message = "<html>", invalidations = "";
        List<String> sectors = getItemsFromComboBox(sectorsComboBox);
                
        if(values.get("name").isEmpty()){ invalidations += "<br/> Nombre no introducido";}
        if(values.get("docNo").isEmpty()){
            invalidations += "<br/> Numero documento no introducido";
        } else if (!tryParse(values.get("docNo"), Integer.class, false)) { 
            invalidations += "<br/> Numero documento mal escrito";
        }
        
        if(values.get("docType").isEmpty()){ invalidations += "<br/> Tipo de documento no introdcido";}
        
        String providerSector = values.get("provSector"); //if not null or empty and doesn't exists
        if (providerSector != null && (!providerSector.isEmpty()) && (!sectors.contains(providerSector))) {
            invalidations += "<br/>El rubro del comprobante no existe";
        }
        
        if (invalidations.isEmpty()) {
            SQLFilter filter = new SQLFilter();
            filter.add("docNo", "=", values.get("docNo"), String.class);
            if (ProviderDAO.providerExist(filter)) {
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
    
    public static String withholdingInput(java.util.Date date, JComboBox<String> sectorsComboBox, 
            Map<String, String> values, List<Provider> selectedProvider) {
        
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
        
        boolean[] numerics = validWithholdingValues(values);
        invalidations += addInvalidWithholdingMessages(numerics);
        
        if (invalidations.isEmpty()) {
            Provider prov = selectedProvider.get(0);
            SQLFilter filter = FilterUtils.createTicketFilter(values, prov, date);
            if (WithholdingDAO.exists(filter)) { 
                invalidations += "<br/>El comprobante de proveedor " + prov.getValues().get("name") + ", numero " + 
                        values.get("number") + " y fecha " + date + " ya esta cargado";
            }
        }
        
        return invalidations;
    }
    
    private static List<String> getItemsFromComboBox(JComboBox<String> sectorsComboBox) {
        List<String> items = new LinkedList<>();
        for (int i = 0; i < sectorsComboBox.getItemCount(); i++) {
            items.add(sectorsComboBox.getItemAt(i));
        }
        return items;
    }
 
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
    
    private static boolean[] validWithholdingValues(Map<String, String> values) {
        boolean[] validations = new boolean[3];
        
        int i = 0;
        validations[i++] = tryParse(values.get("number"), Integer.class, false);
        validations[i++] = values.get("iva").isEmpty() ? true : tryParse(values.get("iva"), Float.class, true);
        validations[i++] = values.get("profits").isEmpty() ? true : tryParse(values.get("profits"), Float.class, true);

        return validations;
    }
    
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
    
    private static String addInvalidWithholdingMessages(boolean[] numerics) {
        String invalidations = "";
        int i = 0;
        if (!numerics[i++])
            invalidations += "<br/>Numero de ticket mal escrito";
        if (!numerics[i++])
            invalidations += "<br/>retencion iva mal escrito";
        if (!numerics[i++])
            invalidations += "<br/>retencion ganancias mal escrito";

        return invalidations;
    }
    
    public static boolean tryParse(String value, Class expectedClass, boolean notZero) {
        try { 
            if (expectedClass == Float.class) {
                Float i = Float.parseFloat(value);
                if (notZero) {
                    return i != 0;
                }
            } else if (expectedClass == Integer.class){
                BigInteger i = new BigInteger(value);
                if (notZero) {
                    return i.signum() != 0;
                }
            } else {
                System.out.println("class wrongly passed, " + expectedClass + " is not valid");
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
