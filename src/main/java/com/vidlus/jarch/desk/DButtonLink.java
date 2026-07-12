package com.vidlus.jarch.desk;
import java.awt.Color;

import java.awt.Desktop;
import java.net.URI;
import java.awt.font.TextAttribute;
import java.util.Map;

/**
 * A specialized {@link DButton} that mimics the appearance and behavior of a web hyperlink.
 * It removes default button styling (borders, backgrounds) and allows automatic URL browsing,
 * hover underlining, and visited link color changes.
 */
public class DButtonLink extends DButton {
    
    /** The color of the link before it has been clicked. */
    private Color unvisitedColor = Color.BLUE;
    
    /** The color of the link after it has been clicked. */
    private Color visitedColor = new Color(85, 26, 139);
    
    /** Indicates whether the link has been clicked during this session. */
    private boolean visited = false;
    
    /** Controls whether the text becomes underlined when the mouse hovers over it. */
    private boolean underlineOnHover = true;

    /**
     * Constructs a new {@code DButtonLink} with no initial text.
     */
    public DButtonLink() {
        init();
    }

    /**
     * Constructs a new {@code DButtonLink} with the specified text.
     * 
     * @param text the text to display as a hyperlink
     */
    public DButtonLink(String text) {
        super(text);
        init();
    }

    /**
     * Initializes the default hyperlink appearance and interaction listeners.
     */
    private void init() {
        borderEmpty(0);
        contentAreaFilled(false);
        borderPainted(false);
        focusPainted(false);
        cursorHand();
        foreground(unvisitedColor);
        
        onMouseEnter(e -> {
            if (underlineOnHover) {
                setUnderline(true);
            }
        });
        
        onMouseExit(e -> {
            if (underlineOnHover) {
                setUnderline(false);
            }
        });
    }
    
    /**
     * Applies or removes the underline text attribute from the current font.
     * 
     * @param underline true to apply the underline, false to remove it
     */
    @SuppressWarnings("unchecked")
    private void setUnderline(boolean underline) {
        if (getFont() != null) {
            Map attributes = getFont().getAttributes();
            attributes.put(TextAttribute.UNDERLINE, underline ? TextAttribute.UNDERLINE_ON : -1);
            setFont(getFont().deriveFont(attributes));
        }
    }

    /**
     * Assigns a URL to this link button. When clicked, it will open the default system web browser
     * to the specified URL and update its text color to the visited state.
     * 
     * @param url the web address to navigate to
     * @return this {@code DButtonLink} instance to allow for method chaining
     */
    public DButtonLink url(String url) {
        onAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI(url));
                visited = true;
                foreground(visitedColor);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        return this;
    }

    /**
     * Enables or disables the hover underline effect.
     * 
     * @param b true to underline text when hovered, false otherwise
     * @return this {@code DButtonLink} instance to allow for method chaining
     */
    public DButtonLink underlineOnHover(boolean b) {
        this.underlineOnHover = b;
        return this;
    }

    /**
     * Sets the color of the link when it has not yet been clicked.
     * 
     * @param c the {@link Color} for unvisited links
     * @return this {@code DButtonLink} instance to allow for method chaining
     */
    public DButtonLink unvisitedColor(Color c) {
        this.unvisitedColor = c;
        if (!visited) {
            foreground(c);
        }
        return this;
    }

    /**
     * Sets the color of the link after it has been clicked.
     * 
     * @param c the {@link Color} for visited links
     * @return this {@code DButtonLink} instance to allow for method chaining
     */
    public DButtonLink visitedColor(Color c) {
        this.visitedColor = c;
        if (visited) {
            foreground(c);
        }
        return this;
    }

    /**
     * Injects a functional action that is executed immediately before the URL is opened.
     * 
     * @param action the {@link Runnable} to execute on click
     * @return this {@code DButtonLink} instance to allow for method chaining
     */
    public DButtonLink onLinkClick(Runnable action) {
        onAction(e -> action.run());
        return this;
    }

    /**
     * Programmatically forces the link into a visited or unvisited state, updating its color immediately.
     * 
     * @param visited {@code true} to mark the link as visited, {@code false} for unvisited
     * @return this {@code DButtonLink} instance to allow for method chaining
     */
    public DButtonLink visited(boolean visited) {
        this.visited = visited;
        foreground(visited ? visitedColor : unvisitedColor);
        return this;
    }
}
