package net.dynamic.statistic;

import base.TokensBase;
import core.State;
import core.Token;
import core.TokenComparer;
import net.staticNet.netStaticImpl;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by deanto on 18.05.14.
 */
public class stateStat extends JPanel implements ContextChangeListener{

    public void Clear(){
        for(tokenStat tS : _tokenStats){
            tS.countMax = 0;
            tS.countNow = 0;
        }

        Reassign();
        text.setText(toString());
        repaint();
    }

    public void Reassign(){
        // переопределить состояние


        State newState = netStaticImpl.getNet().getState(_state.GetName());
        _state = newState;
        _state.AssignTransactionStat(this);

    }

    private State _state;
    public void SetAssociatedState(State state){
        _state = state;
    }
    public String getStateName(){return _state.GetName();}

    // интересующие токены
    private ArrayList<Token> _tokens;
    // статистика по этим токенам
    private ArrayList<tokenStat> _tokenStats;

    private class tokenStat{
        public int countNow = 0;
        public int countMax = 0;
        public int critical = 0;

        @Override
        public String toString() {
            String s = "сейчас: " + countNow+", максимум одновременно было: "+countMax;
            if (critical<=countMax)
                s +=" !Критично.";
            return s;
        }
    }

    public boolean Consctuct(){
        String sName = "";
        while (sName.equals(""))
            sName = JOptionPane.showInputDialog("Введите имя состояния","");

        _state = netStaticImpl.getNet().getState(sName);
        if (_state == null){
            JOptionPane.showMessageDialog(null,"нет такого!");
            return false;
        }

        String tName = "";
        while(tName.equals("")){

                tName = JOptionPane.showInputDialog("Введите имя токена. Для завершения введите \"СТОП\"","");
                Token token = TokensBase.GetTokenBase().getToken(tName);
                if (token == null){
                    JOptionPane.showMessageDialog(null,"нет такого токена!");
                } else {

                    int critical = -1;
                    while(critical==-1) {
                        String c = JOptionPane.showInputDialog("Критическое количество токенов одновременно в состоянии", "1");
                        int col = 0;
                        if (c != null)
                            try {
                                Integer a = new Integer(c.toString());
                                if (a != null) {
                                    col = a;

                                    critical = col;

                                }
                            } catch (Exception e) {

                            }

                    }

                    AddTokenTemplate(token,critical);
                }


            if (!tName.equals("СТОП"))
                tName = "";
        }

        _state.AssignTransactionStat(this);
        return true;
    }

    public void AddTokenTemplate(Token template,int critical){
        _tokens.add(template);
        tokenStat ts = new tokenStat();
        ts.critical = critical;
        _tokenStats.add(ts);
    }

    @Override
    public String toString() {

        StringBuilder answer = new StringBuilder();

        if (_state==null)
            return "нет статистики";

        answer.append("$ состояние [" + _state.GetName()+"] : ");

        for (int i=0;i<_tokens.size();i++){
            answer.append("#"+ _tokens.get(i).GetName()+ "# " + _tokenStats.get(i)+ "; ");
        }

        return answer.toString();
    }


    @Override
    public void FireContextChangeEvent() {
        // состояние изменилось. нужно пробежаться и собрать всю интересующую информацию

        for(int i=0;i<_tokens.size();i++){
            // смотрим сколько сейчас таких токенов

            int now = 0;
            for (Token token : _state.GetTokens()){
                if (TokenComparer.Equals(token,_tokens.get(i)))
                    now++;
            }

            _tokenStats.get(i).countNow = now;

            // меняем максимум
            if (_tokenStats.get(i).countMax<now)
                _tokenStats.get(i).countMax = now;



        }

        text.setText(toString());

    }

    private JLabel text;
    public stateStat(){
        super();
        setLayout(null);
        text = new JLabel(toString());
        text.setBounds(0,0,1000,20);
        add(text);
        _tokens = new ArrayList<Token>();
        _tokenStats = new ArrayList<tokenStat>();
    }

}
