package edu.wpi.evan.britishsquare;

import edu.wpi.evan.britishsquare.controllers.DeckController;
import edu.wpi.evan.britishsquare.controllers.DragCardController;
import edu.wpi.evan.britishsquare.models.Foundation;
import edu.wpi.evan.britishsquare.models.FoundationCollection;
import edu.wpi.evan.britishsquare.models.Tableau;
import edu.wpi.evan.britishsquare.utils.ListenerProxy;
import edu.wpi.evan.britishsquare.views.BritishSquareView;
import ks.client.gamefactory.GameWindow;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.games.Solitaire;
import ks.common.model.Deck;
import ks.common.model.MultiDeck;
import ks.common.model.MutableInteger;
import ks.common.model.Pile;
import ks.common.view.Widget;
import ks.launcher.Main;

import java.awt.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BritishSquare extends Solitaire implements FoundationCollection {

    public MultiDeck deck;
    public Pile discard;
    public List<Tableau> tableau;
    public List<Foundation> foundations;

    public BritishSquareView view;

    @Override
    public String getName() {
        return "BritishSquare";
    }

    @Override
    public boolean hasWon() {
        return this.getFoundations().allMatch(
            f -> f.isComplete()
        );
    }

    @Override
    public void initialize() {
        this.deck = new MultiDeck(2);
        this.deck.create(this.seed);
        this.discard = new Pile();

        this.foundations = Stream.generate(() -> this).map(Foundation::new).limit(4).collect(Collectors.toList());
        this.tableau = Stream.generate(Tableau::new).limit(4).collect(Collectors.toList());
        for (Tableau t : this.tableau) {
            Stream.generate(this.deck::get).limit(4).forEach(t::addInitCard);
        }
        this.numLeft = new MutableInteger(this.deck.count());
        this.score = new MutableInteger(0);


        if (this.container != null) {
            this.view = new BritishSquareView(this);
            this.view.layout();
            this.setupControllers();
        }

        this.deck.setListener(new ListenerProxy<Deck>(
            d -> this.numLeft.setValue(this.cardsLeft()),
            this.deck.getListener()
        ));
        this.deck.getListener().modelChanged(this.deck);

        for (Foundation f : this.foundations) {
            f.setListener(new ListenerProxy<Foundation>(
                __ -> this.score.setValue(this.score()),
                f.getListener()
            ));
        }
    }

    public int score() {
        return this.foundations.stream().mapToInt(Foundation::count).sum();
    }

    public int cardsLeft() {
        return this.deck.count();
    }

    public void registerViewWidget(Widget w){
        addViewWidget(w);
    }

    @Override
    public Stream<Foundation> getFoundations() {
        return this.foundations.stream();
    }

    public Pile getDiscard() {
        return this.discard;
    }

    public void setupControllers() {
        this.view.deckView.setMouseAdapter(new DeckController(this, this.view.deckView));
        this.view.discardView.setMouseAdapter(new DragCardController(this, this.view.discardView));
        this.view.tableauViews.forEach(
            cv -> cv.setMouseAdapter(new DragCardController(this, cv))
        );
        this.view.foundationViews.forEach(
            pv -> pv.setMouseAdapter(new DragCardController(this, pv))
        );

        this.view.cardsLeft.setMouseAdapter(new SolitaireReleasedAdapter(this));
        this.view.scoreView.setMouseAdapter(new SolitaireReleasedAdapter(this));
        this.view.title.setMouseAdapter(new SolitaireReleasedAdapter(this));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1024, 768);
    }

    public static void main (String []args) {
        GameWindow gw = Main.generateWindow(new BritishSquare(), Instant.now().getNano());
//        GameWindow gw = Main.generateWindow(new BritishSquare(), Deck.OrderByRank);
        gw.setVisible(true);
    }
}
