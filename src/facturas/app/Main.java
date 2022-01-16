/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app;

import java.sql.DriverManager;

/**
 *
 * @author Agustin
 */
public class Main {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
        mw.setVisible(true);
        mw.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    DriverManager.getConnection("jdbc:derby:;shutdown=true");
                }catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        });
    }
    
}
