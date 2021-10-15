package pl.qaaacademy.todo.item;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.qaaacademy.todo.item.exceptions.IllegalStatusTransitionException;
import pl.qaaacademy.todo.item.exceptions.TodoItemValidationException;
import pl.qaaacademy.todo.item.utilities.StringGenerator;

import java.util.List;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static pl.qaaacademy.todo.matchers.ValidTodoItemMatcher.isValidTodoItemWith;

@Tag("item")
public class BasicTodoItemPropertiesTest extends BaseTodoItemTest {

    protected final static Logger logger2 = LoggerFactory.getLogger(BasicTodoItemPropertiesTest.class);

    @Tag("happy")
    @Test
    public void shouldCreateTodoItemWithTitleAndDescription() {
        logger2.warn("This is the test log string");
        assertAll(
                () -> assertEquals(item.getTitle(), title),
                () -> assertEquals(item.getDescription(), description)
        );
    }

    @Tag("exception")
    @Test
    public void shouldNotCreateTodoItemWithInvalidTitleAndDescription() {
        String invalidTitle = "";
        String invalidDescription = StringGenerator.randomGenerator();
        assertThrows(TodoItemValidationException.class,
                () -> TodoItem.of(invalidTitle, invalidDescription));
    }

    @Tag("exception")
    @Test
    public void shouldThrowAnExceptionWhileCreatingItemWithEmptyTitle() {
        String invalidTitle = "";
        assertThrows(TodoItemValidationException.class,
                () -> TodoItem.of(invalidTitle, description));
    }

    @Tag("exception")
    @Test
    public void shouldThrowAnExceptionWhileSettingAnEmptyTitle() {
        String invalidTitle = "";
        assertThrows(TodoItemValidationException.class,
                () -> item.setTitle(invalidTitle));
    }

    @Tag("happy")
    @Test
    public void shouldToggleStatusFromPendingToInProgress() {
        item.toggleStatus();
        assertEquals(item.getStatus(), ItemStatus.IN_PROGRESS);
    }

    @Tag("happy")
    @Test
    public void shouldToggleStatusFromInProgressToPending() {
        item.toggleStatus();
        item.toggleStatus();
        assertEquals(item.getStatus(), ItemStatus.PENDING);
    }

    @Tag("happy")
    @Test
    public void shouldCompleteATaskRepresentedByItem() {
        item.toggleStatus();
        item.complete();
        assertEquals(item.getStatus(), ItemStatus.COMPLETED);
    }

    @Tag("happy")
    @Test
    public void shouldCreateItemWithPendingStatus() {
        assertEquals(ItemStatus.PENDING, item.getStatus());
    }

    @Tag("exception")
    @Test
    public void shouldNotToggleStatusFromCompleteToInProgress() {
        item.toggleStatus();
        item.complete();
        assertThrows(IllegalStatusTransitionException.class,
                () -> item.toggleStatus());
    }

    @Tag("exception")
    @Test
    public void shouldNotCreateATodoItemWithDescriptionLongerThan250Chars() {
        String invalidDescription = StringGenerator.randomGenerator();
        assertThrows(TodoItemValidationException.class,
                () -> TodoItem.of(title, invalidDescription));
    }

    @Tag("exception")
    @Test
    public void shouldNotCreateATodoItemWithNullDescription() {
        String invalidDescription = null;
        assertThrows(TodoItemValidationException.class,
                () -> TodoItem.of(title, invalidDescription));
    }

    @Tag("exception")
    @Test
    public void shouldNotSetANewDescriptionLongerThan250Chars() {
        String invalidDescription = StringGenerator.randomGenerator();
        assertThrows(TodoItemValidationException.class,
                () -> item.setDescription(invalidDescription));
    }

    @Tag("exception")
    @Test
    public void shouldNotSetAnNullNewDescription() {
        String invalidDescription = null;
        assertThrows(TodoItemValidationException.class,
                () -> item.setDescription(invalidDescription));
    }

    @Tag("exception")
    @Test
    public void shouldNotToggleStatusFromPendingToComplete() {
        item.toggleStatus();
        item.toggleStatus();
        assertThrows(IllegalStatusTransitionException.class,
                () -> item.complete());
    }

    @Tag("exception")
    @Test
    public void checkingIfExceptionIsThrownWhileSettingTitleThatDoesNotMeetSpecifiedCriteria() {
        Predicate<String> c1 = (title) -> !title.contains("&");
        Predicate<String> c2 = (title) -> !title.contains("$");
        Predicate<String> c3 = (title) -> !title.contains("#");
        Predicate<String> c4 = (title) -> !title.contains("@");
        List<Predicate<String>> listOfCriteria = List.of(c1,c2,c3,c4);
        String incorrectTitle = "#title &";
        assertThrows(TodoItemValidationException.class, () -> item.setTitle(incorrectTitle, listOfCriteria));
    }

    @ParameterizedTest
    @CsvFileSource(files = {"src/test/resources/todos.csv"}, numLinesToSkip = 1)
    public void shouldCreateValidTodoItemCsvFileSource(String title, String description) {
        TodoItem newTodo = TodoItem.of(title, description);
        assertThat(newTodo, isValidTodoItemWith(title, description));
    }
    @ParameterizedTest
    @ArgumentsSource(TodoItemArgumentsProvider.class)
    public void shouldCreateValidTodoItemWithArgumentsSource(String title, String description) {
        TodoItem newTodo = TodoItem.of(title, description);
        assertThat(newTodo, isValidTodoItemWith(title, description));
    }
}
