package com.vidlus.jarch.desk;

import java.awt.CardLayout;
import java.awt.Component;

/**
 * A fluent wrapper for CardLayout.
 * Ideal for "Wizard" style dialogs, settings pages, or multi-step forms where only one view is visible at a time.
 */
public class DPaneCard extends DPane {

    private final CardLayout layout;

    /**
     * Creates a card layout with no horizontal or vertical gaps.
     */
    public DPaneCard() {
        this(0, 0);
    }

    /**
     * Creates a card layout with the specified gaps.
     * 
     * @param hgap the horizontal gap
     * @param vgap the vertical gap
     */
    public DPaneCard(int hgap, int vgap) {
        this.layout = new CardLayout(hgap, vgap);
        super.layout(this.layout);
    }

    /**
     * Adds a component as a new card in the layout with the specified name.
     * 
     * @param name the unique identifier for this card
     * @param comp the component to add
     * @return This DPaneCard instance.
     */
    public DPaneCard addCard(String name, Component comp) {
        add(comp, name);
        return this;
    }

    /**
     * Adds a DEdit component as a new card in the layout with the specified name.
     * 
     * @param name the unique identifier for this card
     * @param edit the DEdit component to add
     * @return This DPaneCard instance.
     */
    public DPaneCard addCard(String name, DEdit<?> edit) {
        return addCard(name, edit.comp());
    }

    /**
     * Flips to the card that was added with the specified name.
     * 
     * @param name the identifier of the card to show
     * @return This DPaneCard instance.
     */
    public DPaneCard showCard(String name) {
        layout.show(this, name);
        return this;
    }

    /**
     * Flips to the next card of the layout. If currently on the last card, it wraps to the first.
     * 
     * @return This DPaneCard instance.
     */
    public DPaneCard next() {
        layout.next(this);
        return this;
    }

    /**
     * Flips to the previous card of the layout. If currently on the first card, it wraps to the last.
     * 
     * @return This DPaneCard instance.
     */
    public DPaneCard previous() {
        layout.previous(this);
        return this;
    }

    /**
     * Flips to the first card of the layout.
     * 
     * @return This DPaneCard instance.
     */
    public DPaneCard first() {
        layout.first(this);
        return this;
    }

    /**
     * Flips to the last card of the layout.
     * 
     * @return This DPaneCard instance.
     */
    public DPaneCard last() {
        layout.last(this);
        return this;
    }
}
