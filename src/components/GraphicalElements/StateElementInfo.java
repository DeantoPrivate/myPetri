package components.GraphicalElements;

/**
 * Created by deanto on 10.05.14.
 */
public class StateElementInfo implements GEInfo{
    private int _tokens = 0;

    private boolean changed = false;

    public void ChangesConsidered(){
        changed = false;
    }

    public int getTokens(){return _tokens;}

    public void SetTokens(int tokens){
        _tokens = tokens;
        changed = true;
    }

    @Override
    public boolean isChanged() {
        return changed;
    }
}
