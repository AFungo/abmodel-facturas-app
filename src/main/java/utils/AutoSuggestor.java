/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * This class implements a comboBox who make to the user suggestion with the strings 
 * user write in the combobox.
 */
public class AutoSuggestor {
    
    private JComboBox comboBox;
    private JTextField textField;
    private Vector<String> suggestions;
    private boolean hide_Flag = false;
    
    /**
     * Constructor of the class
     * @param comboBox combo box who need a auto suggestor
     * @param suggestions the list of options for suggest to the user
     */        
    public AutoSuggestor(JComboBox comboBox, List<String> suggestions) {
        this.comboBox = comboBox;
        this.textField = (JTextField)comboBox.getEditor().getEditorComponent();
        setSuggestions(suggestions);
    }

    /**
     * Add a new list of suggestion to the autoAuggestor
     * @param suggestions list of the new suggestions
     */ 
    public void setSuggestions(List<String> suggestions) {
        SortedSet<String> sortedSuggestions = new TreeSet<>(suggestions);
        this.suggestions = setToVector(sortedSuggestions);
        setModel(getSuggestedModel(this.suggestions, ""), "");
    }
    
    /**
     * Modify the model of combo box
     * @param model a new model for the comboBox
     * @param str new string for the tex field  
     */ 
    private void setModel(DefaultComboBoxModel model, String str) {
        comboBox.setModel(model);
        comboBox.setSelectedIndex(-1);
        textField.setText(str);
    }
    
    /**
     * Create a new combo box with the suggestions
     * @param list list of the elements of the comboBox
     * @param text current text be write in the comboBox
     * @return a new combo box with the suggestions
     */
    private DefaultComboBoxModel getSuggestedModel(List<String> list, String text) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        for (String s : list) {
            if (s.toUpperCase().contains(text.toUpperCase())) {
                m.addElement(s);
            }
        }
        
        return m;
    }
    
    /**
    * @return the String who be in the textField
    */
    public String getText() {
        return textField.getText();
    }
    
    /**
     * Modifi the text of textField
     * @param t new string to put in textField
     */    
    public void setText(String t) {
        textField.setText(t);
    }
    /**
     * this class show in the comboBox the suggestions after whe input a char in the combobox 
     */    
    public void autoSuggest() {
        comboBox.setEditable(true);
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
                            DefaultComboBoxModel m = getSuggestedModel(suggestions, text);
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
                String text = textField.getText();
                int code = e.getKeyCode();
                switch (code) {
                    case KeyEvent.VK_ENTER:
                        if (suggestions.contains(text)) {
                            suggestions.addElement(text);
                            Collections.sort(suggestions);
                            setModel(getSuggestedModel(suggestions, text), text);
                        }   
                        hide_Flag = true;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        hide_Flag = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        for (int i = 0; i < suggestions.size(); i++) {
                            String str = suggestions.elementAt(i);
                            if (str.contains(text)) {
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
    
    /*
    *create a new vector of a set
    *@return a vector of strings
    */
    private Vector<String> setToVector(Set<String> set) {
        Vector<String> vector = new Vector<>();
        for (String str : set) {
            vector.add(str);
        }
        return vector;
    }
}