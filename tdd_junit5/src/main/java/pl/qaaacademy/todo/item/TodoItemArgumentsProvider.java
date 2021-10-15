package pl.qaaacademy.todo.item;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class TodoItemArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(
                Arguments.arguments("Todo item1", "Arguments Provider Description 1"),
                Arguments.arguments("Todo item2", "Arguments Provider Description 2"),
                Arguments.arguments("Todo item3", "Arguments Provider Description 3")
        );
    }

}
