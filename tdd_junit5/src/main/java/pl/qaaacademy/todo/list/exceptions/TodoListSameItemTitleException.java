package pl.qaaacademy.todo.list.exceptions;

public class TodoListSameItemTitleException extends RuntimeException {
    public TodoListSameItemTitleException(String s) {
        super(s);
    }
}
