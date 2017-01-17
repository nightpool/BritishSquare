package edu.wpi.evan.britishsquare.models;

import ks.common.model.Card;
import org.junit.Test;

import static com.github.npathai.hamcrestopt.OptionalMatchers.hasValue;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static org.junit.Assert.*;

public class TableauTest {

    @Test
    public void getGetSuit() {
        Tableau tableau = new Tableau();
        assertFalse(tableau.getSuit().isPresent());
        tableau.add(new Card(Card.ACE, Card.CLUBS));
        assertTrue(tableau.getSuit().isPresent());
        assertEquals(tableau.getSuit().get(), Suit.CLUBS);
    }

    @Test
    public void testSimpleDirection() {
        Tableau tableau = new Tableau();
        tableau.add(new Card(Card.ACE, Card.CLUBS));
        tableau.add(new Card(Card.TWO, Card.CLUBS));
        assertThat(tableau.direction(), hasValue(1));
    }

    @Test
    public void testNoDirection() {
        Tableau tableau = new Tableau();
        assertThat(tableau.direction(), isEmpty());
    }

    @Test
    public void testInitDirection() {
        Tableau tableau = new Tableau();
        tableau.addInitCard(new Card(Card.ACE, Card.CLUBS));
        tableau.addInitCard(new Card(Card.TWO, Card.CLUBS));
        assertThat(tableau.direction(), isEmpty());
    }

    @Test
    public void testComplexDirection() {
        Tableau tableau = new Tableau();
        tableau.addInitCard(new Card(Card.ACE, Card.CLUBS));
        tableau.addInitCard(new Card(Card.THREE, Card.CLUBS));
        tableau.addInitCard(new Card(Card.TWO, Card.CLUBS));
        tableau.addInitCard(new Card(Card.FIVE, Card.CLUBS));
        tableau.add(new Card(Card.FOUR, Card.CLUBS));
        assertThat(tableau.direction(), hasValue(-1));
    }

    @Test
    public void testSimpleValid() {
        Tableau tableau = new Tableau();
        tableau.add(new Card(Card.ACE, Card.CLUBS));
        tableau.add(new Card(Card.TWO, Card.CLUBS));
        assertFalse(tableau.isValidCard(new Card(Card.TWO, Card.CLUBS)));
        assertFalse(tableau.isValidCard(new Card(Card.THREE, Card.HEARTS)));
        assertTrue(tableau.isValidCard(new Card(Card.THREE, Card.CLUBS)));
    }

    @Test
    public void testEmptyValid() {
        Tableau tableau = new Tableau();
        assertTrue(tableau.isValidCard(new Card(Card.ACE, Card.CLUBS)));
        assertTrue(tableau.isValidCard(new Card(Card.TEN, Card.CLUBS)));
    }

    @Test
    public void testValidWithInit() {
        Tableau tableau = new Tableau();
        tableau.addInitCard(new Card(Card.ACE, Card.CLUBS));
        tableau.addInitCard(new Card(Card.THREE, Card.SPADES));
        tableau.addInitCard(new Card(Card.TWO, Card.CLUBS));
        tableau.addInitCard(new Card(Card.FIVE, Card.HEARTS));

        assertFalse(tableau.isValidCard(new Card(Card.SEVEN, Card.HEARTS)));
        assertFalse(tableau.isValidCard(new Card(Card.SIX, Card.CLUBS)));
        assertTrue(tableau.isValidCard(new Card(Card.SIX, Card.HEARTS)));
        assertTrue(tableau.isValidCard(new Card(Card.FOUR, Card.HEARTS)));

        tableau.add(new Card(Card.SIX, Card.HEARTS));

        assertTrue(tableau.isValidCard(new Card(Card.SEVEN, Card.HEARTS)));
    }

    @Test
    public void testRemoveWithInit() {
        Tableau tableau = new Tableau();
        tableau.addInitCard(new Card(Card.ACE, Card.CLUBS));
        tableau.addInitCard(new Card(Card.THREE, Card.SPADES));
        tableau.addInitCard(new Card(Card.TWO, Card.CLUBS));
        tableau.addInitCard(new Card(Card.FIVE, Card.HEARTS));

        tableau.add(new Card(Card.SIX, Card.HEARTS));
        tableau.add(new Card(Card.SEVEN, Card.HEARTS));
        tableau.add(new Card(Card.EIGHT, Card.HEARTS));

        assertTrue(tableau.isValidCard(new Card(Card.NINE, Card.HEARTS)));
        assertFalse(tableau.isValidCard(new Card(Card.SEVEN, Card.HEARTS)));

        tableau.get();
        tableau.get();

        assertTrue(tableau.isValidCard(new Card(Card.SEVEN, Card.HEARTS)));
        assertFalse(tableau.isValidCard(new Card(Card.FIVE, Card.HEARTS)));

        tableau.get();
        tableau.get();

        assertTrue(tableau.isValidCard(new Card(Card.ACE, Card.CLUBS)));
        assertTrue(tableau.isValidCard(new Card(Card.THREE, Card.CLUBS)));
        assertFalse(tableau.isValidCard(new Card(Card.FOUR, Card.CLUBS)));

        tableau.add(new Card(Card.THREE, Card.CLUBS));

        assertTrue(tableau.isValidCard(new Card(Card.FOUR, Card.CLUBS)));
        assertFalse(tableau.isValidCard(new Card(Card.TWO, Card.CLUBS)));
        assertFalse(tableau.isValidCard(new Card(Card.ACE, Card.CLUBS)));

        tableau.get();

        assertTrue(tableau.isValidCard(new Card(Card.ACE, Card.CLUBS)));
        assertTrue(tableau.isValidCard(new Card(Card.THREE, Card.CLUBS)));
        assertFalse(tableau.isValidCard(new Card(Card.FOUR, Card.CLUBS)));
    }
}