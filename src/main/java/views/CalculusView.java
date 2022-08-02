/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.text.DecimalFormat;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import calculations.DollarPriceManager;
import controller.Controller;
import filters.Filter;
import utils.Pair;
import calculations.PricesList;
import views.utils.ViewUtils;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Fungo
 */
public class CalculusView extends javax.swing.JFrame {

    /**
     * Creates new form CalculusView
     */
    public CalculusView(FiltersView filtersView) {
        controller = new Controller();
        this.filtersView = filtersView;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        new javax.swing.JOptionPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        issuedIvaTextField = new javax.swing.JTextField();
        receivedIvaTextField = new javax.swing.JTextField();
        totalWithheldIvaTextField = new javax.swing.JTextField();
        issuedNetAmountTextField = new javax.swing.JTextField();
        receivedNetAmountTextField = new javax.swing.JTextField();
        totalWithheldProfitTextField = new javax.swing.JTextField();
        totalSalesTextField = new javax.swing.JTextField();
        totalBuysTextField = new javax.swing.JTextField();
        totalProfitWOTaxTextField = new javax.swing.JTextField();
        totalProfitWTaxTextField = new javax.swing.JTextField();
        showInDollarsCheckBox = new javax.swing.JCheckBox();
        loadDollarPricesButton = new javax.swing.JButton();
        calculateButton = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        grossMarginTextField = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        totalProfitTaxTextField = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        totalIvaTaxTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("OPERACIONES");
        setIconImage(new ImageIcon(getClass().getResource("/images/icono-facturas-app-opcion-dos.png")).getImage());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Retenciones");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("Calculos");

        jLabel5.setText("Iva ventas");

        jLabel6.setText("Iva compra");

        jLabel7.setText("Total retenido iva");

        jLabel8.setText("Importe neto venta");

        jLabel9.setText("Importe neto compra");

        jLabel10.setText("Total retenido gananacias");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Totales");

        jLabel3.setText("Compras totales");

        jLabel11.setText("Total sin retenciones");

        jLabel12.setText("Total con retenciones");

        jLabel13.setText("Ventas totales");

        issuedIvaTextField.setEditable(false);
        issuedIvaTextField.setBorder(null);

        receivedIvaTextField.setEditable(false);
        receivedIvaTextField.setBorder(null);

        totalWithheldIvaTextField.setEditable(false);
        totalWithheldIvaTextField.setBorder(null);

        issuedNetAmountTextField.setEditable(false);
        issuedNetAmountTextField.setBorder(null);

        receivedNetAmountTextField.setEditable(false);
        receivedNetAmountTextField.setBorder(null);

        totalWithheldProfitTextField.setEditable(false);
        totalWithheldProfitTextField.setBorder(null);

        totalSalesTextField.setEditable(false);
        totalSalesTextField.setBorder(null);

        totalBuysTextField.setEditable(false);
        totalBuysTextField.setBorder(null);

        totalProfitWOTaxTextField.setEditable(false);
        totalProfitWOTaxTextField.setBorder(null);

        totalProfitWTaxTextField.setEditable(false);
        totalProfitWTaxTextField.setBorder(null);

        showInDollarsCheckBox.setText("Ver en dolares");

        loadDollarPricesButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        loadDollarPricesButton.setText("Cargar cotizacion");
        loadDollarPricesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadDollarPricesButtonActionPerformed(evt);
            }
        });

        calculateButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        calculateButton.setText("Calcular");
        calculateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateButtonActionPerformed(evt);
            }
        });

        jLabel14.setText("Margen Bruto");

        grossMarginTextField.setEditable(false);
        grossMarginTextField.setBorder(null);

        jLabel15.setText("Total ganancias");

        totalProfitTaxTextField.setEditable(false);
        totalProfitTaxTextField.setBorder(null);

        jLabel16.setText("Total iva");

        totalIvaTaxTextField.setEditable(false);
        totalIvaTaxTextField.setBorder(null);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalProfitWTaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 399, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(109, 109, 109)
                                .addComponent(totalBuysTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(totalProfitWOTaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(40, 40, 40)
                                        .addComponent(totalSalesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(grossMarginTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel1)))
                                .addGap(0, 313, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(receivedNetAmountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(issuedNetAmountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(totalWithheldIvaTextField))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel5)
                                                    .addComponent(jLabel6))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGap(37, 37, 37)
                                                        .addComponent(receivedIvaTextField))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGap(39, 39, 39)
                                                        .addComponent(issuedIvaTextField)))))
                                        .addGap(44, 44, 44))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(totalIvaTaxTextField)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel15)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(20, 20, 20)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(totalWithheldProfitTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(totalProfitTaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(78, 78, 78))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(212, 212, 212)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(showInDollarsCheckBox)
                                .addGap(72, 72, 72))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(loadDollarPricesButton)
                                    .addGap(34, 34, 34))
                                .addComponent(calculateButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(267, 267, 267)
                        .addComponent(jLabel4)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8)
                    .addComponent(issuedIvaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(issuedNetAmountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(receivedNetAmountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(receivedIvaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(totalWithheldIvaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(totalWithheldProfitTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(totalIvaTaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(totalProfitTaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jLabel2)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(totalSalesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(grossMarginTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(totalBuysTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(totalProfitWOTaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(totalProfitWTaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(calculateButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loadDollarPricesButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showInDollarsCheckBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /*
    * this method open a window where you can load a dollar prices in the program
    */
    private void loadDollarPricesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadDollarPricesButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter fileTypes = new FileNameExtensionFilter("CSV Files", "csv");
        chooser.setFileFilter(fileTypes);
        chooser.showOpenDialog(this);
        controller.loadDollarPricesFromFile(chooser.getSelectedFile());
    }//GEN-LAST:event_loadDollarPricesButtonActionPerformed

    private void calculateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateButtonActionPerformed
        boolean inDollar = showInDollarsCheckBox.isSelected();
        DecimalFormat numberFormat = new DecimalFormat("###,###.00");
        PricesList pricesList;
        Filter[] ticketFilters = filtersView.getFilters().toArray(new Filter[0]);
        //this must be remade
        //Filter withholdingFilter = FilterUtils.separateWithholdingSpecialFilter(ticketFilter);
        try {
            pricesList = new PricesList(inDollar);
            pricesList.calculateSummary(controller.getTickets(ticketFilters),
                    controller.getWithholdings(ticketFilters));
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(null, "No hay valores del dolar cargados, por favor cargue y vuelva a intentar", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return ;
        }
    
        Map<String,Float> values = pricesList.getValues();
        List<Pair<Date,String>> missingPrices = pricesList.getMissingPrices();
        if (!missingPrices.isEmpty()) {
            JTable pricesTable = ViewUtils.createMissingPricesTable(missingPrices);
            int daysLimit = DollarPriceManager.getDaysRoundLimit();
            JOptionPane.showMessageDialog(null, new JScrollPane(pricesTable), 
                "Las siguientes fechas exceden el limite de " + daysLimit +
                " dias para redondear el dolar", JOptionPane.WARNING_MESSAGE);
        }
        
        String money = inDollar ? " USD" : " ARS";
        
        //iva
        issuedIvaTextField.setText(numberFormat.format(values.get("issuedIva")) + money);
        receivedIvaTextField.setText(numberFormat.format(values.get("receivedIva")) + money);
        totalWithheldIvaTextField.setText(numberFormat.format(values.get("withheldIva")) + money);
        totalIvaTaxTextField.setText(numberFormat.format(values.get("totalIva")) + money);
        //withholding
        issuedNetAmountTextField.setText(numberFormat.format(values.get("issuedNetAmount")) + money);
        receivedNetAmountTextField.setText(numberFormat.format(values.get("receivedNetAmount")) + money);
        totalWithheldProfitTextField.setText(numberFormat.format(values.get("withheldProfit")) + money);
        totalProfitTaxTextField.setText(numberFormat.format(values.get("totalProfitTax")) + money);
        
        //total
        totalSalesTextField.setText(numberFormat.format(values.get("totlSell")) + money);
        totalBuysTextField.setText(numberFormat.format(values.get("totalBuy")) + money);
        totalProfitWOTaxTextField.setText(numberFormat.format(values.get("profitWOTax")) + money);
        totalProfitWTaxTextField.setText(numberFormat.format(values.get("profitWTax")) + money);        
        grossMarginTextField.setText(numberFormat.format(values.get("grossMargin")) + money);
        // TODO add your handling code here:
    }//GEN-LAST:event_calculateButtonActionPerformed
    /*
     * This method show all the calculus and data who bring profit calculator
     */
  
    
    private Controller controller;
    private FiltersView filtersView;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calculateButton;
    private javax.swing.JTextField grossMarginTextField;
    private javax.swing.JTextField issuedIvaTextField;
    private javax.swing.JTextField issuedNetAmountTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton loadDollarPricesButton;
    private javax.swing.JTextField receivedIvaTextField;
    private javax.swing.JTextField receivedNetAmountTextField;
    private javax.swing.JCheckBox showInDollarsCheckBox;
    private javax.swing.JTextField totalBuysTextField;
    private javax.swing.JTextField totalIvaTaxTextField;
    private javax.swing.JTextField totalProfitTaxTextField;
    private javax.swing.JTextField totalProfitWOTaxTextField;
    private javax.swing.JTextField totalProfitWTaxTextField;
    private javax.swing.JTextField totalSalesTextField;
    private javax.swing.JTextField totalWithheldIvaTextField;
    private javax.swing.JTextField totalWithheldProfitTextField;
    // End of variables declaration//GEN-END:variables
}
