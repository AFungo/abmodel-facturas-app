package views.utils;

import javax.swing.*;

public class ViewUtils {

    public static void cleanTextField(JTextField[] textField) {
        for(JTextField t : textField) t.setText("");
    }

}
