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
    
    private Float netAmountWOI;
    private Float totalAmount;
    private Float iva;
    
    public Transaction(Float totalAmount, Float iva, Float netAmountWOI) {
        this.totalAmount = totalAmount;
        this.iva = iva;
        this.netAmountWOI = netAmountWOI;
    }
    public void addTransaction(Float amount, Float iva, Float netAmountWOI) {
        this.totalAmount += amount;
        this.iva += iva;
        this.netAmountWOI += netAmountWOI;    
    }
    
    public Map<String, Object> getTransactions() {
        Map<String, Object> transactions = new HashMap<>();
        transactions.put("totalAmount", totalAmount);
        transactions.put("iva", iva);
        transactions.put("netAmountWOI", netAmountWOI);
        return transactions;
    }
}
