package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.WindowConstants;

import com.vidlus.jarch.mage.WizLang;
import com.vidlus.jarch.mage.WizGUI;
import com.vidlus.jarch.mage.WizString;

/**
 * A fluent API wrapper for {@link JFrame}, designed to easily create, configure,
 * and manage main application windows with chainable method calls.
 */
public class DFrame extends JFrame {

    /**
     * Constructs a default {@code DFrame} using the application's localized title.
     */
    public DFrame() {
        this(WizLang.getTitle());
    }

    /**
     * Constructs a {@code DFrame} with the specified title.
     * 
     * @param title the string to display in the frame's title bar
     */
    public DFrame(String title) {
        this(title, null);
    }

    /**
     * Constructs a {@code DFrame} with the specified title and content pane.
     * Initializes the frame with the application logo, sets the name, configures
     * the default close operation, centers it on screen, and optionally packs the content.
     * 
     * @param title the string to display in the frame's title bar
     * @param contentPane the {@link Container} to set as the frame's content pane
     */
    public DFrame(String title, Container contentPane) {
        super(title);
        setName(WizString.getParameterName(title));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(WizGUI.getLogo());
        setLocationRelativeTo(null);
        if (contentPane != null) {
            setContentPane(contentPane);
            pack();
        }
        WizGUI.initFrame(this);
    }

    /**
     * Sets the primary content pane body of the frame and subsequently packs the window.
     * 
     * @param body the {@link Container} to set as the frame's content pane
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame body(Container body) {
        setContentPane(body);
        pack();
        return this;
    }

    /**
     * Retrieves the current title of the frame.
     * 
     * @return the frame's title string
     */
    public String title() {
        return super.getTitle();
    }

    /**
     * Sets the title of the frame.
     * 
     * @param title the new title string
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame title(String title) {
        super.setTitle(title);
        return this;
    }

    /**
     * Retrieves the current dimension (size) of the frame.
     * 
     * @return the {@link Dimension} of the frame
     */
    public Dimension dimension() {
        return super.getSize();
    }

    /**
     * Sets the dimension (size) of the frame.
     * 
     * @param width the width in pixels
     * @param height the height in pixels
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame dimension(int width, int height) {
        super.setSize(width, height);
        return this;
    }

    /**
     * Checks whether the frame is currently visible.
     * 
     * @return {@code true} if visible, {@code false} otherwise
     */
    public boolean visible() {
        return super.isVisible();
    }

    /**
     * Shows or hides the frame.
     * 
     * @param visible {@code true} to show the frame, {@code false} to hide it
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame visible(boolean visible) {
        super.setVisible(visible);
        return this;
    }

    /**
     * Checks whether the frame can be resized by the user.
     * 
     * @return {@code true} if resizable, {@code false} otherwise
     */
    public boolean resizable() {
        return super.isResizable();
    }

    /**
     * Specifies whether this frame should be resizable by the user.
     * 
     * @param resizable {@code true} to allow resizing, {@code false} to prevent it
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame resizable(boolean resizable) {
        super.setResizable(resizable);
        return this;
    }

    /**
     * Checks whether the frame is undecorated (lacks a window border and title bar).
     * 
     * @return {@code true} if undecorated, {@code false} otherwise
     */
    public boolean undecorated() {
        return super.isUndecorated();
    }

    /**
     * Specifies whether this frame should be undecorated.
     * This must be called before the frame is made visible.
     * 
     * @param undecorated {@code true} to remove decorations, {@code false} to retain them
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame undecorated(boolean undecorated) {
        super.setUndecorated(undecorated);
        return this;
    }

    /**
     * Checks whether the frame is set to always be on top of other windows.
     * 
     * @return {@code true} if always on top, {@code false} otherwise
     */
    public boolean alwaysOnTop() {
        return super.isAlwaysOnTop();
    }

    /**
     * Specifies whether this frame should remain always on top of other windows.
     * 
     * @param alwaysOnTop {@code true} to keep the frame on top, {@code false} otherwise
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame alwaysOnTop(boolean alwaysOnTop) {
        super.setAlwaysOnTop(alwaysOnTop);
        return this;
    }

    /**
     * Retrieves the current location/position of the frame on the screen.
     * 
     * @return the {@link Point} representing the position
     */
    public Point position() {
        return super.getLocation();
    }

    /**
     * Moves the frame to the specified coordinates.
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame position(int x, int y) {
        super.setLocation(x, y);
        return this;
    }

    /**
     * Moves the frame to the specified point.
     * 
     * @param p the {@link Point} specifying the new location
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame position(Point p) {
        super.setLocation(p);
        return this;
    }

    /**
     * Retrieves the bounding rectangle of the frame.
     * 
     * @return the {@link Rectangle} bounds
     */
    public Rectangle place() {
        return super.getBounds();
    }

    /**
     * Moves and resizes the frame to fit within the specified bounding box.
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param width the target width
     * @param height the target height
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame place(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        return this;
    }

    /**
     * Moves and resizes the frame to match the specified rectangle bounds.
     * 
     * @param r the {@link Rectangle} specifying the bounds
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame place(Rectangle r) {
        super.setBounds(r);
        return this;
    }

    /**
     * Retrieves the extended state of the frame (e.g., NORMAL, ICONIFIED, MAXIMIZED_BOTH).
     * 
     * @return the extended state integer
     */
    public int state() {
        return super.getExtendedState();
    }

    /**
     * Sets the extended state of the frame.
     * 
     * @param state the extended state integer (e.g., {@link JFrame#NORMAL})
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame state(int state) {
        super.setExtendedState(state);
        return this;
    }

    /**
     * Sets the frame state to normal (restored).
     * 
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame stateNormal() {
        return state(JFrame.NORMAL);
    }

    /**
     * Sets the frame state to iconified (minimized).
     * 
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame stateIconified() {
        return state(JFrame.ICONIFIED);
    }

    /**
     * Sets the frame state to maximized horizontally.
     * 
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame stateMaximizedHorizontal() {
        return state(JFrame.MAXIMIZED_HORIZ);
    }

    /**
     * Sets the frame state to maximized vertically.
     * 
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame stateMaximizedVertical() {
        return state(JFrame.MAXIMIZED_VERT);
    }

    /**
     * Sets the frame state to maximized both horizontally and vertically.
     * 
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame stateMaximizedBoth() {
        return state(JFrame.MAXIMIZED_BOTH);
    }

    /**
     * Retrieves the default close operation of the frame.
     * 
     * @return the close operation constant
     */
    public int defaultCloseOperation() {
        return super.getDefaultCloseOperation();
    }

    /**
     * Sets the operation that will happen by default when the user initiates a "close" on this frame.
     * 
     * @param operation the close operation constant (e.g., {@link WindowConstants#DISPOSE_ON_CLOSE})
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame defaultCloseOperation(int operation) {
        super.setDefaultCloseOperation(operation);
        return this;
    }

    /**
     * Configures the frame to do nothing when closed.
     * 
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame doNothingOnClose() {
        super.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        return this;
    }

    /**
     * Configures the frame to hide itself when closed.
     * 
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame hideOnClose() {
        super.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        return this;
    }

    /**
     * Configures the frame to dispose of itself when closed.
     * 
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame disposeOnClose() {
        super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        return this;
    }

    /**
     * Configures the application to exit when this frame is closed.
     * 
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame exitOnClose() {
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return this;
    }

    /**
     * Retrieves the icon image of the frame.
     * 
     * @return the {@link Image} used as the frame icon
     */
    public Image iconImage() {
        return super.getIconImage();
    }

    /**
     * Sets the icon image of the frame.
     * 
     * @param image the {@link Image} to display as the window icon
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame iconImage(Image image) {
        super.setIconImage(image);
        return this;
    }

    /**
     * Binds a listener to execute an action when the window is opened.
     * 
     * @param consumer a consumer accepting the {@link WindowEvent}
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame onOpened(Consumer<WindowEvent> consumer) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    /**
     * Binds a listener to execute an action only the first time the window is opened.
     * 
     * @param consumer a consumer accepting the {@link WindowEvent}
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame onFirstOpened(Consumer<WindowEvent> consumer) {
        addWindowListener(new WindowAdapter() {
            boolean firstOpened = true;

            @Override
            public void windowOpened(WindowEvent e) {
                if (firstOpened) {
                    firstOpened = false;
                } else {
                    return;
                }
                consumer.accept(e);
            }
        });
        return this;
    }

    /**
     * Binds a listener to execute an action when the window is in the process of closing.
     * 
     * @param consumer a consumer accepting the {@link WindowEvent}
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame onClosing(Consumer<WindowEvent> consumer) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    /**
     * Binds a listener to execute an action after the window has been closed.
     * 
     * @param consumer a consumer accepting the {@link WindowEvent}
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame onClosed(Consumer<WindowEvent> consumer) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    /**
     * Binds a listener to execute an action when the window is iconified (minimized).
     * 
     * @param consumer a consumer accepting the {@link WindowEvent}
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame onIconified(Consumer<WindowEvent> consumer) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    /**
     * Binds a listener to execute an action when the window is deiconified (restored).
     * 
     * @param consumer a consumer accepting the {@link WindowEvent}
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame onDeiconified(Consumer<WindowEvent> consumer) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeiconified(WindowEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    /**
     * Binds a listener to execute an action when the window is activated (brought to front).
     * 
     * @param consumer a consumer accepting the {@link WindowEvent}
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame onActivated(Consumer<WindowEvent> consumer) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    /**
     * Binds a listener to execute an action only the first time the window is activated.
     * 
     * @param consumer a consumer accepting the {@link WindowEvent}
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame onFirstActivated(Consumer<WindowEvent> consumer) {
        addWindowListener(new WindowAdapter() {
            boolean firstActivated = true;

            @Override
            public void windowActivated(WindowEvent e) {
                if (firstActivated) {
                    firstActivated = false;
                } else {
                    return;
                }
                consumer.accept(e);
            }
        });
        return this;
    }

    /**
     * Binds a listener to execute an action when the window is deactivated.
     * 
     * @param consumer a consumer accepting the {@link WindowEvent}
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame onDeactivated(Consumer<WindowEvent> consumer) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    /**
     * Binds a listener to execute an action when the window's state changes.
     * 
     * @param consumer a consumer accepting the {@link WindowEvent}
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame onStateChanged(Consumer<WindowEvent> consumer) {
        addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    /**
     * Binds a listener to execute an action when the window gains focus.
     * 
     * @param consumer a consumer accepting the {@link WindowEvent}
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame onGainedFocus(Consumer<WindowEvent> consumer) {
        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    /**
     * Binds a listener to execute an action when the window loses focus.
     * 
     * @param consumer a consumer accepting the {@link WindowEvent}
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame onLostFocus(Consumer<WindowEvent> consumer) {
        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    /**
     * Makes the frame visible.
     * 
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame view() {
        setVisible(true);
        return this;
    }

    /**
     * Resizes the frame to the specified width and height.
     * 
     * @param width the target width
     * @param height the target height
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame size(int width, int height) {
        super.setSize(width, height);
        return this;
    }

    /**
     * Resizes the frame to the specified dimension.
     * 
     * @param d the target {@link Dimension}
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame size(Dimension d) {
        super.setSize(d);
        return this;
    }

    /**
     * Sets the minimum size of the frame.
     * 
     * @param minimumSize the minimum allowed {@link Dimension}
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame minimumSize(Dimension minimumSize) {
        super.setMinimumSize(minimumSize);
        return this;
    }

    /**
     * Sets the maximum size of the frame.
     * 
     * @param maximumSize the maximum allowed {@link Dimension}
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame maximumSize(Dimension maximumSize) {
        super.setMaximumSize(maximumSize);
        return this;
    }

    /**
     * Sets the preferred size of the frame.
     * 
     * @param preferredSize the preferred {@link Dimension}
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame preferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
        return this;
    }

    /**
     * Sets the background color of the frame.
     * 
     * @param bg the target {@link Color}
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame background(Color bg) {
        super.setBackground(bg);
        return this;
    }

    /**
     * Sets the opacity of the frame. The window must be undecorated for this to take effect.
     * 
     * @param opacity the opacity float value (between 0.0 and 1.0)
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame opacity(float opacity) {
        super.setOpacity(opacity);
        return this;
    }

    /**
     * Sets the shape of the frame window. The window must be undecorated for this to take effect.
     * 
     * @param shape the custom {@link Shape}
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame shape(Shape shape) {
        super.setShape(shape);
        return this;
    }

    /**
     * Sets the sequence of icon images for the frame to allow the OS to pick the best fit.
     * 
     * @param icons a {@link List} of {@link Image} icons
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame iconImages(List<? extends Image> icons) {
        super.setIconImages(icons);
        return this;
    }

    /**
     * Sets the internal name of the frame component.
     * 
     * @param name the internal string name
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame name(String name) {
        super.setName(name);
        return this;
    }

    /**
     * Instantly makes the frame fully transparent by stripping decorations and setting an alpha-zero background.
     * Note: This must be called before the frame is made visible.
     * 
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame transparent() {
        super.setUndecorated(true);
        super.setBackground(new Color(0, 0, 0, 0));
        return this;
    }

    /**
     * Binds a window listener to the frame to listen for lifecycle events.
     * 
     * @param listener the {@link WindowListener} to attach
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame windowListener(WindowListener listener) {
        super.addWindowListener(listener);
        return this;
    }

    /**
     * Causes the frame to be sized to fit the preferred size and layouts of its subcomponents.
     * 
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame packFrame() {
        super.pack();
        return this;
    }

    /**
     * Moves the frame to the specified coordinates.
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame location(int x, int y) {
        super.setLocation(x, y);
        return this;
    }

    /**
     * Moves the frame to the specified point.
     * 
     * @param p the {@link Point} specifying the new location
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame location(Point p) {
        super.setLocation(p);
        return this;
    }

    /**
     * Positions the frame relative to a specified component.
     * Passing {@code null} will center the frame on the screen.
     * 
     * @param c the {@link Component} in relation to which the frame's location is determined
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame locationRelativeTo(Component c) {
        super.setLocationRelativeTo(c);
        return this;
    }

    /**
     * Centers the frame on the screen. This is a convenience method for {@code locationRelativeTo(null)}.
     * 
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame center() {
        super.setLocationRelativeTo(null);
        return this;
    }

    /**
     * Moves and resizes the frame to fit within the specified bounding box.
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param width the target width
     * @param height the target height
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame bounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        return this;
    }

    /**
     * Moves and resizes the frame to match the specified rectangle bounds.
     * 
     * @param r the {@link Rectangle} specifying the bounds
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame bounds(Rectangle r) {
        super.setBounds(r);
        return this;
    }

    /**
     * Sets the menu bar for the frame.
     * 
     * @param menuBar the {@link JMenuBar} to set
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame menuBar(JMenuBar menuBar) {
        super.setJMenuBar(menuBar);
        return this;
    }

    /**
     * Sets the layout manager for the frame.
     * 
     * @param manager the {@link LayoutManager} to set
     * @return this {@code DFrame} instance for method chaining
     */
    public DFrame layout(LayoutManager manager) {
        super.setLayout(manager);
        return this;
    }

}
