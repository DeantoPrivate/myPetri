package fileSavers;

import core.IntegerValue;
import core.StringValue;

import java.util.ArrayList;

/**
 * Created by Денис on 12.03.14.
 */
public class FSRIntegerValue {
    public static String FileSaveIntegerValue = "-i->";
    public static StringBuilder SaveIntegerValue(IntegerValue value){
        StringBuilder sStringValue = new StringBuilder();
        sStringValue.append(FileSaveIntegerValue+value.getStringValue()+"\n");

        return sStringValue;
    }
    public static IntegerValue ReadIntegerValue(ArrayList<String> strings){

        String propertyValue_dirty = strings.get(0);
        String propertyValue = propertyValue_dirty.substring(FileSaveIntegerValue.length());

        IntegerValue integerValue = new IntegerValue(Integer.parseInt(propertyValue));

        strings.remove(0);

        return integerValue;
    }
}
