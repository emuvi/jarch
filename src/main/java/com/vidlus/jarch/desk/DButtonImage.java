package com.vidlus.jarch.desk;
import java.awt.Color;
import java.net.URL;

import java.awt.Image;

/**
 * A specialized {@link DButton} optimized exclusively for displaying an image.
 * By default, it aggressively strips away all native button styling (borders, padding, background) 
 * to ensure the image serves as the sole visual interactive element.
 */
public class DButtonImage extends DButton {

    /**
     * Constructs a new {@code DButtonImage} with no initial image.
     */
    public DButtonImage() {
        super();
        init();
    }

    /**
     * Constructs an image button populated with the given {@link DIcon}.
     * 
     * @param icon the {@link DIcon} to display
     */
    public DButtonImage(DIcon icon) {
        super(icon);
        init();
    }
    
    /**
     * Constructs an image button populated by extracting the underlying image from a {@link DImage}.
     * 
     * @param img the {@link DImage} component whose image will be used
     */
    public DButtonImage(DImage img) {
        super(new DIcon(img.getImage()));
        init();
    }

    /**
     * Initializes the component by removing default button decorations to maximize the image's footprint.
     */
    private void init() {
        borderEmpty(0);
        contentAreaFilled(false);
        borderPainted(false);
        focusPainted(false);
        cursorHand();
    }

    /**
     * Overrides the displayed image by extracting it from the provided {@link DImage}.
     * 
     * @param img the {@link DImage} providing the new image data
     * @return this {@code DButtonImage} instance to allow for method chaining
     */
    public DButtonImage image(DImage img) {
        setIcon(new DIcon(img.getImage()));
        return this;
    }

    /**
     * Scales the current image smoothly to fit precisely within the specified width and height.
     * This relies on the high-quality {@link Image#SCALE_SMOOTH} algorithm.
     * 
     * @param width the target width in pixels
     * @param height the target height in pixels
     * @return this {@code DButtonImage} instance to allow for method chaining
     */
    public DButtonImage scaleToFit(int width, int height) {
        if (getIcon() instanceof DIcon) {
            Image img = ((DIcon) getIcon()).getImage();
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            setIcon(new DIcon(scaledImg));
        }
        return this;
    }

    /**
     * Applies a built-in visual effect that subtly darkens the button when the mouse hovers over it.
     * This adds clear interactivity cues to what might otherwise look like a static image.
     * 
     * @return this {@code DButtonImage} instance to allow for method chaining
     */
    public DButtonImage autoHoverEffect() {
        // Implement simple background change on hover as a visual cue
        onMouseEnter(e -> setOpaque(true));
        onMouseExit(e -> setOpaque(false));
        background(new Color(0, 0, 0, 30));
        return this;
    }

    /**
     * Loads an image directly from the specified file path.
     * 
     * @param path the local file path to the image
     * @return this {@code DButtonImage} instance to allow for method chaining
     */
    public DButtonImage image(String path) {
        setIcon(new DIcon(path));
        return this;
    }

    /**
     * Loads an image directly from the specified URL.
     * 
     * @param url the URL to the image
     * @return this {@code DButtonImage} instance to allow for method chaining
     */
    public DButtonImage image(URL url) {
        setIcon(new DIcon(url));
        return this;
    }

    /**
     * Scales the current image by a percentage factor.
     * 
     * @param factor the scaling multiplier (e.g., 0.5 for half size, 2.0 for double size)
     * @return this {@code DButtonImage} instance to allow for method chaining
     */
    public DButtonImage scale(double factor) {
        if (getIcon() instanceof DIcon) {
            Image img = ((DIcon) getIcon()).getImage();
            int newW = (int) (img.getWidth(null) * factor);
            int newH = (int) (img.getHeight(null) * factor);
            if (newW > 0 && newH > 0) {
                Image scaledImg = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
                setIcon(new DIcon(scaledImg));
            }
        }
        return this;
    }
}
