package edu.wpi.evan.britishsquare.utils;

import org.junit.Test;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class UtilsTest {
    @Test
    public void lastItemValid() throws Exception {
        Optional<Integer> lastValue = Utils.lastItem(IntStream.of(1,2,3,4,5).iterator());
        assertEquals(lastValue, Optional.of(5));

        lastValue = Utils.lastItem(IntStream.of(1).iterator());
        assertEquals(lastValue, Optional.of(1));
    }

    @Test
    public void lastItemInvalid() throws Exception {
        Optional<Integer> lastValue = Utils.lastItem(IntStream.empty().iterator());
        assertEquals(lastValue, Optional.empty());
    }

    @Test
    public void revIntStream() throws Exception {
        assertArrayEquals(new int[]{5, 4, 3, 2, 1, 0},
            Utils.revIntStream(5,0).toArray());
    }

}