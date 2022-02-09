/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.views;

import facturas.app.Controller;
import facturas.app.models.Withholding;
import facturas.app.database.SQLFilter;
import facturas.app.database.SectorDAO;
import facturas.app.utils.FormatUtils;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.util.Map;

/**
 *
 * @author Agustin
 */
public class View extends javax.swing.JFrame {

    private Controller controller;
    private ProvidersView providersView;
    private FiltersView filtersView;
    private ColumnSelector columnSelectorView;
    private TicketLoaderView ticketLoaderView;
    private WithholdingLoaderView withholdingLoaderView;
    private SectorsView sectorsView;

    /**
     * Creates new form MainWindow
     *
     * @param controller
     */
    public View(Controller controller) {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.controller = controller;
        initComponents();
        providersView = new ProvidersView(controller);
        filtersView = new FiltersView(controller, ticketsTable);
        columnSelectorView = new ColumnSelector(ticketsTable, providersView.getTable());
        ticketLoaderView = new TicketLoaderView(controller);
        withholdingLoaderView = new WithholdingLoaderView(controller);
        sectorsView = new SectorsView(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupMenu = new javax.swing.JPopupMenu();
        sectorMenuItem = new javax.swing.JMenuItem();
        deliveredMenuItem = new javax.swing.JMenuItem();
        optionPane = new javax.swing.JOptionPane();
        sectorComboBox = new javax.swing.JComboBox<>();
        ticketsTableScroll = new javax.swing.JScrollPane();
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
        resetDBButton = new javax.swing.JButton();
        viewMoreCalculusButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        files = new javax.swing.JMenu();
        multipleLoad = new javax.swing.JMenu();
        loadTicketsEmitedByMy = new javax.swing.JMenuItem();
        loadTicketsEmitedByO = new javax.swing.JMenuItem();
        loadDollarValue = new javax.swing.JMenuItem();
        loadTicketManually = new javax.swing.JMenuItem();
        loadWithholdingManually = new javax.swing.JMenuItem();
        edit = new javax.swing.JMenu();
        sectorsViewItem = new javax.swing.JMenuItem();
        tools = new javax.swing.JMenu();
        filters = new javax.swing.JMenuItem();
        columnSelector = new javax.swing.JMenuItem();

        sectorMenuItem.setText("Modificar rubro");
        sectorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sectorMenuItemActionPerformed(evt);
            }
        });
        popupMenu.add(sectorMenuItem);

        deliveredMenuItem.setText("jMenuItem1");
        deliveredMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deliveredMenuItemActionPerformed(evt);
            }
        });
        popupMenu.add(deliveredMenuItem);

        sectorComboBox.setModel(new DefaultComboBoxModel(FormatUtils.listToVector(SectorDAO.getSectors())));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PROGRAMA");
        setSize(new java.awt.Dimension(0, 0));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        ticketsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Tipo", "Nro factura", "Numero hasta", "Cód. Autorización", "Nro. Doc. Emisor", "Denominación Emisor", "Tipo Cambio", "Imp. Neto Gravado", "Imp. Neto No Gravado", "Imp. Op. Exentas", "IVA", "Imp. Total", "Rubro", "COMPRA/VENTA", "Env. Contador"
            }
        ) {
            Class[] types = new Class [] {
                Object.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, Float.class, Float.class, Float.class, Float.class, Float.class, Float.class, String.class, String.class, String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
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
        ticketsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ticketsTableMouseReleased(evt);
            }
        });
        ticketsTableScroll.setViewportView(ticketsTable);
        ticketsTable.setAutoCreateRowSorter(true);
        DefaultTableModel model = (DefaultTableModel)ticketsTable.getModel();
        for (Withholding t : controller.getTickets()) {
            model.addRow(facturas.app.utils.FormatUtils.ticketToForm(t));
        }

        ticketsTable.setCellSelectionEnabled(true);
        ticketsTable.setVisible(true);

        total.setEditable(false);
        total.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        total.setBorder(null);

        ivaTaxTextField.setEditable(false);
        ivaTaxTextField.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ivaTaxTextField.setBorder(null);

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

        ivaTaxLabel.setEditable(false);
        ivaTaxLabel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ivaTaxLabel.setText("Iva:");
        ivaTaxLabel.setBorder(null);

        profitTaxLabel.setEditable(false);
        profitTaxLabel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        profitTaxLabel.setText("Ganancias:");
        profitTaxLabel.setBorder(null);

        totalLabel.setEditable(false);
        totalLabel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        totalLabel.setText("Total:");
        totalLabel.setBorder(null);

        inDollars.setText("Precio en dolares");

        resetDBButton.setText("Reset DB");
        resetDBButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetDBButtonActionPerformed(evt);
            }
        });

        viewMoreCalculusButton.setText("Ver mas");
        viewMoreCalculusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewMoreCalculusButtonActionPerformed(evt);
            }
        });

        files.setText("File");

        multipleLoad.setText("Cargar (.csv)...");

        loadTicketsEmitedByMy.setText("Emitidos por mi");
        loadTicketsEmitedByMy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadTicketsEmitedByMyActionPerformed(evt);
            }
        });
        multipleLoad.add(loadTicketsEmitedByMy);

        loadTicketsEmitedByO.setText("Emitidos por terceros");
        loadTicketsEmitedByO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadTicketsEmitedByOActionPerformed(evt);
            }
        });
        multipleLoad.add(loadTicketsEmitedByO);

        loadDollarValue.setText("Cargar valor dolar");
        loadDollarValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadDollarValueActionPerformed(evt);
            }
        });
        multipleLoad.add(loadDollarValue);

        files.add(multipleLoad);

        loadTicketManually.setText("Cargar comprobante");
        loadTicketManually.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadTicketManuallyActionPerformed(evt);
            }
        });
        files.add(loadTicketManually);

        loadWithholdingManually.setText("Cargar retencion");
        loadWithholdingManually.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadWithholdingManuallyActionPerformed(evt);
            }
        });
        files.add(loadWithholdingManually);

        menuBar.add(files);

        edit.setText("Edit");

        sectorsViewItem.setText("Agregar/Borrar rubro");
        sectorsViewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sectorsViewItemActionPerformed(evt);
            }
        });
        edit.add(sectorsViewItem);

        menuBar.add(edit);

        tools.setText("Herramientas");

        filters.setText("Filtros");
        filters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtersActionPerformed(evt);
            }
        });
        tools.add(filters);

        columnSelector.setText("Seleccionar columnas");
        columnSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                columnSelectorActionPerformed(evt);
            }
        });
        tools.add(columnSelector);

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
                            .addComponent(showTickets, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(57, 57, 57)
                        .addComponent(calculateButton)
                        .addGap(205, 205, 205)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ivaTaxLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ivaTaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(profitTaxLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(profitTax, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(totalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(inDollars))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(146, 146, 146)
                                .addComponent(resetDBButton))
                            .addComponent(viewMoreCalculusButton)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ticketsTableScroll)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ticketsTableScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 32615, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(showTickets)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(showProviders))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(resetDBButton)
                                    .addComponent(ivaTaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ivaTaxLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(profitTax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(profitTaxLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(calculateButton)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(totalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inDollars))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(viewMoreCalculusButton)))))
                .addGap(33, 33, 33))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    //calculates profit of tickets
    private void calculateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateButtonActionPerformed
        boolean dollar = inDollars.isSelected();
        DecimalFormat numberFormat = new DecimalFormat("###,###.00");
        

        Map<String, Float> values = controller.getProfit(filtersView.getFilters(), dollar);
        
        String money = dollar ? " USD" : " ARS";
        profitTax.setText(numberFormat.format(values.get("totalProfitTax")) + money);
        ivaTaxTextField.setText(numberFormat.format(values.get("totalIva"))+ money);
        total.setText(numberFormat.format(values.get("profitWTax")) + money);
    }//GEN-LAST:event_calculateButtonActionPerformed

    //show providers if any
    private void showProvidersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showProvidersActionPerformed
        providersView.setVisible(true);
        // FIXME: search a better option (optimized)
        providersView.updateProviders(evt);
    }//GEN-LAST:event_showProvidersActionPerformed

    //show tickets
    private void showTicketsActionPerformed(java.awt.event.ActionEvent evt) {                                             
        List<Withholding> tickets = controller.getTickets();
        
        DefaultTableModel model = (DefaultTableModel)ticketsTable.getModel();
        cleanTable(model);
        for (Withholding t : tickets) {
            model.addRow(FormatUtils.ticketToForm(t));
        }
    }                                            
 
    private void filtersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtersActionPerformed
        filtersView.setVisible(true);
    }//GEN-LAST:event_filtersActionPerformed

    private void loadTicketsEmitedByMyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadTicketsEmitedByMyActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter fileTypes = new FileNameExtensionFilter("CSV Files", "csv");
        chooser.setFileFilter(fileTypes);
        chooser.showOpenDialog(this);

        controller.loadTickets(chooser.getSelectedFile(), true);
        // FIXME: Maybe we can update the suggestions only 
        // when we know that a providers was added
        providersView.updateSuggestions();
        showTicketsActionPerformed(evt);
    }//GEN-LAST:event_loadTicketsEmitedByMyActionPerformed

    private void loadTicketManuallyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadTicketManuallyActionPerformed
        ticketLoaderView.setVisible(true);
        ticketLoaderView.updateSuggestions();
    }//GEN-LAST:event_loadTicketManuallyActionPerformed

    private void loadDollarValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadDollarValueActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter fileTypes = new FileNameExtensionFilter("CSV Files", "csv");
        chooser.setFileFilter(fileTypes);
        chooser.showOpenDialog(this);

        controller.loadDollarPrices(chooser.getSelectedFile());
    }//GEN-LAST:event_loadDollarValueActionPerformed

    private void columnSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_columnSelectorActionPerformed
        columnSelectorView.setVisible(true);
    }//GEN-LAST:event_columnSelectorActionPerformed

    private void resetDBButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetDBButtonActionPerformed
        controller.resetDB();
        cleanTable((DefaultTableModel)ticketsTable.getModel());
    }//GEN-LAST:event_resetDBButtonActionPerformed

    private void sectorsViewItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sectorsViewItemActionPerformed
        sectorsView.setVisible(true);
        sectorsView.updateSuggestions();
    }//GEN-LAST:event_sectorsViewItemActionPerformed

    private void loadTicketsEmitedByOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadTicketsEmitedByOActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter fileTypes = new FileNameExtensionFilter("CSV Files", "csv");
        chooser.setFileFilter(fileTypes);
        chooser.showOpenDialog(this);

        controller.loadTickets(chooser.getSelectedFile(), false);
        // FIXME: Maybe we can update the suggestions only 
        // when we know that a providers was added
        providersView.updateSuggestions();
        showTicketsActionPerformed(evt);
    }//GEN-LAST:event_loadTicketsEmitedByOActionPerformed

    private void ticketsTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ticketsTableMouseReleased
        if (evt.getButton() == MouseEvent.BUTTON3) {//right click
            if (evt.isPopupTrigger() && ticketsTable.getSelectedRowCount() != 0) {
                int row = ticketsTable.getSelectedRow();
                String deliveredValue = (String)ticketsTable.getValueAt(row, 15); //15 is the delivered column
                deliveredMenuItem.setText(deliveredValue == "NO" ? "Marcar como enviado" : "Marcar como no enviado");
                popupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }
    }//GEN-LAST:event_ticketsTableMouseReleased

    private void sectorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sectorMenuItemActionPerformed
        int selection = optionPane.showConfirmDialog(null, sectorComboBox, "Seleccione un rubro", optionPane.OK_CANCEL_OPTION);
        if (selection == optionPane.OK_OPTION) {
            int row = ticketsTable.getSelectedRow();
            SQLFilter filter = new SQLFilter();
            Date date = (Date)ticketsTable.getValueAt(row, 0); //0 is the date column
            filter.add("date", "=", date, Date.class);
            Integer noTicket = (Integer)ticketsTable.getValueAt(row, 2); //2 is the noTicket column
            filter.add("number", "=", noTicket, Integer.class);
            String cuit = (String)ticketsTable.getValueAt(row, 5); //5 is the cuit column
            filter.add("providerDoc", "=", cuit, String.class);
            
            String sector = (String)sectorComboBox.getSelectedItem();
            String type = (String)ticketsTable.getValueAt(row, 1);
            if (type.contains("Retencion"))
                controller.changeWithholdingAttribute(filter, "sector", sector);
            else
                controller.changeTicketAttribute(filter, "sector", sector);
        
            ticketsTable.setValueAt(sector, row, 13);   //column 13 is for sector
        }
    }//GEN-LAST:event_sectorMenuItemActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        columnSelectorView.dispatchEvent(new WindowEvent(columnSelectorView, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_formWindowClosing

    private void deliveredMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deliveredMenuItemActionPerformed
        int row = ticketsTable.getSelectedRow();
        SQLFilter filter = new SQLFilter();
        Date date = (Date)ticketsTable.getValueAt(row, 0); //0 is the date column
        filter.add("date", "=", date, Date.class);
        Integer noTicket = (Integer)ticketsTable.getValueAt(row, 2); //2 is the noTicket column
        filter.add("number", "=", noTicket, Integer.class);
        String cuit = (String)ticketsTable.getValueAt(row, 5); //5 is the cuit column
        filter.add("providerDoc", "=", cuit, String.class);
        
        String deliveredValue = (String)ticketsTable.getValueAt(row, 15) == "NO" ? "SI" : "NO";
        String type = (String)ticketsTable.getValueAt(row, 1);
        if (type.contains("Retencion"))
            controller.changeWithholdingAttribute(filter, "delivered", deliveredValue == "NO" ? "false" : "true");
        else
            controller.changeTicketAttribute(filter, "delivered", deliveredValue == "NO" ? "false" : "true");
        
        ticketsTable.setValueAt(deliveredValue, row, 15);   //column 13 is for sector
    }//GEN-LAST:event_deliveredMenuItemActionPerformed

    private void loadWithholdingManuallyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadWithholdingManuallyActionPerformed
        withholdingLoaderView.setVisible(true);
        withholdingLoaderView.updateSuggestions();
    }//GEN-LAST:event_loadWithholdingManuallyActionPerformed

    private void viewMoreCalculusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewMoreCalculusButtonActionPerformed
        CalculusView calculusView = new CalculusView(filtersView);
        calculusView.setVisible(true);
    }//GEN-LAST:event_viewMoreCalculusButtonActionPerformed

    public void updateSectors(List<String> sectors) {
        sectorComboBox.setModel(new DefaultComboBoxModel(FormatUtils.listToVector(sectors)));
        providersView.updateSectors(sectors);
        //filtersView.updateSectors(sectors);
        ticketLoaderView.updateSectors(sectors);
        withholdingLoaderView.updateSectors(sectors);
    }
    
    private void cleanTable(DefaultTableModel model) {
        for (int i = model.getRowCount() - 1; 0 <= i; i--)
            model.removeRow(i);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calculateButton;
    private javax.swing.JMenuItem columnSelector;
    private javax.swing.JMenuItem deliveredMenuItem;
    private javax.swing.JMenu edit;
    private javax.swing.JMenu files;
    private javax.swing.JMenuItem filters;
    private javax.swing.JCheckBox inDollars;
    private javax.swing.JTextField ivaTaxLabel;
    private javax.swing.JTextField ivaTaxTextField;
    private javax.swing.JMenuItem loadDollarValue;
    private javax.swing.JMenuItem loadTicketManually;
    private javax.swing.JMenuItem loadTicketsEmitedByMy;
    private javax.swing.JMenuItem loadTicketsEmitedByO;
    private javax.swing.JMenuItem loadWithholdingManually;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu multipleLoad;
    private javax.swing.JOptionPane optionPane;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JTextField profitTax;
    private javax.swing.JTextField profitTaxLabel;
    private javax.swing.JButton resetDBButton;
    private javax.swing.JComboBox<String> sectorComboBox;
    private javax.swing.JMenuItem sectorMenuItem;
    private javax.swing.JMenuItem sectorsViewItem;
    private javax.swing.JButton showProviders;
    private javax.swing.JButton showTickets;
    private javax.swing.JTable ticketsTable;
    private javax.swing.JScrollPane ticketsTableScroll;
    private javax.swing.JMenu tools;
    private javax.swing.JTextField total;
    private javax.swing.JTextField totalLabel;
    private javax.swing.JButton viewMoreCalculusButton;
    // End of variables declaration//GEN-END:variables

}
