package br.com.pointel.jarch.desk;

import java.awt.FlowLayout;

public class DWrapPane extends DPane {

    public DWrapPane() {
        super(new WrapLayout(FlowLayout.LEFT, 2, 2));
    }

}
