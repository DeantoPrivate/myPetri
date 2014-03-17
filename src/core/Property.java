package core;

import javax.swing.*;

/**
 * Created by Денис on 11.03.14.
 */
public class Property{
    private static String _defaultName = "nonamed";
    private static String _defaultValue = "novalue";
    private String _name;
    private IValue _value;

    public String getName() {
        return _name;
    }

    public IValue getValue() {
        return _value;
    }

    public Property(){
        _name = _defaultName;
        _value = new StringValue(_defaultValue);
    }

    public Property(String name,IValue value){
        _name = name;
        _value = value;
    }
}
