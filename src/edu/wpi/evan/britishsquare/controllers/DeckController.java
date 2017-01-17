package edu.wpi.evan.britishsquare.controllers;

import edu.wpi.evan.britishsquare.BritishSquare;
import edu.wpi.evan.britishsquare.models.DealCardMove;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.Deck;
import ks.common.view.DeckView;

import java.awt.event.MouseEvent;

public class DeckController extends SolitaireReleasedAdapter {
    protected final BritishSquare game;
    protected final DeckView deckView;

    public DeckController(BritishSquare game, DeckView deckView) {
        super(game);
        this.game = game;
        this.deckView = deckView;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        DealCardMove move = new DealCardMove((Deck) this.deckView.getModelElement(), this.game.getDiscard());
        if (move.valid(this.game)) {
            move.doMove(this.game);
            this.game.pushMove(move);
        }
        this.game.refreshWidgets();
    }
}
