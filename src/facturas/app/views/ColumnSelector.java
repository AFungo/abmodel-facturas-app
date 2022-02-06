/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.views;

import facturas.app.utils.ConfigManager;
import java.util.Map;
import javax.swing.JTable;

/**
 *
 * @author nacho
 */
public class ColumnSelector extends javax.swing.JFrame {

    /**
     * Creates new form ColumnSelector
     */
    public ColumnSelector(JTable ticketsTable, JTable providersTable) {
         
        this.ticketsTable = ticketsTable;
        this.providersTable = providersTable;
        this.config = ConfigManager.readConfig();
        initComponents();
        applyButtonActionPerformed(null);
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
        aliasCheckBox = new javax.swing.JCheckBox();
        buyNSellCheckBox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("COLUMNAS");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        ticketLabel.setText("Comprobante");

        dateCheckBox.setSelected(config.get("date"));
        dateCheckBox.setText("Fecha");

        typeCheckBox.setSelected(config.get("type"));
        typeCheckBox.setText("Tipo");

        noTicketCheckBox.setSelected(config.get("noTicket"));
        noTicketCheckBox.setText("Nro factura");

        numberToCheckBox.setSelected(config.get("numberTo"));
        numberToCheckBox.setText("Numero hasta");

        authCodeCheckBox.setSelected(config.get("authCode"));
        authCodeCheckBox.setText("Cód. Autorización");

        noCuitCheckBox.setSelected(config.get("providerDoc"));
        noCuitCheckBox.setText("Nro. Doc. Emisor");

        providerNameCheckBox.setSelected(config.get("providerName"));
        providerNameCheckBox.setText("Denominación Emisor");

        changeTypeCheckBox.setSelected(config.get("changeType"));
        changeTypeCheckBox.setText("Tipo Cambio");

        netAmountWICheckBox.setSelected(config.get("netAmountWI"));
        netAmountWICheckBox.setText("Imp. Neto Gravado");

        netAmountWOICheckBox.setSelected(config.get("netAmountWOI"));
        netAmountWOICheckBox.setText("Imp. Neto No Gravado");

        amountImpExCheckBox.setSelected(config.get("amountImpEx"));
        amountImpExCheckBox.setText("Imp. Op. Exentas");

        ivaCheckBox.setSelected(config.get("iva"));
        ivaCheckBox.setText("IVA");

        ticketSectorCheckBox.setSelected(config.get("ticketSector"));
        ticketSectorCheckBox.setText("Rubro");

        totalAmountCheckBox.setSelected(config.get("totalAmount"));
        totalAmountCheckBox.setText("Imp. Total");

        providerLabel.setText("Proveedor");

        cuitCheckBox.setSelected(config.get("docNo"));
        cuitCheckBox.setText("Nro. Documento");

        nameCheckBox.setSelected(config.get("name"));
        nameCheckBox.setText("Nombre");

        docTypeCheckBox.setSelected(config.get("docType"));
        docTypeCheckBox.setText("Tipo de documento");

        directionCheckBox.setSelected(config.get("direction"));
        directionCheckBox.setText("Dirección");

        providerSectorCheckBox.setSelected(config.get("providerSector"));
        providerSectorCheckBox.setText("Rubro");

        aliasCheckBox.setSelected(config.get("alias"));
        aliasCheckBox.setText("Alias");

        buyNSellCheckBox.setSelected(config.get("buyNSell"));
        buyNSellCheckBox.setText("Compra/Venta");

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
                                .addGap(10, 10, 10)
                                .addComponent(aliasCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(docTypeCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(directionCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(providerSectorCheckBox))
                            .addComponent(providerLabel)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buyNSellCheckBox)
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
                                .addComponent(ticketSectorCheckBox))
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
                                .addComponent(providerNameCheckBox)))))
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
                .addGap(3, 3, 3)
                .addComponent(buyNSellCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(providerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cuitCheckBox)
                    .addComponent(nameCheckBox)
                    .addComponent(docTypeCheckBox)
                    .addComponent(directionCheckBox)
                    .addComponent(providerSectorCheckBox)
                    .addComponent(aliasCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                .addComponent(applyButton)
                .addGap(28, 28, 28))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void applyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyButtonActionPerformed
        boolean[] ticketColumns = getTicketSelectedColumns();
        boolean[] providerColumns = getProviderSelectedColumns();
        applyChanges(ticketsTable, ticketColumns);
        updateTicketConfig();
        applyChanges(providersTable, providerColumns);
        updateProviderConfig();
    }//GEN-LAST:event_applyButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        ConfigManager.saveConfig(config);
    }//GEN-LAST:event_formWindowClosing

    private void applyChanges(JTable model, boolean[] columns) {
        for (int i = columns.length - 1; 0 <= i; i--) {
            if (columns[i]) {
                unhideColumn(i, model);
            } else {
                hideColumn(i, model);
            }
        }
    }
    
    private void unhideColumn(int i, JTable model) {
        model.getColumnModel().getColumn(i).setMinWidth(0);
        model.getColumnModel().getColumn(i).setMaxWidth(500);
        model.getColumnModel().getColumn(i).setWidth(300);
    }
    
    private void hideColumn(int i, JTable model) {
        model.getColumnModel().getColumn(i).setMinWidth(0);
        model.getColumnModel().getColumn(i).setMaxWidth(0);
        model.getColumnModel().getColumn(i).setWidth(0);
    }
    
    private void updateTicketConfig() {
        config.put("date", dateCheckBox.isSelected());
        config.put("type", typeCheckBox.isSelected());
        config.put("noTicket", noTicketCheckBox.isSelected());
        config.put("numberTo", numberToCheckBox.isSelected());
        config.put("authCode", authCodeCheckBox.isSelected());
        config.put("providerDoc", noCuitCheckBox.isSelected());
        config.put("providerName", providerNameCheckBox.isSelected());
        config.put("changeType", changeTypeCheckBox.isSelected());
        config.put("netAmountWI", netAmountWICheckBox.isSelected());
        config.put("netAmountWOI", netAmountWOICheckBox.isSelected());
        config.put("amountImpEx", amountImpExCheckBox.isSelected());
        config.put("iva", ivaCheckBox.isSelected());
        config.put("totalAmount", totalAmountCheckBox.isSelected());
        config.put("ticketSector", ticketSectorCheckBox.isSelected());
        config.put("buyNSell", buyNSellCheckBox.isSelected());
    }
    
    private void updateProviderConfig() {
        config.put("providerDoc", cuitCheckBox.isSelected());
        config.put("name", nameCheckBox.isSelected());
        config.put("docType", docTypeCheckBox.isSelected());
        config.put("direction", directionCheckBox.isSelected());
        config.put("providerSector", providerSectorCheckBox.isSelected());
        config.put("alias", aliasCheckBox.isSelected());
    }
    
    public boolean[] getTicketSelectedColumns() {
        boolean[] columns = new boolean [15];
        
        columns[0] = dateCheckBox.isSelected();
        columns[1] = typeCheckBox.isSelected();
        columns[2] = noTicketCheckBox.isSelected();
        columns[3] = numberToCheckBox.isSelected();
        columns[4] = authCodeCheckBox.isSelected();
        columns[5] = noCuitCheckBox.isSelected();
        columns[6] = providerNameCheckBox.isSelected();
        columns[7] = changeTypeCheckBox.isSelected();
        columns[8] = netAmountWICheckBox.isSelected();
        columns[9] = netAmountWOICheckBox.isSelected();
        columns[10] = amountImpExCheckBox.isSelected();
        columns[11] = ivaCheckBox.isSelected();
        columns[12] = totalAmountCheckBox.isSelected();
        columns[13] = ticketSectorCheckBox.isSelected();
        columns[14] = buyNSellCheckBox.isSelected();
        
        return columns;
    }
    
    public boolean[] getProviderSelectedColumns() {
        boolean[] columns = new boolean [6];
        
        columns[0] = cuitCheckBox.isSelected();
        columns[1] = nameCheckBox.isSelected();
        columns[2] = aliasCheckBox.isSelected();
        columns[3] = docTypeCheckBox.isSelected();
        columns[4] = directionCheckBox.isSelected();
        columns[5] = providerSectorCheckBox.isSelected();
        
        return columns;
    }
    
    private JTable providersTable;
    private JTable ticketsTable;
    private Map<String, Boolean> config;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox aliasCheckBox;
    private javax.swing.JCheckBox amountImpExCheckBox;
    private javax.swing.JButton applyButton;
    private javax.swing.JCheckBox authCodeCheckBox;
    private javax.swing.JCheckBox buyNSellCheckBox;
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
