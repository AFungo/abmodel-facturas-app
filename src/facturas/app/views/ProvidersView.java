/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.views;

import facturas.app.Controller;
import facturas.app.database.ProviderDAO;
import facturas.app.database.SQLFilter;
import facturas.app.database.SectorDAO;
import facturas.app.models.Provider;
import facturas.app.utils.AutoSuggestor;
import facturas.app.utils.FormatUtils;
import facturas.app.utils.Pair;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Agustin
 */
public class ProvidersView extends javax.swing.JFrame {

    /**
     * Creates new form ProvidersView
     */
    public ProvidersView(Controller controller) {
        this.controller = controller;
        initComponents();
        autoSuggestor = new AutoSuggestor(comboBox, nameTextField, getProvidersName());
        autoSuggestor.autoSuggest();
    }
    
    public void updateSuggestions() {
        autoSuggestor.setSuggestions(getProvidersName());
    }
    
    public javax.swing.JTable getTable() {
        return providersTable;
    }
    
    private List<String> getProvidersName() {
        List<String> names = new LinkedList<>();
        for (Provider p : controller.getProviders()) {
            names.add(p.getName());
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

        popupMenu = new javax.swing.JPopupMenu();
        directionMenuItem = new javax.swing.JMenuItem();
        sectorMenuItem = new javax.swing.JMenuItem();
        optionPane = new javax.swing.JOptionPane();
        sectorComboBox = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        providersTable = new javax.swing.JTable();
        searchProvider = new javax.swing.JButton();
        comboBox = new javax.swing.JComboBox<>();
        showAllProviders = new javax.swing.JButton();

        directionMenuItem.setText("Modificar direccion");
        directionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directionMenuItemActionPerformed(evt);
            }
        });
        popupMenu.add(directionMenuItem);

        sectorMenuItem.setText("Modificar rubro");
        sectorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sectorMenuItemActionPerformed(evt);
            }
        });
        popupMenu.add(sectorMenuItem);

        optionPane.setWantsInput(true);

        sectorComboBox.setModel(new DefaultComboBoxModel(FormatUtils.listToVector(SectorDAO.getSectors())));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PROVEDORES");
        setLocation(new java.awt.Point(700, 350));

        providersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CUIT", "Nombre", "Tipo de documento", "Direccion", "Rubro"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        providersTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                providersTableMouseReleased(evt);
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

        providersTable.setCellSelectionEnabled(true);
        providersTable.setVisible(true);

        searchProvider.setText("Buscar proveedor");
        searchProvider.setPreferredSize(new java.awt.Dimension(140, 23));
        searchProvider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchProviderActionPerformed(evt);
            }
        });

        showAllProviders.setText("Mostrar proveedores");
        showAllProviders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAllProvidersActionPerformed(evt);
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(85, Short.MAX_VALUE)
                .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(showAllProviders, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchProvider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(112, 112, 112))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchProvider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showAllProviders)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchProviderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchProviderActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)providersTable.getModel();
        cleanTable(model);
        
        SQLFilter filter = new SQLFilter();
        filter.add(new Pair<>("name", "="), new Pair<>(autoSuggestor.getText(), String.class));
        List<Provider> providers = ProviderDAO.getProviders(filter);
        for (Provider p : providers) {
            model.addRow(FormatUtils.providerToForm(p));
        }
    }//GEN-LAST:event_searchProviderActionPerformed

    private void showAllProvidersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showAllProvidersActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)providersTable.getModel();
        cleanTable(model);
        for (Provider p : controller.getProviders()) {
            model.addRow(FormatUtils.providerToForm(p));
        }
    }//GEN-LAST:event_showAllProvidersActionPerformed

    private void providersTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_providersTableMouseReleased
        if (evt.getButton() == MouseEvent.BUTTON3) {//right click
            if (evt.isPopupTrigger() && providersTable.getSelectedRowCount() != 0) {
                int row = providersTable.getSelectedRow();
                selectedCuit = (String)providersTable.getValueAt(row, 0); //0 is the cuit column
                popupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }
    }//GEN-LAST:event_providersTableMouseReleased

    private void directionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directionMenuItemActionPerformed
        String userInput = optionPane.showInputDialog(null, "Dirección: ", "");
        if (userInput != null) {
            ProviderDAO.changeDirection(selectedCuit, userInput);
            int row = providersTable.getSelectedRow();
            providersTable.setValueAt(userInput, row, 3);   //column 3 is for direction
        }
    }//GEN-LAST:event_directionMenuItemActionPerformed

    private void sectorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sectorMenuItemActionPerformed
        int selection = optionPane.showConfirmDialog(null, sectorComboBox, "Seleccione un rubro", optionPane.OK_CANCEL_OPTION);
        if (selection == optionPane.OK_OPTION) {
            String sector = (String)sectorComboBox.getSelectedItem();
            ProviderDAO.changeSector(selectedCuit, sector);
            int row = providersTable.getSelectedRow();
            providersTable.setValueAt(sector, row, 4);   //column 4 is for sector
        }
    }//GEN-LAST:event_sectorMenuItemActionPerformed

    public void updateProviders(java.awt.event.ActionEvent evt) {
        showAllProvidersActionPerformed(evt);
    }
    
    
    private void cleanTable(DefaultTableModel model) {
        for (int i = model.getRowCount() - 1; 0 <= i; i--)
            model.removeRow(i);
    }
    
    
    private Controller controller;
    private AutoSuggestor autoSuggestor;
    private JTextField nameTextField;
    private String selectedCuit;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboBox;
    private javax.swing.JMenuItem directionMenuItem;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JOptionPane optionPane;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JTable providersTable;
    private javax.swing.JButton searchProvider;
    private javax.swing.JComboBox<String> sectorComboBox;
    private javax.swing.JMenuItem sectorMenuItem;
    private javax.swing.JButton showAllProviders;
    // End of variables declaration//GEN-END:variables
}
