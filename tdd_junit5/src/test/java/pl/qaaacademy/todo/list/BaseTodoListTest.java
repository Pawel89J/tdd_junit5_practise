package pl.qaaacademy.todo.list;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.qaaacademy.todo.item.TodoItem;

public class BaseTodoListTest {

    protected static final Logger logger = LoggerFactory.getLogger(BaseTodoListTest.class);
    protected String title;
    protected TodoList itemList;
    protected TodoItem item1;
    protected TodoItem item2;

    @BeforeEach
    public void setUpListTest() {
        title = "List of Items";
        itemList = new TodoList(title);
        item1 = TodoItem.of("t1","d1");
        item2 = TodoItem.of("t2","d2");
    }

    @AfterEach
    public void cleanUpAfterListTest() {
        itemList = null;
    }
}
