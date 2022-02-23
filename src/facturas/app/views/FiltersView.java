/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.views;

import com.toedter.calendar.JTextFieldDateEditor;
import facturas.app.Controller;
import facturas.app.database.ProviderDAO;
import facturas.app.database.SQLFilter;
import facturas.app.database.SectorDAO;
import facturas.app.models.Provider;
import facturas.app.models.Ticket;
import facturas.app.models.Withholding;
import facturas.app.utils.AutoSuggestor;
import facturas.app.utils.FixedData;
import facturas.app.utils.FormatUtils;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Agustin
 */
public class FiltersView extends javax.swing.JFrame {

    /**
     * Creates new form FiltersView
     * @param controller
     * @param ticketsTable
     */
    public FiltersView(Controller controller, JTable ticketsTable) {
        this.controller = controller;
        this.ticketsTable = ticketsTable;
        initComponents();
        providersAutoSuggestor = new AutoSuggestor(providersComboBox, getProvidersName());
        providersAutoSuggestor.autoSuggest();
        sectorsAutoSuggestor = new AutoSuggestor(sectorsComboBox, SectorDAO.getSectors());
        sectorsAutoSuggestor.autoSuggest();
    }
    
    public void updateSuggestions() {
        providersAutoSuggestor.setSuggestions(getProvidersName());
        sectorsAutoSuggestor.setSuggestions(SectorDAO.getSectors());
    }
    
    private List<String> getProvidersName() {
        List<String> names = new LinkedList<>();
        for (Provider p : controller.getProviders()) {
            names.add(p.getValues().get("name"));
        }
        return names;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        purchaseNSellButtonGroup = new javax.swing.ButtonGroup();
        withholdingGroupButton = new javax.swing.ButtonGroup();
        minTotalAmountTextField = new javax.swing.JTextField();
        maxTotalAmountTextField = new javax.swing.JTextField();
        minIvaTextField = new javax.swing.JTextField();
        maxIvaTextField = new javax.swing.JTextField();
        minDateLabel = new javax.swing.JLabel();
        maxDateLabel = new javax.swing.JLabel();
        minTotalAmountLabel = new javax.swing.JLabel();
        maxTotalAmountLabel = new javax.swing.JLabel();
        minIvaLabel = new javax.swing.JLabel();
        maxIvaLabel = new javax.swing.JLabel();
        cuitLabel = new javax.swing.JLabel();
        appyFilters = new javax.swing.JButton();
        ticketTypesScrollPane = new javax.swing.JScrollPane();
        ticketTypesList = new javax.swing.JList<>(FixedData.getTicketTypes());
        minDateChooser = new com.toedter.calendar.JDateChooser();
        maxDateChooser = new com.toedter.calendar.JDateChooser();
        purchaseRadioButton = new javax.swing.JRadioButton();
        saleRadioButton = new javax.swing.JRadioButton();
        bothRadioButton = new javax.swing.JRadioButton();
        withholdingRadioButton = new javax.swing.JRadioButton();
        ticketRadioButton = new javax.swing.JRadioButton();
        bothRadioButton2 = new javax.swing.JRadioButton();
        providersComboBox = new javax.swing.JComboBox<>();
        sectorsComboBox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        noTicketWithholdingTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        idTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        docNoTextField = new javax.swing.JTextField();
        cleanDateFilters = new javax.swing.JButton();
        cleanFilters = new javax.swing.JButton();

        purchaseNSellButtonGroup.add(purchaseRadioButton);
        purchaseNSellButtonGroup.add(saleRadioButton);
        purchaseNSellButtonGroup.add(bothRadioButton);

        withholdingGroupButton.add(withholdingRadioButton);
        withholdingGroupButton.add(ticketRadioButton);
        withholdingGroupButton.add(bothRadioButton2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("FILTROS");
        setLocation(new java.awt.Point(0, 350));

        minDateLabel.setText("Fecha Minino");

        maxDateLabel.setText("Fecha Maxima");

        minTotalAmountLabel.setText("Importe Total Min.");

        maxTotalAmountLabel.setText("Importe Total Max.");

        minIvaLabel.setText("IVA Minimo");

        maxIvaLabel.setText("IVA Maximo");

        cuitLabel.setText("Proveedor");

        appyFilters.setText("Aplicar filtros");
        appyFilters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appyFiltersActionPerformed(evt);
            }
        });

        ticketTypesList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] types = FixedData.getTicketTypes();
            public int getSize() { return types.length; }
            public String getElementAt(int i) { return types[i]; }
        });
        ticketTypesScrollPane.setViewportView(ticketTypesList);
        ticketTypesList.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if(super.isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                }
                else {
                    super.addSelectionInterval(index0, index1);
                }
                fireValueChanged(index0, index1);
            }
        });

        minDateChooser.setDateFormatString("dd-MM-yyyy");
        JTextFieldDateEditor textField = (JTextFieldDateEditor) minDateChooser.getDateEditor();
        textField.setEditable(false);

        maxDateChooser.setDateFormatString("dd-MM-yyyy");
        textField = (JTextFieldDateEditor) maxDateChooser.getDateEditor();
        textField.setEditable(false);

        purchaseRadioButton.setText("Compras");

        saleRadioButton.setText("Ventas");

        bothRadioButton.setSelected(true);
        bothRadioButton.setText("Ambos");

        withholdingRadioButton.setText("Retenciones");

        ticketRadioButton.setText("Facturas");

        bothRadioButton2.setSelected(true);
        bothRadioButton2.setText("Ambos");

        jLabel1.setText("Rubro");

        jLabel2.setText("Nro. Comprob/Reten");

        jLabel3.setText("ID");

        jLabel4.setText("Prov. num. doc.");

        cleanDateFilters.setText("Limpiar fechas");
        cleanDateFilters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanDateFiltersActionPerformed(evt);
            }
        });

        cleanFilters.setText("Limpiar filtros");
        cleanFilters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanFiltersActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(minDateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(minTotalAmountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cuitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(minIvaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(minDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(minTotalAmountTextField)
                                .addComponent(minIvaTextField)
                                .addComponent(providersComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(docNoTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(noTicketWithholdingTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 24, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(maxTotalAmountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(maxIvaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(maxDateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sectorsComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(maxDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(maxTotalAmountTextField)
                            .addComponent(maxIvaTextField)
                            .addComponent(idTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bothRadioButton)
                                    .addComponent(saleRadioButton)
                                    .addComponent(purchaseRadioButton))
                                .addGap(56, 56, 56)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bothRadioButton2)
                                    .addComponent(withholdingRadioButton)
                                    .addComponent(ticketRadioButton)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(appyFilters, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ticketTypesScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cleanDateFilters, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(cleanFilters, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(noTicketWithholdingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(idTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(minDateLabel)
                        .addComponent(maxDateLabel))
                    .addComponent(maxDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(minTotalAmountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maxTotalAmountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minTotalAmountLabel)
                    .addComponent(maxTotalAmountLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(minIvaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maxIvaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minIvaLabel)
                    .addComponent(maxIvaLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cuitLabel)
                    .addComponent(providersComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sectorsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(docNoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cleanDateFilters)
                                .addGap(18, 18, 18)
                                .addComponent(cleanFilters))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(purchaseRadioButton)
                                    .addComponent(withholdingRadioButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(saleRadioButton)
                                    .addComponent(ticketRadioButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(bothRadioButton)
                                    .addComponent(bothRadioButton2))
                                .addGap(34, 34, 34)
                                .addComponent(appyFilters)))
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ticketTypesScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void appyFiltersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_appyFiltersActionPerformed
        List<Withholding> tickets = controller.getWithholdings(getFilters(false));
        tickets.addAll(controller.getTickets(getFilters(true)));
        
        DefaultTableModel model = (DefaultTableModel)ticketsTable.getModel();
        cleanTable(model);
        for (Withholding t : tickets) {
            if(t instanceof Ticket){
                if(!((Ticket)t).isIncome()){
                    t = controller.makeNegative((Ticket) t);
                }
            }
            if (withholdingRadioButton.isSelected() && !(t instanceof Ticket)) {
                model.addRow(FormatUtils.ticketToForm(t));
            } else if (ticketRadioButton.isSelected() && t instanceof Ticket) {
                model.addRow(FormatUtils.ticketToForm(t));
            } else if (bothRadioButton2.isSelected()) {
                model.addRow(FormatUtils.ticketToForm(t));
            }
        }
    }//GEN-LAST:event_appyFiltersActionPerformed

    private void cleanDateFiltersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanDateFiltersActionPerformed
        minDateChooser.setDate(null);
        maxDateChooser.setDate(null);
        
    }//GEN-LAST:event_cleanDateFiltersActionPerformed

    private void cleanFiltersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanFiltersActionPerformed
        cleanDateFiltersActionPerformed(evt);
        noTicketWithholdingTextField.setText("");
        idTextField.setText("");
        minTotalAmountTextField.setText("");
        maxTotalAmountTextField.setText("");
        minIvaTextField.setText("");
        maxIvaTextField.setText("");
        providersComboBox.setSelectedItem(null);
        sectorsComboBox.setSelectedItem(null);
        docNoTextField.setText("");
        bothRadioButton.setSelected(true);
        bothRadioButton2.setSelected(true);
        ticketTypesList.clearSelection();
    }//GEN-LAST:event_cleanFiltersActionPerformed

    public SQLFilter getFilters(boolean isTicket) {
        SQLFilter selectedFilters = new SQLFilter();
        
        if (FormatUtils.tryParse(idTextField.getText(), "Integer")) {             
            selectedFilters.add("id", "=", Integer.parseInt(idTextField.getText()), Integer.class);
        }
        if (FormatUtils.tryParse(noTicketWithholdingTextField.getText(), "Integer")) {
            selectedFilters.add("number", "=", noTicketWithholdingTextField.getText(), String.class);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(minDateChooser.getDate()!=null) {
            selectedFilters.add("date", ">=", FormatUtils.dateGen(sdf.format(minDateChooser.getDate())), Date.class);
        } 
        if(maxDateChooser.getDate()!=null) {
            selectedFilters.add("date", "<=", FormatUtils.dateGen(sdf.format(maxDateChooser.getDate())), Date.class);
        }
        
        if (FormatUtils.tryParse(minTotalAmountTextField.getText(), "Float")) {
            selectedFilters.add("totalAmount", ">=", Float.parseFloat(minTotalAmountTextField.getText()), Float.class);
        }
        if (FormatUtils.tryParse(maxTotalAmountTextField.getText(), "Float")) {
            selectedFilters.add("totalAmount", "<=", Float.parseFloat(maxTotalAmountTextField.getText()), Float.class);       
        }        
        
        SQLFilter providersDoc = new SQLFilter();
        String selectedProvider = providersAutoSuggestor.getText();
        if (selectedProvider != null && !selectedProvider.isEmpty()) {
            providersDoc.add("name", "=", selectedProvider, String.class);
            if (!ProviderDAO.providerExist(providersDoc)) {
                throw new IllegalArgumentException("provider doesn't exists");
            }
            selectedFilters.addDisjunction("providerDoc", "=", 
                    getDocsList(controller.getProviders(providersDoc)), String.class);
        }
        
        String selectedSector = sectorsAutoSuggestor.getText();
        if (selectedSector != null && !selectedSector.isEmpty()) {
            if (SectorDAO.sectorExist(selectedSector)) {
                throw new IllegalArgumentException("sector doesn't exists");
            }
            selectedFilters.add("sector", "=", selectedSector, String.class);
        }
        
        if (FormatUtils.tryParse(docNoTextField.getText(), "Integer")) {
            selectedFilters.add("providerDoc", "=", docNoTextField.getText(), String.class);
        }
        
        if (!isTicket) {
            return selectedFilters;
        }
             
        if (FormatUtils.tryParse(minIvaTextField.getText(), "Float")) {
            selectedFilters.add("iva", ">=", Float.parseFloat(minIvaTextField.getText()), Float.class);
        }
        if (FormatUtils.tryParse(maxIvaTextField.getText(), "Float")) {
            selectedFilters.add("iva", "<=", Float.parseFloat(maxIvaTextField.getText()), Float.class);
        }  
           
        if (!ticketTypesList.getSelectedValuesList().isEmpty()) {
            selectedFilters.addDisjunction("type", "=", ticketTypesList.getSelectedValuesList(), String.class);
        }
        
        if (saleRadioButton.isSelected()) {
            selectedFilters.add("issuedByMe", "=", true, Boolean.class);
        } else if (purchaseRadioButton.isSelected()) {
            selectedFilters.add("issuedByMe", "=", false, Boolean.class);
        }
        
        return selectedFilters;
    }
    
    private List<String> getDocsList(List<Provider> list) {
        List<String> namesList = new LinkedList<>();
        for (Provider p : list) {
            namesList.add(p.getValues().get("docNo"));
        }
        return namesList;
    }
    
    public void updateSectors(List<String> sectors) {
        sectorsAutoSuggestor.setSuggestions(sectors);
    }
    
    public void updateProviders(List<String> names) {
        providersAutoSuggestor.setSuggestions(names);
    }
    
    private void cleanTable(DefaultTableModel model) {
        for (int i = model.getRowCount() - 1; 0 <= i; i--)
            model.removeRow(i);
    }
    
    private Controller controller;
    private JTable ticketsTable;
    private AutoSuggestor providersAutoSuggestor;
    private AutoSuggestor sectorsAutoSuggestor;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton appyFilters;
    private javax.swing.JRadioButton bothRadioButton;
    private javax.swing.JRadioButton bothRadioButton2;
    private javax.swing.JButton cleanDateFilters;
    private javax.swing.JButton cleanFilters;
    private javax.swing.JLabel cuitLabel;
    private javax.swing.JTextField docNoTextField;
    private javax.swing.JTextField idTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private com.toedter.calendar.JDateChooser maxDateChooser;
    private javax.swing.JLabel maxDateLabel;
    private javax.swing.JLabel maxIvaLabel;
    private javax.swing.JTextField maxIvaTextField;
    private javax.swing.JLabel maxTotalAmountLabel;
    private javax.swing.JTextField maxTotalAmountTextField;
    private com.toedter.calendar.JDateChooser minDateChooser;
    private javax.swing.JLabel minDateLabel;
    private javax.swing.JLabel minIvaLabel;
    private javax.swing.JTextField minIvaTextField;
    private javax.swing.JLabel minTotalAmountLabel;
    private javax.swing.JTextField minTotalAmountTextField;
    private javax.swing.JTextField noTicketWithholdingTextField;
    private javax.swing.JComboBox<String> providersComboBox;
    private javax.swing.ButtonGroup purchaseNSellButtonGroup;
    private javax.swing.JRadioButton purchaseRadioButton;
    private javax.swing.JRadioButton saleRadioButton;
    private javax.swing.JComboBox<String> sectorsComboBox;
    private javax.swing.JRadioButton ticketRadioButton;
    private javax.swing.JList<String> ticketTypesList;
    private javax.swing.JScrollPane ticketTypesScrollPane;
    private javax.swing.ButtonGroup withholdingGroupButton;
    private javax.swing.JRadioButton withholdingRadioButton;
    // End of variables declaration//GEN-END:variables
}
