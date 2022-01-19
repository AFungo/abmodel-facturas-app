/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.utils;

/**
 *
 * @author nacho
 */
public class Purchase {
    
    private float totalAmount;
    private float iva;
    
    public Purchase(float totalAmount, float iva) {
        this.totalAmount = totalAmount;
        this.iva = iva;
    }
    
    public void addPurchase(float amount, float iva) {
        this.totalAmount += amount;
        this.iva += iva;
    }
    
    public float [ ] getPurchases() {
        float [ ] purchases = {totalAmount, iva};
        return purchases;
    }
}
