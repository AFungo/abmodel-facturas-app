/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.views;

import facturas.app.Controller;
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

    private Controller controller;
    private ProvidersView pv;
    private FiltersView fv;
    private JTable providersTable = new JTable();

    /**
     * Creates new form MainWindow
     *
     * @param controller
     */
    public View(Controller controller) {
        this.controller = controller;
        initComponents();
        pv = new ProvidersView(controller);
        fv = new FiltersView(controller, ticketsTable);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vouchersTableScroll = new javax.swing.JScrollPane();
        ticketsTable = new javax.swing.JTable();
        total = new javax.swing.JTextField();
        ivaTaxTextField = new javax.swing.JTextField();
        calculateButton = new javax.swing.JButton();
        showProviders = new javax.swing.JButton();
        showTickets = new javax.swing.JButton();
        profitTax = new javax.swing.JTextField();
        ivaTaxLabel = new javax.swing.JTextField();
        profitTaxLabel = new javax.swing.JTextField();
        totalLabel = new javax.swing.JTextField();
        inDollars = new javax.swing.JCheckBox();
        menuBar = new javax.swing.JMenuBar();
        files = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        dolarPrice = new javax.swing.JMenuItem();
        edit = new javax.swing.JMenu();
        tools = new javax.swing.JMenu();
        filters = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ticketsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        ticketsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ticketsTable.getTableHeader().setReorderingAllowed(false);
        vouchersTableScroll.setViewportView(ticketsTable);
        if (ticketsTable.getColumnModel().getColumnCount() > 0) {
            ticketsTable.getColumnModel().getColumn(0).setPreferredWidth(2);
            ticketsTable.getColumnModel().getColumn(1).setPreferredWidth(3);
        }
        DefaultTableModel model = (DefaultTableModel)ticketsTable.getModel();
        for (Ticket t : controller.getTickets())
        model.addRow(Formater.ticketToForm(t));

        ticketsTable.setCellSelectionEnabled(true);
        ticketsTable.setVisible(true);

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
        ivaTaxTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ivaTaxTextFieldActionPerformed(evt);
            }
        });

        calculateButton.setText("Calcular");
        calculateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateButtonActionPerformed(evt);
            }
        });

        showProviders.setText("Mostrar proveedores");
        showProviders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showProvidersActionPerformed(evt);
            }
        });

        showTickets.setText("Mostrar comprobantes");
        showTickets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showTicketsActionPerformed(evt);
            }
        });

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

        inDollars.setText("Precio en dolares");

        files.setText("File");

        jMenuItem2.setText("Cargar comprobante");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        files.add(jMenuItem2);

        dolarPrice.setText("Cargar valor dolar");
        dolarPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dolarPriceActionPerformed(evt);
            }
        });
        files.add(dolarPrice);

        menuBar.add(files);

        edit.setText("Edit");
        menuBar.add(edit);

        tools.setText("Herramientas");

        filters.setText("Filtros");
        filters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtersActionPerformed(evt);
            }
        });
        tools.add(filters);

        menuBar.add(tools);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(472, 472, 472)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(showProviders, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(showTickets, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                        .addGap(82, 82, 82)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(calculateButton)
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
                                    .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(inDollars)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(vouchersTableScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 1286, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(vouchersTableScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(calculateButton)
                        .addGap(33, 33, 33)
                        .addComponent(showTickets)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(showProviders)
                            .addComponent(inDollars)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
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
                .addGap(33, 33, 33))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalActionPerformed
    
    //calculates profit of tickets
    private void calculateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateButtonActionPerformed
        boolean dollar = inDollars.isSelected();
        ProfitCalculator profit = controller.getProfit(fv.getFilters(), dollar);
        
        String money = dollar ? " USD" : " ARS";
        profitTax.setText(profit.getGanancia().toString() + money);
        ivaTaxTextField.setText(profit.getProfit().toString() + money);
        total.setText(profit.getIva().toString() + money);
    }//GEN-LAST:event_calculateButtonActionPerformed

    //show providers if any
    private void showProvidersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showProvidersActionPerformed
        // TODO add your handling code here:
        pv.setVisible(true);
    }//GEN-LAST:event_showProvidersActionPerformed

    //show tickets
    private void showTicketsActionPerformed(java.awt.event.ActionEvent evt) {                                             
        List<Ticket> tickets = controller.getTickets();
        
        DefaultTableModel model = (DefaultTableModel)ticketsTable.getModel();
        cleanTable(model);
        for (Ticket t : tickets)
            model.addRow(Formater.ticketToForm(t));
    }                                            

    private void profitTaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profitTaxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_profitTaxActionPerformed

    private void ivaTaxLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ivaTaxLabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ivaTaxLabelActionPerformed

    private void filtersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtersActionPerformed
        // TODO add your handling code here:
        fv.setVisible(true);
    }//GEN-LAST:event_filtersActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter fileTypes = new FileNameExtensionFilter("CSV Files", "csv");
        chooser.setFileFilter(fileTypes);
        chooser.showOpenDialog(this);

        controller.loadTickets(chooser.getSelectedFile());
        // FIXME: Maybe we can update the suggestions only 
        // when we know that a providers was added
        pv.updateSuggestions();
        showTicketsActionPerformed(evt);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void dolarPriceActionPerformed(java.awt.event.ActionEvent evt) {                                           
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter fileTypes = new FileNameExtensionFilter("CSV Files", "csv");
        chooser.setFileFilter(fileTypes);
        chooser.showOpenDialog(this);

        controller.loadDollarPrices(chooser.getSelectedFile());
    }                                          

    private void ivaTaxTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ivaTaxTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ivaTaxTextFieldActionPerformed

    private void cleanTable(DefaultTableModel model) {
        for (int i = model.getRowCount() - 1; 0 <= i; i--)
            model.removeRow(i);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calculateButton;
    private javax.swing.JMenuItem dolarPrice;
    private javax.swing.JMenu edit;
    private javax.swing.JMenu files;
    private javax.swing.JMenuItem filters;
    private javax.swing.JCheckBox inDollars;
    private javax.swing.JTextField ivaTaxLabel;
    private javax.swing.JTextField ivaTaxTextField;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JTextField profitTax;
    private javax.swing.JTextField profitTaxLabel;
    private javax.swing.JButton showProviders;
    private javax.swing.JButton showTickets;
    private javax.swing.JTable ticketsTable;
    private javax.swing.JMenu tools;
    private javax.swing.JTextField total;
    private javax.swing.JTextField totalLabel;
    private javax.swing.JScrollPane vouchersTableScroll;
    // End of variables declaration//GEN-END:variables

}
