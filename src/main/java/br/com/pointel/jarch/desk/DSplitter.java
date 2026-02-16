package br.com.pointel.jarch.desk;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JSplitPane;
import javax.swing.border.Border;

public class DSplitter extends JSplitPane {

    public DSplitter() {
        super();
    }

    public DSplitter(Component leftComponent, Component rightComponent) {
        super(JSplitPane.HORIZONTAL_SPLIT, leftComponent, rightComponent);
    }

    public DSplitter(int orientation) {
        super(orientation);
    }

    public DSplitter(int orientation, boolean continuousLayout) {
        super(orientation, continuousLayout);
    }

    public DSplitter(int orientation, Component leftComponent, Component rightComponent) {
        super(orientation, leftComponent, rightComponent);
    }

    public DSplitter(int orientation, boolean continuousLayout, Component leftComponent, Component rightComponent) {
        super(orientation, continuousLayout, leftComponent, rightComponent);
    }

    public DSplitter horizontal() {
        setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        return this;
    }

    public DSplitter vertical() {
        setOrientation(JSplitPane.VERTICAL_SPLIT);
        return this;
    }

    public DSplitter left(Component comp) {
        setLeftComponent(comp);
        return this;
    }

    public DSplitter left(DEdit<?> edit) {
        return left(edit.comp());
    }

    public DSplitter right(Component comp) {
        setRightComponent(comp);
        return this;
    }

    public DSplitter right(DEdit<?> edit) {
        return right(edit.comp());
    }

    public DSplitter top(Component comp) {
        setTopComponent(comp);
        return this;
    }

    public DSplitter top(DEdit<?> edit) {
        return top(edit.comp());
    }

    public DSplitter bottom(Component comp) {
        setBottomComponent(comp);
        return this;
    }

    public DSplitter bottom(DEdit<?> edit) {
        return bottom(edit.comp());
    }

    public DSplitter divider(int location) {
        setDividerLocation(location);
        return this;
    }

    public DSplitter divider(double proportionalLocation) {
        setDividerLocation(proportionalLocation);
        return this;
    }

    public DSplitter dividerSize(int size) {
        setDividerSize(size);
        return this;
    }

    public DSplitter resizeWeight(double value) {
        setResizeWeight(value);
        return this;
    }

    public DSplitter continuousLayout(boolean continuousLayout) {
        setContinuousLayout(continuousLayout);
        return this;
    }

    public DSplitter oneTouchExpandable(boolean newValue) {
        setOneTouchExpandable(newValue);
        return this;
    }

    public DSplitter border(Border border) {
        setBorder(border);
        return this;
    }

    public DSplitter borderEmpty(int size) {
        setBorder(BorderFactory.createEmptyBorder(size, size, size, size));
        return this;
    }

    public DSplitter borderEmpty(int top, int left, int bottom, int right) {
        setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        return this;
    }

    public DSplitter borderLine(Color color) {
        setBorder(BorderFactory.createLineBorder(color));
        return this;
    }

    public DSplitter borderLine(Color color, int thickness) {
        setBorder(BorderFactory.createLineBorder(color, thickness));
        return this;
    }

    public DSplitter borderLine(Color color, int thickness, boolean rounded) {
        setBorder(BorderFactory.createLineBorder(color, thickness, rounded));
        return this;
    }

    public DSplitter borderEtched() {
        setBorder(BorderFactory.createEtchedBorder());
        return this;
    }

    public DSplitter borderEtched(int type) {
        setBorder(BorderFactory.createEtchedBorder(type));
        return this;
    }

    public DSplitter borderEtched(Color highlight, Color shadow) {
        setBorder(BorderFactory.createEtchedBorder(highlight, shadow));
        return this;
    }

    public DSplitter borderEtched(int type, Color highlight, Color shadow) {
        setBorder(BorderFactory.createEtchedBorder(type, highlight, shadow));
        return this;
    }

    public DSplitter borderBevelRaised() {
        setBorder(BorderFactory.createRaisedBevelBorder());
        return this;
    }

    public DSplitter borderBevelLowered() {
        setBorder(BorderFactory.createLoweredBevelBorder());
        return this;
    }

    public DSplitter borderBevel(int type) {
        setBorder(BorderFactory.createBevelBorder(type));
        return this;
    }

    public DSplitter borderBevel(int type, Color highlight, Color shadow) {
        setBorder(BorderFactory.createBevelBorder(type, highlight, shadow));
        return this;
    }

    public DSplitter borderBevel(int type, Color highlightOuter, Color highlightInner, Color shadowOuter, Color shadowInner) {
        setBorder(BorderFactory.createBevelBorder(type, highlightOuter, highlightInner, shadowOuter, shadowInner));
        return this;
    }

    public DSplitter borderSoftBevel(int type) {
        setBorder(BorderFactory.createSoftBevelBorder(type));
        return this;
    }

    public DSplitter borderSoftBevel(int type, Color highlight, Color shadow) {
        setBorder(BorderFactory.createSoftBevelBorder(type, highlight, shadow));
        return this;
    }

    public DSplitter borderSoftBevel(int type, Color highlightOuter, Color highlightInner, Color shadowOuter, Color shadowInner) {
        setBorder(BorderFactory.createSoftBevelBorder(type, highlightOuter, highlightInner, shadowOuter, shadowInner));
        return this;
    }

    public DSplitter borderTitled(String title) {
        setBorder(BorderFactory.createTitledBorder(title));
        return this;
    }

    public DSplitter borderTitled(Border border) {
        setBorder(BorderFactory.createTitledBorder(border));
        return this;
    }

    public DSplitter borderTitled(Border border, String title) {
        setBorder(BorderFactory.createTitledBorder(border, title));
        return this;
    }

    public DSplitter borderTitled(String title, int justification, int position) {
        setBorder(BorderFactory.createTitledBorder(null, title, justification, position));
        return this;
    }

    public DSplitter borderTitled(String title, int justification, int position, Font font) {
        setBorder(BorderFactory.createTitledBorder(null, title, justification, position, font));
        return this;
    }

    public DSplitter borderTitled(String title, int justification, int position, Font font, Color color) {
        setBorder(BorderFactory.createTitledBorder(null, title, justification, position, font, color));
        return this;
    }

    public DSplitter borderTitled(Border border, String title, int justification, int position, Font font, Color color) {
        setBorder(BorderFactory.createTitledBorder(border, title, justification, position, font, color));
        return this;
    }

    public DSplitter borderMatte(int top, int left, int bottom, int right, Color color) {
        setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, color));
        return this;
    }

    public DSplitter borderMatte(int top, int left, int bottom, int right, Icon tileIcon) {
        setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, tileIcon));
        return this;
    }

    public DSplitter borderCompound(Border outside, Border inside) {
        setBorder(BorderFactory.createCompoundBorder(outside, inside));
        return this;
    }

    public String name() {
        return getName();
    }

    public DSplitter name(String name) {
        setName(name);
        return this;
    }

    public DSplitter hint(String hint) {
        setToolTipText(hint);
        return this;
    }

    public String hint() {
        return getToolTipText();
    }

}
