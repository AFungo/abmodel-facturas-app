/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.views;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nacho
 */
public class ColumnSelector extends javax.swing.JFrame {

    /**
     * Creates new form ColumnSelector
     */
    public ColumnSelector() {
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

        ticketLabel = new javax.swing.JLabel();
        dateCheckBox = new javax.swing.JCheckBox();
        typeCheckBox = new javax.swing.JCheckBox();
        noTicketCheckBox = new javax.swing.JCheckBox();
        numberToCheckBox = new javax.swing.JCheckBox();
        authCodeCheckBox = new javax.swing.JCheckBox();
        noCuitCheckBox = new javax.swing.JCheckBox();
        providerNameCheckBox = new javax.swing.JCheckBox();
        changeTypeCheckBox = new javax.swing.JCheckBox();
        netAmountWICheckBox = new javax.swing.JCheckBox();
        netAmountWOICheckBox = new javax.swing.JCheckBox();
        amountImpExCheckBox = new javax.swing.JCheckBox();
        ivaCheckBox = new javax.swing.JCheckBox();
        ticketSectorCheckBox = new javax.swing.JCheckBox();
        totalAmountCheckBox = new javax.swing.JCheckBox();
        providerLabel = new javax.swing.JLabel();
        cuitCheckBox = new javax.swing.JCheckBox();
        nameCheckBox = new javax.swing.JCheckBox();
        docTypeCheckBox = new javax.swing.JCheckBox();
        directionCheckBox = new javax.swing.JCheckBox();
        providerSectorCheckBox = new javax.swing.JCheckBox();
        applyButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ticketLabel.setText("Comprobante");

        dateCheckBox.setSelected(true);
        dateCheckBox.setText("Fecha");

        typeCheckBox.setSelected(true);
        typeCheckBox.setText("Tipo");

        noTicketCheckBox.setSelected(true);
        noTicketCheckBox.setText("Nro factura");

        numberToCheckBox.setSelected(true);
        numberToCheckBox.setText("Numero hasta");

        authCodeCheckBox.setSelected(true);
        authCodeCheckBox.setText("Cód. Autorización");

        noCuitCheckBox.setSelected(true);
        noCuitCheckBox.setText("Nro. Doc. Emisor");

        providerNameCheckBox.setSelected(true);
        providerNameCheckBox.setText("Denominación Emisor");

        changeTypeCheckBox.setSelected(true);
        changeTypeCheckBox.setText("Tipo Cambio");

        netAmountWICheckBox.setSelected(true);
        netAmountWICheckBox.setText("Imp. Neto Gravado");

        netAmountWOICheckBox.setSelected(true);
        netAmountWOICheckBox.setText("Imp. Neto No Gravado");

        amountImpExCheckBox.setSelected(true);
        amountImpExCheckBox.setText("Imp. Op. Exentas");

        ivaCheckBox.setSelected(true);
        ivaCheckBox.setText("IVA");

        ticketSectorCheckBox.setSelected(true);
        ticketSectorCheckBox.setText("Rubro");

        totalAmountCheckBox.setSelected(true);
        totalAmountCheckBox.setText("Imp. Total");

        providerLabel.setText("Proveedor");

        cuitCheckBox.setSelected(true);
        cuitCheckBox.setText("CUIT");

        nameCheckBox.setSelected(true);
        nameCheckBox.setText("Nombre");

        docTypeCheckBox.setSelected(true);
        docTypeCheckBox.setText("Tipo de documento");

        directionCheckBox.setSelected(true);
        directionCheckBox.setText("Dirección");

        providerSectorCheckBox.setSelected(true);
        providerSectorCheckBox.setText("Rubro");

        applyButton.setText("Aplicar");
        applyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dateCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(typeCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(noTicketCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numberToCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(authCodeCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(noCuitCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(providerNameCheckBox))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(changeTypeCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(netAmountWICheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(netAmountWOICheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(amountImpExCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ivaCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalAmountCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ticketSectorCheckBox))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ticketLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(cuitCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nameCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(docTypeCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(directionCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(providerSectorCheckBox))
                            .addComponent(providerLabel))))
                .addContainerGap(78, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(applyButton)
                .addGap(138, 138, 138))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(ticketLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateCheckBox)
                    .addComponent(typeCheckBox)
                    .addComponent(noTicketCheckBox)
                    .addComponent(numberToCheckBox)
                    .addComponent(authCodeCheckBox)
                    .addComponent(noCuitCheckBox)
                    .addComponent(providerNameCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(changeTypeCheckBox)
                    .addComponent(netAmountWICheckBox)
                    .addComponent(netAmountWOICheckBox)
                    .addComponent(amountImpExCheckBox)
                    .addComponent(ivaCheckBox)
                    .addComponent(totalAmountCheckBox)
                    .addComponent(ticketSectorCheckBox))
                .addGap(30, 30, 30)
                .addComponent(providerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cuitCheckBox)
                    .addComponent(nameCheckBox)
                    .addComponent(docTypeCheckBox)
                    .addComponent(directionCheckBox)
                    .addComponent(providerSectorCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                .addComponent(applyButton)
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void applyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_applyButtonActionPerformed

    public Map<String, Boolean> getTicketSelectedColumns() {
        Map<String, Boolean> dict = new HashMap<>();
        
        dict.put("date", dateCheckBox.isSelected());
        dict.put("type", typeCheckBox.isSelected());
        dict.put("noTicket", noTicketCheckBox.isSelected());
        dict.put("numberTo", numberToCheckBox.isSelected());
        dict.put("authCode", authCodeCheckBox.isSelected());
        dict.put("noCuit", noCuitCheckBox.isSelected());
        dict.put("providerName", providerNameCheckBox.isSelected());
        dict.put("changeType", changeTypeCheckBox.isSelected());
        dict.put("netAmountWI", netAmountWICheckBox.isSelected());
        dict.put("netAmountWOI", netAmountWOICheckBox.isSelected());
        dict.put("amountImpEx", amountImpExCheckBox.isSelected());
        dict.put("iva", ivaCheckBox.isSelected());
        dict.put("totalAmount", totalAmountCheckBox.isSelected());
        dict.put("ticketSector", ticketSectorCheckBox.isSelected());
        
        return dict;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox amountImpExCheckBox;
    private javax.swing.JButton applyButton;
    private javax.swing.JCheckBox authCodeCheckBox;
    private javax.swing.JCheckBox changeTypeCheckBox;
    private javax.swing.JCheckBox cuitCheckBox;
    private javax.swing.JCheckBox dateCheckBox;
    private javax.swing.JCheckBox directionCheckBox;
    private javax.swing.JCheckBox docTypeCheckBox;
    private javax.swing.JCheckBox ivaCheckBox;
    private javax.swing.JCheckBox nameCheckBox;
    private javax.swing.JCheckBox netAmountWICheckBox;
    private javax.swing.JCheckBox netAmountWOICheckBox;
    private javax.swing.JCheckBox noCuitCheckBox;
    private javax.swing.JCheckBox noTicketCheckBox;
    private javax.swing.JCheckBox numberToCheckBox;
    private javax.swing.JLabel providerLabel;
    private javax.swing.JCheckBox providerNameCheckBox;
    private javax.swing.JCheckBox providerSectorCheckBox;
    private javax.swing.JLabel ticketLabel;
    private javax.swing.JCheckBox ticketSectorCheckBox;
    private javax.swing.JCheckBox totalAmountCheckBox;
    private javax.swing.JCheckBox typeCheckBox;
    // End of variables declaration//GEN-END:variables
}
