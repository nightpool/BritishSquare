package edu.wpi.evan.britishsquare.controllers;

import edu.wpi.evan.britishsquare.models.CardDestination;
import ks.client.gamefactory.GameWindow;
import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Pile;
import ks.common.model.Stack;
import ks.common.view.PileView;
import ks.launcher.Main;
import org.junit.Test;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

public class DragCardTest {

    public class MockDestination extends Pile implements CardDestination {
        protected final Predicate<Card> predicate;
        @Override
        public Stack getStack() {
            return this;
        }
        @Override
        public boolean isValidCard(Card toAdd) {
            return this.predicate.test(toAdd);
        }
        public MockDestination(Predicate<Card> predicate){
            this.predicate = predicate;
        }
    }

    public class MockSolitaire extends Solitaire {

        @Override
        public String getName() {
            return "mock";
        }
        @Override
        public boolean hasWon() {
            return false;
        }

        public final List<Pile> piles;

        public MockSolitaire(Pile... piles) {
            this.piles = Arrays.asList(piles);
        }

        @Override
        public void initialize() {
            int index = 0;
            for (Pile pile : this.piles) {
                PileView view = new PileView(pile);
                view.setBounds(index*20, 0, 10, 10);
                addViewWidget(view);
                view.setMouseAdapter(new DragCardController(this, view));
                index++;
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(this.piles.size()*20, 10);
        }
    }

    @Test
    public void canDrag() throws Exception {
        MockSolitaire game = new MockSolitaire(
            new Pile(),
            new MockDestination(c -> true)
        );
        GameWindow gw = Main.generateWindow(game, 0);
        gw.setVisible(false);
        game.piles.get(0).add(new Card(Card.ACE, Card.HEARTS));
        game.getContainer().processMouseEvent(createPressed(game, 5, 5));
        game.getContainer().processMouseEvent(createReleased(game, 25, 5));

        assertEquals(0, game.piles.get(0).count());
        assertEquals(1, game.piles.get(1).count());
        assertEquals(new Card(Card.ACE, Card.HEARTS), game.piles.get(1).get());
    }

    @Test
    public void cannotDragInvalid() throws Exception {
        MockSolitaire game = new MockSolitaire(
            new Pile(),
            new MockDestination(c -> false)
        );
        GameWindow gw = Main.generateWindow(game, 0);
        gw.setVisible(false);
        game.piles.get(0).add(new Card(Card.ACE, Card.HEARTS));
        game.getContainer().processMouseEvent(createPressed(game, 5, 5));
        game.getContainer().processMouseEvent(createReleased(game, 25, 5));

        assertEquals(1, game.piles.get(0).count());
        assertEquals(0, game.piles.get(1).count());
        assertEquals(new Card(Card.ACE, Card.HEARTS), game.piles.get(0).get());
    }

    @Test
    public void cannotDragNoDestination() throws Exception {
        MockSolitaire game = new MockSolitaire(
            new Pile(),
            new Pile()
        );
        GameWindow gw = Main.generateWindow(game, 0);
        gw.setVisible(false);
        game.piles.get(0).add(new Card(Card.ACE, Card.HEARTS));
        game.getContainer().processMouseEvent(createPressed(game, 5, 5));
        game.getContainer().processMouseEvent(createReleased(game, 25, 5));

        assertEquals(1, game.piles.get(0).count());
        assertEquals(0, game.piles.get(1).count());
        assertEquals(new Card(Card.ACE, Card.HEARTS), game.piles.get(0).get());
    }

    public MouseEvent createPressed(Solitaire game, int x, int y) {
        return new MouseEvent(game.getContainer(), MouseEvent.MOUSE_PRESSED,
            System.currentTimeMillis(), 0,
            x, y, 0, false);
    }

    /** (dx,dy) are offsets into the widget space. Feel Free to Use as Is. */
    public MouseEvent createReleased(Solitaire game, int x, int y) {
        return new MouseEvent(game.getContainer(), MouseEvent.MOUSE_RELEASED,
            System.currentTimeMillis(), 0,
            x, y, 0, false);
    }
}