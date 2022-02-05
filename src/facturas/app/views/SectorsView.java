/*S
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package facturas.app.views;

import facturas.app.database.ProviderDAO;
import facturas.app.database.SQLFilter;
import facturas.app.database.SectorDAO;
import facturas.app.database.TicketDAO;
import facturas.app.models.Ticket;
import facturas.app.utils.AutoSuggestor;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author agustinnolasco
 */
public class SectorsView extends javax.swing.JFrame {

    /**
     * Creates new form SectorsView
     */
    public SectorsView() {
        initComponents();
        sectorsAutoSuggestor = new AutoSuggestor(sectorsComboBox, getSectors());
        sectorsAutoSuggestor.autoSuggest();
    }
     
    public void updateSuggestions() {
        sectorsAutoSuggestor.setSuggestions(getSectors());
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

        updateSectorOptionPane = new javax.swing.JOptionPane();
        addSector = new javax.swing.JButton();
        deleteSector = new javax.swing.JButton();
        sectorsComboBox = new javax.swing.JComboBox<>();
        updateSector = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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
    }// </editor-fold>//GEN-END:initComponents

    private void addSectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSectorActionPerformed
        if (!SectorDAO.sectorExist(sectorsAutoSuggestor.getText())) {
            SectorDAO.addSector(sectorsAutoSuggestor.getText());
            updateSuggestions();
            sectorsAutoSuggestor.setText("");
        } // TODO: Maybe if the sector already exists then show a massage
    }//GEN-LAST:event_addSectorActionPerformed

    private void deleteSectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSectorActionPerformed
        if (SectorDAO.sectorExist(sectorsAutoSuggestor.getText())) {
            SectorDAO.deleteSector(sectorsAutoSuggestor.getText());
            updateSuggestions();
            sectorsAutoSuggestor.setText("");
        } // TODO: Maybe if the sector is deleted then show a massage
        // FIXME: If a sector is related with a ticket or provider then it cant
        // be removed
    }//GEN-LAST:event_deleteSectorActionPerformed

    private void updateSectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateSectorActionPerformed
        String userInput = updateSectorOptionPane.showInputDialog(null, "Nuevo nombre: ", "");
        if (userInput != null && sectorsAutoSuggestor.getText() != null) {
            SQLFilter filter = new SQLFilter();
            filter.add("sector", "=", sectorsAutoSuggestor.getText(), String.class);
            SectorDAO.addSector(userInput);
            TicketDAO.changeSector(filter, userInput);
            SectorDAO.deleteSector(sectorsAutoSuggestor.getText());
            sectorsAutoSuggestor.setText("");
            updateSuggestions();
        }
    }//GEN-LAST:event_updateSectorActionPerformed

    AutoSuggestor sectorsAutoSuggestor;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addSector;
    private javax.swing.JButton deleteSector;
    private javax.swing.JComboBox<String> sectorsComboBox;
    private javax.swing.JButton updateSector;
    private javax.swing.JOptionPane updateSectorOptionPane;
    // End of variables declaration//GEN-END:variables
}
