package internalComponents;

import base.TokensBase;
import components.TokenManager.*;
import components.TokenManager.Dialog;
import core.Property;
import core.Token;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Денис on 11.03.14.
 *
 * component to work with tokens
 */
public class WWToken extends JDialog implements ActionListener {
    private static String _windowName = "Token";
    private Token _token;

    private JButton _BSaveExit,_BExit;

    private JPanel _panel;
    private JPanel _propertiesPanel;
    private JPanel _propertiesList;

    ArrayList<Property> _currentProperties;

    public Token GetToken(){
        return _token;
    };

    public void SetToken(Token token)
    {
        _token = token;
    }
    JButton _BNewProperty;
    public static String NewProperty = "Add Property";
    void AddNewPropertyButton()
    {
        _BNewProperty = new JButton(NewProperty);
        _BNewProperty.addActionListener(this);
        _propertiesPanel.add(_BNewProperty,BorderLayout.SOUTH);
    }

    void AddButtons(){
        _BSaveExit = new JButton("Save and exit/");
        _BSaveExit.addActionListener(this);

        _BExit = new JButton("Cancel without saving/");
        _BExit.addActionListener(this);

        JPanel tmp = new JPanel();
        tmp.add(_BSaveExit);
        tmp.add(_BExit);

        _panel.add(tmp,BorderLayout.SOUTH);
    }

    public void Init(){
        _panel = new JPanel();
        _panel.setLayout(new BorderLayout());
        _propertiesPanel = new JPanel();
        _propertiesPanel.setLayout(new BorderLayout());
        _propertiesList = new JPanel();
        _propertiesList.setAutoscrolls(true);
        _propertiesList.setLayout(new FlowLayout());
        _propertiesList.setAutoscrolls(true);

        _propertiesPanel.add(_propertiesList);
        _panel.add(_propertiesPanel);
        AddNewPropertyButton();
        AddButtons();
        this.add(_panel);
        this.setBounds(100,100,400,300);

        _currentProperties = _token.get_properties();
    }

     public void ShowToken(){
         if (_token==null)
         {
             ConstructToken();
             return;
         }

        if (_panel==null) Init();


        _propertiesList.removeAll();
        setTitle(_windowName + " : " + _token.GetName());


        ArrayList<WWProperty> wwProperties = new ArrayList<WWProperty>();
        for(int i=0;i<_currentProperties.size();i++)
        {
            wwProperties.add(new WWProperty());
            wwProperties.get(wwProperties.size()-1).SetProperty(_currentProperties.get(i));
            _propertiesList.add(wwProperties.get(wwProperties.size() - 1));
        }

        this.setModal(true);
        this.setVisible(true);
    }

    public void ConstructToken(){
        _currentProperties = new ArrayList<Property>();

        this.setModal(true);

        try {

            if (JOptionPane.showOptionDialog(null, "взять из базы?", "выбор токена", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 0) == 0) {
                // берем токен из базы

                _token = Dialog.getInstanse().selectAndGetToken();
            }

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"не удалось...");
        }
        if (_token == null){

//TODO make constants for all questions
            String name = JOptionPane.showInputDialog(this, "Enter a name for new Token.");
            while (name == null)
                name = JOptionPane.showInputDialog(this, "Empty name is not allowed!\nEnter a name for new Token.");

            _token = new Token();
            _token.ChangeName(name);

            ShowToken();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(_BExit))
        {
            _token=null;
            this.setVisible(false);

        }else if (e.getSource().equals(_BSaveExit))
        {
            _token.ChangeProperties(_currentProperties);
            this.setVisible(false);
        }else if (e.getSource().equals( _BNewProperty))
        {
            WWProperty wwProperty = new WWProperty();
            wwProperty.ConstractProperty();

            _currentProperties.add(wwProperty.GetProperty());

            ShowToken();
        }
    }

}
