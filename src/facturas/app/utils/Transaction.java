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
    
    private Double totalAmount;
    private Double iva;
    
    public Transaction(Double totalAmount, Double iva) {
        this.totalAmount = totalAmount;
        this.iva = iva;
    }
    
    public void addTransaction(Double amount, Double iva) {
        this.totalAmount += amount;
        this.iva += iva;
    }
    
    public Pair<Double, Double> getTransactions() {
        Pair<Double, Double> transactions = new Pair<> (totalAmount, iva);
        return transactions;
    }
}
