package br.com.pointel.jarch.desk;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.LayoutManager;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class DPane extends JPanel {

    public DPane() {
    }

    public DPane(LayoutManager layout) {
        super(layout);
    }

    public DPane layout(LayoutManager layout) {
        setLayout(layout);
        return this;
    }
    
    public DPane border(Border border) {
        setBorder(border);
        return this;
    }

    public DPane borderEmpty(int size) {
        setBorder(BorderFactory.createEmptyBorder(size, size, size, size));
        return this;
    }

    public DPane borderEmpty(int top, int left, int bottom, int right) {
        setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        return this;
    }

    public DPane borderLine(Color color) {
        setBorder(BorderFactory.createLineBorder(color));
        return this;
    }

    public DPane borderLine(Color color, int thickness) {
        setBorder(BorderFactory.createLineBorder(color, thickness));
        return this;
    }

    public DPane borderLine(Color color, int thickness, boolean rounded) {
        setBorder(BorderFactory.createLineBorder(color, thickness, rounded));
        return this;
    }

    public DPane borderEtched() {
        setBorder(BorderFactory.createEtchedBorder());
        return this;
    }

    public DPane borderEtched(int type) {
        setBorder(BorderFactory.createEtchedBorder(type));
        return this;
    }

    public DPane borderEtched(Color highlight, Color shadow) {
        setBorder(BorderFactory.createEtchedBorder(highlight, shadow));
        return this;
    }

    public DPane borderEtched(int type, Color highlight, Color shadow) {
        setBorder(BorderFactory.createEtchedBorder(type, highlight, shadow));
        return this;
    }

    public DPane borderBevelRaised() {
        setBorder(BorderFactory.createRaisedBevelBorder());
        return this;
    }

    public DPane borderBevelLowered() {
        setBorder(BorderFactory.createLoweredBevelBorder());
        return this;
    }

    public DPane borderBevel(int type) {
        setBorder(BorderFactory.createBevelBorder(type));
        return this;
    }

    public DPane borderBevel(int type, Color highlight, Color shadow) {
        setBorder(BorderFactory.createBevelBorder(type, highlight, shadow));
        return this;
    }

    public DPane borderBevel(int type, Color highlightOuter, Color highlightInner, Color shadowOuter, Color shadowInner) {
        setBorder(BorderFactory.createBevelBorder(type, highlightOuter, highlightInner, shadowOuter, shadowInner));
        return this;
    }

    public DPane borderSoftBevel(int type) {
        setBorder(BorderFactory.createSoftBevelBorder(type));
        return this;
    }

    public DPane borderSoftBevel(int type, Color highlight, Color shadow) {
        setBorder(BorderFactory.createSoftBevelBorder(type, highlight, shadow));
        return this;
    }

    public DPane borderSoftBevel(int type, Color highlightOuter, Color highlightInner, Color shadowOuter, Color shadowInner) {
        setBorder(BorderFactory.createSoftBevelBorder(type, highlightOuter, highlightInner, shadowOuter, shadowInner));
        return this;
    }

    public DPane borderTitled(String title) {
        setBorder(BorderFactory.createTitledBorder(title));
        return this;
    }

    public DPane borderTitled(Border border) {
        setBorder(BorderFactory.createTitledBorder(border));
        return this;
    }

    public DPane borderTitled(Border border, String title) {
        setBorder(BorderFactory.createTitledBorder(border, title));
        return this;
    }

    public DPane borderTitled(String title, int justification, int position) {
        setBorder(BorderFactory.createTitledBorder(null, title, justification, position));
        return this;
    }

    public DPane borderTitled(String title, int justification, int position, Font font) {
        setBorder(BorderFactory.createTitledBorder(null, title, justification, position, font));
        return this;
    }

    public DPane borderTitled(String title, int justification, int position, Font font, Color color) {
        setBorder(BorderFactory.createTitledBorder(null, title, justification, position, font, color));
        return this;
    }

    public DPane borderTitled(Border border, String title, int justification, int position, Font font, Color color) {
        setBorder(BorderFactory.createTitledBorder(border, title, justification, position, font, color));
        return this;
    }

    public DPane borderMatte(int top, int left, int bottom, int right, Color color) {
        setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, color));
        return this;
    }

    public DPane borderMatte(int top, int left, int bottom, int right, Icon tileIcon) {
        setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, tileIcon));
        return this;
    }

    public DPane borderCompound(Border outside, Border inside) {
        setBorder(BorderFactory.createCompoundBorder(outside, inside));
        return this;
    }

    public String name() {
        return getName();
    }

    public DPane name(String name) {
        setName(name);
        return this;
    }

    public DPane put(DEdit<?> edit) {
        return put(edit.comp());
    }
    

    public DPane put(Component comp) {
        add(comp);
        return this;
    }

    public DPane hint(String hint) {
        setToolTipText(hint);
        return this;
    }

    public String hint() {
        return getToolTipText();
    }

}
