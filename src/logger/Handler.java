/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import facturas.app.views.View;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

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
    
    public Handler(View view) {
        this.view = view;
        initializeLogger();
    }
    
    private void initializeLogger() {
        fileHandler.setFormatter(new FacturasFormatter());
        logger.addHandler(fileHandler);
    }
    
    public void uncaughtException(Thread t, Throwable e) {
        if (e instanceof DerbySQLIntegrityConstraintViolationException) {
            view.showError(e, "El item que se intento cargar ya estaba cargado");
        } else if (e instanceof IllegalArgumentException) {
            if (e.getMessage().contains("File does not have a valid format to be loaded")) {
                view.showError(e, "El archivo no tiene un formato valido para cargarse");
            } else if (e.getMessage().contains("provider doesn't exists")) {
                view.showError(e, "El proveedor elegido no existe");
            } else if (e.getMessage().contains("sector doesn't exists")) {
                view.showError(e, "El rubro elegido no existe");
            }
        } else {
            view.showError(e, "Ocurrio un error inesperado");
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            logger.info(sw.toString());
        }
    }
}
