package net.dynamic.changes;

import base.TokensBase;
import components.TokenManager.*;
import core.State;
import core.Token;
import net.dynamic.analyze.ChangeOneState;
import net.staticNet.netStaticImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by deanto on 19.05.14.
 */
public class changeStat extends JPanel {


    private State _state;

    public void SetAssociatedState(State state){
        _state = state;
        text.setText("Изменения для состояния ["+state.GetName()+"]");
    }
    public String getStateName(){return _state.GetName();}

    // интересующие токены
    private ArrayList<Token> _tokens;
    // изменения для этих токенов
    private ArrayList<TokenChanges> _tokenChanges;

    public ArrayList<Token> get_tokens(){return _tokens;}
    public ArrayList<TokenChanges> get_tokenChanges(){return _tokenChanges;}

    // панельки для отображения этих токенов
    private ArrayList<JPanel> tokenPanels;

    private ArrayList<String> _TokenChangesStrings;

    public StringBuilder toStrings(){
        String n = System.getProperty("line.separator");
        StringBuilder answer = new StringBuilder();
        answer.append("Изменения для состояния ["+_state.GetName()+"]"+n);
        for (String s : _TokenChangesStrings)
            answer.append(s+n);

        return answer;
    }

    private JPanel ConstructTokenChanges(){

        String sName = "";
        while (sName.equals(""))
            sName = JOptionPane.showInputDialog("Введите имя токена","");

        Token token = TokensBase.GetTokenBase().getToken(sName);

        if (token == null) return null;

        TokenChanges tokenChanges = new TokenChanges();

        _tokens.add(token);
        _tokenChanges.add(tokenChanges);

        String var = "";
        while (!var.equals("потеря") && !var.equals("появление"))
            var = JOptionPane.showInputDialog("Параметр изменения: появление или потеря","появление / потеря");

        if (var.equals("потеря"))
            tokenChanges.losses = true;
        if (var.equals("появление"))
            tokenChanges.appearance = true;

        var = "";
        int col= 0;
        while (var.equals("")){
            String count = JOptionPane.showInputDialog(this,"количество?");
            col = -1;
            if (count!=null)
                try{
                    Integer a = new Integer(count.toString());
                    if (a!=null)
                        col=a;
                }catch (Exception e){
                    var = "";
                }
            if (col == -1)
                return null;

            tokenChanges.Count = col;

            count = JOptionPane.showInputDialog(this,"частота?");
            col = -1;
            if (count!=null)
                try{
                    Integer a = new Integer(count.toString());
                    if (a!=null)
                        col=a;
                }catch (Exception e){
                    return null;
                }
            if (col == -1)
                return null;

            tokenChanges.Step = col;

            JPanel answer = new JPanel();
            answer.setLayout(null);

            StringBuilder s = new StringBuilder();
            s.append("Токен #" + token.GetName() +"#: параметры изменения - ");
            if (tokenChanges.appearance)
                s.append("появление ");
            if (tokenChanges.losses)
                s.append("потеря ");

            s.append( tokenChanges.Count+" токен(а), каждый " + tokenChanges.Step + "-й шаг.");

            _TokenChangesStrings.add(s.toString());

            JLabel text = new JLabel();
            text.setText(s.toString());
            text.setBounds(0,0,1000,20);
            answer.add(text);
            return answer;

        }
        return null;
    }

    public void AddTokenTemplate(Token template){
        _tokens.add(template);
    }

    private JButton Construct, newToken, changeToken;

    public class TokenChanges{
        // есть ли потери этого токена
        public boolean losses = false;
        // незапланированное появление токена
        public boolean appearance = false;


        // параметры
        public int Count = 0;
        // через сколько шагов повторяется
        public int Step = 0;
    }

    public ArrayList<ChangeOneState> getStateChanges(){

        ChangeOneState cos;
        ArrayList<ChangeOneState> answer = new ArrayList<ChangeOneState>();

        for (int i=0;i<_tokens.size();i++){
            cos = new ChangeOneState();
            cos.appearance = _tokenChanges.get(i).appearance;
            cos.count = _tokenChanges.get(i).Count;
            cos.loose = _tokenChanges.get(i).losses;
            cos.State = _state.GetName();
            cos.Token = _tokens.get(i).GetName();
            cos.step = _tokenChanges.get(i).Step;

            answer.add(cos);
        }

        // добавить НЕ изменение
        cos = new ChangeOneState();
        cos.State=_state.GetName();

        answer.add(cos);

        return answer;
    }

    private int tPos = 20;

    private JLabel text;
    public changeStat(){
        super();
        setLayout(null);
        text = new JLabel("Изменения для состояния. Не сконфигурено.");
        text.setBounds(100,0,400,20);
        add(text);

        Construct = new JButton("Новый");
        Construct.setBounds(0,0,100,20);
        Construct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String sName = "";
                while (sName.equals(""))
                    sName = JOptionPane.showInputDialog("Введите имя состояния","");

                State state = netStaticImpl.getNet().getState(sName);

                if (state == null) return;

                if (tokenPanels!=null)
                    for (JPanel p : tokenPanels)
                        remove(p);

                repaint();

                _tokens = new ArrayList<Token>();
                _tokenChanges = new ArrayList<TokenChanges>();
                tokenPanels = new ArrayList<JPanel>();
                _TokenChangesStrings = new ArrayList<String>();
                SetAssociatedState(state);

                newToken.setEnabled(true);


            }
        });
        add(Construct);


        newToken = new JButton("+");
        newToken.setBounds(0,20,50,20);
        newToken.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel tokenPanel = ConstructTokenChanges();
                if (tokenPanel!=null){
                    tokenPanel.setBounds(50,tPos,800,20);
                    tokenPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
                    tPos+=20;
                    tokenPanels.add(tokenPanel);
                    add(tokenPanel);
                    repaint();
                }
            }
        });
        add(newToken);

        newToken.setEnabled(false);



    }

}
