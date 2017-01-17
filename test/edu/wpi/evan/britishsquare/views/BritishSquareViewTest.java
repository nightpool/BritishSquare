package edu.wpi.evan.britishsquare.views;

import edu.wpi.evan.britishsquare.BritishSquare;
import ks.client.gamefactory.GameWindow;
import ks.launcher.Main;
import org.junit.Test;

public class BritishSquareViewTest {

    @Test
    public void testLayout() throws Exception {
        BritishSquare bs = new BritishSquare();
        GameWindow gw = Main.generateWindow(bs, 4201);
        gw.dispose();
    }

}