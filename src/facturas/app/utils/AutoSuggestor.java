/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.utils;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author Agustin
 */
public class AutoSuggestor {
    
    private JComboBox comboBox;
    private JTextField textField;
    private Vector<String> suggestions;
    private boolean hide_Flag = false;
    
    public AutoSuggestor(JComboBox comboBox, JTextField textField, List<String> suggestions) {
        this.comboBox = comboBox;
        this.textField = textField;
        setSuggestions(suggestions);
    }
    
    public void setSuggestions(List<String> suggestions) {
        this.suggestions = FormatUtils.listToVector(suggestions);
    }
    
    private void setModel(DefaultComboBoxModel model, String str) {
        comboBox.setModel(model);
        comboBox.setSelectedIndex(-1);
        textField.setText(str);
    }
    
    private static DefaultComboBoxModel getSuggestedModel(List<String> list, String text) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        list.stream().filter((s) -> (s.startsWith(text))).forEachOrdered((s) -> {
            m.addElement(s);
        });
        
        return m;
    }
    
    public String getText() {
        return textField.getText();
    }
    
    public void setText(String t) {
        textField.setText(t);
    }
    
    // FIXME: The suggestion is not case sensitive, for the providers problem
    // works well but maybe in other context the case sensitive could be important
    public void autoSuggest() {
        comboBox.setEditable(true);
        textField = (JTextField)comboBox.getEditor().getEditorComponent();
        setModel(getSuggestedModel(suggestions, ""), "");
        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        String text = textField.getText();
                        if (text.length() == 0) {
                            comboBox.hidePopup();
                            setModel(new DefaultComboBoxModel(suggestions), text);
                        } else {
                            DefaultComboBoxModel m = getSuggestedModel(suggestions, text.toUpperCase());
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
                String text = textField.getText().toUpperCase();
                int code = e.getKeyCode();
                switch (code) {
                    case KeyEvent.VK_ENTER:
                        if (suggestions.contains(text)) {
                            suggestions.addElement(text);
                            Collections.sort(suggestions);
                            setModel(getSuggestedModel(suggestions, text), text);
                        }   hide_Flag = true;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        hide_Flag = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        for (int i = 0; i < suggestions.size(); i++) {
                            String str = suggestions.elementAt(i);
                            if (str.startsWith(text)) {
                                comboBox.setSelectedIndex(-1);
                                textField.setText(str);
                                return;
                            }
                        }   break;
                    default:
                        break;
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }
    
}