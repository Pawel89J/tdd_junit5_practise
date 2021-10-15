package pl.qaaacademy.todo.item.utilities;

import java.util.Random;

public class StringGenerator {

    public static String randomGenerator() {
        int lefLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 251;
        Random random = new Random();

        String generatedString = random.ints(lefLimit,rightLimit +1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }
}
