package core;

import javax.swing.*;

/**
 * Created by Денис on 11.03.14.
 *
 * Interface for values at token's
 */
public interface IValue {
    // string value to make every value is shown into text
    String getStringValue();
    // id to identify the value object
    String getId();

    static String StringValue = "StringValue";
    static String IntegerValue = "IntegerValue";
}

