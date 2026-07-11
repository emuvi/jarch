package com.vidlus.jarch.desk;

import java.awt.FlowLayout;

public class DWrapPane extends DPane {

    public DWrapPane() {
        super(new LayoutWrap(FlowLayout.LEFT, 2, 2));
    }

}
