package model;

//Represents an exception raised when the name of something is empty
public class EmptyNameException extends Exception {

    //EFFECTS: Passes the error msg up to the Exception superclass
    public EmptyNameException(String message) {
        super(message);
    }
}
