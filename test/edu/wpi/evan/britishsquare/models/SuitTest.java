package edu.wpi.evan.britishsquare.models;

import ks.common.model.Card;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by evan on 11/15/16.
 */
public class SuitTest {
    @Test
    public void testSuit() throws Exception {
        assertEquals(Suit.SPADES.suitIndex, Card.SPADES);
        assertEquals(Suit.DIAMONDS.name, Card.DIAMONDSname);
        assertEquals(Suit.CLUBS.abbr, Card.CLUBSabbreviation);
    }

    @Test
    public void fromIndex() {
        assertEquals(Suit.fromIndex(Card.HEARTS), Suit.HEARTS);
    }

    @Test(expected = Suit.NonexistentSuitException.class)
    public void testInvalidIndex() {
        Suit.fromIndex(-1);
    }

}