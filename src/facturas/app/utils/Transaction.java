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
    
    private Float totalAmount;
    private Float iva;
    
    public Transaction(Float totalAmount, Float iva) {
        this.totalAmount = totalAmount;
        this.iva = iva;
    }
    
    public void addTransaction(Float amount, Float Tiva) {
        this.totalAmount += amount;
        this.iva += iva;
    }
    
    public Pair<Float, Float> getTransactions() {
        Pair<Float, Float> transactions = new Pair<> (totalAmount, iva);
        return transactions;
    }
}
