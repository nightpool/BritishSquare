package edu.wpi.evan.britishsquare.models;

import ks.common.model.Card;

import java.util.Arrays;

public enum Suit {
    DIAMONDS(Card.DIAMONDS, Card.DIAMONDSname, Card.DIAMONDSabbreviation),
    SPADES(Card.SPADES, Card.SPADESname, Card.SPADESabbreviation),
    HEARTS(Card.HEARTS, Card.HEARTSname, Card.HEARTSabbreviation),
    CLUBS(Card.CLUBS, Card.CLUBSname, Card.CLUBSabbreviation);

    public final int suitIndex;
    public final String name;
    public final String abbr;

    Suit(int suit, String name, String abbr) {
        this.suitIndex = suit;
        this.name = name;
        this.abbr = abbr;
    }

    public static Suit fromIndex(int index) {
        return Arrays.stream(Suit.values())
                .filter(s -> s.suitIndex == index)
                .findFirst()
                .orElseThrow(NonexistentSuitException::new);
    }

    public static Suit fromCard(Card card) {
        return fromIndex(card.getSuit());
    }

    public static class NonexistentSuitException extends RuntimeException {
    }
}
