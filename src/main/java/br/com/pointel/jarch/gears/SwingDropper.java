package br.com.pointel.jarch.gears;

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JFrame;
import javax.swing.text.JTextComponent;
import br.com.pointel.jarch.mage.WizDesk;

/**
 *
 * @author emuvi
 */
public class SwingDropper {
    
    public static void initAllOn(JFrame frame) {
        new SwingDropper(WizDesk.getAllCompontentsOf(frame, JTextComponent.class)).init();
    }

    private final List<Component> components;
    private final Consumer<List<File>> fileConsumer;
    private final Consumer<String> stringConsumer;

    public SwingDropper(Component... components) {
        this.components = Arrays.asList(components);
        this.fileConsumer = null;
        this.stringConsumer = null;
    }
    
    public SwingDropper(List<Component> components) {
        this.components = components;
        this.fileConsumer = null;
        this.stringConsumer = null;
    }
    
    public SwingDropper(Consumer<List<File>> fileConsumer, Component... components) {
        this.components = Arrays.asList(components);
        this.fileConsumer = fileConsumer;
        this.stringConsumer = null;
    }
    
    public SwingDropper(Consumer<List<File>> fileConsumer, List<Component> components) {
        this.components = components;
        this.fileConsumer = fileConsumer;
        this.stringConsumer = null;
    }
    
    public SwingDropper(Consumer<String> stringConsumer, Consumer<List<File>> fileConsumer, Component... components) {
        this.components = Arrays.asList(components);
        this.fileConsumer = fileConsumer;
        this.stringConsumer = stringConsumer;
    }
    
    public SwingDropper(Consumer<String> stringConsumer, Consumer<List<File>> fileConsumer, List<Component> components) {
        this.components = components;
        this.fileConsumer = fileConsumer;
        this.stringConsumer = stringConsumer;
    }
    
    public void init() {
        for (var component : components) {
            new DropTarget(component, DnDConstants.ACTION_COPY, new DropTargetAdapter() {
                @Override
                public void drop(DropTargetDropEvent e) {
                    try {
                        e.acceptDrop(DnDConstants.ACTION_COPY);
                        DataFlavor[] flavors = e.getCurrentDataFlavors();
                        for (DataFlavor flavor : flavors) {
                            if (flavor.isFlavorJavaFileListType()) {
                                var gout = (List<File>) e.getTransferable().getTransferData(flavor);
                                if (fileConsumer == null) {
                                    dropped(component, gout);
                                } else {
                                    fileConsumer.accept(gout);
                                }
                                break;
                            } else if (flavor.isFlavorTextType()) {
                                var gout = (String) e.getTransferable().getTransferData(flavor);
                                if (stringConsumer != null) {
                                    dropped(component, gout);
                                } else {
                                    stringConsumer.accept(gout);
                                }
                                break;
                            }
                        }
                        e.dropComplete(true);
                    } catch (Exception ex) {
                        e.dropComplete(false);
                        WizDesk.showError(ex);
                    }
                }
            });
        }
    }

    private void dropped(Component component, List<File> files) {
        if (component instanceof JTextComponent textComp) {
            textComp.setText(String.join(";", files.stream().map(f -> f.getAbsolutePath()).toList()));
        }
    }

    private void dropped(Component component, String text) {
        if (component instanceof JTextComponent textComp) {
            textComp.setText(text);
        }
    }

}
