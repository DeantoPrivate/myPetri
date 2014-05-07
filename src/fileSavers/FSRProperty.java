package fileSavers;

import core.IValue;
import core.Property;

import java.util.ArrayList;

/**
 * Created by Денис on 12.03.14.
 */
public class FSRProperty {
    public static String FileSavePropertyID = "*** ";
    public static StringBuilder SaveProperty(Property property){
        StringBuilder sProperty = new StringBuilder();
        sProperty.append(FileSavePropertyID+property.getName()+"\n");
        sProperty.append(FSRIValue.SaveIValue(property.getValue()));
        return sProperty;
    }

    public static Property ReadProperty(ArrayList<String> strings){
        String property_name_dirty = strings.get(0);
        strings.remove(0);

        String property_name = property_name_dirty.substring(FileSavePropertyID.length());
        IValue value = FSRIValue.ReadIValue(strings);

        Property property = new Property(property_name,value);

        return property;
    }

}
