/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import views.View;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nacho
 */
public  class Handler implements Thread.UncaughtExceptionHandler {

    private static Logger logger = Logger.getLogger(Handler.class.getName());
    private static View view;
    private static FileHandler fileHandler;
    static {
            try {
                fileHandler = new FileHandler("./log", 0, 1, true);
            } catch (IOException | SecurityException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    
    /**
     * Creates the handler with a view to show errors
     * @param view is the view where the errors will popup
     */
    public Handler(View view) {
        this.view = view;
        initializeLogger();
    }
    
    private void initializeLogger() {
        fileHandler.setFormatter(new FacturasFormatter());
        logger.addHandler(fileHandler);
    }
    
    /**
     * Looks for known information in the exception
     * if found a custom message is shown in a view 
     * and exception is not logged
     * else a default error message is shown in a view
     * and the exception is logged
     * @param t the thread where the exception occurred
     * @param e the exception that was raised
     */
    public void uncaughtException(Thread t, Throwable e) {
        if (e instanceof IllegalArgumentException) {
            String msg = e.getMessage();
            if (msg.contains("File does not have a valid format to be loaded")) {
                view.showError("El archivo no tiene un formato valido para cargarse");
            } else if (msg.contains("provider doesn't exists")) {
                view.showError("El proveedor elegido no existe");
            } else if (msg.contains("sector doesn't exists")) {
                view.showError("El rubro elegido no existe");
            } else if (msg.contains("is not a valid backup folder")) {
                view.showError("La carpeta elegida no es un backup valido");
            } else if (msg.contains("File is null")) { //we just want to do nothing
            } else {
                logUnexpectedError(e);
            }
        } else if (e instanceof IllegalStateException) {
            String msg = e.getMessage();
            if (msg.contains("File is null")) {  //do nothing
            } else if (msg.contains("File does not have a valid format to be loaded")) {
                view.showError("El archivo no tiene un formato valido para cargarse");
            } else {
                logUnexpectedError(e);
            }
        } else if (e instanceof SQLException) {
            String sqlState = ((SQLException) e).getSQLState();
            if (sqlState.equals("23505")) {
                view.showError("El item que se intento cargar ya estaba cargado");
            }
        } else {
            logUnexpectedError(e);
        }
    }
    
    /**
     * Logs the stacktrace of the exception and shows 
     * an error message in the view
     * @param e the exception that will be logged
     */
    public static void logUnexpectedError(Throwable e) {
        view.showError("Ocurrio un error inesperado");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        logger.info(sw.toString());
    }
    
    /**
     * Logs the stacktrace of the exception along with 
     * additional info and shows an error message in the view
     * @param e the exception that will be logged
     * @param additionalInfo additional information about the 
     * cause of the exception that will be logged along with 
     * the stacktrace
     */
    public static void logUnexpectedError(Throwable e, String additionalInfo) {
        if (view != null) {
            view.showError("Ocurrio un error inesperado");
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        Exception ex = new Exception(additionalInfo, e);
        ex.printStackTrace(pw);
        logger.info(sw.toString());
    }
    
    /**
     * Shows an error message in the settled view
     * @param errorMessage is the message to show
     */
    public static void showErrorMessage(String errorMessage) {
        if (view != null) {
            view.showError(errorMessage);
        }
    }
}
    