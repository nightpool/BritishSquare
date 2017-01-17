package edu.wpi.evan.britishsquare.models;

import ks.common.model.Card;
import ks.common.model.Deck;
import ks.common.model.Pile;
import ks.tests.model.ModelFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class DealCardMoveTest {
    @Test
    public void testValidSimple() throws Exception {
        Deck deck = new Deck();
        ModelFactory.init(deck, "1H 2H 3H");
        Pile discard = new Pile();

        DealCardMove move = new DealCardMove(deck, discard);

        assertTrue(move.valid(null));
    }

    @Test
    public void testInvalid() throws Exception {
        Deck deck = new Deck();
        Pile discard = new Pile();
        ModelFactory.init(discard, "1H 2H 3H");
        DealCardMove move = new DealCardMove(deck, discard);

        assertFalse(move.valid(null));
    }

    @Test
    public void doMove() throws Exception {
        Deck deck = new Deck();
        ModelFactory.init(deck, "1H 2H 3H");
        Pile discard = new Pile();
        DealCardMove move = new DealCardMove(deck, discard);

        move.doMove(null);

        assertEquals(2, deck.count());
        assertEquals(1, discard.count());
        assertEquals(new Card(Card.THREE, Card.HEARTS), discard.peek());
        assertEquals(new Card(Card.TWO, Card.HEARTS), deck.peek());
    }

    @Test
    public void undo() throws Exception {
        Deck deck = new Deck();
        ModelFactory.init(deck, "1H 2H 3H");
        Pile discard = new Pile();
        DealCardMove move = new DealCardMove(deck, discard);

        assertTrue(move.doMove(null));
        assertTrue(move.undo(null));

        assertEquals(3, deck.count());
        assertEquals(0, discard.count());
        assertEquals(new Card(Card.THREE, Card.HEARTS), deck.peek());
    }

    @Test
    public void invalidUndo() throws Exception {
        Deck deck = new Deck();
        ModelFactory.init(deck, "1H 2H 3H");
        Pile discard = new Pile();
        DealCardMove move = new DealCardMove(deck, discard);

        assertTrue(move.doMove(null));
        DealCardMove nextMove = new DealCardMove(deck, discard);
        assertTrue(nextMove.doMove(null));
        assertFalse(move.undo(null));

        assertEquals(1, deck.count());
        assertEquals(2, discard.count());
        assertEquals(new Card(Card.ACE, Card.HEARTS), deck.peek());
        assertEquals(new Card(Card.TWO, Card.HEARTS), discard.peek());
    }

}