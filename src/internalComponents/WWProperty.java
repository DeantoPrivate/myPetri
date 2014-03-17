package internalComponents;

import core.IValue;
import core.Property;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Денис on 11.03.14.
 */
public class WWProperty extends JPanel{
    //TODO: make a separated class for constants
    public static String PropertyName = "#:";
    public static String PropertyValue = " :->";
    private Property _property;

    void Init(){
        setPreferredSize(new Dimension(390,50));
        setBorder(BorderFactory.createLineBorder(Color.RED));
    }

    public WWProperty(){}

    public WWProperty(Property property){
    SetProperty(property);
    }

    public void SetProperty(Property property){
        Init();
        _property=property;

        JTextArea name = new JTextArea(PropertyName);
        name.setEditable(false);
        add(name);
        JTextArea v= new JTextArea(_property.getName());
        v.setEditable(false);
        add(v);
        JTextArea valuex = new JTextArea(PropertyValue);
        valuex.setEditable(false);
        add(valuex);
        JPanel value = WWFabric.GetWWPanel(_property.getValue());
        add(value);
    }

    public static String PropertyNameNew = "Enter a property name.";
    public static String selectValueQuestion = "Select a value type.";

    public void ConstractProperty(){
        String name=null;
        while (name==null)
            name = JOptionPane.showInputDialog(this,PropertyNameNew);

        String[] values = {IValue.IntegerValue,IValue.StringValue};
        String select = (String)JOptionPane.showInputDialog(this,selectValueQuestion,"question",JOptionPane.QUESTION_MESSAGE,null,values,null);

        IValue value = WWFabric.GetValue(select);
        _property = new Property(name, value);
    }

    public Property GetProperty(){
        return _property;
    }
}
