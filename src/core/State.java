package core;

import java.util.ArrayList;

/**
 * Created by Денис on 17.03.14.
 *
 * state is a place for tokens
 */
public class State {
    private Integer _id;
    private static Integer ids = 0;

    private String _name;
    public static String DefaultStateName = "nonamed state";

    private Integer getNextId(){
        ids ++;
        return ids;
    }

    public State(){
        _id = getNextId();
        _name = DefaultStateName;
        _tokens = new ArrayList<Token>();
    }

    public void ChangeName(String name){
        _name = name;
    }

    private ArrayList<Token> _tokens;
    public void LocateToken(Token token){
      if (!_tokens.contains(token))
          _tokens.add(token);
    }

    public void TokenGone(Token token){
        _tokens.remove(token);
    }

    public ArrayList<Token> GetTokens(){
        ArrayList<Token> copy = new ArrayList<Token>();
        for (int i=0;i<_tokens.size();i++)
            copy.add(_tokens.get(i));

        return copy;
    }


}

