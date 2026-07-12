package com.vidlus.jarch.desk;

import java.awt.FlowLayout;

public class DPaneWrap extends DPane {

    public DPaneWrap() {
        super(new LayoutWrap(FlowLayout.LEFT, 2, 2));
    }

}
