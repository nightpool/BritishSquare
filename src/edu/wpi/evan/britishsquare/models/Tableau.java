package edu.wpi.evan.britishsquare.models;

import edu.wpi.evan.britishsquare.utils.Utils;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Stack;

import java.util.*;
import java.util.stream.Stream;

public class Tableau extends Column implements CardDestination {

    protected List<Card> initCards = new ArrayList<>();

    public void addInitCard(Card c) {
        initCards.add(c);
        super.add(c);
    }

    public Optional<Suit> getSuit() {
        return Optional.ofNullable(this.peek())
                .map(c -> Suit.fromIndex(c.getSuit()));
    }

    public Optional<Card> nextCard() {
        return count() > 1 ?
                Optional.of(peek(count() - 2)) : Optional.empty();
    }

    public Stream<Card> nonInitCards(){
        Iterator<Card> cardIterator = this.initCards.iterator();
        return Arrays.stream(this.cards)
            .filter(c -> c != null)
            .filter(c -> !cardIterator.hasNext() || c != cardIterator.next());
    }

    public Optional<Integer> direction() {
        return Utils.lastItem(nonInitCards().iterator())
                .flatMap(c -> nextCard().map(c::compareTo))
                .filter(c -> c == 1 || c == -1);
    }

    @Override
    public Stack getStack() {
        return this;
    }

    public boolean isValidCard(Card toAdd) {
        int diff = toAdd.compareTo(this.peek());
        Suit suit = Suit.fromIndex(toAdd.getSuit());
        boolean dirGood = this.direction().map(d -> d == diff).orElse(
            diff == Integer.MAX_VALUE || diff == 1 || diff == -1
        );
        boolean suitGood = getSuit().map(s -> s == suit).orElse(true);
        return dirGood && suitGood;
    }
}
