/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.views;

import facturas.app.Controller;
import facturas.app.database.SectorDAO;
import facturas.app.models.Provider;
import facturas.app.utils.AutoSuggestor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JTextField;

/**
 *
 * @author Lenovo
 */
public class ProviderLoader extends javax.swing.JFrame {

    /**
     * Creates new form providerLoader
     */
    public ProviderLoader(Controller controller, View mainView) {
        initComponents();
        this.controller = controller;
        this.mainView = mainView;
        sectorsAutoSuggestor = new AutoSuggestor(sectorsComboBox, getSectors());
        sectorsAutoSuggestor.autoSuggest();
    }
    
     private List<String> getSectors() {
        return SectorDAO.getSectors();
    }
    public void updateSuggestions() {
        sectorsAutoSuggestor.setSuggestions(getSectors());
    }
    private List<String> getProvidersName() {
        List<String> names = new LinkedList<>();
        for(Provider p : controller.getProviders()) {
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

        invalidParamDialog = new javax.swing.JOptionPane();
        jLabel1 = new javax.swing.JLabel();
        providerAddressTextField = new javax.swing.JTextField();
        providerDocTypeComboBox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        providerNameTextField = new javax.swing.JTextField();
        providerDocTextField = new javax.swing.JTextField();
        providerAliasTextField = new javax.swing.JTextField();
        sectorsComboBox = new javax.swing.JComboBox<>();
        addProviderButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("CARGAR PROVEEDOR");

        providerDocTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CUIT", "CUIL" }));

        jLabel2.setText("Razon social");

        jLabel3.setText("Nombre ficticio");

        jLabel4.setText("Direccion");

        jLabel5.setText("Numero Documento");

        jLabel6.setText("Rubro");

        jLabel7.setText("Tipo documento");

        addProviderButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        addProviderButton.setText("Añadir proveedor");
        addProviderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProviderButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(providerDocTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(providerAddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(37, 37, 37)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(addProviderButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(providerDocTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))))
                                .addGap(40, 40, 40)
                                .addComponent(providerAliasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(providerNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(37, 37, 37)
                                .addComponent(jLabel5)
                                .addGap(126, 126, 126)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(sectorsComboBox, 0, 180, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 11, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(133, 133, 133)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(providerNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(providerDocTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sectorsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(providerAddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(providerDocTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(providerAliasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(addProviderButton)
                .addContainerGap(96, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addProviderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProviderButtonActionPerformed
        //provider attributes
        Map<String, String> values = new HashMap();
        values.put("docNo", providerDocTextField.getText());
        values.put("name", providerNameTextField.getText());
        values.put("direction, ", providerAddressTextField.getText()); // may be null
        values.put("provSector", (String) sectorsComboBox.getSelectedItem());//may be null
        values.put("alias", providerAliasTextField.getText());      //may be null
        values.put("docType", providerDocTypeComboBox.getSelectedItem().toString());
        values.put("provSector", (String)sectorsComboBox.getSelectedItem());

        String errorMessage = controller.validateProviderParam(values, sectorsComboBox);
        if (errorMessage != null) {
            invalidParamDialog.showMessageDialog(null, errorMessage, "Los siguientes datos son invalidos", invalidParamDialog.ERROR_MESSAGE);
            return ;
        }
        
        controller.addProvider(values);
        mainView.updateProviders(getProvidersName());
        cleanTextField();
    }//GEN-LAST:event_addProviderButtonActionPerformed
    
    public void updateSectors(List<String> sectors) {
        sectorsAutoSuggestor.setSuggestions(sectors);
    }
    
    private void cleanTextField(){
        JTextField[] forClean = new JTextField[] {providerAddressTextField, providerAliasTextField, providerDocTextField, providerNameTextField};        
        controller.cleanTextField(forClean);
    }
    View mainView;
    Controller controller;
    AutoSuggestor sectorsAutoSuggestor;
    AutoSuggestor providersAutoSuggestor;        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addProviderButton;
    private javax.swing.JOptionPane invalidParamDialog;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField providerAddressTextField;
    private javax.swing.JTextField providerAliasTextField;
    private javax.swing.JTextField providerDocTextField;
    private javax.swing.JComboBox<String> providerDocTypeComboBox;
    private javax.swing.JTextField providerNameTextField;
    private javax.swing.JComboBox<String> sectorsComboBox;
    // End of variables declaration//GEN-END:variables
}
