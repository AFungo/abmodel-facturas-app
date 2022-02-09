/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.utils;

import java.util.Map;
import java.util.HashMap;
/**
 *
 * @author nacho
 */
public class Transaction {
    
    private Float netAmountWI;
    private Float totalAmount;
    private Float iva;

    public Transaction() {
        this.totalAmount = 0.0f;
        this.iva = 0.0f;
        this.netAmountWI = 0.0f;
    }
    
    public Transaction(Float totalAmount, Float iva, Float netAmountWI) {
        this.totalAmount = totalAmount;
        this.iva = iva;
        this.netAmountWI = netAmountWI;
    }
    
    public void addTransaction(Float amount, Float iva, Float netAmountWI) {
        this.totalAmount += amount;
        this.iva += iva;
        this.netAmountWI += netAmountWI;    
    }
    
    public Map<String, Object> getTransactions() {
        Map<String, Object> transactions = new HashMap<>();
        transactions.put("totalAmount", totalAmount);
        transactions.put("iva", iva);
        transactions.put("netAmountWI", netAmountWI);
        return transactions;
    }
}
