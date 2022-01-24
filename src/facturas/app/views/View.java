/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.views;

import facturas.app.Controller;
import facturas.app.models.Provider;
import facturas.app.models.Ticket;
import facturas.app.utils.Formater;
import facturas.app.utils.ProfitCalculator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Agustin
 */
public class View extends javax.swing.JFrame {

    public Controller controller;

    /**
     * Creates new form MainWindow
     *
     * @param controller
     */
    public View(Controller controller) {
        this.controller = controller;
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

        addVouchers = new javax.swing.JButton();
        vouchersTableScroll = new javax.swing.JScrollPane();
        vouchersTable = new javax.swing.JTable(new DefaultTableModel(new Object[]{"Fecha","Tipo","Nro factura","Número Hasta","Cód. Autorización","Nro. Doc. Emisor","Denominación Emisor","Tipo Cambio","Imp. Neto Gravado","Imp. Neto No Gravado","Imp. Op. Exentas","IVA","Imp. Total"}, 0));
        total = new javax.swing.JTextField();
        ivaTaxTextField = new javax.swing.JTextField();
        calculateButton = new javax.swing.JButton();
        showCompanies = new javax.swing.JButton();
        showVouchers = new javax.swing.JButton();
        startDate = new javax.swing.JTextField();
        finishDate = new javax.swing.JTextField();
        labelDate = new javax.swing.JLabel();
        maxTotal = new javax.swing.JTextField();
        labelImpTotal = new javax.swing.JLabel();
        minTotal = new javax.swing.JTextField();
        maxIva = new javax.swing.JTextField();
        labelTicketType = new javax.swing.JLabel();
        minIva = new javax.swing.JTextField();
        labelIva = new javax.swing.JLabel();
        ticketTypesListScroll = new javax.swing.JScrollPane();
        ticketTypesList = new javax.swing.JList<>();
        profitTax = new javax.swing.JTextField();
        ivaTaxLabel = new javax.swing.JTextField();
        profitTaxLabel = new javax.swing.JTextField();
        totalLabel = new javax.swing.JTextField();
        labelCompanyCuit = new javax.swing.JLabel();
        companyCuit = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        addVouchers.setText("Agregar Comprobantes");
        addVouchers.setPreferredSize(new java.awt.Dimension(150, 50));
        addVouchers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addVouchersActionPerformed(evt);
            }
        });

        vouchersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Tipo", "Nro factura", "Numero hasta", "Cód. Autorización", "Nro. Doc. Emisor", "Denominación Emisor", "Tipo Cambio", "Imp. Neto Gravado", "Imp. Neto No Gravado", "Imp. Op. Exentas", "IVA", "Imp. Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        vouchersTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        vouchersTable.getTableHeader().setReorderingAllowed(false);
        vouchersTableScroll.setViewportView(vouchersTable);
        if (vouchersTable.getColumnModel().getColumnCount() > 0) {
            vouchersTable.getColumnModel().getColumn(0).setPreferredWidth(2);
            vouchersTable.getColumnModel().getColumn(1).setPreferredWidth(3);
        }

        total.setEditable(false);
        total.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        total.setBorder(null);
        total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalActionPerformed(evt);
            }
        });

        ivaTaxTextField.setEditable(false);
        ivaTaxTextField.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ivaTaxTextField.setBorder(null);

        calculateButton.setText("Calcular");
        calculateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateButtonActionPerformed(evt);
            }
        });

        showCompanies.setText("Mostrar proveedores");
        showCompanies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showCompaniesActionPerformed(evt);
            }
        });

        showVouchers.setText("Mostrar comprobantes");
        showVouchers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showVouchersActionPerformed(evt);
            }
        });

        startDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startDateActionPerformed(evt);
            }
        });

        labelDate.setText("< Fecha <");

        labelImpTotal.setText("< imp. total <");

        labelTicketType.setText("Tipo comprobante:");

        labelIva.setText("< iva <");

        ticketTypesList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "6 - Factura B", "1 - Factura A", "11 - Factura C", "3 - Nota de Crédito A", "2 - Nota de Débito A", "13 - Nota de Crédito C" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        ticketTypesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        ticketTypesListScroll.setViewportView(ticketTypesList);

        profitTax.setEditable(false);
        profitTax.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        profitTax.setBorder(null);
        profitTax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profitTaxActionPerformed(evt);
            }
        });

        ivaTaxLabel.setEditable(false);
        ivaTaxLabel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ivaTaxLabel.setText("Iva:");
        ivaTaxLabel.setBorder(null);
        ivaTaxLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ivaTaxLabelActionPerformed(evt);
            }
        });

        profitTaxLabel.setEditable(false);
        profitTaxLabel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        profitTaxLabel.setText("Ganancias:");
        profitTaxLabel.setBorder(null);

        totalLabel.setEditable(false);
        totalLabel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        totalLabel.setText("Total:");
        totalLabel.setBorder(null);

        labelCompanyCuit.setText("cuit provedor =");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(minTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelDate)
                                .addGap(12, 12, 12)
                                .addComponent(finishDate, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelImpTotal)
                                .addGap(12, 12, 12)
                                .addComponent(maxTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(minIva, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(12, 12, 12)
                            .addComponent(labelIva)
                            .addGap(12, 12, 12)
                            .addComponent(maxIva))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(labelCompanyCuit)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(companyCuit, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelTicketType)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ticketTypesListScroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(showCompanies)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(showVouchers, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addVouchers, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(80, 80, 80)
                                .addComponent(calculateButton)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ivaTaxLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(totalLabel)
                                .addComponent(profitTaxLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(profitTax, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ivaTaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(vouchersTableScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 1286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 233, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(vouchersTableScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(addVouchers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(calculateButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(showVouchers))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(ivaTaxLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5))
                                    .addComponent(ivaTaxTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(profitTax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(profitTaxLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(totalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(showCompanies))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(finishDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelDate))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(minTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(maxTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelImpTotal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(minIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(maxIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelIva))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(companyCuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelCompanyCuit)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(ticketTypesListScroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(labelTicketType)
                            .addGap(84, 84, 84))))
                .addContainerGap(298, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //load tickets
    private void addVouchersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addVouchersActionPerformed
        
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter fileTypes = new FileNameExtensionFilter("CSV Files", "csv");
        chooser.setFileFilter(fileTypes);
        chooser.showOpenDialog(this);

        controller.loadTickets(chooser.getSelectedFile());
        showVouchersActionPerformed(evt);
    }//GEN-LAST:event_addVouchersActionPerformed

    private void totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalActionPerformed
    
    //calculates profit of tickets
    private void calculateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateButtonActionPerformed
        ProfitCalculator profit = controller.getProfit();
        
        profitTax.setText(profit.getGanancia().toString());
        ivaTaxTextField.setText(profit.getProfit().toString());
        total.setText(profit.getIva().toString());
    }//GEN-LAST:event_calculateButtonActionPerformed

    //show providers if any
    private void showCompaniesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showCompaniesActionPerformed
        // TODO add your handling code here:
        providersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CUIT", "Nombre", "Tipo de Documento"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        providersTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        providersTable.getTableHeader().setReorderingAllowed(false);
        vouchersTableScroll.setViewportView(providersTable);
        if (providersTable.getColumnModel().getColumnCount() > 0) {
            providersTable.getColumnModel().getColumn(0).setPreferredWidth(2);
            providersTable.getColumnModel().getColumn(1).setPreferredWidth(3);
        }
        
        DefaultTableModel model = (DefaultTableModel)providersTable.getModel();
        for (Provider p : controller.getProviders()) {
            model.addRow(new Object[] {p.getCuit(), p.getName(), p.getDocType()});
        }
        providersTable.setVisible(true);
    }//GEN-LAST:event_showCompaniesActionPerformed

    //show tickets
    private void showVouchersActionPerformed(java.awt.event.ActionEvent evt) {                                             
        vouchersTableScroll.setViewportView(vouchersTable);
        Map<String, Object> selectedFilters = getFilters();
        List<Ticket> tickets = controller.getTickets(selectedFilters);
        
        DefaultTableModel model = (DefaultTableModel)vouchersTable.getModel();
        refreshTable(model);
        for (Ticket t : tickets)
            model.addRow(Formater.ticketToForm(t));
    }                                            

    private void startDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_startDateActionPerformed

    private void profitTaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profitTaxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_profitTaxActionPerformed

    private void ivaTaxLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ivaTaxLabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ivaTaxLabelActionPerformed

    private Map<String, Object> getFilters() {
        Map<String, Object> selectedFilters = new HashMap<> ();
        
        selectedFilters.put("startDate", startDate.getText());
        selectedFilters.put("finishDate", finishDate.getText());
        selectedFilters.put("minTotal", minTotal.getText());
        selectedFilters.put("maxTotal", maxTotal.getText());
        selectedFilters.put("minIva", minIva.getText());
        selectedFilters.put("maxIva", maxIva.getText());
        selectedFilters.put("companyCuit", companyCuit.getText());
        selectedFilters.put("ticketTypesList", ticketTypesList.getSelectedValuesList());
        
        return selectedFilters;
    }
    
    private void refreshTable(DefaultTableModel model) {
        for (int i = model.getRowCount() - 1; 0 <= i; i--)
            model.removeRow(i);
    }
    
    JTable providersTable = new JTable();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addVouchers;
    private javax.swing.JButton calculateButton;
    private javax.swing.JTextField companyCuit;
    private javax.swing.JTextField finishDate;
    private javax.swing.JTextField ivaTaxLabel;
    private javax.swing.JTextField ivaTaxTextField;
    private javax.swing.JLabel labelCompanyCuit;
    private javax.swing.JLabel labelDate;
    private javax.swing.JLabel labelImpTotal;
    private javax.swing.JLabel labelIva;
    private javax.swing.JLabel labelTicketType;
    private javax.swing.JTextField maxIva;
    private javax.swing.JTextField maxTotal;
    private javax.swing.JTextField minIva;
    private javax.swing.JTextField minTotal;
    private javax.swing.JTextField profitTax;
    private javax.swing.JTextField profitTaxLabel;
    private javax.swing.JButton showCompanies;
    private javax.swing.JButton showVouchers;
    private javax.swing.JTextField startDate;
    private javax.swing.JList<String> ticketTypesList;
    private javax.swing.JScrollPane ticketTypesListScroll;
    private javax.swing.JTextField total;
    private javax.swing.JTextField totalLabel;
    private javax.swing.JTable vouchersTable;
    private javax.swing.JScrollPane vouchersTableScroll;
    // End of variables declaration//GEN-END:variables

}
