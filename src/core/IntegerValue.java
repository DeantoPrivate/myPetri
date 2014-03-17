package core;

/**
 * Created by Денис on 11.03.14.
 */
public class IntegerValue implements IValue {

    private Integer _value;
    private static String _id = "IntegerValue";

    @Override
    public String getStringValue() {
        return _value.toString();
    }

    @Override
    public String getId() {
        return _id;
    }

    public IntegerValue(Integer value){
        _value = value;
    }

    public Integer GetIntegerValue(){
        return _value;
    }
    public void SetValue(Integer newvalue){
        _value = newvalue;
    }
}
