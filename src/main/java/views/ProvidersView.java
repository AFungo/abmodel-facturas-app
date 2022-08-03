/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controller.Controller;
import database.ProviderDAO;
import database.SectorDAO;
import filters.Comparison;
import filters.Filter;
import models.Provider;
import utils.AutoSuggestor;
import utils.ConfigManager;
import utils.FixedData;
import utils.FormatUtils;
import utils.PdfCreator;
import utils.Validate;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Agustin
 */
public class ProvidersView extends JFrame {

    /**
     * Creates new form ProvidersView
     * @param controller
     */
    public ProvidersView(Controller controller, View mainView) {
        this.mainView = mainView;
        this.controller = controller;
        initComponents();
        providersAutoSuggestor = new AutoSuggestor(comboBox, getProvidersName());
        providersAutoSuggestor.autoSuggest();
    }
    
    public void updateSuggestions() {
        providersAutoSuggestor.setSuggestions(getProvidersName());
    }
    
    public javax.swing.JTable getTable() {
        return providersTable;
    }
    
    private List<String> getProvidersName() {
        List<String> names = new LinkedList<>();
        for (Provider p : controller.getProviders()) {
            names.add((String) p.getValues().get("name"));
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
        modifyDirection = new javax.swing.JMenuItem();
        modifySector = new javax.swing.JMenuItem();
        modifyAlias = new javax.swing.JMenuItem();
        modifyName = new javax.swing.JMenuItem();
        deleteSector = new javax.swing.JMenuItem();
        modifyDocumentType = new javax.swing.JMenuItem();
        modifyNoDoc = new javax.swing.JMenuItem();
        deleteProvider = new javax.swing.JMenuItem();
        sectorComboBox = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        providersTable = new javax.swing.JTable();
        searchProvider = new javax.swing.JButton();
        comboBox = new javax.swing.JComboBox<>();
        showAllProviders = new javax.swing.JButton();
        createPdf = new javax.swing.JButton();

        modifyDirection.setText("Modificar direccion");
        modifyDirection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyDirectionActionPerformed(evt);
            }
        });
        popupMenu.add(modifyDirection);

        modifySector.setText("Modificar rubro");
        modifySector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifySectorActionPerformed(evt);
            }
        });
        popupMenu.add(modifySector);

        modifyAlias.setText("Modificar alias");
        modifyAlias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyAliasActionPerformed(evt);
            }
        });
        popupMenu.add(modifyAlias);

        modifyName.setText("Modificar nombre");
        modifyName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyNameActionPerformed(evt);
            }
        });
        popupMenu.add(modifyName);

        deleteSector.setText("Eliminar rubro");
        deleteSector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSectorActionPerformed(evt);
            }
        });
        popupMenu.add(deleteSector);

        modifyDocumentType.setText("Modificar tipo documento");
        modifyDocumentType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyDocumentTypeActionPerformed(evt);
            }
        });
        popupMenu.add(modifyDocumentType);

        modifyNoDoc.setText("Modificar numero documento");
        modifyNoDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyNoDocActionPerformed(evt);
            }
        });
        popupMenu.add(modifyNoDoc);

        deleteProvider.setText("Borrar proveedor");
        deleteProvider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteProviderActionPerformed(evt);
            }
        });
        popupMenu.add(deleteProvider);

        sectorComboBox.setModel(new DefaultComboBoxModel(FormatUtils.listToVector(SectorDAO.getInstance().getAll().stream().toList())));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PROVEDORES");
        //setIconImage(new ImageIcon(getClass().getResource("/IMG/icono-facturas-app-opcion-dos.png")).getImage());
        setLocation(new java.awt.Point(700, 350));

        providersTable.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CUIT", "Nombre", "Alias", "Tipo de documento", "Direccion", "Rubro"
            }
        ) {
            Class[] types = new Class [] {
                Integer.class, String.class, String.class, String.class, String.class, String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        providersTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                providersTableMousePressed(evt);
            }
            public void mouseReleased(MouseEvent evt) {
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

        providersTable.setAutoCreateRowSorter(true);
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

        createPdf.setText("Crear PDF");
        createPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createPdfActionPerformed(evt);
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(createPdf)
                        .addGap(120, 120, 120)))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(showAllProviders)
                    .addComponent(createPdf))
                .addGap(21, 21, 21))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void searchProviderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchProviderActionPerformed
        DefaultTableModel model = (DefaultTableModel)providersTable.getModel();
        cleanTable(model);
        
        List<Filter> filter = new ArrayList<>();
        filter.add(new Filter("name", providersAutoSuggestor.getText(), Comparison.EQUALS));
        List<Provider> providers = controller.getProviders(filter.toArray(new Filter[0]));
        for (Provider p : providers) {
            model.addRow(FormatUtils.providerToForm(p));
        }
    }//GEN-LAST:event_searchProviderActionPerformed

    private void showAllProvidersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showAllProvidersActionPerformed
        DefaultTableModel model = (DefaultTableModel)providersTable.getModel();
        cleanTable(model);
        for (Provider p : controller.getProviders()) {
            model.addRow(FormatUtils.providerToForm(p));
        }
    }//GEN-LAST:event_showAllProvidersActionPerformed

    private void providersTableMouseReleased(MouseEvent evt) {//GEN-FIRST:event_providersTableMouseReleased
        if (evt.getButton() == MouseEvent.BUTTON3) {//right click
            int rowPoint = providersTable.rowAtPoint(evt.getPoint());
            providersTable.clearSelection();
            providersTable.addRowSelectionInterval(rowPoint, rowPoint);
            int row = providersTable.getSelectedRow();
            if (evt.isPopupTrigger() && providersTable.getSelectedRowCount() != 0) {
                selectedDoc = getAttribute(0); //0 is the cuit column
                String sector = (String)providersTable.getValueAt(row, 5); //5 is the sector column
                deleteSector.setEnabled(!(sector == null || sector.isEmpty()));
                deleteProvider.setEnabled(!controller.providerHasTickets(selectedDoc));
                popupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }
    }//GEN-LAST:event_providersTableMouseReleased

    private void providersTableMousePressed(MouseEvent evt) {//GEN-FIRST:event_providersTableMousePressed
        //just to make it work on mac
        providersTableMouseReleased(evt);
    }//GEN-LAST:event_providersTableMousePressed

    private void modifyAliasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyAliasActionPerformed
        String alias = getAttribute(2);
        String userInput = JOptionPane.showInputDialog(this, "alias: ", alias);
        if (userInput != null) {
            updateAttribute("alias", userInput, 2); //column 2 is for alias
        }
    }//GEN-LAST:event_modifyAliasActionPerformed

    private void modifySectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifySectorActionPerformed
        int selection = JOptionPane.showConfirmDialog(this, sectorComboBox, "Seleccione un rubro", JOptionPane.OK_CANCEL_OPTION);
        if (selection == JOptionPane.OK_OPTION) {
            String sector = (String)sectorComboBox.getSelectedItem();
            updateAttribute("sector", sector, 5);   //column 5 is for sector
        }
    }//GEN-LAST:event_modifySectorActionPerformed

    private void modifyDirectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyDirectionActionPerformed
        String direction = getAttribute(4);
        String userInput = JOptionPane.showInputDialog(this, "Dirección: ", direction);
        if (userInput != null) {
            updateAttribute("direction", userInput, 4); //column 4 is for direction
        }
    }//GEN-LAST:event_modifyDirectionActionPerformed

    private void createPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createPdfActionPerformed
        JFrame parentFrame = new JFrame();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Elije un archivo donde guardarlo");   

        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("pdf")) {
                file = new File(file.toString() + ".pdf");
                file = new File(file.getParentFile(), FilenameUtils.getBaseName(file.getName())+".pdf");
            }
                   
            PdfCreator pdfCreator = new PdfCreator(file.getAbsolutePath(), providersTable);
            pdfCreator.setSelectedColumns(getSelectedColumns());
            pdfCreator.createPDF();
        }
    }//GEN-LAST:event_createPdfActionPerformed

    private void deleteSectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSectorActionPerformed
        int selection = JOptionPane.showConfirmDialog(this, "Se le removera el rubro al proveedor", "Estas seguro?", JOptionPane.OK_CANCEL_OPTION);
        if (selection == JOptionPane.OK_OPTION) {
            int row = providersTable.getSelectedRow();
            Filter filter = new Filter("docNo", selectedDoc, Comparison.EQUALS);
            controller.deleteProviderAttribute(filter, "sector");
            providersTable.setValueAt(null, row, 5);   //column 5 is for sector
        }
    }//GEN-LAST:event_deleteSectorActionPerformed

    private void modifyNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyNameActionPerformed
        String name = getAttribute(1);
        String userInput = JOptionPane.showInputDialog(this, "nombre: ", name);
        if (userInput != null) {
            updateAttribute("name", userInput, 1); //column 1 is for name
        }
    }//GEN-LAST:event_modifyNameActionPerformed

    private void modifyDocumentTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyDocumentTypeActionPerformed
        javax.swing.JComboBox<String> documentTypesComboBox = new javax.swing.JComboBox<>(
                new DefaultComboBoxModel<>(FixedData.getDocumentTypes()));
        int selection = JOptionPane.showConfirmDialog(this, documentTypesComboBox, "Seleccione el tipo de documento: ", JOptionPane.OK_CANCEL_OPTION);
        if (selection == JOptionPane.OK_OPTION) {
            String type = (String)documentTypesComboBox.getSelectedItem();
            updateAttribute("documentType", type, 3); //column 3 is for document type
        }
    }//GEN-LAST:event_modifyDocumentTypeActionPerformed

    private void modifyNoDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyNoDocActionPerformed
        String noDoc = getAttribute(0); //column 0 is for docNo
        String userInput = JOptionPane.showInputDialog(this, "Numero documento: ", noDoc);
        if (userInput != null) {
            if (!Validate.tryParse(userInput, Integer.class, false)) {
                JOptionPane.showMessageDialog(this, "El documento introducido esta mal escrito", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int row = providersTable.getSelectedRow();
            if (controller.updateProviderDoc(userInput, selectedDoc)) {
                providersTable.setValueAt(userInput, row, 0);  //update view
            } else {
                JOptionPane.showMessageDialog(this, "El numero de documento elegido ya existe", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_modifyNoDocActionPerformed

    private void deleteProviderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteProviderActionPerformed
        String name = getAttribute(1);
        int selection = JOptionPane.showConfirmDialog(this, "El proveedor " + name + " sera eliminado", "Estas seguro?", JOptionPane.OK_CANCEL_OPTION);
        if (selection == JOptionPane.OK_OPTION) {
            int row = providersTable.getSelectedRow();
            ProviderDAO.getInstance().delete(ProviderDAO.getInstance().getAll().stream()
            .filter(p -> p.getValues().get("docNo").equals(selectedDoc)).findFirst().get());//i think this line is a method in the controller
            row = providersTable.convertRowIndexToModel(row); //translate cell coordinates to DefaultTableModel
            ((DefaultTableModel)providersTable.getModel()).removeRow(row); //remove row from view
        }
        mainView.updateProviders(getProvidersName());
    }//GEN-LAST:event_deleteProviderActionPerformed

    private String getAttribute(int column) {
        int row = providersTable.getSelectedRow();
        return (String)providersTable.getValueAt(row, column);
    }
    
    private void updateAttribute(String attribute, String value, int column) {
        Filter filter = new Filter("docNo", selectedDoc, Comparison.EQUALS);
        controller.updateProvider(filter, attribute, value);    //update db
        int row = providersTable.getSelectedRow();
        providersTable.setValueAt(value, row, column);  //update view
    }
    
    public void updateSectors(List<String> sectors) {
        sectorComboBox.setModel(new DefaultComboBoxModel(FormatUtils.listToVector(sectors)));
    }
    
    public void updateProviders(List<String> names) {
        providersAutoSuggestor.setSuggestions(names);
    }
    
    
    public void updateProviders(java.awt.event.ActionEvent evt) {
        showAllProvidersActionPerformed(evt);
    }
    
    private void cleanTable(DefaultTableModel model) {
        for (int i = model.getRowCount() - 1; 0 <= i; i--)
            model.removeRow(i);
    }
    
    private boolean[] getSelectedColumns() {
        Map<String, Boolean> config = ConfigManager.readConfig();
        boolean[] selectedColumns = new boolean[6];
        selectedColumns[0] = config.get("docNo");
        selectedColumns[1] = config.get("providerName");
        selectedColumns[2] = config.get("alias");
        selectedColumns[3] = config.get("docType");
        selectedColumns[4] = config.get("direction");
        selectedColumns[5] = config.get("providerSector");
        return selectedColumns;
    }
    
    private Controller controller;
    private AutoSuggestor providersAutoSuggestor;
    private String selectedDoc;
    private View mainView;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboBox;
    private javax.swing.JButton createPdf;
    private javax.swing.JMenuItem deleteProvider;
    private javax.swing.JMenuItem deleteSector;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem modifyAlias;
    private javax.swing.JMenuItem modifyDirection;
    private javax.swing.JMenuItem modifyDocumentType;
    private javax.swing.JMenuItem modifyName;
    private javax.swing.JMenuItem modifyNoDoc;
    private javax.swing.JMenuItem modifySector;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JTable providersTable;
    private javax.swing.JButton searchProvider;
    private javax.swing.JComboBox<String> sectorComboBox;
    private javax.swing.JButton showAllProviders;
    // End of variables declaration//GEN-END:variables
}
