package com.vidlus.jarch.desk;

import java.awt.Color;

public class DChangeColor extends DEditChange<Color> {

    private Color currentColor;

    public DChangeColor() {
        super("■");
        getField().setEditable(false);
    }

    @Override
    public Color getValue() {
        return currentColor;
    }

    @Override
    public void setValue(Color value) {
        this.currentColor = value;
        if (value != null) {
            getField().setText(String.format("#%02X%02X%02X", value.getRed(), value.getGreen(), value.getBlue()));
            getButton().setForeground(value);
        } else {
            getField().setText("");
            getButton().setForeground(Color.BLACK);
        }
    }

    @Override
    protected void onActionPressed() {
        DColor colorDialog = new DColor();
        colorDialog.parent(comp());
        if (currentColor != null) {
            colorDialog.color(currentColor);
        }
        
        Color result = colorDialog.showDialog();
        if (result != null) {
            setValue(result);
        }
    }
}
