package model;

//Represents an exception raised when the billing rate entered is negative
public class NegativeRateException extends Exception {

    //EFFECTS: Passes the error msg up to the Exception superclass
    public NegativeRateException(String message) {
        super(message);
    }
}
