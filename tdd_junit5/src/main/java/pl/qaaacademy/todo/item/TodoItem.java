package pl.qaaacademy.todo.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.qaaacademy.todo.item.exceptions.IllegalStatusTransitionException;
import pl.qaaacademy.todo.item.exceptions.TodoItemValidationException;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;


public class TodoItem implements StatusChangeable {
    private String title;
    private String description;
    private ItemStatus status;
    private static final Logger logger = LoggerFactory.getLogger(TodoItem.class);

    private TodoItem() {
    }

    private TodoItem(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = ItemStatus.PENDING;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        validateTitle(title);
        this.title = title;
    }
    public void setTitle(String title, List<Predicate<String>> criteria) {
        validateTitle(title);
        validateTitle(title, criteria);
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        validateDescription(description);
        this.description = description;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public static TodoItem of(String title, String description) {
        validateTitle(title);
        validateDescription(description);
        return new TodoItem(title, description);
    }

    private static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            logger.error("Item title is null or blank");
            throw new TodoItemValidationException("The title is null or blank");
        }
    }

    private static void validateDescription(String description) {
        if (description == null || description.length() > 250) {
            logger.error("Item description is null or longer than 250 characters");
            throw new TodoItemValidationException("The description have more than 250 characters");
        }
    }

    private static void validateTitle(String title, List<Predicate<String>> criteria) {

        List<Boolean> resultsOfTestingIfFileMeetsCriteria = criteria
                .stream()
                .map(c -> c.test(title))
                .toList();
        if (resultsOfTestingIfFileMeetsCriteria
                .stream()
                .filter(r -> r == false)
                .count() > 0) {
            logger.error("This item title does not meet all given criteria");
            throw new TodoItemValidationException("This item title does not meet all given criteria");
        }
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public void toggleStatus() {
        if (this.status == ItemStatus.COMPLETED) {
            logger.error("Status cannot be changed for COMPLETED item");
            throw new IllegalStatusTransitionException("Status cannot be changed for COMPLETED item");
        }
        if (this.status == ItemStatus.PENDING) {
            this.status = ItemStatus.IN_PROGRESS;
        } else {
            this.status = ItemStatus.PENDING;
        }
    }

    @Override
    public void complete() {
        if (this.status == ItemStatus.IN_PROGRESS){
            this.status = ItemStatus.COMPLETED;
        } else if (this.status == ItemStatus.PENDING) {
            throw new IllegalStatusTransitionException("You cannot complete an item that is not IN_PROGRESS");
        } else {
            throw new IllegalStatusTransitionException("This item is already COMPLETED");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoItem item = (TodoItem) o;
        return Objects.equals(title,item.title) && Objects.equals(description, item.description) && status == item.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title,description,status);
    }
}
