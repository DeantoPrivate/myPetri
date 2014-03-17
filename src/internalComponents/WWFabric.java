package internalComponents;

import core.IValue;
import core.IntegerValue;
import core.StringValue;

import javax.swing.*;

/**
 * Created by Денис on 11.03.14.
 */
public class WWFabric {
    public static JPanel GetWWPanel(IValue value){
        if (value.getId().equals(IValue.StringValue)) return new WWStringValue((StringValue)value);
        else if (value.getId().equals(IValue.IntegerValue)) return new WWIntegerValue((IntegerValue)value);
        else return null;
    }

    public static IValue GetValue(String id){
        if(id.equals(IValue.IntegerValue)) return new IntegerValue(0);
        else if (id.equals(IValue.StringValue)) return new StringValue();
        else return null;
    }
}
