package core;

import net.staticNet.UIActionListener;

import java.util.ArrayList;

/**
 * Created by Денис on 17.03.14.
 *
 * state is a place for tokens
 */
public class State {
    private Integer _id;
    private static Integer ids = 0;

    private boolean processing = false;
    public void setProcessing(boolean flag){
        processing = flag;
        if (processing == false){
            for (Token t : _processingTokens)
            if (!_tokens.contains(t))
                _tokens.add(t);

            if (_listener!=null){
                _listener.FireUIChangedEvent();
            }
            _processingTokens.clear();
        }
    }



    // TODO сделать интерфейс отдающий имена и id
    public Integer GetID(){
        return _id;
    }
    private String _name;
    public String GetName(){
        return _name;
    }
    public static String DefaultStateName = "nonamed state";

    private Integer getNextId(){
        ids ++;
        return ids;
    }

    public State(){
        _id = getNextId();
        _name = DefaultStateName;
        _tokens = new ArrayList<Token>();
        _processingTokens = new ArrayList<Token>();
    }

    public void ChangeName(String name){
        _name = name;
    }

    private ArrayList<Token> _tokens;
    private ArrayList<Token> _processingTokens;

    public void LocateToken(Token token){

      if (!processing){
        if (!_tokens.contains(token))
            _tokens.add(token);
          if (_listener!=null){
              _listener.FireUIChangedEvent();
          }
      }else
      {
          if (!_processingTokens.contains(token))
              _processingTokens.add(token);
      }
    }

    public void TokenGone(Token token){
        _tokens.remove(token);
        if (_listener!=null){
            _listener.FireUIChangedEvent();
        }
    }

    public ArrayList<Token> GetTokens(){
        ArrayList<Token> copy = new ArrayList<Token>();
        for (int i=0;i<_tokens.size();i++)
            copy.add(_tokens.get(i));

        return copy;
    }


    private UIActionListener _listener;
    public void addUIChangeActionListener(UIActionListener listener){
        _listener = listener;
    }

}

