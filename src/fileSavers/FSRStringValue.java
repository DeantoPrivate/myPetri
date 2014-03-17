package fileSavers;

import core.StringValue;

import java.util.ArrayList;

/**
 * Created by Денис on 12.03.14.
 */
public class FSRStringValue {
    public static String FileSaveStringValue = "-s->";
    public static StringBuilder SaveStringValue(StringValue value){
        StringBuilder sStringValue = new StringBuilder();
        sStringValue.append(FileSaveStringValue+value.getStringValue()+"\n");

        return sStringValue;
    }

    public static StringValue ReadStringValue(ArrayList<String> strings){

        String propertyValue_dirty = strings.get(0);
        String propertyValue = propertyValue_dirty.substring(FileSaveStringValue.length());

        StringValue stringValue = new StringValue(propertyValue);

        strings.remove(0);

        return stringValue;
    }
}
