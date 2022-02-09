/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package facturas.app.views;

import com.toedter.calendar.JTextFieldDateEditor;
import facturas.app.Controller;
import facturas.app.database.ProviderDAO;
import facturas.app.database.SQLFilter;
import facturas.app.database.SectorDAO;
import facturas.app.models.Provider;
import facturas.app.utils.AutoSuggestor;
import facturas.app.utils.Enabler;
import facturas.app.utils.FormatUtils;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;

/**
 *
 * @author agustinnolasco
 */
public class TicketLoaderView extends javax.swing.JFrame {

    /**
     * Creates new form TicketLoaderView
     * @param controller
     */
    public TicketLoaderView(Controller controller) {
        this.controller = controller;
        initComponents();
        providersAutoSuggestor = new AutoSuggestor(providersComboBox, getProvidersName());
        providersAutoSuggestor.autoSuggest();
        sectorsAutoSuggestor = new AutoSuggestor(sectorsComboBox, getSectors());
        sectorsAutoSuggestor.autoSuggest();
        providerSectorsAutoSuggestor = new AutoSuggestor(providerSectorComboBox, getSectors());
        providersAutoSuggestor.autoSuggest();
        exchangeTypeTextField.setEditable(false);
        
        e = providersAutoSuggestor.getEnabler();
        e.addComboBox(providerDocTypeComboBox);
        e.addComboBox(providerSectorComboBox);
        e.addTextField(providerDocTextField);
        e.addTextField(providerNameTextField);
        e.addTextField(providerAddressTextField);
        e.addTextField(providerAliasTextField);
    }
    
    public void updateSuggestions() {
        providersAutoSuggestor.setSuggestions(getProvidersName());
        sectorsAutoSuggestor.setSuggestions(getSectors());
        providerSectorsAutoSuggestor.setSuggestions(getSectors());
    }
    
    private List<String> getProvidersName() {
        List<String> names = new LinkedList<>();
        for (Provider p : controller.getProviders()) {
            names.add(p.getValues().get("name"));
        }
        return names;
    }
    
    // FIXME: Maybe we should use the controller here
    private List<String> getSectors() {
        return SectorDAO.getSectors();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        invalidParamDialog = new javax.swing.JOptionPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        numberTextField = new javax.swing.JTextField();
        providerDocTextField = new javax.swing.JTextField();
        providerNameTextField = new javax.swing.JTextField();
        exchangeTypeTextField = new javax.swing.JTextField();
        netAmountWITextField = new javax.swing.JTextField();
        netAmountWOITextField = new javax.swing.JTextField();
        amountImpExTextField = new javax.swing.JTextField();
        ivaTextField = new javax.swing.JTextField();
        totalAmountTextField = new javax.swing.JTextField();
        loadTicket = new javax.swing.JButton();
        issuedByMeCheckBox = new javax.swing.JCheckBox();
        dateDateChooser = new com.toedter.calendar.JDateChooser();
        providerDocTypeComboBox = new javax.swing.JComboBox<>();
        providersComboBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        sectorsComboBox = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        addNewProvider = new javax.swing.JLabel();
        ticketType = new javax.swing.JComboBox<>();
        providerSectorComboBox = new javax.swing.JComboBox<>();
        providerAddressTextField = new javax.swing.JTextField();
        rubroLabel = new javax.swing.JLabel();
        addressLabel = new javax.swing.JLabel();
        exchangeMoneyComboBox = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        providerAliasTextField = new javax.swing.JTextField();
        addNewProvider1 = new javax.swing.JLabel();
        addNewProvider2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CARGAR COMPROBANTE");

        jLabel1.setText("Fecha");

        jLabel2.setText("Tipo");

        jLabel3.setText("Numero de Comprobante");

        jLabel7.setText("Tipo Doc. Proveedor");

        jLabel8.setText("Numero Doc. Proveedor");

        jLabel9.setText("Denominacion Proveedor");

        jLabel10.setText("Tipo de Cambio");

        jLabel11.setText("Moneda");

        jLabel12.setText("Imp. Neto Gravado");

        jLabel13.setText("Imp. Neto no Gravado");

        jLabel14.setText("Imp. Op. Exentas");

        jLabel15.setText("IVA");

        jLabel16.setText("Imp. Total");

        exchangeTypeTextField.setText("1");

        loadTicket.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        loadTicket.setText("Agregar comprobante");
        loadTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadTicketActionPerformed(evt);
            }
        });

        issuedByMeCheckBox.setText("Emitido por mi");

        dateDateChooser.setDateFormatString("dd-MM-yyyy");
        JTextFieldDateEditor textField = (JTextFieldDateEditor) dateDateChooser.getDateEditor();
        textField.setEditable(false);

        providerDocTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CUIT", "CUIL" }));
        providerDocTypeComboBox.setSelectedIndex(-1);

        providersComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                providersComboBoxItemStateChanged(evt);
            }
        });

        jLabel4.setText("Proveedores existentes");

        jLabel5.setText("Rubro");

        addNewProvider.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        addNewProvider.setText("Comprobante");

        ticketType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Factura A", "Factura B", "Factura C", "Nota de credito A", "Nota de credito B ", "Nota de credito C", "Nota de debito A", "Nota de debito B ", "Nota de debito C", "Liquidacion", " " }));

        providerSectorComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        rubroLabel.setText("Rubro");

        addressLabel.setText("Direccion");

        exchangeMoneyComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pesos", "USD", "Euro", "Real" }));
        exchangeMoneyComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exchangeMoneyComboBoxActionPerformed(evt);
            }
        });

        jLabel6.setText("Alias Proveedor");

        addNewProvider1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        addNewProvider1.setText("Importes");

        addNewProvider2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        addNewProvider2.setText("Añadir un nuevo proveedor");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(providerDocTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(providerDocTextField)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(providerNameTextField)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 144, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rubroLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(providerSectorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(addressLabel)
                                    .addComponent(providerAddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(providerAliasTextField)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(289, 289, 289))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addNewProvider)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(exchangeTypeTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(exchangeMoneyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(netAmountWOITextField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(netAmountWITextField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(amountImpExTextField)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ivaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(totalAmountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(providersComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(sectorsComboBox, 0, 150, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(ticketType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(numberTextField)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(dateDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(loadTicket)
                                    .addComponent(issuedByMeCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(addNewProvider1))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addNewProvider2)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addNewProvider)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(providersComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ticketType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(41, 41, 41)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sectorsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(numberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dateDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(loadTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(issuedByMeCheckBox)))))
                .addGap(18, 18, 18)
                .addComponent(addNewProvider1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exchangeMoneyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(netAmountWITextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ivaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(netAmountWOITextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(amountImpExTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exchangeTypeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalAmountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(addNewProvider2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(rubroLabel)
                    .addComponent(addressLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(providerDocTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(providerDocTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(providerNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(providerSectorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(providerAddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(providerAliasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ticketType.getAccessibleContext().setAccessibleName("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void loadTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadTicketActionPerformed
        Map<String, String> values = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        //amounts
        values.put("amountImpEx", amountImpExTextField.getText());
        values.put("exchangeType", exchangeTypeTextField.getText());
        values.put("iva", ivaTextField.getText());
        values.put("netAmountWI", netAmountWITextField.getText());
        values.put("netAmountWOI", netAmountWOITextField.getText());
        values.put("exchangeMoney", (String) exchangeMoneyComboBox.getSelectedItem());
        values.put("totalAmount", totalAmountTextField.getText());
        
        //ticket data
        values.put("number", numberTextField.getText());
        values.put("type", (String) ticketType.getSelectedItem());
        values.put("issuedByMe", String.valueOf(issuedByMeCheckBox.isSelected()));        
        
        //provider attributes
        values.put("docNo", providerDocTextField.getText());
        values.put("name", providerNameTextField.getText());
        values.put("direction, ", providerAddressTextField.getText()); // may be null
        values.put("provSector", (String) providerSectorComboBox.getSelectedItem());//may be null
        values.put("alias", providerAliasTextField.getText());      //may be null

        String errorMessage = controller.validateParam(dateDateChooser.getDate(), providersComboBox, providerDocTypeComboBox, values, true);
        if (errorMessage != null) {
            invalidParamDialog.showMessageDialog(null, errorMessage, "Los siguientes datos son invalidos", invalidParamDialog.ERROR_MESSAGE);
            return ;
        }
               
        values.put("date", sdf.format(dateDateChooser.getDate()));
        if (providersComboBox.getSelectedItem() != null) {
            SQLFilter filter = new SQLFilter();
            filter.add("name", "=", providersComboBox.getSelectedItem(), String.class);
            Provider provider = ProviderDAO.getProviders(filter).get(0);
            values.put("docNo", provider.getValues().get("docNo"));
            values.put("docType", provider.getValues().get("docType"));
            values.put("name", provider.getValues().get("name"));
            values.put("provSector", provider.getValues().get("provSector"));
            values.put("alias", (String) provider.getValues().get("alias"));
        } else {
            values.put("docType", providerDocTypeComboBox.getSelectedItem().toString());
        }
        
        if (sectorsComboBox.getSelectedItem() != null) {
            values.put("sector", sectorsComboBox.getSelectedItem().toString());
        }

        controller.loadTicket(values);
        List<JTextField> forClean = new LinkedList();
        forClean.add(numberTextField);
        forClean.add(netAmountWITextField);
        forClean.add(netAmountWOITextField);
        forClean.add(amountImpExTextField);
        forClean.add(ivaTextField);
        forClean.add(totalAmountTextField);
        forClean.add(providerDocTextField);
        forClean.add(providerNameTextField);
        forClean.add(providerAliasTextField);
        forClean.add(providerAddressTextField);
        controller.cleanTextField(forClean);
    }//GEN-LAST:event_loadTicketActionPerformed

    private void exchangeMoneyComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exchangeMoneyComboBoxActionPerformed
        if(exchangeMoneyComboBox.getSelectedItem().equals("Pesos")){
            exchangeTypeTextField.setEditable(false);
            exchangeTypeTextField.setText("1");
        }
        else exchangeTypeTextField.setEditable(true);
    }//GEN-LAST:event_exchangeMoneyComboBoxActionPerformed

    private void providersComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_providersComboBoxItemStateChanged
        if (e != null) {
            e.setEnabled(providersComboBox.getSelectedItem() == null);
        }
    }//GEN-LAST:event_providersComboBoxItemStateChanged
    
    public void updateSectors(List<String> sectors) {
        Vector<String> sectorsVector = FormatUtils.listToVector(sectors);
        sectorsComboBox.setModel(new DefaultComboBoxModel(sectorsVector));
        providerSectorComboBox.setModel(new DefaultComboBoxModel(sectorsVector));
    }
    
    Controller controller;
    AutoSuggestor providersAutoSuggestor;
    AutoSuggestor sectorsAutoSuggestor;
    AutoSuggestor providerSectorsAutoSuggestor;    
    Enabler e;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addNewProvider;
    private javax.swing.JLabel addNewProvider1;
    private javax.swing.JLabel addNewProvider2;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JTextField amountImpExTextField;
    private com.toedter.calendar.JDateChooser dateDateChooser;
    private javax.swing.JComboBox<String> exchangeMoneyComboBox;
    private javax.swing.JTextField exchangeTypeTextField;
    private javax.swing.JOptionPane invalidParamDialog;
    private javax.swing.JCheckBox issuedByMeCheckBox;
    private javax.swing.JTextField ivaTextField;
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
    private javax.swing.JButton loadTicket;
    private javax.swing.JTextField netAmountWITextField;
    private javax.swing.JTextField netAmountWOITextField;
    private javax.swing.JTextField numberTextField;
    private javax.swing.JTextField providerAddressTextField;
    private javax.swing.JTextField providerAliasTextField;
    private javax.swing.JTextField providerDocTextField;
    private javax.swing.JComboBox<String> providerDocTypeComboBox;
    private javax.swing.JTextField providerNameTextField;
    private javax.swing.JComboBox<String> providerSectorComboBox;
    private javax.swing.JComboBox<String> providersComboBox;
    private javax.swing.JLabel rubroLabel;
    private javax.swing.JComboBox<String> sectorsComboBox;
    private javax.swing.JComboBox<String> ticketType;
    private javax.swing.JTextField totalAmountTextField;
    // End of variables declaration//GEN-END:variables
}
