package edu.wpi.evan.britishsquare.models;

import ks.common.model.Card;
import ks.common.model.Stack;

public interface CardDestination {
    public Stack getStack();
    public boolean isValidCard(Card toAdd);
}
