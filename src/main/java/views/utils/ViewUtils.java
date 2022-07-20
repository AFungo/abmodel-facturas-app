package views.utils;

import javax.swing.*;

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

}
