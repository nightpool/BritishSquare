package edu.wpi.evan.britishsquare.utils;

import ks.common.model.Element;
import ks.common.model.ElementListener;

import java.util.function.Consumer;

public class ListenerProxy<T extends Element> implements ElementListener {
    protected final Consumer<T> onChange;
    protected ElementListener next;

    public ListenerProxy(Consumer<T> onChange, ElementListener listener) {
        this.next = listener;
        this.onChange = onChange;
    }

    @Override
    public void modelChanged(Element elt) {
        this.onChange.accept((T) elt);
        if (this.next != null) {
            this.next.modelChanged(elt);
        }
    }
}
