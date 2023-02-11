package exceptions;

public class PastCallException extends Exception{
    public PastCallException() {
        super("Can't create a task in the past!");
    }
}
