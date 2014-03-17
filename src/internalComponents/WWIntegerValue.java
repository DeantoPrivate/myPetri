package internalComponents;

import core.IntegerValue;

import javax.swing.*;
import javax.swing.tree.ExpandVetoException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Денис on 11.03.14.
 */
public class WWIntegerValue extends JPanel implements ActionListener {
    JTextField _textField;
    IntegerValue _value;
    Integer originalValue,newValue;

    public WWIntegerValue(IntegerValue value){
        _value=value;
        originalValue = _value.GetIntegerValue();
        _textField = new JTextField(_value.getStringValue());
        _textField.addActionListener(this);
        _textField.setColumns(4);
        add(_textField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(_textField))
        {
            try{
            newValue = Integer.parseInt(e.getActionCommand());
            }
            catch (Exception ee)
            {
                _value.SetValue(originalValue);
                _textField.setText(originalValue.toString());
                return;
            }

            if (originalValue==newValue) return;

            String question = "Do you want \nto change \""+originalValue+"\" \nby \""+newValue+"\" ?";
            int n = JOptionPane.showOptionDialog(this,question,"Confirm change",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,1);
            if (n==0)
            {
                // change
                _value.SetValue(newValue);
                originalValue=newValue;
            }

        }
    }
}
