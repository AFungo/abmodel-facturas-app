/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.utils;

import facturas.app.database.SQLFilter;
import java.sql.Date;
import java.util.Map;
import javax.swing.JTable;

/**
 *
 * @author nacho
 */
public class FilterUtils {
    
    public static SQLFilter createTicketFilter(int row, JTable ticketsTable) {
        SQLFilter filter = new SQLFilter();
        Date date = (Date)ticketsTable.getValueAt(row, 1); //1 is the date column
        filter.add("date", "=", date, Date.class);
        String noTicket = (String)ticketsTable.getValueAt(row, 3); //3 is the noTicket column
        filter.add("number", "=", noTicket, String.class);
        String cuit = (String)ticketsTable.getValueAt(row, 6); //6 is the cuit column
        filter.add("providerDoc", "=", cuit, String.class);
        
        return filter;
    }
    
    public static SQLFilter createTicketFilter(Map<String, String> values) {
        SQLFilter filter = new SQLFilter();
        Date date = FormatUtils.dateGen(values.get("date"));
        filter.add("date", "=", date, Date.class);
        String noTicket = values.get("number");
        filter.add("number", "=", noTicket, String.class);
        String docNo = values.get("docNo");
        filter.add("providerDoc", "=", docNo, String.class);
        
        return filter;
    }
    
}
