package views.utils;

import utils.Pair;

import javax.swing.*;
import java.sql.Date;
import java.util.List;

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

}
