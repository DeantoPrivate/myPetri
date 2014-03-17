package fileSavers;

import core.IValue;
import core.IntegerValue;
import core.StringValue;

import java.util.ArrayList;

/**
 * Created by Денис on 12.03.14.
 */
public class FSRIValue {
    public static StringBuilder SaveIValue(IValue value){

    if (value.getId().equals(IValue.StringValue)) return FSRStringValue.SaveStringValue((StringValue)value);
    else if (value.getId().equals(IValue.IntegerValue)) return FSRIntegerValue.SaveIntegerValue((IntegerValue)value);
    else return null;
    }

    public static IValue ReadIValue(ArrayList<String> strings){
        IValue value=null;

        String check = strings.get(0);
        String checkStringValue = check.substring(0, FSRStringValue.FileSaveStringValue.length());
        if (checkStringValue.equals(FSRStringValue.FileSaveStringValue))
            value=FSRStringValue.ReadStringValue(strings);

        String checkIntegerValue = check.substring(0, FSRIntegerValue.FileSaveIntegerValue.length());
        if (checkStringValue.equals(FSRIntegerValue.FileSaveIntegerValue))
            value=FSRIntegerValue.ReadIntegerValue(strings);

        if(value==null) return null;

        return value;
    }
}
