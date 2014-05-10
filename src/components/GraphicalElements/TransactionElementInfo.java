package components.GraphicalElements;

/**
 * Created by deanto on 10.05.14.
 */
public class TransactionElementInfo implements GEInfo {

    private boolean changed = false;

    public void ChangesConsidered(){
        changed = false;
    }

    public void SetActivated(){
        changed = true;
    }

    @Override
    public boolean isChanged() {
        return changed;
    }
}
