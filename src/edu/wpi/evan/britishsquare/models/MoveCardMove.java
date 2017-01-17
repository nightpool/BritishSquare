package edu.wpi.evan.britishsquare.models;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Move;
import ks.common.model.Stack;

public class MoveCardMove extends Move {
    protected final Stack src;
    protected final CardDestination destination;
    protected final Card card;

    public MoveCardMove(Card card, CardDestination destination, Stack src) {
        this.card = card;
        this.destination = destination;
        this.src = src;
    }

    @Override
    public boolean valid(Solitaire game) {
        return this.destination.isValidCard(this.card);
    }

    @Override
    public boolean doMove(Solitaire game) {
        this.destination.getStack().add(this.card);
        return true;
    }

    @Override
    public boolean undo(Solitaire game) {
        if (!this.destination.getStack().peek().equals(this.card)) {
            return false;
        }
        this.destination.getStack().get();
        this.src.add(this.card);
        return true;
    }
}
