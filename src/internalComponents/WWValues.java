package internalComponents;

import core.IValue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Денис on 11.03.14.
 *
 * component to work with all values: show,change etc.
 */
public class WWValues extends JPanel implements ActionListener{
    private IValue _value;
    private JTextField _textField;
    public void SetValue(IValue value){
        _value = value;
        _textField = new JTextField(_value.getStringValue());
        _textField.addActionListener(this);
        add(_textField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       int t=0;
    }

    JDialog _changeDialog;

}
