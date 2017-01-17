package edu.wpi.evan.britishsquare.models;

import java.util.stream.Stream;

public interface FoundationCollection {
    Stream<Foundation> getFoundations();

    default boolean hasFoundationForSuit(Suit suit) {
        return getFoundations().anyMatch(f ->
            f.getSuit().filter(suit::equals).isPresent()
        );
    }
}
