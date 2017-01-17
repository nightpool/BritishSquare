package edu.wpi.evan.britishsquare.controllers;

import edu.wpi.evan.britishsquare.models.CardDestination;
import edu.wpi.evan.britishsquare.models.MoveCardMove;
import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Move;
import ks.common.model.Stack;
import ks.common.view.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DragCardController extends MouseAdapter {

    protected final Widget view;
    protected final Solitaire game;

    public DragCardController(Solitaire game, Widget view) {
        this.game = game;
        this.view = view;
    }

    public CardDestination getCardDestination() {
        return this.view.getModelElement() instanceof CardDestination ?
            (CardDestination) this.view.getModelElement() : null;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

        Container container = this.game.getContainer();
        Stack source = (Stack) this.view.getModelElement();

        if (source.count() == 0) {
            return;
        }

        CardView cardView = getCardViewForTopCard(this.view, mouseEvent);
        if (cardView == null) {
            return;
        }

        container.setActiveDraggingObject(cardView, mouseEvent);
        container.setDragSource(this.view);

        this.view.redraw();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Container container = this.game.getContainer();
        Widget draggingWidget = container.getActiveDraggingObject();
        if (draggingWidget == Container.getNothingBeingDragged()) {
            return;
        }

        Widget fromWidget = container.getDragSource();
        if (fromWidget == null) {
            container.releaseDraggingObject();
            return;
        }

        if (this.getCardDestination() == null) {
            fromWidget.returnWidget(draggingWidget);
            container.releaseDraggingObject();
            container.repaint();
            return;
        }
        Move move = new MoveCardMove(
            (Card) draggingWidget.getModelElement(),
            getCardDestination(),
            (Stack) fromWidget.getModelElement()
        );

        if (!move.valid(this.game)) {
            fromWidget.returnWidget(draggingWidget);
        } else {
            move.doMove(this.game);
            this.game.pushMove(move);
        }

        container.releaseDraggingObject();
        container.repaint();
    }

    protected CardView getCardViewForTopCard(Widget view, MouseEvent mouseEvent) {
        if (view instanceof ColumnView) {
            return ((ColumnView) view).getCardViewForTopCard(mouseEvent);
        } else if (view instanceof PileView) {
            return ((PileView) view).getCardViewForTopCard(mouseEvent);
        } else {
            return null;
        }
    }
}
