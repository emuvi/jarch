package br.com.pointel.jarch.flow;

import br.com.pointel.jarch.desk.DFrame;
import br.com.pointel.jarch.mage.WizGUI;

public class AppGUI {

    private final Class<? extends DFrame> frameClass;

    public AppGUI(Class<? extends DFrame> frameClass) {
        this.frameClass = frameClass;
    }

    public void start(String title, String[] args) {
        WizGUI.start(title, () -> {
            try {
                var frame = frameClass.getConstructor().newInstance();
                frame.setVisible(true);
            } catch (Exception e) {
                WizGUI.showError(e);
            }
        });
    }

}
