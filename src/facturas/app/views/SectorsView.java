/*S
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package facturas.app.views;

import facturas.app.database.ProviderDAO;
import facturas.app.database.SQLFilter;
import facturas.app.database.SectorDAO;
import facturas.app.database.WithholdingDAO;
import facturas.app.utils.AutoSuggestor;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author agustinnolasco
 */
public class SectorsView extends javax.swing.JFrame {

    /**
     * Creates new form SectorsView
     * @param mainView
     */
    public SectorsView(View mainView) {
        initComponents();
        sectorsAutoSuggestor = new AutoSuggestor(sectorsComboBox, getSectors());
        sectorsAutoSuggestor.autoSuggest();
        this.mainView = mainView;
    }
     
    public void updateSuggestions() {
        List<String> sectors = getSectors();
        sectorsAutoSuggestor.setSuggestions(sectors);
        mainView.updateSectors(sectors);
    }
    
    // FIXME: Maybe we should use the controller here
    private List<String> getSectors() {
        return SectorDAO.get();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addSector = new javax.swing.JButton();
        deleteSector = new javax.swing.JButton();
        sectorsComboBox = new javax.swing.JComboBox<>();
        updateSector = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("RUBROS");
        setIconImage(new ImageIcon(getClass().getResource("/IMG/icono-facturas-app-opcion-dos.png")).getImage()
        );

        addSector.setText("Agregar");
        addSector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSectorActionPerformed(evt);
            }
        });

        deleteSector.setText("Borrar");
        deleteSector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSectorActionPerformed(evt);
            }
        });

        updateSector.setText("Actualizar");
        updateSector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateSectorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(addSector, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(updateSector, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(deleteSector, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sectorsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(sectorsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addSector)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(deleteSector)
                        .addComponent(updateSector)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addSectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSectorActionPerformed
        String newSector = sectorsAutoSuggestor.getText();
        if (!newSector.trim().isEmpty() && !SectorDAO.exist(newSector)) {
            SectorDAO.add(sectorsAutoSuggestor.getText());
            updateSuggestions();
            sectorsAutoSuggestor.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "El rubro ingresado es vacio o ya existe",
                    "Rubto invalido", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_addSectorActionPerformed

    private void deleteSectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSectorActionPerformed
        String sector = sectorsAutoSuggestor.getText();
        if (sector != null && SectorDAO.exist(sector)) {
            int userInput = JOptionPane.showConfirmDialog(this, 
                    "¿Seguro que desea eliminar el rubro " + sector + "?", 
                    "Confirmacion", 0, 2
            );
            if (userInput == 0) {
                SectorDAO.remove(sectorsAutoSuggestor.getText());
                updateSuggestions();
                sectorsAutoSuggestor.setText("");
            }
        }
    }//GEN-LAST:event_deleteSectorActionPerformed

    private void updateSectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateSectorActionPerformed
        String userInput = JOptionPane.showInputDialog(this, "Nuevo nombre: ", "");
        if (userInput != null && sectorsAutoSuggestor.getText() != null) {
            SQLFilter filter = new SQLFilter();
            filter.add("sector", "=", sectorsAutoSuggestor.getText(), String.class);
            SectorDAO.add(userInput);
            WithholdingDAO.update(filter, "sector", userInput, true);
            ProviderDAO.update(filter, "sector", userInput);
            SectorDAO.remove(sectorsAutoSuggestor.getText());
            sectorsAutoSuggestor.setText("");
            updateSuggestions();
        }
    }//GEN-LAST:event_updateSectorActionPerformed

    private AutoSuggestor sectorsAutoSuggestor;
    private View mainView;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addSector;
    private javax.swing.JButton deleteSector;
    private javax.swing.JComboBox<String> sectorsComboBox;
    private javax.swing.JButton updateSector;
    // End of variables declaration//GEN-END:variables
}
