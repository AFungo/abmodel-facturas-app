/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app;

import com.itextpdf.text.log.LoggerFactory;
import facturas.app.views.View;

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
        LOGGER.info("Unhandled exception caught!");
        view.showError(e);
        e.printStackTrace();
    }
}
