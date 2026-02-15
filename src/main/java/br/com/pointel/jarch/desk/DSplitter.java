package br.com.pointel.jarch.desk;

import java.awt.Component;
import javax.swing.JSplitPane;

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

    public String name() {
        return getName();
    }

    public DSplitter name(String name) {
        setName(name);
        return this;
    }

}
