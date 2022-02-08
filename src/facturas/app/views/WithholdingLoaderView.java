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
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author agustinnolasco
 */
public class WithholdingLoaderView extends javax.swing.JFrame {

    /**
     * Creates new form WithholdingLoaderView
     */
    public WithholdingLoaderView(Controller controller) {
        this.controller = controller;
        initComponents();
        providersAutoSuggestor = new AutoSuggestor(providersComboBox, getProvidersName());
        providersAutoSuggestor.autoSuggest();
        sectorsAutoSuggestor = new AutoSuggestor(sectorsComboBox, getSectors());
        sectorsAutoSuggestor.autoSuggest();
        notificationView = new NotificationView();
        
        Enabler e = providersAutoSuggestor.getEnabler();
        e.addComboBox(providerDocTypeComboBox);
        e.addTextField(providerNameTextField);
        e.addTextField(providerDocTextField);
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
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        numberTextField = new javax.swing.JTextField();
        providerDocTextField = new javax.swing.JTextField();
        providerNameTextField = new javax.swing.JTextField();
        totalAmountTextField = new javax.swing.JTextField();
        loadWithholding = new javax.swing.JButton();
        dateDateChooser = new com.toedter.calendar.JDateChooser();
        providerDocTypeComboBox = new javax.swing.JComboBox<>();
        providersComboBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        sectorsComboBox = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        typeComboBox = new javax.swing.JComboBox<>();
        retentionTypeLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CARGAR COMPROBANTE");

        jLabel1.setText("Fecha");

        jLabel3.setText("Numero de Comprobante");

        jLabel7.setText("Tipo Doc. Emisor");

        jLabel8.setText("Numero Doc. Emisor");

        jLabel9.setText("Denominacion Emisor");

        jLabel16.setText("Imp. Total");

        loadWithholding.setText("Agregar comprobante");
        loadWithholding.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadWithholdingActionPerformed(evt);
            }
        });

        dateDateChooser.setDateFormatString("dd-MM-yyyy");
        JTextFieldDateEditor textField = (JTextFieldDateEditor) dateDateChooser.getDateEditor();
        textField.setEditable(false);

        providerDocTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CUIT", "CUIL" }));
        providerDocTypeComboBox.setSelectedIndex(-1);

        jLabel4.setText("Proveedores existentes");

        jLabel5.setText("Rubro");

        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Retencion iva", "Retencion ganancias" }));
        typeComboBox.setSelectedIndex(-1);

        retentionTypeLabel.setText("Tipo de Retencion");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dateDateChooser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(providerDocTypeComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(providerDocTextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(numberTextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(providerNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sectorsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(providersComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(totalAmountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(307, 307, 307)
                                .addComponent(loadWithholding))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(retentionTypeLabel)
                                    .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dateDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(4, 4, 4)
                                .addComponent(numberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sectorsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalAmountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(providerDocTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(providerDocTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(providerNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(providersComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(18, 18, 18)
                .addComponent(retentionTypeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(loadWithholding)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void loadWithholdingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadWithholdingActionPerformed
        Map<String, String> values = new HashMap<>();
        
        values.put("number", numberTextField.getText());
        values.put("totalAmount", totalAmountTextField.getText());
        values.put("docNo", providerDocTextField.getText());
        values.put("name", providerNameTextField.getText());
        if (typeComboBox.getSelectedItem() != null)
            values.put("type", typeComboBox.getSelectedItem().toString());
        else
            values.put("type", "");
        
        String errorMessage = controller.validateParam(dateDateChooser.getDate(), providersComboBox, 
                providerDocTypeComboBox, values, false);
        if (errorMessage != null) {
            notificationView.changeText(errorMessage);
            notificationView.setVisible(true);
            return ;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        values.put("date", sdf.format(dateDateChooser.getDate()));
        
        if (providersComboBox.getSelectedItem() != null) {
            SQLFilter filter = new SQLFilter();
            filter.add("name", "=", providersComboBox.getSelectedItem(), String.class);
            Provider provider = ProviderDAO.getProviders(filter).get(0);
            values.put("docNo", provider.getDocNo());
            values.put("docType", provider.getDocType());
            values.put("name", provider.getName());
            values.put("provSector", provider.getSector());
        } else {
            values.put("docType", providerDocTypeComboBox.getSelectedItem().toString());
        }
        
        if (sectorsComboBox.getSelectedItem() != null) {
            values.put("sector", sectorsComboBox.getSelectedItem().toString());
        }

        controller.loadWithholding(values);
    }//GEN-LAST:event_loadWithholdingActionPerformed
    
    public void updateSectors(List<String> sectors) {
        sectorsComboBox.setModel(new DefaultComboBoxModel(FormatUtils.listToVector(sectors)));
    }
    
    Controller controller;
    AutoSuggestor providersAutoSuggestor;
    AutoSuggestor sectorsAutoSuggestor;
    NotificationView notificationView;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser dateDateChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton loadWithholding;
    private javax.swing.JTextField numberTextField;
    private javax.swing.JTextField providerDocTextField;
    private javax.swing.JComboBox<String> providerDocTypeComboBox;
    private javax.swing.JTextField providerNameTextField;
    private javax.swing.JComboBox<String> providersComboBox;
    private javax.swing.JLabel retentionTypeLabel;
    private javax.swing.JComboBox<String> sectorsComboBox;
    private javax.swing.JTextField totalAmountTextField;
    private javax.swing.JComboBox<String> typeComboBox;
    // End of variables declaration//GEN-END:variables
}
