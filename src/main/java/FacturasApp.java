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
import views.utils.ViewMediator;

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
        ViewMediator viewMediator = new ViewMediator(new Controller());/*TODO: i dont know if i understand but i think a
                                                                                view mediator its a good class to call
                                                                                the main view an do all of shit here :)*/
        viewMediator.setMainViewVisible(true);

    }
    
}
