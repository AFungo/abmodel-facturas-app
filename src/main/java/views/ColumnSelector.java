/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import utils.ConfigManager;
import java.util.Map;
import javax.swing.JTable;

/**
 *
 * @author nacho
 */
public class ColumnSelector extends javax.swing.JFrame {

    /**
     * Creates new form ColumnSelector
     * @param ticketsTable
     * @param providersTable
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ticketLabel = new javax.swing.JLabel();
        dateCheckBox = new javax.swing.JCheckBox();
        typeCheckBox = new javax.swing.JCheckBox();
        noTicketCheckBox = new javax.swing.JCheckBox();
        numberToCheckBox = new javax.swing.JCheckBox();
        authCodeCheckBox = new javax.swing.JCheckBox();
        providerDocCheckBox = new javax.swing.JCheckBox();
        providerNameCheckBox = new javax.swing.JCheckBox();
        changeTypeCheckBox = new javax.swing.JCheckBox();
        netAmountWICheckBox = new javax.swing.JCheckBox();
        netAmountWOICheckBox = new javax.swing.JCheckBox();
        amountImpExCheckBox = new javax.swing.JCheckBox();
        ivaCheckBox = new javax.swing.JCheckBox();
        ticketSectorCheckBox = new javax.swing.JCheckBox();
        totalAmountCheckBox = new javax.swing.JCheckBox();
        providerLabel = new javax.swing.JLabel();
        docNoCheckBox = new javax.swing.JCheckBox();
        nameCheckBox = new javax.swing.JCheckBox();
        docTypeCheckBox = new javax.swing.JCheckBox();
        directionCheckBox = new javax.swing.JCheckBox();
        providerSectorCheckBox = new javax.swing.JCheckBox();
        applyButton = new javax.swing.JButton();
        aliasCheckBox = new javax.swing.JCheckBox();
        purchaseNSalesCheckBox = new javax.swing.JCheckBox();
        deliveredCheckBox = new javax.swing.JCheckBox();
        idCheckBox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("COLUMNAS");
        //setIconImage(new ImageIcon(getClass().getResource("/IMG/icono-facturas-app-opcion-dos.png")).getImage());
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

        providerDocCheckBox.setSelected(config.get("providerDoc"));
        providerDocCheckBox.setText("Nro. Doc. Emisor");

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

        docNoCheckBox.setSelected(config.get("docNo")
        );
        docNoCheckBox.setText("CUIT");

        nameCheckBox.setSelected(config.get("name"));
        nameCheckBox.setText("Nombre");

        docTypeCheckBox.setSelected(config.get("docType"));
        docTypeCheckBox.setText("Tipo de documento");

        directionCheckBox.setSelected(config.get("direction"));
        directionCheckBox.setText("Dirección");

        providerSectorCheckBox.setSelected(config.get("providerSector"));
        providerSectorCheckBox.setText("Rubro");

        applyButton.setText("Aplicar");
        applyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyButtonActionPerformed(evt);
            }
        });

        aliasCheckBox.setSelected(config.get("alias"));
        aliasCheckBox.setText("Alias");

        purchaseNSalesCheckBox.setSelected(config.get("purchaseNSales"));
        purchaseNSalesCheckBox.setText("Compra/Venta");

        deliveredCheckBox.setSelected(config.get("delivered"));
        deliveredCheckBox.setText("Env. contador");

        idCheckBox.setSelected(config.get("id"));
        idCheckBox.setText("Id");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(applyButton)
                .addGap(138, 138, 138))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(providerNameCheckBox)
                                .addGap(3, 3, 3)
                                .addComponent(changeTypeCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(netAmountWICheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(netAmountWOICheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(amountImpExCheckBox))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ivaCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalAmountCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ticketSectorCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(purchaseNSalesCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deliveredCheckBox))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(idCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                                .addComponent(providerDocCheckBox))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ticketLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(providerLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(docNoCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nameCheckBox)
                                .addGap(10, 10, 10)
                                .addComponent(aliasCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(docTypeCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(directionCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(providerSectorCheckBox)))))
                .addContainerGap(136, Short.MAX_VALUE))
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
                    .addComponent(providerDocCheckBox)
                    .addComponent(idCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(changeTypeCheckBox)
                    .addComponent(netAmountWICheckBox)
                    .addComponent(netAmountWOICheckBox)
                    .addComponent(amountImpExCheckBox)
                    .addComponent(providerNameCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(purchaseNSalesCheckBox)
                    .addComponent(deliveredCheckBox)
                    .addComponent(ticketSectorCheckBox)
                    .addComponent(totalAmountCheckBox)
                    .addComponent(ivaCheckBox))
                .addGap(16, 16, 16)
                .addComponent(providerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(docNoCheckBox)
                    .addComponent(nameCheckBox)
                    .addComponent(docTypeCheckBox)
                    .addComponent(directionCheckBox)
                    .addComponent(providerSectorCheckBox)
                    .addComponent(aliasCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
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
        config.put("providerDoc", providerDocCheckBox.isSelected());
        config.put("providerName", providerNameCheckBox.isSelected());
        config.put("changeType", changeTypeCheckBox.isSelected());
        config.put("netAmountWI", netAmountWICheckBox.isSelected());
        config.put("netAmountWOI", netAmountWOICheckBox.isSelected());
        config.put("amountImpEx", amountImpExCheckBox.isSelected());
        config.put("iva", ivaCheckBox.isSelected());
        config.put("totalAmount", totalAmountCheckBox.isSelected());
        config.put("ticketSector", ticketSectorCheckBox.isSelected());
        config.put("purchaseNSales", purchaseNSalesCheckBox.isSelected());
        config.put("delivered", deliveredCheckBox.isSelected());
    }
    
    private void updateProviderConfig() {
        config.put("providerDoc", docNoCheckBox.isSelected());
        config.put("name", nameCheckBox.isSelected());
        config.put("docType", docTypeCheckBox.isSelected());
        config.put("direction", directionCheckBox.isSelected());
        config.put("providerSector", providerSectorCheckBox.isSelected());
        config.put("alias", aliasCheckBox.isSelected());
    }
    
    public boolean[] getTicketSelectedColumns() {
        boolean[] columns = new boolean [17];
        
        int i = 0;
        columns[i++] = idCheckBox.isSelected();
        columns[i++] = dateCheckBox.isSelected();
        columns[i++] = typeCheckBox.isSelected();
        columns[i++] = noTicketCheckBox.isSelected();
        columns[i++] = numberToCheckBox.isSelected();
        columns[i++] = authCodeCheckBox.isSelected();
        columns[i++] = providerDocCheckBox.isSelected();
        columns[i++] = providerNameCheckBox.isSelected();
        columns[i++] = changeTypeCheckBox.isSelected();
        columns[i++] = netAmountWICheckBox.isSelected();
        columns[i++] = netAmountWOICheckBox.isSelected();
        columns[i++] = amountImpExCheckBox.isSelected();
        columns[i++] = ivaCheckBox.isSelected();
        columns[i++] = totalAmountCheckBox.isSelected();
        columns[i++] = ticketSectorCheckBox.isSelected();
        columns[i++] = purchaseNSalesCheckBox.isSelected();
        columns[i++] = deliveredCheckBox.isSelected();
        
        return columns;
    }
    
    public boolean[] getProviderSelectedColumns() {
        boolean[] columns = new boolean [6];
        
        int i = 0;
        columns[i++] = docNoCheckBox.isSelected();
        columns[i++] = nameCheckBox.isSelected();
        columns[i++] = aliasCheckBox.isSelected();
        columns[i++] = docTypeCheckBox.isSelected();
        columns[i++] = directionCheckBox.isSelected();
        columns[i++] = providerSectorCheckBox.isSelected();
        
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
    private javax.swing.JCheckBox changeTypeCheckBox;
    private javax.swing.JCheckBox dateCheckBox;
    private javax.swing.JCheckBox deliveredCheckBox;
    private javax.swing.JCheckBox directionCheckBox;
    private javax.swing.JCheckBox docNoCheckBox;
    private javax.swing.JCheckBox docTypeCheckBox;
    private javax.swing.JCheckBox idCheckBox;
    private javax.swing.JCheckBox ivaCheckBox;
    private javax.swing.JCheckBox nameCheckBox;
    private javax.swing.JCheckBox netAmountWICheckBox;
    private javax.swing.JCheckBox netAmountWOICheckBox;
    private javax.swing.JCheckBox noTicketCheckBox;
    private javax.swing.JCheckBox numberToCheckBox;
    private javax.swing.JCheckBox providerDocCheckBox;
    private javax.swing.JLabel providerLabel;
    private javax.swing.JCheckBox providerNameCheckBox;
    private javax.swing.JCheckBox providerSectorCheckBox;
    private javax.swing.JCheckBox purchaseNSalesCheckBox;
    private javax.swing.JLabel ticketLabel;
    private javax.swing.JCheckBox ticketSectorCheckBox;
    private javax.swing.JCheckBox totalAmountCheckBox;
    private javax.swing.JCheckBox typeCheckBox;
    // End of variables declaration//GEN-END:variables
}
