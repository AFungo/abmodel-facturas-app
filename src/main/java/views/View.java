package views;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.apache.commons.io.FilenameUtils;
import backup.BackUpBuilder;
import calculations.DollarPriceManager;
import controller.Controller;
import database.SectorDAO;
import filters.Comparison;
import filters.Filter;
import formatters.ModelToForm;
import models.Withholding;
import models.Ticket;
import utils.ConfigManager;
import utils.Pair;
import utils.PdfCreator;
import calculations.PricesList;
import views.utils.ViewMediator;
import views.utils.ViewUtils;

/**
 *
 * @author Agustin
 */
public class View extends JFrame {

    /**
     * Creates new form MainWindow
     *
     * @param controller
     */
    public View(Controller controller, ViewMediator viewMediator) {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.controller = controller;
        initComponents();
        this.viewMediator = viewMediator;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        menuBar = new JMenuBar();
        files = new JMenu();
        multipleLoad = new JMenu();
        loadTickets = new JMenuItem();
        loadDollarValue = new JMenuItem();
        loadTicketManually = new JMenuItem();
        loadWithholdingManually = new JMenuItem();
        edit = new JMenu();
        sectorsViewItem = new JMenuItem();
        addProviderMenuItem = new JMenuItem();
        tools = new JMenu();
        filters = new JMenuItem();
        columnSelector = new JMenuItem();
        createBackup = new JMenuItem();
        loadBackup = new JMenuItem();
        createPDFMenuItem = new JMenuItem();
        ticketsTableScroll = new JScrollPane();
        ticketsTable = new javax.swing.JTable();
        total = new javax.swing.JTextField();
        ivaTaxTextField = new JTextField();
        calculateButton = new JButton();
        showProviders = new JButton();
        showTickets = new JButton();
        profitTax = new JTextField();
        ivaTaxLabel = new JTextField();
        profitTaxLabel = new JTextField();
        totalLabel = new JTextField();
        inDollars = new JCheckBox();
        resetDBButton = new JButton();
        viewMoreCalculusButton = new JButton();
        popupMenu = new JPopupMenu();
        sectorMenuItem = new JMenuItem();
        deliveredMenuItem = new JMenuItem();
        exchangeTypeMenuItem = new JMenuItem();
        deleteSectorMenuItem = new JMenuItem();
        deleteMenuItem = new JMenuItem();
        sectorComboBox = new JComboBox<>();

        //======== this ========
//        this.setIconImage(new ImageIcon(getClass().getResource("/resources/images/icono-facturas-app-opcion-dos.png")).getImage()
//    );
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("ADMINISTRADOR CONTABLE ABMODEL");
        setSize(new Dimension(0, 0));
        Container contentPane = getContentPane();

        //======== menuBar ========
        {

            //======== files ========
            {
                files.setText("Cargar");

                //======== multipleLoad ========
                {
                    multipleLoad.setText("Cargar (.csv)...");

                    //---- loadTickets ----
                    loadTickets.setText("Cargar Comprobante");
                    loadTickets.addActionListener(e -> loadTicketsActionPerformed(e));
                    multipleLoad.add(loadTickets);

                    //---- loadDollarValue ----
                    loadDollarValue.setText("Cargar valor dolar");
                    loadDollarValue.addActionListener(e -> loadDollarValueActionPerformed(e));
                    multipleLoad.add(loadDollarValue);
                }
                files.add(multipleLoad);

                //---- loadTicketManually ----
                loadTicketManually.setText("Cargar comprobante");
                loadTicketManually.addActionListener(e -> loadTicketManuallyActionPerformed(e));
                files.add(loadTicketManually);

                //---- loadWithholdingManually ----
                loadWithholdingManually.setText("Cargar retencion");
                loadWithholdingManually.addActionListener(e -> loadWithholdingManuallyActionPerformed(e));
                files.add(loadWithholdingManually);
            }
            menuBar.add(files);

            //======== edit ========
            {
                edit.setText("Editar");

                //---- sectorsViewItem ----
                sectorsViewItem.setText("Agregar/Borrar rubro");
                sectorsViewItem.addActionListener(e -> sectorsViewItemActionPerformed(e));
                edit.add(sectorsViewItem);

                //---- addProviderMenuItem ----
                addProviderMenuItem.setText("A\u00f1adir proveedor");
                addProviderMenuItem.addActionListener(e -> addProviderMenuItemActionPerformed(e));
                edit.add(addProviderMenuItem);
            }
            menuBar.add(edit);

            //======== tools ========
            {
                tools.setText("Herramientas");

                //---- filters ----
                filters.setText("Filtros");
                filters.addActionListener(e -> filtersActionPerformed(e));
                tools.add(filters);

                //---- columnSelector ----
                columnSelector.setText("Seleccionar columnas");
                columnSelector.addActionListener(e -> columnSelectorActionPerformed(e));
                tools.add(columnSelector);

                //---- createBackup ----
                createBackup.setText("Crear backup");
                createBackup.addActionListener(e -> createBackupActionPerformed(e));
                tools.add(createBackup);

                //---- loadBackup ----
                loadBackup.setText("Cargar backup");
                loadBackup.addActionListener(e -> loadBackupActionPerformed(e));
                tools.add(loadBackup);

                //---- createPDFMenuItem ----
                createPDFMenuItem.setText("Crear PDF");
                createPDFMenuItem.addActionListener(e -> createPDFMenuItemActionPerformed(e));
                tools.add(createPDFMenuItem);
            }
            menuBar.add(tools);
        }
        setJMenuBar(menuBar);

        //======== ticketsTableScroll ========
        {

            //---- ticketsTable ----
            ticketsTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                    "id", "Fecha", "Tipo", "Nro factura", "Numero hasta", "Cód. Autorización", "Nro. Doc. Emisor", "Denominación Emisor", "Tipo Cambio", "Imp. Neto Gravado", "Imp. Neto No Gravado", "Imp. Op. Exentas", "IVA", "Imp. Total", "Rubro", "COMPRA/VENTA", "Env. Contador"
                }
            ) {
                Class[] types = new Class [] {
                    Integer.class, Object.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class, Float.class, Float.class, Float.class, Float.class, Float.class, Float.class, String.class, String.class, String.class
                };
                boolean[] canEdit = new boolean [] {
                    false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
                };

                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });
            ticketsTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    ticketsTableMousePressed(e);
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                    ticketsTableMouseReleased(e);
                }
            });
            ticketsTableScroll.setViewportView(ticketsTable);
        }

        //---- total ----
        total.setEditable(false);
        total.setFont(new Font("Arial", Font.BOLD, 12));
        total.setBorder(null);

        //---- ivaTaxTextField ----
        ivaTaxTextField.setEditable(false);
        ivaTaxTextField.setFont(new Font("Arial", Font.BOLD, 12));
        ivaTaxTextField.setBorder(null);

        //---- calculateButton ----
        calculateButton.setText("Calcular");
        calculateButton.addActionListener(e -> calculateButtonActionPerformed(e));

        //---- showProviders ----
        showProviders.setText("Mostrar proveedores");
        showProviders.addActionListener(e -> showProvidersActionPerformed(e));

        //---- showTickets ----
        showTickets.setText("Mostrar comprobantes");
        showTickets.addActionListener(e -> showTicketsActionPerformed(e));

        //---- profitTax ----
        profitTax.setEditable(false);
        profitTax.setFont(new Font("Arial", Font.BOLD, 12));
        profitTax.setBorder(null);

        //---- ivaTaxLabel ----
        ivaTaxLabel.setEditable(false);
        ivaTaxLabel.setFont(new Font("Arial", Font.BOLD, 12));
        ivaTaxLabel.setText("Total IVA:");
        ivaTaxLabel.setBorder(null);

        //---- profitTaxLabel ----
        profitTaxLabel.setEditable(false);
        profitTaxLabel.setFont(new Font("Arial", Font.BOLD, 12));
        profitTaxLabel.setText("Total ganancias:");
        profitTaxLabel.setBorder(null);

        //---- totalLabel ----
        totalLabel.setEditable(false);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 12));
        totalLabel.setText("Total:");
        totalLabel.setBorder(null);

        //---- inDollars ----
        inDollars.setText("Precio en dolares");

        //---- resetDBButton ----
        resetDBButton.setText("Reset DB");
        resetDBButton.addActionListener(e -> resetDBButtonActionPerformed(e));

        //---- viewMoreCalculusButton ----
        viewMoreCalculusButton.setText("Ver mas");
        viewMoreCalculusButton.addActionListener(e -> viewMoreCalculusButtonActionPerformed(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGap(475, 475, 475)
                                    .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(showTickets, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                            .addGap(42, 42, 42)
                                            .addComponent(showProviders)))
                                    .addGap(204, 204, 204)
                                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(viewMoreCalculusButton)
                                        .addComponent(calculateButton))
                                    .addGap(18, 18, 18))
                                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(resetDBButton)
                                    .addGap(371, 371, 371)))
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(profitTaxLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(profitTax, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(totalLabel, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(total, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
                                .addComponent(inDollars)
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(ivaTaxLabel, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(ivaTaxTextField, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)))
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(ticketsTableScroll)))
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(ticketsTableScroll)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(ivaTaxTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(ivaTaxLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(calculateButton))
                            .addGap(2, 2, 2)
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(profitTax, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(profitTaxLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(viewMoreCalculusButton))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(totalLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(total, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(showTickets)
                            .addGap(18, 18, 18)
                            .addComponent(showProviders)))
                    .addGap(2, 2, 2)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(inDollars)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(16, 16, 16)
                            .addComponent(resetDBButton)))
                    .addGap(27, 27, 27))
        );
        pack();
        setLocationRelativeTo(null);

        //======== popupMenu ========
        {

            //---- sectorMenuItem ----
            sectorMenuItem.setText("Modificar rubro");
            sectorMenuItem.addActionListener(e -> sectorMenuItemActionPerformed(e));
            popupMenu.add(sectorMenuItem);

            //---- deliveredMenuItem ----
            deliveredMenuItem.setText("jMenuItem1");
            deliveredMenuItem.addActionListener(e -> deliveredMenuItemActionPerformed(e));
            popupMenu.add(deliveredMenuItem);

            //---- exchangeTypeMenuItem ----
            exchangeTypeMenuItem.setText("Modificar tipo de cambio");
            exchangeTypeMenuItem.addActionListener(e -> exchangeTypeMenuItemActionPerformed(e));
            popupMenu.add(exchangeTypeMenuItem);

            //---- deleteSectorMenuItem ----
            deleteSectorMenuItem.setText("Eliminar rubro");
            deleteSectorMenuItem.addActionListener(e -> deleteSectorMenuItemActionPerformed(e));
            popupMenu.add(deleteSectorMenuItem);

            //---- deleteMenuItem ----
            deleteMenuItem.setText("Eliminar");
            deleteMenuItem.addActionListener(e -> deleteMenuItemActionPerformed(e));
            popupMenu.add(deleteMenuItem);
        }

        //---- sectorComboBox ----
        sectorComboBox.setModel(new DefaultComboBoxModel(new Vector<>(SectorDAO.getInstance().getAll())));
    }// </editor-fold>//GEN-END:initComponents
    
    //calculates profit of tickets
    private void calculateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateButtonActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        boolean dollar = inDollars.isSelected();
        DecimalFormat numberFormat = new DecimalFormat("###,###.00");
        PricesList pricesList;
        Filter[] filters = viewMediator.getFilters();
        try {
            pricesList = new PricesList(dollar);
            pricesList.calculateSummary(controller.getTickets(filters),
                    controller.getWithholdings(filters));
        } catch (IllegalStateException e) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(this, "No hay valores del dolar cargados, por favor cargue y vuelva a intentar", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return ;
        }
    
        Map<String,Float> values = pricesList.getValues();
        List<Pair<Date,String>> missingPrices = pricesList.getMissingPrices();
        if (!missingPrices.isEmpty()) {
            JTable pricesTable = ViewUtils.createMissingPricesTable(missingPrices);
            int daysLimit = DollarPriceManager.getDaysRoundLimit();
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(this, new JScrollPane(pricesTable), 
                "Las siguientes fechas exceden el limite de " + daysLimit +
                " dias para redondear el dolar", JOptionPane.WARNING_MESSAGE);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        }
        
        String money = dollar ? " USD" : " ARS";
        profitTax.setText(numberFormat.format(values.get("totalProfitTax")) + money);
        ivaTaxTextField.setText(numberFormat.format(values.get("totalIva"))+ money);
        total.setText(numberFormat.format(values.get("profitWTax")) + money);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_calculateButtonActionPerformed

    //show providers if any
    private void showProvidersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showProvidersActionPerformed
        viewMediator.setProviderLoaderVisible(true);
        viewMediator.updateProviderSuggestions();
        
    }//GEN-LAST:event_showProvidersActionPerformed

    //show tickets
    private void showTicketsActionPerformed(java.awt.event.ActionEvent evt) {                                             
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            loadTicketsInTable();
        } finally {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }                                            
 
    private void filtersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtersActionPerformed
        viewMediator.setFiltersViewVisible(true);
        viewMediator.updateFiltersSuggestions();
    }//GEN-LAST:event_filtersActionPerformed

    private void loadTicketsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadTicketsActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter fileTypes = new FileNameExtensionFilter("CSV Files", "csv");
        chooser.setFileFilter(fileTypes);
        chooser.showOpenDialog(this);
        
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        controller.loadTicketsFromAFIPFile(chooser.getSelectedFile());
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        // FIXME: Maybe we can update the suggestions only
        //  when we know that a providers was added.
        //TODO: I think the same, if we add a provider update suggestions and never be outdated
        viewMediator.updateProviderSuggestions();
        loadTicketsInTable();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_loadTicketsActionPerformed

    private void loadTicketManuallyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadTicketManuallyActionPerformed
        viewMediator.setTicketLoaderVisible(true);
        viewMediator.updateTicketLoaderSuggestions();
    }//GEN-LAST:event_loadTicketManuallyActionPerformed

    private void loadDollarValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadDollarValueActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter fileTypes = new FileNameExtensionFilter("CSV Files", "csv");
        chooser.setFileFilter(fileTypes);
        chooser.showOpenDialog(this);

        controller.loadDollarPricesFromFile(chooser.getSelectedFile());
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_loadDollarValueActionPerformed

    private void columnSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_columnSelectorActionPerformed
        viewMediator.setColumnSelectorVisible(true);
    }//GEN-LAST:event_columnSelectorActionPerformed

    private void resetDBButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetDBButtonActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        controller.resetDB();
        cleanTable((DefaultTableModel)ticketsTable.getModel());
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_resetDBButtonActionPerformed

    private void sectorsViewItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sectorsViewItemActionPerformed
        viewMediator.setSectorsViewVisible(true);
        viewMediator.updateSectorSuggestions();
    }//GEN-LAST:event_sectorsViewItemActionPerformed

    private void ticketsTableMouseReleased(MouseEvent evt) {//GEN-FIRST:event_ticketsTableMouseReleased
        if (evt.getButton() == MouseEvent.BUTTON3) {//right click
            int rowPoint = ticketsTable.rowAtPoint(evt.getPoint());
            ticketsTable.clearSelection();
            ticketsTable.addRowSelectionInterval(rowPoint, rowPoint);
            int row = ticketsTable.getSelectedRow();
            if (evt.isPopupTrigger() && ticketsTable.getSelectedRowCount() != 0) {
                String deliveredValue = (String)ticketsTable.getValueAt(row, 16); //16 is the delivered column
                deliveredMenuItem.setText(Objects.equals(deliveredValue, "NO") ? "Marcar como enviado" : "Marcar como no enviado");
                String sector = (String)ticketsTable.getValueAt(row, 14); //14 is the delivered column
                deleteSectorMenuItem.setEnabled(sector != null && !sector.isEmpty());
                popupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }
    }//GEN-LAST:event_ticketsTableMouseReleased

    private void sectorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sectorMenuItemActionPerformed
        int selection = JOptionPane.showConfirmDialog(this, sectorComboBox, "Seleccione un rubro", JOptionPane.OK_CANCEL_OPTION);
        if (selection == JOptionPane.OK_OPTION) {
            int row = ticketsTable.getSelectedRow();
            String id = (String) ticketsTable.getValueAt(row, 0);
            Filter filter = new Filter("id", id, Comparison.EQUALS);

            String sector = (String)sectorComboBox.getSelectedItem();
            controller.updateWithholdings(filter, "sector", sector);
        
            ticketsTable.setValueAt(sector, row, 14);   //column 14 is for sector
        }
    }//GEN-LAST:event_sectorMenuItemActionPerformed

    private void deliveredMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deliveredMenuItemActionPerformed
        int row = ticketsTable.getSelectedRow();
        String id = (String) ticketsTable.getValueAt(row, 0);
        Filter filter = new Filter("id", id, Comparison.EQUALS);

        String deliveredValue = (String)ticketsTable.getValueAt(row, 16) == "NO" ? "SI" : "NO";
            controller.updateWithholdings(filter, "delivered", deliveredValue == "NO" ? "false" : "true");
        
        ticketsTable.setValueAt(deliveredValue, row, 16);   //column 16 is for delivered
    }//GEN-LAST:event_deliveredMenuItemActionPerformed

    private void loadWithholdingManuallyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadWithholdingManuallyActionPerformed
        viewMediator.setWithholdingLoaderVisible(true);
        viewMediator.updateProviderSuggestions();
        viewMediator.updateSectorSuggestions();
    }//GEN-LAST:event_loadWithholdingManuallyActionPerformed

    private void viewMoreCalculusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewMoreCalculusButtonActionPerformed
        viewMediator.setCalculusViewVisible(true);
    }//GEN-LAST:event_viewMoreCalculusButtonActionPerformed

    private void ticketsTableMousePressed(MouseEvent evt) {//GEN-FIRST:event_ticketsTableMousePressed
        //just to make it work on mac
        ticketsTableMouseReleased(evt);
    }//GEN-LAST:event_ticketsTableMousePressed

    private void deleteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteMenuItemActionPerformed
        int row = ticketsTable.getSelectedRow();
        JTable toDelete = createToDeleteTable(row);
        int selection = JOptionPane.showConfirmDialog(this, new JScrollPane(toDelete), "Estas seguro?", JOptionPane.OK_CANCEL_OPTION);
        if (selection == JOptionPane.OK_OPTION) {
            String id = (String) ticketsTable.getValueAt(row, 0);
            Filter filter = new Filter("id", id, Comparison.EQUALS);
            String type = (String)ticketsTable.getValueAt(row, 2);

            // for some reason here we differentiate the withholding by if contains 'Retencion'
            if (type.contains("Retencion"))
                controller.deleteWithholdings(filter);
            else
                controller.deleteWithholdings(filter);
            
            row = ticketsTable.convertRowIndexToModel(row); //translate cell coordinates to DefaultTableModel
            ((DefaultTableModel)ticketsTable.getModel()).removeRow(row); //remove row from view
        }
    }//GEN-LAST:event_deleteMenuItemActionPerformed

    private JTable createToDeleteTable(int rowIndex) {
        Object[] rowToDelete = new Object[6];
        rowToDelete[0] = ticketsTable.getValueAt(rowIndex, 0);    //id
        rowToDelete[1] = ticketsTable.getValueAt(rowIndex, 1);    //date
        rowToDelete[2] = ticketsTable.getValueAt(rowIndex, 2);    //type
        rowToDelete[3] = ticketsTable.getValueAt(rowIndex, 3);    //noTicket
        rowToDelete[4] = ticketsTable.getValueAt(rowIndex, 7);    //provider name
        rowToDelete[5] = ticketsTable.getValueAt(rowIndex, 13);  //total import

        return ViewUtils.createTableToDelete(rowToDelete);
    }
    
    private void addProviderMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProviderMenuItemActionPerformed
        viewMediator.setProviderLoaderVisible(true);
    }//GEN-LAST:event_addProviderMenuItemActionPerformed

    private void deleteSectorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSectorMenuItemActionPerformed
        int selection = JOptionPane.showConfirmDialog(this, "se le removera el rubro al ticket", "Estas seguro?", JOptionPane.OK_CANCEL_OPTION);
        if (selection == JOptionPane.OK_OPTION) {
            int row = ticketsTable.getSelectedRow();
            String id = (String) ticketsTable.getValueAt(row, 0);
            Filter filter = new Filter("id", id, Comparison.EQUALS);

            controller.deleteWithholdingAttribute(filter, "sector");
            ticketsTable.setValueAt(null, row, 14);   //column 14 is for sector
        }
    }//GEN-LAST:event_deleteSectorMenuItemActionPerformed

    private void exchangeTypeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exchangeTypeMenuItemActionPerformed
        int row = ticketsTable.getSelectedRow();
        Float exchangeType = (Float)ticketsTable.getValueAt(row, 8); //column 8 is for exchange type
        String userInput = JOptionPane.showInputDialog(this, "Tipo de cambio: ", exchangeType);
        if (userInput != null) {
            updateAttribute("exchangeType", userInput, 8); //column 8 is for exchange type
        }
    }//GEN-LAST:event_exchangeTypeMenuItemActionPerformed

    private void createBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createBackupActionPerformed
        JFrame parentFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Elije una carpeta donde guardar el backup");   
        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            BackUpBuilder.saveBackup(file, null);    //null filename will create default backup filename
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_createBackupActionPerformed

    private void loadBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadBackupActionPerformed
        JFrame parentFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Seleccione la carpeta de backup a cargar");   
        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {
                BackUpBuilder.loadBackup(file);
            } catch (Exception e) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                throw e;
            }
            loadTicketsInTable();
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_loadBackupActionPerformed

    private void createPDFMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createPDFMenuItemActionPerformed
        JFrame parentFrame = new JFrame();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");   

        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("pdf")) {
                file = new File(file.toString() + ".pdf");
                file = new File(file.getParentFile(), FilenameUtils.getBaseName(file.getName())+".pdf");
            }
                   
            PdfCreator pdfCreator = new PdfCreator(file.getAbsolutePath(), ticketsTable);
            pdfCreator.setSelectedColumns(getSelectedColumns());
            pdfCreator.createPDF();
        }
    }//GEN-LAST:event_createPDFMenuItemActionPerformed

    private void updateAttribute(String attribute, String value, int column) {
        int row = ticketsTable.getSelectedRow();
        String id = (String) ticketsTable.getValueAt(row, 0);
        Filter filter = new Filter("id", id, Comparison.EQUALS);
        controller.updateTickets(filter, attribute, value);    //update db
        ticketsTable.setValueAt(Float.parseFloat(value), row, column);  //update view
    }
    
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void loadTicketsInTable() {
        List<Withholding> withholdings = controller.getWithholdings();
        List<Ticket> tickets = controller.getTickets();

        DefaultTableModel model = (DefaultTableModel)ticketsTable.getModel();
        cleanTable(model);

        for (Ticket t : tickets) {
            if(!t.isIncome()){
                t = ViewUtils.makeNegative(t);
            }
            model.addRow(ModelToForm.toForm(t));
        }

        for (Withholding w : withholdings) {
            Object[] profitWithholding = ModelToForm.profitWithholdingToForm(w);
            Object[] ivaWithholding = ModelToForm.IVAWithholdingToForm(w);

            if (profitWithholding.length != 0) {
                model.addRow(profitWithholding);
            }
            if (ivaWithholding.length != 0) {
                model.addRow(ivaWithholding);
            }
        }
    }
    
    private void cleanTable(DefaultTableModel model) {
        for (int i = model.getRowCount() - 1; 0 <= i; i--)
            model.removeRow(i);
    }
            
    private boolean[] getSelectedColumns() {
        Map<String, Boolean> config = ConfigManager.readConfig();
        boolean[] selectedColumns = new boolean[17];
        selectedColumns[0] = config.get("id");
        selectedColumns[1] = config.get("date");
        selectedColumns[2] = config.get("type");
        selectedColumns[3] = config.get("noTicket");
        selectedColumns[4] = config.get("numberTo");
        selectedColumns[5] = config.get("authCode");
        selectedColumns[6] = config.get("providerDoc");
        selectedColumns[7] = config.get("providerName");
        selectedColumns[8] = config.get("changeType");
        selectedColumns[9] = config.get("netAmountWI");
        selectedColumns[10] = config.get("netAmountWOI");
        selectedColumns[11] = config.get("amountImpEx");
        selectedColumns[12] = config.get("iva");
        selectedColumns[13] = config.get("totalAmount");
        selectedColumns[14] = config.get("ticketSector");
        selectedColumns[15] = config.get("purchaseNSales");
        selectedColumns[16] = config.get("delivered");
        return selectedColumns;
    }

    public JTable getTicketsTable() { return ticketsTable; }
    
    private ViewMediator viewMediator;
    private Controller controller;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JMenuBar menuBar;
    private JMenu files;
    private JMenu multipleLoad;
    private JMenuItem loadTickets;
    private JMenuItem loadDollarValue;
    private JMenuItem loadTicketManually;
    private JMenuItem loadWithholdingManually;
    private JMenu edit;
    private JMenuItem sectorsViewItem;
    private JMenuItem addProviderMenuItem;
    private JMenu tools;
    private JMenuItem filters;
    private JMenuItem columnSelector;
    private JMenuItem createBackup;
    private JMenuItem loadBackup;
    private JMenuItem createPDFMenuItem;
    private JScrollPane ticketsTableScroll;
    private JTable ticketsTable;
    private JTextField total;
    private JTextField ivaTaxTextField;
    private JButton calculateButton;
    private JButton showProviders;
    private JButton showTickets;
    private JTextField profitTax;
    private JTextField ivaTaxLabel;
    private JTextField profitTaxLabel;
    private JTextField totalLabel;
    private JCheckBox inDollars;
    private JButton resetDBButton;
    private JButton viewMoreCalculusButton;
    private JPopupMenu popupMenu;
    private JMenuItem sectorMenuItem;
    private JMenuItem deliveredMenuItem;
    private JMenuItem exchangeTypeMenuItem;
    private JMenuItem deleteSectorMenuItem;
    private JMenuItem deleteMenuItem;
    private JComboBox<String> sectorComboBox;
    // End of variables declaration//GEN-END:variables

}
