package views.utils;

import models.Provider;
import models.Ticket;
import utils.FormatUtils;
import utils.Pair;
import utils.Validate;

import javax.swing.*;

import java.io.UncheckedIOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

public class ViewUtils {

    public static void cleanTextField(JTextField[] textField) {
        for(JTextField t : textField) t.setText("");
    }

    public static JTable createTableToDelete(Object[] rowToDelete) {
        Object[][] rows = {rowToDelete};
        Object[] cols = {"id", "Fecha", "Tipo", "Nro factura", "Denominación Emisor", "Imp. Total"};
        JTable table = new JTable(rows, cols);
        table.setDefaultEditor(Object.class, null);
        table.setCellSelectionEnabled(true);
        return table;
    }

    public static JTable createMissingPricesTable(List<Pair<Date,String>> data) {
        int length = data.size(), i = 0;
        Object[][] rows = new Object[length][2];
        for (Pair<Date,String> p : data) {
            rows[i][0] = p.getFst();
            rows[i][1] = p.getSnd();
            i++;
        }
        Object[] cols = {"Fecha","Dias hasta el precio mas cercano"};
        JTable table = new JTable(rows, cols);
        table.setDefaultEditor(Object.class, null);
        table.setCellSelectionEnabled(true);
        return table;
    }

    public static String validateProviderParam(Map<String, Object> values, JComboBox<String> sectorsComboBox) {
        return Validate.providerInput(values, sectorsComboBox);
    }

    public static Ticket makeNegative(Ticket t) {
        Map<String, Object> values = FormatUtils.objectToStringMap(t.getValues());
        values.put("totalAmount", "-" + values.get("totalAmount"));
        if (values.get("netAmountWI") != null) values.put("netAmountWI","-" + values.get("netAmountWI"));
        if (values.get("netAmountWOI") != null) values.put("netAmountWOI","-" + values.get("netAmountWOI"));
        if (values.get("amountImpEx") != null) values.put("amountImpEx","-" + values.get("amountImpEx"));
        if (values.get("ivaTax") != null) values.put("ivaTax", "-" + values.get("ivaTax"));
        return new Ticket(values);
    }

    //ticket is a boolean representing if the validation is for ticket or withholding
    public static String validateParam(java.util.Date date, Map<String, String> values, boolean ticket,
                                JComboBox<String> sectorsComboBox, List<Provider> selectedProvider) {

        String message = Validate.withholdingInput(date, sectorsComboBox, values, selectedProvider, ticket);
        if (ticket) {
            message += Validate.ticketInput(date, values);
        }

        if (!message.isEmpty()) {
            return "<html>" + message + "</html>";
        }

        return null;
    }
        /**
     * take the list of values to create a ticket, 
     * check if are in the correct order (like getAttributes return in ticket)
     * and check if all the obhject are in correct format
     * @param values
     * @return if are cerrect
     */
    public static boolean validateParamTicket(List<Object> values){
        throw new UnsupportedOperationException("TODO: Implement");
    }

    /**
     * take the list of values to create a withholding, 
     * check if are in the correct order (like getAttributes return in withholding)
     * and check if all the obhject are in correct format
     * @param values
     * @return if are cerrect
     */
    public static boolean validateParamWithholding(List<Object> values){
        throw new UnsupportedOperationException("TODO: Implement");
    }

}
