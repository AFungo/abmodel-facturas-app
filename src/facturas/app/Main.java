/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Agustin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("facturas-appPU");
        FacturaJpaController jpa = new FacturaJpaController(emf);
        Factura factura = new Factura();
        try {
            jpa.create(factura);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        for(Factura f : jpa.findFacturaEntities()) {
            System.out.println(f);
            /*try {
                jpa.destroy(f.getId());
            } catch (Exception e) {
                System.out.println(e);
            }*/
        }
        
        MainWindow mw = new MainWindow();
        mw.setVisible(true);
        
    }
    
}
