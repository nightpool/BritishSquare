package edu.wpi.evan.britishsquare.utils;

import java.util.Iterator;
import java.util.Optional;
import java.util.stream.IntStream;

public class Utils {
    public static <T> Optional<T> lastItem(Iterator<T> iterator) {
        Object current = null;
        while(iterator.hasNext()) {
            current = iterator.next();
        }

        return Optional.ofNullable((T) current);
    }

    public static IntStream revIntStream(int endInclusive, int startInclusive) {
        return IntStream.rangeClosed(startInclusive, endInclusive)
            .map(i -> endInclusive - i + startInclusive);
    }
}
