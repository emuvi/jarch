package com.vidlus.jarch.desk;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JLayeredPane;

/**
 * A fluent wrapper for JLayeredPane.
 * Ideal for modern UIs that require overlapping components (like HUDs, floating buttons, or toast notifications).
 * Components added to the layers automatically resize to fill the pane, so you can easily use
 * transparent DPane containers with Border/Grid layouts to position overlays exactly where you want them.
 */
public class DPaneLayer extends DPane {

    private final JLayeredPane layeredPane;

    public DPaneLayer() {
        super(new BorderLayout());
        this.layeredPane = new JLayeredPane();
        
        // Ensure all components resize dynamically when the layered pane resizes
        this.layeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = layeredPane.getWidth();
                int h = layeredPane.getHeight();
                for (Component c : layeredPane.getComponents()) {
                    c.setBounds(0, 0, w, h);
                    c.revalidate();
                }
            }
        });
        
        super.add(layeredPane, BorderLayout.CENTER);
    }

    /**
     * Puts a component into a specific numerical layer. Higher numbers are drawn on top.
     * 
     * @param comp  the component to add
     * @param layer the Z-index layer
     * @return This DPaneLayer instance.
     */
    public DPaneLayer putLayer(Component comp, int layer) {
        layeredPane.add(comp, Integer.valueOf(layer));
        comp.setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());
        return this;
    }

    /**
     * Puts a DEdit into a specific numerical layer.
     * 
     * @param edit  the DEdit component to add
     * @param layer the Z-index layer
     * @return This DPaneLayer instance.
     */
    public DPaneLayer putLayer(DEdit<?> edit, int layer) {
        return putLayer(edit.comp(), layer);
    }

    /**
     * Puts a component in the base layer (drawn at the absolute bottom).
     * 
     * @param comp the component to add
     * @return This DPaneLayer instance.
     */
    public DPaneLayer putBase(Component comp) {
        return putLayer(comp, JLayeredPane.DEFAULT_LAYER);
    }

    public DPaneLayer putBase(DEdit<?> edit) {
        return putBase(edit.comp());
    }

    /**
     * Puts a component in the overlay layer (drawn above the base).
     * 
     * @param comp the component to add
     * @return This DPaneLayer instance.
     */
    public DPaneLayer putOverlay(Component comp) {
        return putLayer(comp, JLayeredPane.PALETTE_LAYER);
    }

    public DPaneLayer putOverlay(DEdit<?> edit) {
        return putOverlay(edit.comp());
    }

    /**
     * Puts a component in the popup layer (drawn at the very top).
     * 
     * @param comp the component to add
     * @return This DPaneLayer instance.
     */
    public DPaneLayer putPopup(Component comp) {
        return putLayer(comp, JLayeredPane.POPUP_LAYER);
    }

    public DPaneLayer putPopup(DEdit<?> edit) {
        return putPopup(edit.comp());
    }
}
