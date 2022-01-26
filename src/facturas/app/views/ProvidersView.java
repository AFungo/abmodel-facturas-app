/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.views;

import facturas.app.Controller;
import facturas.app.database.ProviderDAO;
import facturas.app.models.Provider;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Agustin
 */
public class ProvidersView extends javax.swing.JFrame {

    private Controller controller;
    private JTextField nameTextField;
    
    /**
     * Creates new form ProvidersView
     */
    public ProvidersView(Controller controller) {
        this.controller = controller;
        initComponents();
        loadProviders();
        autoSuggest();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        providersTable = new javax.swing.JTable();
        showProviders = new javax.swing.JButton();
        comboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        providersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CUIT", "Nombre", "Tipo de documento"
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
        jScrollPane1.setViewportView(providersTable);
        providersTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        providersTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(providersTable);
        if (providersTable.getColumnModel().getColumnCount() > 0) {
            providersTable.getColumnModel().getColumn(0).setPreferredWidth(2);
            providersTable.getColumnModel().getColumn(1).setPreferredWidth(3);
        }

        DefaultTableModel model = (DefaultTableModel)providersTable.getModel();
        for (Provider p : controller.getProviders()) {
            model.addRow(new Object[] {p.getCuit(), p.getName(), p.getDocType()});
        }
        providersTable.setCellSelectionEnabled(true);
        providersTable.setVisible(true);

        showProviders.setText("Mostrar proveedores");
        showProviders.setPreferredSize(new java.awt.Dimension(140, 23));
        showProviders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showProvidersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(85, Short.MAX_VALUE)
                .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(showProviders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(showProviders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void showProvidersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showProvidersActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)providersTable.getModel();
        cleanTable(model);
        List<Provider> providers = ProviderDAO.getProviders(nameTextField.getText());
        for (Provider p : providers) {
            model.addRow(new Object[] {p.getCuit(), p.getName(), p.getDocType()});
        }
    }//GEN-LAST:event_showProvidersActionPerformed

    private void cleanTable(DefaultTableModel model) {
        for (int i = model.getRowCount() - 1; 0 <= i; i--)
            model.removeRow(i);
    }

    private final Vector<String> v = new Vector<>();
    private boolean hide_Flag = false;
    
    private void loadProviders() {
        List<Provider> providers = controller.getProviders();
        for (Provider p : providers) {
            v.add(p.getName());
        }
    }
    
    private void setModel(DefaultComboBoxModel model, String str) {
        comboBox.setModel(model);
        comboBox.setSelectedIndex(-1);
        nameTextField.setText(str);
    }
    
    private static DefaultComboBoxModel getSuggestedModel(List<String> list, String text) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        for (String s : list) {
            if (s.startsWith(text)) {
                m.addElement(s);
            }
        }
        
        return m;
    }
    
    private void autoSuggest() {
        nameTextField = new JTextField();
        nameTextField.setText("");
        comboBox.setEditable(true);
        nameTextField = (JTextField)comboBox.getEditor().getEditorComponent();
        nameTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        String text = nameTextField.getText();
                        if (text.length() == 0) {
                            comboBox.hidePopup();
                            setModel(new DefaultComboBoxModel(v), text);
                        } else {
                            DefaultComboBoxModel m = getSuggestedModel(v, text);
                            if (m.getSize() == 0 || hide_Flag) {
                                comboBox.hidePopup();
                                hide_Flag = false;
                            } else {
                                setModel(m, text);
                                comboBox.showPopup();
                            }
                        }
                    }
                
                });
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
                String text = nameTextField.getText();
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_ENTER) {
                    if (v.contains(text)) {
                        v.addElement(text);
                        Collections.sort(v);
                        setModel(getSuggestedModel(v, text), text);
                    }
                    hide_Flag = true;
                } else if (code == KeyEvent.VK_ESCAPE) {
                    hide_Flag = true;
                } else if (code == KeyEvent.VK_RIGHT) {
                    for (int i = 0; i < v.size(); i++) {
                        String str = v.elementAt(i);
                        if (str.startsWith(text)) {
                            comboBox.setSelectedIndex(-1);
                            nameTextField.setText(str);
                            return;
                        }
                    }
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboBox;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable providersTable;
    private javax.swing.JButton showProviders;
    // End of variables declaration//GEN-END:variables
}
