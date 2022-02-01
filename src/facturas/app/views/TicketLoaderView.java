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
import facturas.app.utils.Pair;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JTextField;

/**
 *
 * @author agustinnolasco
 */
public class TicketLoaderView extends javax.swing.JFrame {

    /**
     * Creates new form TicketLoaderView
     */
    public TicketLoaderView(Controller controller) {
        this.controller = controller;
        initComponents();
        providersAutoSuggestor = new AutoSuggestor(providersComboBox, providersTextField, getProvidersName());
        providersAutoSuggestor.autoSuggest();
        sectorsAutoSuggestor = new AutoSuggestor(sectorsComboBox, sectorsTextField, getSectors());
        sectorsAutoSuggestor.autoSuggest();
    }
    
    public void updateSuggestions() {
        providersAutoSuggestor.setSuggestions(getProvidersName());
        sectorsAutoSuggestor.setSuggestions(getSectors());
    }
    
    private List<String> getProvidersName() {
        List<String> names = new LinkedList<>();
        for (Provider p : controller.getProviders()) {
            names.add(p.getName());
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
        typeTextField = new javax.swing.JTextField();
        numberTextField = new javax.swing.JTextField();
        providerDocTextField = new javax.swing.JTextField();
        providerNameTextField = new javax.swing.JTextField();
        exchangeTypeTextField = new javax.swing.JTextField();
        exchangeMoneyTextField = new javax.swing.JTextField();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CARGAR COMPROBANTE");

        jLabel1.setText("Fecha");

        jLabel2.setText("Tipo");

        jLabel3.setText("Numero de Comprobante");

        jLabel7.setText("Tipo Doc. Emisor");

        jLabel8.setText("Numero Doc. Emisor");

        jLabel9.setText("Denominacion Emisor");

        jLabel10.setText("Tipo de Cambio");

        jLabel11.setText("Moneda");

        jLabel12.setText("Imp. Neto Gravado");

        jLabel13.setText("Imp. Neto no Gravado");

        jLabel14.setText("Imp. Op. Exentas");

        jLabel15.setText("IVA");

        jLabel16.setText("Imp. Total");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dateDateChooser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(providerDocTypeComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(exchangeTypeTextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(netAmountWOITextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(typeTextField)
                                .addComponent(amountImpExTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(providerDocTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(exchangeMoneyTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(numberTextField)
                                .addComponent(ivaTextField)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(providerNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(netAmountWITextField)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(totalAmountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(issuedByMeCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sectorsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(providersComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(loadTicket)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(typeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(numberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(issuedByMeCheckBox)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(providerDocTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(providerDocTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(providerNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(providersComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(exchangeTypeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(netAmountWITextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sectorsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(exchangeMoneyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(netAmountWOITextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(amountImpExTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ivaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalAmountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(loadTicket)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void loadTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadTicketActionPerformed
        // TODO add your handling code here:
        Map<String, String> values = new HashMap<>();
        values.put("amountImpEx", amountImpExTextField.getText());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        values.put("date", sdf.format(dateDateChooser.getDate()));
        values.put("exchangeType", exchangeTypeTextField.getText());
        values.put("iva", ivaTextField.getText());
        values.put("netAmountWI", netAmountWITextField.getText());
        values.put("netAmountWOI", netAmountWOITextField.getText());
        values.put("number", numberTextField.getText());
        
        if (providersComboBox.getSelectedItem() != null) {
            SQLFilter filter = new SQLFilter();
            filter.add(new Pair<>("name", "="), new Pair<>(providersComboBox.getSelectedItem(), String.class));
            List<Provider> providers = ProviderDAO.getProviders(filter);
            values.put("providerCuit", providers.get(0).getCuit());
            values.put("providerDocType", providers.get(0).getDocType());
            values.put("providerName", providers.get(0).getName());
        } else {
            values.put("providerCuit", providerDocTextField.getText());
            values.put("providerDocType", providerDocTypeComboBox.getSelectedItem().toString());
            values.put("providerName", providerNameTextField.getText());
        }
        
        values.put("totalAmount", totalAmountTextField.getText());
        values.put("type", typeTextField.getText());
        values.put("issuedByMe", String.valueOf(issuedByMeCheckBox.isSelected()));
        
        if (sectorsComboBox.getSelectedItem() != null) {
            values.put("sector", sectorsComboBox.getSelectedItem().toString());
        }


        controller.loadTicket(values);
    }//GEN-LAST:event_loadTicketActionPerformed

    private void providersComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_providersComboBoxItemStateChanged
        // TODO add your handling code here:
        setEnabledProvidersDataLoader(providersComboBox.getSelectedItem() == null);
    }//GEN-LAST:event_providersComboBoxItemStateChanged
    
    private void setEnabledProvidersDataLoader(boolean value) {
        providerDocTextField.setEnabled(value);
        providerNameTextField.setEnabled(value);
        providerDocTypeComboBox.setEnabled(value);
    }
    
    //"Fecha","Tipo","Punto de Venta","Número Desde","Número Hasta","Cód. Autorización",
    //"Tipo Doc. Emisor","Nro. Doc. Emisor","Denominación Emisor","Tipo Cambio","Moneda",
    //"Imp. Neto Gravado","Imp. Neto No Gravado","Imp. Op. Exentas","IVA","Imp. Total"

    Controller controller;
    AutoSuggestor providersAutoSuggestor;
    AutoSuggestor sectorsAutoSuggestor;
    JTextField providersTextField;
    JTextField sectorsTextField;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField amountImpExTextField;
    private com.toedter.calendar.JDateChooser dateDateChooser;
    private javax.swing.JTextField exchangeMoneyTextField;
    private javax.swing.JTextField exchangeTypeTextField;
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
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton loadTicket;
    private javax.swing.JTextField netAmountWITextField;
    private javax.swing.JTextField netAmountWOITextField;
    private javax.swing.JTextField numberTextField;
    private javax.swing.JTextField providerDocTextField;
    private javax.swing.JComboBox<String> providerDocTypeComboBox;
    private javax.swing.JTextField providerNameTextField;
    private javax.swing.JComboBox<String> providersComboBox;
    private javax.swing.JComboBox<String> sectorsComboBox;
    private javax.swing.JTextField totalAmountTextField;
    private javax.swing.JTextField typeTextField;
    // End of variables declaration//GEN-END:variables
}
