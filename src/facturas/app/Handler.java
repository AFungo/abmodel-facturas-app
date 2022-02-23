/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app;

import com.itextpdf.text.log.LoggerFactory;
import facturas.app.views.View;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

/**
 *
 * @author nacho
 */
class Handler implements Thread.UncaughtExceptionHandler {

    private static com.itextpdf.text.log.Logger LOGGER = LoggerFactory.getLogger(Handler.class);
    private static View view;
    
    public Handler(View view) {
        this.view = view;
    }
    
    public void uncaughtException(Thread t, Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        LOGGER.info(sw.toString());
        
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
        }
    }
}
