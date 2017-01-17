package edu.wpi.evan.britishsquare.views;


import edu.wpi.evan.britishsquare.BritishSquare;
import ks.common.view.*;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class BritishSquareView {

    protected final BritishSquare game;

    public List<PileView> foundationViews;
    public List<ColumnView> tableauViews;

    public DeckView deckView;
    public PileView discardView;

    public StringView title;
    public StringView scoreView;
    public StringView cardsLeft;

    public BritishSquareView(BritishSquare game) {
        this.game = game;

        this.foundationViews = game.foundations.stream()
            .map(PileView::new)
            .collect(Collectors.toList());

        this.tableauViews = game.tableau.stream()
            .map(ColumnView::new)
            .collect(Collectors.toList());

        this.deckView = new DeckView(game.deck);
        this.discardView = new PileView(game.discard);

        this.title = new StringView("BritishSquare");
        this.title.setFont(getHeaderFont());

        this.scoreView = new StringView(new LabelString("score: ", game.getScore()));
        this.scoreView.setFont(Font.decode("Courier New 20"));

        this.cardsLeft = new StringView(new LabelString("cards left: ", game.getNumLeft()));
        this.cardsLeft.setFont(Font.decode("Courier New 20"));

    }

    private static Font headerFont;
    public static Font getHeaderFont() {
        if (headerFont == null) {
            headerFont = new Font("Courier New", Font.BOLD, 32);
        }
        return headerFont;
    }

    @SuppressWarnings("UnqualifiedFieldAccess")
    public void layout() {
        title.setBounds(200, -20, 500, 50);
        game.registerViewWidget(title);

        scoreView.setBounds(700, -10, 200, 40);
        game.registerViewWidget(scoreView);

        cardsLeft.setBounds(700, 40, 200, 40);
        game.registerViewWidget(cardsLeft);

        deckView.setBounds(cardGridRect(0,0));
        game.registerViewWidget(deckView);

        discardView.setBounds(cardGridRect(1,0));
        game.registerViewWidget(discardView);

        int row = 2;
        for (PileView fv : foundationViews){
            fv.setBounds(cardGridRect(row++, 0));
            game.registerViewWidget(fv);
        }

        row = 2;
        for (ColumnView tv : tableauViews){
            tv.setBounds(cardGridRect(row++, 1));
            tv.setHeight(1000);
            game.registerViewWidget(tv);
        }
    }

    protected Rectangle cardGridRect(int row, int col) {
        return new Rectangle(
            100 + (this.game.getCardImages().getWidth() + 30) * row,
            100 + (this.game.getCardImages().getHeight() + 30) * col,
            this.game.getCardImages().getWidth(),
            this.game.getCardImages().getHeight());
    }

}
