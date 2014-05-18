package net.dynamic.statistic;

import core.State;
import core.Token;
import core.TokenComparer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by deanto on 18.05.14.
 */
public class stateStat extends JPanel implements ContextChangeListener{

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


        @Override
        public String toString() {
            return "сейчас: " + countNow+", максимум одновременно было: "+countMax;
        }
    }

    public void AddTokenTemplate(Token template){
        _tokens.add(template);
        _tokenStats.add(new tokenStat());
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
    public void FireContextChangeEvent(Context context) {
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

    }

    private JLabel text;
    public stateStat(){
        super();
        setLayout(new BorderLayout());
        text = new JLabel(toString());
        add(text,BorderLayout.NORTH);
    }

    @Override
    public void paint(Graphics g) {
        text.setText(toString());
        super.paint(g);
    }
}
