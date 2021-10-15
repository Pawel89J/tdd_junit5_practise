package pl.qaaacademy.todo.list;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.qaaacademy.todo.item.ItemStatus;
import pl.qaaacademy.todo.item.TodoItem;
import pl.qaaacademy.todo.list.exceptions.NoItemWithThisTitleException;
import pl.qaaacademy.todo.list.exceptions.TodoListSameItemTitleException;
import pl.qaaacademy.todo.list.exceptions.TodoListValidationException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class TodoList {

    private String title;
    private List<TodoItem> itemList;
    private static final Logger logger = LoggerFactory.getLogger(TodoList.class);

    public TodoList(String title) {
        listTitleValidator(title);
        this.title = title;
        this.itemList = new ArrayList<>();
    }

    public static TodoList of(String title){
        listTitleValidator(title);
        return new TodoList(title);
    }

    private TodoList(String title, List<TodoItem> list){
        listTitleValidator(title);
        this.itemList = list;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        listTitleValidator(title);
        this.title = title;
    }

    public List<TodoItem> getItemList() {
        return itemList;
    }

    public int getSize() {
        return this.itemList.size();
    }

    public void addItem(TodoItem item) {
        for (TodoItem t : this.itemList) {
            if (t.getTitle().equals(item.getTitle())) {
                logger.warn("Element with the same title already exists");
                throw new TodoListSameItemTitleException("Element with the same title already exists");
            }
        }
        this.itemList.add(item);
    }

    public void removeItem(TodoItem item) {
        if (this.itemList.contains(item)) {
            this.itemList.remove(item);
        }else {
            logger.warn("Item does not exists");
            throw new NoSuchElementException("Item does not exists");
        }
    }

    public void removeItem(String itemTitle) {
        for (TodoItem t : itemList.stream().toList()) {
            if (t.getTitle().equals(itemTitle)) {
                removeItem(t);
            } else {
                logger.warn("There is not item with this title");
                throw new NoItemWithThisTitleException("There is not item with this title");
            }
        }
    }

    public TodoList filterByStatus(ItemStatus status) {
        List<TodoItem> listOfItems = this.itemList
                .stream()
                .filter(t -> t.getStatus().equals(status))
                .collect(Collectors.toList());
        return new TodoList(this.title, listOfItems);
    }

    public void sortByTitle() {
        this.itemList = getItemList()
                .stream()
                .sorted(Comparator.comparing(TodoItem::getTitle))
                .collect(Collectors.toList());
    }

    public static TodoList combineList(TodoList firstList, TodoList secondList, String title) {
        TodoList newList = new TodoList(title);
        for (TodoItem t : firstList.getItemList()) {
            newList.addItem(t);
        }
        for (TodoItem t : secondList.getItemList()) {
            newList.addItem(t);
        }
        return newList;
    }

    private static void listTitleValidator(String title) {
        if (title == null || title.isBlank()) {
            logger.warn("List title is null or blank");
            throw new TodoListValidationException("List title is null or blank");
        }
    }

    @Override
    public String toString() {
        return title;
    }

}
