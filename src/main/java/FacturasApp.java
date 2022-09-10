/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import controller.Controller;
import logger.Handler;
import views.View;
import database.DBManager;
import database.DBManager.TypeDB;
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
        DBManager.createConnection(TypeDB.PRODUCTION);
        DBManager.initializeDB();
        // TODO: Controller could be created inside View, since is not used here
        //  i think the best is to create the controller and inside of it create the view,
        //  so we can use controller methods here in main

        View view = new View(new Controller());
        Handler globalExceptionHandler = new Handler(view);
        Thread.setDefaultUncaughtExceptionHandler(globalExceptionHandler);
        view.setVisible(true);
        
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                DBManager.closeConnection();
            }
        });
    }
    
}
