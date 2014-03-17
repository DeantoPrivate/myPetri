package core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Денис on 11.03.14.
 */
public class Token {

    private static Integer _nextID = 0;
    public static Integer getNextID(){
        _nextID++;
        return _nextID;
    }

    private static String _defaultName = "nonamed";

    private String _name;
    private Integer _id;
    private ArrayList<Property> _properties;

    public Token(){
        _id = getNextID();
        _name = _defaultName;
        _properties = new ArrayList<Property>();
    }

    public void ChangeName(String newname){
        _name = newname;
    }
    public String GetName(){
        return _name;
    }
    public void ChangeProperties(ArrayList<Property> newproperties){
        _properties = newproperties;
    }
    public ArrayList<Property> get_properties(){
        return _properties;
    }
}
