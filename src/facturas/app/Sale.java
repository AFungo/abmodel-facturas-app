/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app;

/**
 *
 * @author nacho
 */
public class Sale {
    
    private float totalAmount;
    private float iva;
    private float retainedIVA;
    private float profitRetention;
    
    public Sale(float totalAmount, float iva, float retainedIVA, float profitRetention) {
        this.totalAmount = totalAmount;
        this.iva = iva;
        this.retainedIVA = retainedIVA;
        this.profitRetention = profitRetention;
    }
    
    public void addSale(float amount, float iva, float retainedIVA, float profitRetention) {
        this.totalAmount += amount;
        this.iva += iva;
        this.retainedIVA += retainedIVA;
        this.profitRetention += profitRetention;
    }
    
    public float [ ] getSales() {
        float [ ] sales = {totalAmount, iva, retainedIVA, profitRetention};
        return sales;
    }
}
