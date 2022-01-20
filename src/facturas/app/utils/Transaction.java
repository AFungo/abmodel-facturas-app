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
public class Transaction {
    
    private float totalAmount;
    private float iva;
    
    public Transaction(float totalAmount, float iva) {
        this.totalAmount = totalAmount;
        this.iva = iva;
    }
    
    public void addTransaction(float amount, float iva) {
        this.totalAmount += amount;
        this.iva += iva;
    }
    
    public float [ ] getTransactions() {
        float [ ] transactions = {totalAmount, iva};
        return transactions;
    }
}
