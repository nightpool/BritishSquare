package edu.wpi.evan.britishsquare.views;

import ks.common.model.Element;
import ks.common.model.ElementListener;
import ks.common.model.MutableInteger;
import ks.common.model.MutableString;

public class LabelString extends MutableString implements ElementListener {
    protected final String label;

    public LabelString(String label, MutableInteger score) {
        super(label + " " + score.getValue());
        this.label = label;
        score.setListener(this);
    }

    @Override
    public void modelChanged(Element elt) {
        MutableInteger mi = (MutableInteger) elt;
        setValue(this.label + " " + mi.getValue());
    }
}
