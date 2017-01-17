package edu.wpi.evan.britishsquare.models;

import ks.common.model.Card;
import ks.common.model.Move;
import ks.common.model.Pile;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MoveCardMoveTest {
    @Test
    public void valid() throws Exception {
        Pile pile = new Pile();
        Tableau tableau = new Tableau();

        Move move = new MoveCardMove(
            new Card(Card.ACE, Card.HEARTS),
            tableau,
            pile
        );

        assertTrue(move.valid(null));
    }

    @Test
    public void doMove() throws Exception {
        Foundation foundation = new Foundation(FoundationTest.emptyFC());
        Pile source = new Pile();

        Move move = new MoveCardMove(
            new Card(Card.ACE, Card.HEARTS),
            foundation,
            source
        );

        move.doMove(null);

        assertEquals(1, foundation.count());
        assertEquals(new Card(Card.ACE, Card.HEARTS), foundation.peek());
    }

    @Test
    public void undoValid() throws Exception {
        Foundation foundation = new Foundation(FoundationTest.emptyFC());
        Pile source = new Pile();

        Move move = new MoveCardMove(
            new Card(Card.ACE, Card.HEARTS),
            foundation,
            source
        );

        move.doMove(null);
        move.undo(null);

        assertEquals(0, foundation.count());
        assertEquals(1, source.count());
        assertEquals(new Card(Card.ACE, Card.HEARTS), source.peek());
    }

    @Test
    public void undoInvalid() throws Exception {
        Foundation foundation = new Foundation(FoundationTest.emptyFC());
        Pile source = new Pile();

        Move move = new MoveCardMove(
            new Card(Card.ACE, Card.HEARTS),
            foundation,
            source
        );

        move.doMove(null);
        foundation.add(new Card(Card.TWO, Card.HEARTS));
        assertFalse(move.undo(null));

        assertEquals(2, foundation.count());
        assertEquals(0, source.count());
        assertEquals(new Card(Card.TWO, Card.HEARTS), foundation.peek());
    }

}