package pl.qaaacademy.todo.item.exceptions;

public class IllegalStatusTransitionException extends RuntimeException {

    public IllegalStatusTransitionException(String s) {
        super(s);
    }
}
