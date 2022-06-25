/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.utils;

import java.util.Map;
import java.util.HashMap;

/**
 *Make the sumatory of the totals of the tickets/withholdng you charge in it.
 */
public class Transaction {
    
    private Float netAmountWI;
    private Float totalAmount;
    private Float iva;

    /**
     * Constructor
     */
    public Transaction() {
        this.totalAmount = 0.0f;
        this.iva = 0.0f;
        this.netAmountWI = 0.0f;
    }
    
    /**
     * Constructor
     * @param totalAmount total amount 
     * @param iva total iva
     * @param netAmountWI  total net amount with iva
     */
    public Transaction(Float totalAmount, Float iva, Float netAmountWI) {
        this.totalAmount = totalAmount;
        this.iva = iva;
        this.netAmountWI = netAmountWI;
    }
    
    /**
     * Make a plus with te totals and the currently number saved in parameters
     * @param amount total amount 
     * @param iva total iva
     * @param netAmountWI total of net amuount with iva
     */
    public void addTransaction(Float amount, Float iva, Float netAmountWI) {
        this.totalAmount += amount;
        this.iva += iva;
        this.netAmountWI += netAmountWI;    
    }
    
    /**
     * Make a map with all the totals and return them 
     * @return Map<> with all the totals
     */
    public Map<String, Object> getTransactions() {
        Map<String, Object> transactions = new HashMap<>();
        transactions.put("totalAmount", totalAmount);
        transactions.put("iva", iva);
        transactions.put("netAmountWI", netAmountWI);
        return transactions;
    }
}
