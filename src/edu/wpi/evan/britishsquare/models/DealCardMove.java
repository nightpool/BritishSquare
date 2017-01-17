package edu.wpi.evan.britishsquare.models;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.Pile;

public class DealCardMove extends Move {

    protected final Deck deck;
    protected final Pile discard;

    private Card card;

    public DealCardMove(Deck deck, Pile discard) {
        this.deck = deck;
        this.discard = discard;
    }

    @Override
    public boolean valid(Solitaire game) {
        return this.deck.count() > 0;
    }

    @Override
    public boolean doMove(Solitaire game) {
        this.card = this.deck.get();

        this.discard.add(this.card);
        return true;
    }

    @Override
    public boolean undo(Solitaire game) {
        if (!this.discard.peek().equals(this.card)) {
            return false;
        }

        this.discard.get();
        this.deck.add(this.card);
        return true;
    }
}
