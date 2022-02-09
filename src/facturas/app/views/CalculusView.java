/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.views;

import facturas.app.utils.ProfitCalculator;
import java.text.DecimalFormat;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import facturas.app.Controller;
import facturas.app.views.FiltersView;
import java.util.Map;

/**
 *
 * @author Lenovo
 */
public class CalculusView extends javax.swing.JFrame {

    /**
     * Creates new form CalculusView
     */
    private Controller controller =new Controller();
    private FiltersView filtersView;
    public CalculusView(FiltersView filtersView) {
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
        totalIvaTaxTextField = new javax.swing.JTextField();
        issuedNetAmountTextField = new javax.swing.JTextField();
        receivedNetAmountTextField = new javax.swing.JTextField();
        totalProfitTaxTextField = new javax.swing.JTextField();
        totalSalesTextField = new javax.swing.JTextField();
        totalBuysTextField = new javax.swing.JTextField();
        totalProfitWOTaxTextField = new javax.swing.JTextField();
        totalProfitWTaxTextField = new javax.swing.JTextField();
        showInDollarsCheckBox = new javax.swing.JCheckBox();
        loadDollarPricesButton = new javax.swing.JButton();
        calculateButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Retenciones");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("Calculos");

        jLabel5.setText("Iva emitido");

        jLabel6.setText("Iva recibido");

        jLabel7.setText("Total retencion iva");

        jLabel8.setText("Importe neto emitido");

        jLabel9.setText("Importe neto recibido");

        jLabel10.setText("Total retencion ganancias");

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

        totalIvaTaxTextField.setEditable(false);
        totalIvaTaxTextField.setBorder(null);

        issuedNetAmountTextField.setEditable(false);
        issuedNetAmountTextField.setBorder(null);
        issuedNetAmountTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                issuedNetAmountTextFieldActionPerformed(evt);
            }
        });

        receivedNetAmountTextField.setEditable(false);
        receivedNetAmountTextField.setBorder(null);
        receivedNetAmountTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                receivedNetAmountTextFieldActionPerformed(evt);
            }
        });

        totalProfitTaxTextField.setEditable(false);
        totalProfitTaxTextField.setBorder(null);

        totalSalesTextField.setEditable(false);
        totalSalesTextField.setBorder(null);
        totalSalesTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalSalesTextFieldActionPerformed(evt);
            }
        });

        totalBuysTextField.setEditable(false);
        totalBuysTextField.setBorder(null);
        totalBuysTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalBuysTextFieldActionPerformed(evt);
            }
        });

        totalProfitWOTaxTextField.setEditable(false);
        totalProfitWOTaxTextField.setBorder(null);
        totalProfitWOTaxTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalProfitWOTaxTextFieldActionPerformed(evt);
            }
        });

        totalProfitWTaxTextField.setEditable(false);
        totalProfitWTaxTextField.setBorder(null);
        totalProfitWTaxTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalProfitWTaxTextFieldActionPerformed(evt);
            }
        });

        showInDollarsCheckBox.setText("Ver en dolares");
        showInDollarsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showInDollarsCheckBoxActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(totalProfitWOTaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(showInDollarsCheckBox)
                        .addGap(118, 118, 118))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(totalIvaTaxTextField))
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
                                .addGap(44, 44, 44)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel1))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(40, 40, 40)
                                        .addComponent(totalSalesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 140, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(loadDollarPricesButton)
                                .addGap(80, 80, 80))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(calculateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(receivedNetAmountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(totalProfitTaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(issuedNetAmountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(198, 198, 198)
                                .addComponent(jLabel4))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalProfitWTaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(totalBuysTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(receivedIvaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(receivedNetAmountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(totalIvaTaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(totalProfitTaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(totalSalesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(calculateButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(totalBuysTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loadDollarPricesButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(totalProfitWOTaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showInDollarsCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(totalProfitWTaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(123, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void totalSalesTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalSalesTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalSalesTextFieldActionPerformed

    private void totalBuysTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalBuysTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalBuysTextFieldActionPerformed

    private void totalProfitWOTaxTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalProfitWOTaxTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalProfitWOTaxTextFieldActionPerformed

    private void totalProfitWTaxTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalProfitWTaxTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalProfitWTaxTextFieldActionPerformed

    private void showInDollarsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showInDollarsCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_showInDollarsCheckBoxActionPerformed

    private void loadDollarPricesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadDollarPricesButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter fileTypes = new FileNameExtensionFilter("CSV Files", "csv");
        chooser.setFileFilter(fileTypes);
        chooser.showOpenDialog(this);
        controller.loadDollarPrices(chooser.getSelectedFile());
    }//GEN-LAST:event_loadDollarPricesButtonActionPerformed

    private void issuedNetAmountTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_issuedNetAmountTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_issuedNetAmountTextFieldActionPerformed

    private void receivedNetAmountTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_receivedNetAmountTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_receivedNetAmountTextFieldActionPerformed

    private void calculateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateButtonActionPerformed
        boolean dollar = showInDollarsCheckBox.isSelected();
        DecimalFormat numberFormat = new DecimalFormat("###,###.00");

        Map<String, Float> values = controller.getProfit(filtersView.getFilters(), dollar);
        String money = dollar ? " USD" : " ARS";
        //iva
        issuedIvaTextField.setText(numberFormat.format(values.get("issuedIva")) + money);
        receivedIvaTextField.setText(numberFormat.format(values.get("receivedIva")) + money);
        totalIvaTaxTextField.setText(numberFormat.format(values.get("totalIva")) + money);
        
        //ganancias
        issuedNetAmountTextField.setText(numberFormat.format(values.get("issuedNetAmount")) + money);
        receivedNetAmountTextField.setText(numberFormat.format(values.get("receivedNetAmount")) + money);
        totalProfitTaxTextField.setText(numberFormat.format(values.get("totalProfitTax")) + money);
        
        //totales
        totalSalesTextField.setText(numberFormat.format(values.get("totlSell")) + money);
        totalBuysTextField.setText(numberFormat.format(values.get("totalBuy")) + money);
        totalProfitWOTaxTextField.setText(numberFormat.format(values.get("profitWOTax")) + money);
        totalProfitWTaxTextField.setText(numberFormat.format(values.get("profitWTax")) + money);        
    }//GEN-LAST:event_calculateButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CalculusView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CalculusView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CalculusView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CalculusView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calculateButton;
    private javax.swing.JTextField issuedIvaTextField;
    private javax.swing.JTextField issuedNetAmountTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    // End of variables declaration//GEN-END:variables
}