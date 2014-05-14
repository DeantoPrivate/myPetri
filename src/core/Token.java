package core;

import base.TokensBase;
import components.TokenManager.Dialog;

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

    public Integer GetID(){
        return  _id;
    }
    public Token(){
        _id = getNextID();
        _name = _defaultName;
        _properties = new ArrayList<Property>();


    }

    public void ChangeName(String newname){

        if (_name.equals(_defaultName)) {
            _name = newname;
            TokensBase.GetTokenBase().AddToken(this);

        }
        _name = newname;
        Dialog.getInstanse().UpdateGUI();
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


    public Token Clone(){
        Token clone = new Token();
        clone._name = this._name;

        for (int i=0;i<this._properties.size();i++)
            clone._properties.add(this._properties.get(i).Clone());

        return clone;
    }
}
