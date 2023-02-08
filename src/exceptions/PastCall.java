package exceptions;

public class PastCall extends RuntimeException{
    public PastCall() {
        super("Can't create a task in the past!");
    }
}
