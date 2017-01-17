package edu.wpi.evan.britishsquare;

import edu.wpi.evan.britishsquare.models.Suit;
import ks.common.model.Card;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BritishSquareTest {

    @Test
    public void setupTest() {
        BritishSquare bs = new BritishSquare();
        bs.initialize();

        assertEquals(88, bs.deck.count());
        assertEquals(0, bs.discard.count());

        assertEquals(4, bs.tableau.size());
        assertEquals(4, bs.foundations.size());

        assertEquals(88, bs.getNumLeft().getValue());
        assertEquals(0, bs.getScore().getValue());

        assertFalse(bs.hasWon());
    }

    @Test
    public void testFoundationCollection() {
        BritishSquare bs = new BritishSquare();
        bs.initialize();

        assertEquals(Optional.empty(), bs.foundations.get(0).getSuit());
        bs.foundations.get(0).add(new Card(Card.ACE, Card.HEARTS));
        assertEquals(Optional.of(Suit.HEARTS), bs.foundations.get(0).getSuit());
        assertFalse(bs.foundations.get(1).isValidCard(new Card(Card.ACE, Card.HEARTS)));
    }

    @Test
    public void testGetName(){
        BritishSquare bs = new BritishSquare();
        assertEquals("BritishSquare", bs.getName());
    }
}