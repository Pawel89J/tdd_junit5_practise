package pl.qaaacademy.todo.list.exceptions;

public class NoItemWithThisTitleException extends RuntimeException {
    public NoItemWithThisTitleException(String s) {
        super(s);
    }
}
