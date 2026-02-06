package br.com.pointel.jarch.desk;

import javax.swing.JLabel;

public class DLabel extends JLabel {

    public DLabel() {
        super();
    }

    public DLabel(String text) {
        super(text);
    }

    public DLabel text(String text) {
        setText(text);
        return this;
    }

    public DLabel horizontalAlignment(int alignment) {
        setHorizontalAlignment(alignment);
        return this;
    }

    public DLabel horizontalAlignmentLeft() {
        setHorizontalAlignment(JLabel.LEFT);
        return this;
    }

    public DLabel horizontalAlignmentCenter() {
        setHorizontalAlignment(JLabel.CENTER);
        return this;
    }

    public DLabel horizontalAlignmentRight() {
        setHorizontalAlignment(JLabel.RIGHT);
        return this;
    }

    public DLabel horizontalAlignmentLeading() {
        setHorizontalAlignment(JLabel.LEADING);
        return this;
    }

    public DLabel horizontalAlignmentTrailing() {
        setHorizontalAlignment(JLabel.TRAILING);
        return this;
    }

    public DLabel verticalAlignment(int alignment) {
        setVerticalAlignment(alignment);
        return this;
    }
    
    public DLabel verticalAlignmentTop() {
        setVerticalAlignment(JLabel.TOP);
        return this;
    }

    public DLabel verticalAlignmentCenter() {
        setVerticalAlignment(JLabel.CENTER);
        return this;
    }

    public DLabel verticalAlignmentBottom() {
        setVerticalAlignment(JLabel.BOTTOM);
        return this;
    }

    public String name() {
        return getName();
    }

    public DLabel name(String name) {
        setName(name);
        return this;
    }

}
