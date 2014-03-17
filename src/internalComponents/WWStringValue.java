package internalComponents;

import com.sun.prism.PixelFormat;
import com.sun.prism.j2d.J2DTexture;
import core.StringValue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Денис on 11.03.14.
 */
public class WWStringValue extends JPanel implements ActionListener{

    JTextField _textField;
    StringValue _value;
    String originalValue,newValue;

    public WWStringValue(StringValue value){
        _value=value;
        originalValue = _value.getStringValue();
        _textField = new JTextField(_value.getStringValue());
        _textField.addActionListener(this);
        _textField.setBorder(null);
        add(_textField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(_textField))
        {

            newValue = e.getActionCommand();
            if (originalValue.equals(newValue)) return;

            String question = "Do you want to change \""+originalValue+"\" by \""+newValue+"\" ?";
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
