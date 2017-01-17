package edu.wpi.evan.britishsquare.models;

import ks.common.model.Card;
import ks.common.model.Pile;
import ks.common.model.Stack;

import java.util.Optional;

public class Foundation extends Pile implements CardDestination {

    protected final FoundationCollection parent;

    public Foundation(FoundationCollection parent) {
        this.parent = parent;
    }

    public Optional<Suit> getSuit() {
        return Optional.ofNullable(peek()).map(Suit::fromCard);
    }

    public Optional<Card> topCard() {
        return Optional.ofNullable(peek());
    }

    public Optional<Card> nextCard() {
        return count() > 1 ?
            Optional.of(peek(count() - 2)) : Optional.empty();
    }

    public int direction() {
        return topCard()
            .flatMap(c -> nextCard().map(c::compareTo))
            .map(Integer::signum)
            .orElse(1);
    }

    @Override
    public Stack getStack() {
        return this;
    }

    public boolean isValidCard(Card toAdd) {
        boolean dirSame = topCard().map(
            c -> this.direction() == toAdd.compareTo(c)
        ).orElse(toAdd.getRank() == Card.ACE);

        boolean aroundTheCorner = topCard().map(
            tc -> tc.getRank() == Card.KING && toAdd.getRank() == Card.ACE
                 || tc.getRank() == Card.ACE && toAdd.getRank() == Card.KING
        ).orElse(false);

        Suit cardSuit = Suit.fromCard(toAdd);
        boolean suitValid = getSuit()
            .map(cardSuit::equals)
            .orElse(!this.parent.hasFoundationForSuit(cardSuit));

        return (dirSame || aroundTheCorner) && suitValid;
    }

    public boolean isComplete() {
        return this.getSuit().map(__ -> this.sameSuit()).orElse(false)
               && this.count() == 26
               && this.ascending(0, 13) && this.descending(13, 26);
    }
}
