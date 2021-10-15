package pl.qaaacademy.todo.list;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.qaaacademy.todo.item.ItemStatus;
import pl.qaaacademy.todo.item.TodoItem;
import pl.qaaacademy.todo.list.exceptions.NoItemWithThisTitleException;
import pl.qaaacademy.todo.list.exceptions.TodoListSameItemTitleException;
import pl.qaaacademy.todo.list.exceptions.TodoListValidationException;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@Tag("list")
public class BaseTodoListPropertiesTest extends BaseTodoListTest{

    protected static final Logger logger2 = LoggerFactory.getLogger(BaseTodoListPropertiesTest.class);

    @Tag("happy")
    @Test
    public void shouldCreateAnEmptyList() {
        assertEquals(0, itemList.getSize());
    }

    @Tag("happy")
    @Test
    public void shouldListHaveATitleAndSizeAfterCreation() {
        assertAll(
                () -> assertEquals(title,itemList.getTitle()),
                () -> assertEquals(0, itemList.getSize())
        );
    }

    @Tag("exception")
    @Test
    public void shouldThrowAnExceptionWhileSettingAnEmptyTitle() {
        assertThrows(TodoListValidationException.class, () -> itemList.setTitle(""));
    }

    @Tag("happy")
    @Test
    public void shouldAddItemToTodoList() {
        itemList.addItem(item1);
        assertEquals(1,itemList.getSize());
    }

    @Tag("exception")
    @Test
    public void shouldNotAddItemWithTheSameItemTitleToTodoList() {
        TodoItem item2 = TodoItem.of("t1","d1");
        itemList.addItem(item1);
        assertThrows(TodoListSameItemTitleException.class, () -> itemList.addItem(item2));
    }

    @Tag("happy")
    @Test
    public void shouldRemoveItemFromTodoList() {
        itemList.addItem(item1);
        itemList.removeItem(item1);
        assertEquals(0, itemList.getSize());
    }

    @Tag("exception")
    @Test
    public void shouldThrowAnExceptionWhileRemovingItemDoesNotExists() {
        assertThrows(NoSuchElementException.class, () -> itemList.removeItem(item1));
    }

    @Tag("happy")
    @Test
    public void shouldRemoveItemFromTodoListByTitle() {
        itemList.addItem(item1);
        itemList.removeItem("t1");
        assertEquals(0, itemList.getSize());
    }

    @Tag("exception")
    @Test
    public void shouldThrowAnExceptionWhileRemovingItemTitleDoesNotExists() {
        itemList.addItem(item1);
        assertThrows(NoItemWithThisTitleException.class, () -> itemList.removeItem("t2"));
    }

    @Tag("happy")
    @Test
    public void shouldListIncreaseAfterAddingItem() {
        itemList.addItem(item1);
        itemList.addItem(item2);
        assertEquals(2, itemList.getSize());
    }

    @Tag("happy")
    @Test
    public void shouldFilterItemsByStatus() {
        itemList.addItem(item1);
        itemList.addItem(item2);
        item2.toggleStatus();

        TodoList filteredList = itemList.filterByStatus(ItemStatus.IN_PROGRESS);
        assertAll(
                () -> assertEquals(1, filteredList.getSize()),
                () -> assertEquals(ItemStatus.IN_PROGRESS, filteredList.getItemList().get(0).getStatus())
        );
    }

    @Tag("happy")
    @Test
    public void shouldSortedItemsByTitle() {
        itemList.addItem(item1);
        itemList.addItem(item2);
        itemList.sortByTitle();
        assertAll(
                () -> assertEquals(0, itemList.getItemList().indexOf(item1)),
                () -> assertEquals(1, itemList.getItemList().indexOf(item2))
        );
    }

    @Tag("happy")
    @Test
    public void shouldCombineSeveralListIntoOne() {
        TodoList itemList2 = new TodoList("List2");
        TodoList itemListCombine = new TodoList("CombineList");
        itemList.addItem(item1);
        itemList.addItem(item2);
        itemList2.addItem(TodoItem.of("L2t1","L2d1"));
        itemList2.addItem(TodoItem.of("L2t2","L2d2"));

        itemListCombine = TodoList.combineList(itemList,itemList2,itemListCombine.getTitle());
        TodoList finalItemListCombine = itemListCombine;
        TodoList finalItemListCombine1 = itemListCombine;
        assertAll(
                () -> assertEquals(4, finalItemListCombine.getSize()),
                () -> assertEquals("CombineList", finalItemListCombine1.getTitle())
        );
    }
}
