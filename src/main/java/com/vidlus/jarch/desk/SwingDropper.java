package com.vidlus.jarch.desk;

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

import com.vidlus.jarch.mage.WizGUI;

/**
 * A utility class designed to simplify the addition of drag-and-drop functionality
 * to Swing components. Supports dragging and dropping of files, text, and images.
 * Provides both static convenience methods for rapid initialization and a fluid 
 * builder pattern for more complex setups.
 *
 * @author emuvi
 */
public class SwingDropper {
    
    /**
     * Recursively initializes a default {@code SwingDropper} on all {@code JComponent}s 
     * contained within the given root component.
     * 
     * @param root the root component to scan for {@code JComponent}s.
     */
    public static void initAllOn(Component root) {
        new SwingDropper(WizGUI.getAllComponentsOf(root, JComponent.class)).init();
    }

    /**
     * Recursively initializes a {@code SwingDropper} with a file consumer on all 
     * {@code JComponent}s contained within the given root component.
     * 
     * @param fileConsumer the consumer to handle a list of dropped files.
     * @param root         the root component to scan for {@code JComponent}s.
     */
    public static void initAllOn(Consumer<List<File>> fileConsumer, Component root) {
        new SwingDropper(fileConsumer, WizGUI.getAllComponentsOf(root, JComponent.class)).init();
    }

    /**
     * Recursively initializes a {@code SwingDropper} with both a string and a file consumer
     * on all {@code JComponent}s contained within the given root component.
     * 
     * @param stringConsumer the consumer to handle dropped text strings.
     * @param fileConsumer   the consumer to handle a list of dropped files.
     * @param root           the root component to scan for {@code JComponent}s.
     */
    public static void initAllOn(Consumer<String> stringConsumer, Consumer<List<File>> fileConsumer, Component root) {
        new SwingDropper(stringConsumer, fileConsumer, WizGUI.getAllComponentsOf(root, JComponent.class)).init();
    }

    /**
     * Initializes a default {@code SwingDropper} directly on the specified array of components.
     * 
     * @param components the components to enable drag-and-drop on.
     */
    public static void initOn(Component... components) {
        new SwingDropper(components).init();
    }

    /**
     * Initializes a {@code SwingDropper} with a file consumer directly on the specified array of components.
     * 
     * @param fileConsumer the consumer to handle a list of dropped files.
     * @param components   the components to enable drag-and-drop on.
     */
    public static void initOn(Consumer<List<File>> fileConsumer, Component... components) {
        new SwingDropper(fileConsumer, components).init();
    }

    /**
     * Initializes a {@code SwingDropper} with a string and a file consumer directly on the specified array of components.
     * 
     * @param stringConsumer the consumer to handle dropped text strings.
     * @param fileConsumer   the consumer to handle a list of dropped files.
     * @param components     the components to enable drag-and-drop on.
     */
    public static void initOn(Consumer<String> stringConsumer, Consumer<List<File>> fileConsumer, Component... components) {
        new SwingDropper(stringConsumer, fileConsumer, components).init();
    }

    /**
     * Recursively finds all {@code JComponent}s within the given root component and removes 
     * any associated {@code DropTarget}.
     * 
     * @param root the root component to scan for {@code JComponent}s.
     */
    public static void removeAllFrom(Component root) {
        List<Component> components = WizGUI.getAllComponentsOf(root, JComponent.class);
        for (Component component : components) {
            component.setDropTarget(null);
        }
    }

    /**
     * Removes any associated {@code DropTarget} from the specified array of components.
     * 
     * @param components the components to clear drop targets from.
     */
    public static void removeAllFrom(Component... components) {
        for (Component component : components) {
            component.setDropTarget(null);
        }
    }

    private final List<Component> components;

    private Consumer<List<File>> fileConsumer = null;
    private Consumer<String> stringConsumer = null;
    private Consumer<java.awt.Image> imageConsumer = null;

    /**
     * Constructs a new {@code SwingDropper} for an array of components without default consumers.
     * 
     * @param components the components to associate with this dropper.
     */
    public SwingDropper(Component... components) {
        this.components = Arrays.asList(components);
        this.fileConsumer = null;
        this.stringConsumer = null;
        this.imageConsumer = null;
    }
    
    /**
     * Constructs a new {@code SwingDropper} for a list of components without default consumers.
     * 
     * @param components the list of components to associate with this dropper.
     */
    public SwingDropper(List<Component> components) {
        this.components = components;
        this.fileConsumer = null;
        this.stringConsumer = null;
        this.imageConsumer = null;
    }
    
    /**
     * Constructs a new {@code SwingDropper} for an array of components with a file consumer.
     * 
     * @param fileConsumer the consumer to handle dropped files.
     * @param components   the components to associate with this dropper.
     */
    public SwingDropper(Consumer<List<File>> fileConsumer, Component... components) {
        this.components = Arrays.asList(components);
        this.fileConsumer = fileConsumer;
        this.stringConsumer = null;
        this.imageConsumer = null;
    }
    
    /**
     * Constructs a new {@code SwingDropper} for a list of components with a file consumer.
     * 
     * @param fileConsumer the consumer to handle dropped files.
     * @param components   the list of components to associate with this dropper.
     */
    public SwingDropper(Consumer<List<File>> fileConsumer, List<Component> components) {
        this.components = components;
        this.fileConsumer = fileConsumer;
        this.stringConsumer = null;
        this.imageConsumer = null;
    }
    
    /**
     * Constructs a new {@code SwingDropper} for an array of components with both a string and a file consumer.
     * 
     * @param stringConsumer the consumer to handle dropped text strings.
     * @param fileConsumer   the consumer to handle dropped files.
     * @param components     the components to associate with this dropper.
     */
    public SwingDropper(Consumer<String> stringConsumer, Consumer<List<File>> fileConsumer, Component... components) {
        this.components = Arrays.asList(components);
        this.fileConsumer = fileConsumer;
        this.stringConsumer = stringConsumer;
        this.imageConsumer = null;
    }
    
    /**
     * Constructs a new {@code SwingDropper} for a list of components with both a string and a file consumer.
     * 
     * @param stringConsumer the consumer to handle dropped text strings.
     * @param fileConsumer   the consumer to handle dropped files.
     * @param components     the list of components to associate with this dropper.
     */
    public SwingDropper(Consumer<String> stringConsumer, Consumer<List<File>> fileConsumer, List<Component> components) {
        this.components = components;
        this.fileConsumer = fileConsumer;
        this.stringConsumer = stringConsumer;
        this.imageConsumer = null;
    }

    /**
     * Retrieves the list of components managed by this dropper.
     * 
     * @return the managed components.
     */
    public List<Component> getComponents() {
        return this.components;
    }

    /**
     * Retrieves the current file consumer.
     * 
     * @return the consumer handling dropped files, or {@code null} if none is set.
     */
    public Consumer<List<File>> getFileConsumer() {
        return this.fileConsumer;
    }

    /**
     * Sets the consumer to handle dropped files.
     * 
     * @param fileConsumer the file consumer to set.
     */
    public void setFileConsumer(Consumer<List<File>> fileConsumer) {
        this.fileConsumer = fileConsumer;
    }

    /**
     * Retrieves the current string consumer.
     * 
     * @return the consumer handling dropped strings, or {@code null} if none is set.
     */
    public Consumer<String> getStringConsumer() {
        return this.stringConsumer;
    }

    /**
     * Sets the consumer to handle dropped text strings.
     * 
     * @param stringConsumer the string consumer to set.
     */
    public void setStringConsumer(Consumer<String> stringConsumer) {
        this.stringConsumer = stringConsumer;
    }

    /**
     * Retrieves the current image consumer.
     * 
     * @return the consumer handling dropped images, or {@code null} if none is set.
     */
    public Consumer<java.awt.Image> getImageConsumer() {
        return this.imageConsumer;
    }

    /**
     * Sets the consumer to handle dropped images.
     * 
     * @param imageConsumer the image consumer to set.
     */
    public void setImageConsumer(Consumer<java.awt.Image> imageConsumer) {
        this.imageConsumer = imageConsumer;
    }

    /**
     * Fluid builder method to set the file consumer.
     * 
     * @param fileConsumer the file consumer to handle dropped files.
     * @return this {@code SwingDropper} instance for chaining.
     */
    public SwingDropper fileConsumer(Consumer<List<File>> fileConsumer) {
        setFileConsumer(fileConsumer);
        return this;
    }

    /**
     * Fluid builder method to set the string consumer.
     * 
     * @param stringConsumer the string consumer to handle dropped strings.
     * @return this {@code SwingDropper} instance for chaining.
     */
    public SwingDropper stringConsumer(Consumer<String> stringConsumer) {
        setStringConsumer(stringConsumer);
        return this;
    }

    /**
     * Fluid builder method to set the image consumer.
     * 
     * @param imageConsumer the image consumer to handle dropped images.
     * @return this {@code SwingDropper} instance for chaining.
     */
    public SwingDropper imageConsumer(Consumer<java.awt.Image> imageConsumer) {
        setImageConsumer(imageConsumer);
        return this;
    }
    
    /**
     * Initializes the drop target listeners on all components managed by this instance.
     * This method actually activates the drag-and-drop capability.
     */
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
                            } else if (flavor.equals(DataFlavor.imageFlavor)) {
                                var dropped = (java.awt.Image) e.getTransferable().getTransferData(flavor);
                                if (imageConsumer != null) {
                                    imageConsumer.accept(dropped);
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

    /**
     * Removes the drop targets from all components managed by this instance.
     * Useful for cleanup or dynamically disabling drag-and-drop behavior.
     */
    public void dispose() {
        for (var component : components) {
            component.setDropTarget(null);
        }
    }


    /**
     * Default behavior when files are dropped on a component, and no file consumer is set.
     * If the component is a {@code JTextComponent}, it sets the text to a semicolon-separated
     * list of the absolute paths of the files.
     * 
     * @param component the component the files were dropped onto.
     * @param files     the list of files dropped.
     */
    private void defaultDropped(Component component, List<File> files) {
        if (component instanceof JTextComponent textComp) {
            textComp.setText(String.join(";", files.stream().map(f -> f.getAbsolutePath()).toList()));
        }
    }

    /**
     * Default behavior when text is dropped on a component, and no string consumer is set.
     * If the component is a {@code JTextComponent}, it sets the text to the dropped string.
     * 
     * @param component the component the text was dropped onto.
     * @param text      the text dropped.
     */
    private void defaultDropped(Component component, String text) {
        if (component instanceof JTextComponent textComp) {
            textComp.setText(text);
        }
    }

}
