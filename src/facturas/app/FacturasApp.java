/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app;

import facturas.app.views.View;
import facturas.app.database.DBManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Agustin
 */
public class FacturasApp {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DBManager.createConnection();
        DBManager.initializeDB();
        
        View view = new View(new Controller());
        view.setVisible(true);
        
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                DBManager.closeConnection();
            }
        });
    }
    
}
