package core;

import javax.swing.*;

/**
 * Created by Денис on 11.03.14.
 */
public class StringValue implements IValue {
    private static String _id = IValue.StringValue;
    public static String defaultStringValue = "defaultString";
    private String _value;

    @Override
    public String getStringValue() {
        return _value;
    }

    @Override
    public String getId() {
        return _id;
    }

    public StringValue(){
        _value=defaultStringValue;
    }
    public StringValue(String value){
        _value=value;
    }
    public void SetValue(String newvalue){
        _value = newvalue;
    }

}


