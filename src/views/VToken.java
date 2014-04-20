package views;

import internalComponents.WWProperty;
import core.Property;
import core.Token;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Денис on 13.03.14.
 * to view Token as JPanel
 */
public class VToken extends JPanel{
    private String vTokenName;
    private ArrayList<WWProperty> _VTokenProperties;

    public VToken(Token token){
        vTokenName = token.GetName();
        _VTokenProperties = new ArrayList<WWProperty>();
        ArrayList<Property> properties = token.get_properties();
        for (int i=0;i<properties.size();i++)
            _VTokenProperties.add(new WWProperty(properties.get(i)));

        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        add(new JLabel(vTokenName));
        for (int i=0;i<_VTokenProperties.size();i++)
        add(_VTokenProperties.get(i));


        }


}
