package com.vidlus.jarch.desk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * A composite UI component for editing and selecting a color via a modal dialog.
 * <p>
 * This component visually consists of a {@link DColor} (a solid color box) alongside 
 * an action button. When clicked, the button summons a native {@link JColorChooser} dialog,
 * and upon confirmation, the selected color is piped back into the visual box.
 * </p>
 * <p>
 * <b>Useful implementation detail:</b> Since this class extends {@link DEdit}, it serves 
 * as a drop-in replacement for standard text fields in forms or data grids (like {@code DTable}),
 * bridging complex UI dialogs with standard value get/set interfaces.
 * </p>
 */
public class DChangeColor extends DEdit<Color> {

    /** The main container panel that acts as the root component for this editor. */
    private JPanel panel;
    
    /** The visual box used to display the currently selected color. */
    private DColor colorBox;
    
    /** The button the user interacts with to trigger the color chooser dialog. */
    private JButton actionButton;
    
    /** Internal flag tracking whether the component accepts user input. */
    private boolean isEditable = true;

    /** The title of the modal color chooser dialog. */
    private String dialogTitle = "Select a Color";

    /** The parent component for the dialog. Defaults to the internal panel. */
    private Component dialogParent;

    /**
     * Constructs a new {@code DChangeColor} composite component.
     * <p>
     * The internal layout uses a {@link BorderLayout} to position the color box in 
     * the center and the action button tightly on the right side.
     * </p>
     */
    public DChangeColor() {
        super(new JPanel(new BorderLayout()));
        this.panel = (JPanel) super.comp();
        this.dialogParent = this.panel;

        this.colorBox = new DColor();
        this.actionButton = new JButton("■");

        // Style the outer panel to mimic the look of a standard text field
        panel.setBackground(UIManager.getColor("TextField.background"));
        panel.setBorder(UIManager.getBorder("TextField.border"));

        // Tighten up the button margins for a cleaner inline appearance
        actionButton.setMargin(new Insets(0, 4, 0, 4));
        actionButton.setFocusable(false);
        actionButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panel.add(colorBox, BorderLayout.CENTER);
        panel.add(actionButton, BorderLayout.EAST);

        // Bind the button click to trigger the dialog pop-up
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onActionPressed();
            }
        });
    }

    /**
     * Retrieves the color currently displayed in the inner color box.
     * 
     * @return the selected {@link Color}, or {@code null} if none is set
     */
    @Override
    public Color getValue() {
        return colorBox.getValue();
    }

    /**
     * Updates the internal color state.
     * <p>
     * This applies the new value directly to the {@link DColor} box and updates 
     * the foreground color of the action button to match.
     * </p>
     * 
     * @param value the {@link Color} to set
     */
    @Override
    public void setValue(Color value) {
        colorBox.setValue(value);
        if (value != null) {
            actionButton.setForeground(value);
        } else {
            actionButton.setForeground(Color.BLACK);
        }
    }

    /**
     * Fluent setter to define whether this component accepts user interactions.
     * When disabled, the action button is grayed out and cannot be clicked.
     * 
     * @param editable {@code true} to enable interactions, {@code false} to disable
     * @return this {@code DChangeColor} instance to allow method chaining
     */
    public DChangeColor editable(boolean editable) {
        this.isEditable = editable;
        actionButton.setEnabled(editable);
        return this;
    }

    /**
     * Returns whether this component is currently editable.
     * 
     * @return {@code true} if editable, {@code false} otherwise
     */
    public boolean editable() {
        return isEditable;
    }

    /**
     * Exposes the inner action button component.
     * Useful for attaching custom external listeners or applying deep styling.
     * 
     * @return the {@link JButton} used to trigger the dialog
     */
    public JButton getButton() {
        return actionButton;
    }

    /**
     * Exposes the inner color box component.
     * 
     * @return the {@link DColor} instance displaying the current color
     */
    public DColor getColorBox() {
        return colorBox;
    }

    /**
     * Programmatically triggers the color selection dialog as if the action button was pressed.
     */
    public void showDialog() {
        onActionPressed();
    }

    /**
     * Internal handler invoked when the action button is pressed.
     * <p>
     * If the component is editable, this method halts execution, spawns a modal 
     * {@link JColorChooser} dialog, and awaits the user's selection. If a valid 
     * color is confirmed, the internal state is updated.
     * </p>
     */
    protected void onActionPressed() {
        if (!editable()) return;
        
        Color result = JColorChooser.showDialog(dialogParent, dialogTitle, getValue());
        if (result != null) {
            setValue(result);
        }
    }

    /**
     * Fluent setter for updating the component's color value.
     * 
     * @param color the {@link Color} to set
     * @return this {@code DChangeColor} instance to allow method chaining
     */
    public DChangeColor color(Color color) {
        setValue(color);
        return this;
    }

    /**
     * Fluent setter for updating the current color using specific RGB values.
     * 
     * @param r the red component (0-255)
     * @param g the green component (0-255)
     * @param b the blue component (0-255)
     * @return this {@code DChangeColor} instance to allow method chaining
     */
    public DChangeColor color(int r, int g, int b) {
        setValue(new Color(r, g, b));
        return this;
    }

    /**
     * Fluent setter for updating the current color using an integer RGB value.
     * 
     * @param c the new color represented as an integer
     * @return this {@code DChangeColor} instance to allow method chaining
     */
    public DChangeColor color(int c) {
        setValue(new Color(c));
        return this;
    }

    /**
     * Fluent setter for updating the current color using a hex string.
     * 
     * @param hex the hex string (e.g., "#FF0000" or "FF0000")
     * @return this {@code DChangeColor} instance to allow method chaining
     */
    public DChangeColor color(String hex) {
        if (hex != null && !hex.isEmpty()) {
            if (!hex.startsWith("#")) {
                hex = "#" + hex;
            }
            setValue(Color.decode(hex));
        }
        return this;
    }

    /**
     * Fluent setter for updating the current color using Hue, Saturation, Brightness.
     * 
     * @param h the hue component
     * @param s the saturation component
     * @param b the brightness component
     * @return this {@code DChangeColor} instance to allow method chaining
     */
    public DChangeColor colorHSB(float h, float s, float b) {
        setValue(Color.getHSBColor(h, s, b));
        return this;
    }

    /**
     * Fluent setter to customize the title of the popup color chooser dialog.
     * 
     * @param title the dialog title
     * @return this {@code DChangeColor} instance to allow method chaining
     */
    public DChangeColor dialogTitle(String title) {
        this.dialogTitle = title;
        return this;
    }

    /**
     * Fluent setter to explicitly specify the parent component for the popup dialog.
     * 
     * @param parent the parent {@link Component}
     * @return this {@code DChangeColor} instance to allow method chaining
     */
    public DChangeColor dialogParent(Component parent) {
        this.dialogParent = parent;
        return this;
    }

    /**
     * Fluent setter to change the text on the action button.
     * 
     * @param text the button text
     * @return this {@code DChangeColor} instance to allow method chaining
     */
    public DChangeColor buttonText(String text) {
        actionButton.setText(text);
        return this;
    }

    /**
     * Fluent setter to set an icon on the action button.
     * 
     * @param icon the {@link Icon} to display
     * @return this {@code DChangeColor} instance to allow method chaining
     */
    public DChangeColor buttonIcon(Icon icon) {
        actionButton.setIcon(icon);
        return this;
    }
}
