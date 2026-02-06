package br.com.pointel.jarch.desk;

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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.text.JTextComponent;
import br.com.pointel.jarch.mage.WizGUI;

/**
 *
 * @author emuvi
 */
public class SwingDropper {
    
    public static void initAllOn(JFrame frame) {
        new SwingDropper(WizGUI.getAllComponentsOf(frame, JComponent.class)).init();
    }

    public static void initAllOn(Consumer<List<File>> fileConsumer, JFrame frame) {
        new SwingDropper(fileConsumer, WizGUI.getAllComponentsOf(frame, JComponent.class)).init();
    }

    public static void initAllOn(Consumer<String> stringConsumer, Consumer<List<File>> fileConsumer, JFrame frame) {
        new SwingDropper(stringConsumer, fileConsumer, WizGUI.getAllComponentsOf(frame, JComponent.class)).init();
    }

    private final List<Component> components;

    private Consumer<List<File>> fileConsumer = null;
    private Consumer<String> stringConsumer = null;

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

    public List<Component> getComponents() {
        return this.components;
    }

    public Consumer<List<File>> getFileConsumer() {
        return this.fileConsumer;
    }

    public void setFileConsumer(Consumer<List<File>> fileConsumer) {
        this.fileConsumer = fileConsumer;
    }

    public Consumer<String> getStringConsumer() {
        return this.stringConsumer;
    }

    public void setStringConsumer(Consumer<String> stringConsumer) {
        this.stringConsumer = stringConsumer;
    }

    public SwingDropper fileConsumer(Consumer<List<File>> fileConsumer) {
        setFileConsumer(fileConsumer);
        return this;
    }

    public SwingDropper stringConsumer(Consumer<String> stringConsumer) {
        setStringConsumer(stringConsumer);
        return this;
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
                                var dropped = (List<File>) e.getTransferable().getTransferData(flavor);
                                if (fileConsumer == null) {
                                    defaultDropped(component, dropped);
                                } else {
                                    fileConsumer.accept(dropped);
                                }
                                break;
                            } else if (flavor.isFlavorTextType()) {
                                var dropped = (String) e.getTransferable().getTransferData(flavor);
                                if (stringConsumer == null) {
                                    defaultDropped(component, dropped);
                                } else {
                                    stringConsumer.accept(dropped);
                                }
                                break;
                            }
                        }
                        e.dropComplete(true);
                    } catch (Exception ex) {
                        e.dropComplete(false);
                        WizGUI.showError(ex);
                    }
                }
            });
        }
    }

    private void defaultDropped(Component component, List<File> files) {
        if (component instanceof JTextComponent textComp) {
            textComp.setText(String.join(";", files.stream().map(f -> f.getAbsolutePath()).toList()));
        }
    }

    private void defaultDropped(Component component, String text) {
        if (component instanceof JTextComponent textComp) {
            textComp.setText(text);
        }
    }

}
