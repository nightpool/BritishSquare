package edu.wpi.evan.britishsquare.models;

import edu.wpi.evan.britishsquare.utils.Utils;
import ks.common.model.Card;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FoundationTest {

    public static FoundationCollection emptyFC() {
        return Stream::empty;
    }

    public static FoundationCollection foundationsFor(int... suits) {
        return () ->
            Arrays.stream(suits).mapToObj(s -> {
                Foundation f = new Foundation(emptyFC());
                f.add(new Card(Card.ACE, s));
                return f;
            });
    }

    @Test
    public void testAddSuitNoOthers() {
        Foundation foundation = new Foundation(emptyFC());
        assertTrue(foundation.isValidCard(new Card(Card.ACE, Card.CLUBS)));
        assertTrue(foundation.isValidCard(new Card(Card.ACE, Card.HEARTS)));
    }

    @Test
    public void testAddSuitWithOthers() {
        Foundation foundation = new Foundation(foundationsFor(Card.HEARTS));
        assertTrue(foundation.isValidCard(new Card(Card.ACE, Card.CLUBS)));
        assertFalse(foundation.isValidCard(new Card(Card.ACE, Card.HEARTS)));
    }

    @Test
    public void testAddCard() {
        Foundation foundation = new Foundation(emptyFC());
        assertTrue(foundation.isValidCard(new Card(Card.ACE, Card.HEARTS)));
        assertFalse(foundation.isValidCard(new Card(Card.TWO, Card.HEARTS)));
    }

    @Test
    public void testAddMultipleCards() {
        Foundation foundation = new Foundation(emptyFC());
        foundation.add(new Card(Card.ACE, Card.SPADES));

        assertTrue(foundation.isValidCard(new Card(Card.TWO, Card.SPADES)));
        assertFalse(foundation.isValidCard(new Card(Card.ACE, Card.SPADES)));
        assertFalse(foundation.isValidCard(new Card(Card.TWO, Card.HEARTS)));

        foundation.add(new Card(Card.TWO, Card.SPADES));

        assertTrue(foundation.isValidCard(new Card(Card.THREE, Card.SPADES)));
        assertFalse(foundation.isValidCard(new Card(Card.TWO, Card.SPADES)));
    }

    @Test
    public void testRoundTheCorner() {
        Foundation foundation = new Foundation(emptyFC());
        foundation.add(new Card(Card.JACK, Card.DIAMONDS));
        foundation.add(new Card(Card.QUEEN, Card.DIAMONDS));
        foundation.add(new Card(Card.KING, Card.DIAMONDS));

        assertFalse(foundation.isValidCard(new Card(Card.KING, Card.DIAMONDS)));
        assertTrue(foundation.isValidCard(new Card(Card.ACE, Card.DIAMONDS)));

        foundation.add(new Card(Card.ACE, Card.DIAMONDS));

        assertTrue(foundation.isValidCard(new Card(Card.KING, Card.DIAMONDS)));
        assertFalse(foundation.isValidCard(new Card(Card.TWO, Card.DIAMONDS)));
    }

    @Test
    public void nothingIsNotComplete() {
        Foundation foundation = new Foundation(emptyFC());
        assertFalse(foundation.isComplete());
    }

    @Test
    public void fullStackIsComplete() {
        Foundation foundation = new Foundation(emptyFC());
        IntStream.rangeClosed(1,13).mapToObj(r -> new Card(r, Card.HEARTS)).forEach(foundation::add);
        Utils.revIntStream(13,1).mapToObj(r -> new Card(r, Card.HEARTS)).forEach(foundation::add);
        assertTrue(foundation.isComplete());
    }

    @Test
    public void wrongSuitIsNotComplete() {
        Foundation foundation = new Foundation(emptyFC());
        IntStream.rangeClosed(1,13).mapToObj(r -> new Card(r, Card.HEARTS)).forEach(foundation::add);
        Utils.revIntStream(13,1).mapToObj(r -> new Card(r, Card.DIAMONDS)).forEach(foundation::add);
        assertFalse(foundation.isComplete());
    }

    @Test
    public void missingLastIsNotComplete() {
        Foundation foundation = new Foundation(emptyFC());
        IntStream.rangeClosed(1,13).mapToObj(r -> new Card(r, Card.HEARTS)).forEach(foundation::add);
        Utils.revIntStream(13,2).mapToObj(r -> new Card(r, Card.DIAMONDS)).forEach(foundation::add);
        assertFalse(foundation.isComplete());
    }
}