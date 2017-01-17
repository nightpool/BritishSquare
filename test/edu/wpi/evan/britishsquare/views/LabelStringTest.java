package edu.wpi.evan.britishsquare.views;

import ks.common.model.MutableInteger;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LabelStringTest {

    @Test
    public void labelStringTest() throws Exception {
        MutableInteger source = new MutableInteger(0);
        LabelString label = new LabelString("labeled:", source);

        assertEquals("labeled: 0", label.getValue());

        source.setValue(1);
        assertEquals("labeled: 1", label.getValue());
    }

}