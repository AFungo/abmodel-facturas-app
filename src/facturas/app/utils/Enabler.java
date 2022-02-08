/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facturas.app.utils;

import java.util.LinkedList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author agustinnolasco
 */
public class Enabler {
    
    List<JComboBox> comboBoxes = new LinkedList<>();
    List<JTextField> textFields = new LinkedList<>();
    
    public void setEnabled(boolean p) {
        for (JComboBox cb : comboBoxes) {
            cb.setEnabled(p);
        }
        for (JTextField tf : textFields) {
            tf.setEnabled(p);
        }
    }
    
    public void addComboBox(JComboBox cb) {
        comboBoxes.add(cb);
    }
    
    public void addTextField(JTextField tf) {
        textFields.add(tf);
    }
    
}
