package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * A custom clock component for selecting the time of a java.util.Date.
 * Extends DEdit to integrate with the jarch framework and provides a fluent API.
 * The UI is constructed with a concentric circular layout containing toggle buttons
 * for hours (inner circle) and minutes (outer circle), along with a central AM/PM toggle.
 */
public class DEditTime extends DEdit<Date> {

    /** The main clock panel drawing the background and handling circular layout. */
    private final ClockPanel rootPanel;
    
    /** Internal calendar instance used for calculating and storing time values. */
    private final Calendar calendar;
    
    /** The currently selected date (acting purely as a time container) in the component. */
    private Date selectedDate;
    
    /** Array containing the 12 toggle buttons representing hours 1 through 12. */
    private final JToggleButton[] hourButtons;
    
    /** Array containing the 12 toggle buttons representing minutes in 5-minute increments (0-55). */
    private final JToggleButton[] minuteButtons;
    
    /** The central button toggling between AM and PM states. */
    private final JToggleButton amPmButton;
    
    /** ButtonGroup ensuring only one hour can be selected at a time in the inner circle. */
    private final ButtonGroup hourGroup;
    
    /** ButtonGroup ensuring only one minute slot can be selected at a time in the outer circle. */
    private final ButtonGroup minuteGroup;
    
    /** Explicit color for the clock's circular face; if null, defaults to panel's darker background. */
    private Color clockColor = null;

    /**
     * Constructs a new DEditTime initialized to the current system time.
     * Sets up the circular UI layout with concentric circles for hours and minutes.
     */
    public DEditTime() {
        super();
        this.rootPanel = new ClockPanel();
        super.comp(this.rootPanel);
        
        // Initialize internal calendar and pull the initial current time
        this.calendar = Calendar.getInstance();
        this.selectedDate = calendar.getTime();
        
        hourGroup = new ButtonGroup();
        minuteGroup = new ButtonGroup();
        
        // --- Hours (Inner Circle) ---
        // Populates 12 buttons handling the 12-hour clock (1-12)
        hourButtons = new JToggleButton[12];
        for (int i = 0; i < 12; i++) {
            // Index 0 represents 12 o'clock, 1 represents 1 o'clock, etc.
            int hour = (i == 0) ? 12 : i;
            JToggleButton btn = createCircleButton(String.valueOf(hour));
            hourButtons[i] = btn;
            hourGroup.add(btn);
            rootPanel.add(btn);
            
            // Recompute full date state when an hour is clicked
            btn.addActionListener(e -> updateTimeFromSelection());
        }
        
        // --- Minutes (Outer Circle) ---
        // Populates 12 buttons handling minutes at 5-minute intervals (00, 05, 10, ... 55)
        minuteButtons = new JToggleButton[12];
        for (int i = 0; i < 12; i++) {
            int minute = i * 5;
            JToggleButton btn = createCircleButton(String.format("%02d", minute));
            minuteButtons[i] = btn;
            minuteGroup.add(btn);
            rootPanel.add(btn);
            
            // Recompute full date state when a minute is clicked
            btn.addActionListener(e -> updateTimeFromSelection());
        }
        
        // --- AM / PM Toggle ---
        // Instantiates the central toggle for AM/PM phase changes
        amPmButton = createCircleButton("AM");
        amPmButton.addActionListener(e -> {
            if (amPmButton.isSelected()) {
                amPmButton.setText("PM");
            } else {
                amPmButton.setText("AM");
            }
            updateTimeFromSelection();
        });
        rootPanel.add(amPmButton);
        
        // Sync the initial UI layout toggles to the current default selectedDate
        syncUIWithDate();
    }
    
    /**
     * Helper method to generate uniformly styled toggle buttons for the clock.
     * @param text the text to display on the button
     * @return a properly styled JToggleButton
     */
    private JToggleButton createCircleButton(String text) {
        JToggleButton btn = new JToggleButton(text);
        btn.setMargin(new Insets(2, 2, 2, 2));
        btn.setFocusPainted(false);
        return btn;
    }

    /**
     * Reads the current toggled state across the clock face and applies it to the internal selectedDate.
     * Determines which hour and minute buttons are active, checks the AM/PM state, and updates the Calendar.
     */
    private void updateTimeFromSelection() {
        int h = 0;
        for (int i = 0; i < 12; i++) {
            if (hourButtons[i].isSelected()) {
                h = i;
                break;
            }
        }
        
        int m = 0;
        for (int i = 0; i < 12; i++) {
            if (minuteButtons[i].isSelected()) {
                m = i * 5;
                break;
            }
        }
        
        boolean isPm = amPmButton.isSelected();
        
        calendar.set(Calendar.HOUR, h);
        calendar.set(Calendar.MINUTE, m);
        calendar.set(Calendar.AM_PM, isPm ? Calendar.PM : Calendar.AM);
        
        selectedDate = calendar.getTime();
    }
    
    /**
     * Synchronizes the UI toggle buttons with the current underlying selectedDate object.
     * Calculates the nearest 5-minute interval for minutes and updates button states accordingly.
     */
    private void syncUIWithDate() {
        if (selectedDate == null) {
            // Wipe UI state entirely if date is cleared
            hourGroup.clearSelection();
            minuteGroup.clearSelection();
            amPmButton.setSelected(false);
            amPmButton.setText("AM");
            return;
        }
        
        calendar.setTime(selectedDate);
        int hour = calendar.get(Calendar.HOUR); // 0-11 based on 12-hour AM/PM clock
        int minute = calendar.get(Calendar.MINUTE);
        int amPm = calendar.get(Calendar.AM_PM);
        
        // Select matching hour button (where index 0 is 12)
        hourButtons[hour].setSelected(true);
        
        // Snap raw minute reading to the nearest 5-minute index
        int minIndex = Math.round(minute / 5.0f);
        if (minIndex == 12) minIndex = 0;
        minuteButtons[minIndex].setSelected(true);
        
        // Toggle the central AM/PM state
        if (amPm == Calendar.PM) {
            amPmButton.setSelected(true);
            amPmButton.setText("PM");
        } else {
            amPmButton.setSelected(false);
            amPmButton.setText("AM");
        }
    }

    /**
     * Retrieves the currently selected date containing the chosen time.
     * @return the selected date, or null if cleared
     */
    @Override
    public Date getValue() {
        return selectedDate;
    }

    /**
     * Sets the currently selected date and strictly updates the UI components.
     * @param value the date to select, or null to clear selection
     */
    @Override
    public void setValue(Date value) {
        this.selectedDate = value;
        syncUIWithDate();
    }
    
    /**
     * Custom inner panel that strictly manages laying out components dynamically
     * in concentric circles via trigonometric calculations and draws the clock background.
     */
    private class ClockPanel extends JPanel {
        
        /**
         * Constructs the custom ClockPanel using an absolute layout (null layout)
         * to allow free coordinate manipulation.
         */
        public ClockPanel() {
            super(null); 
        }
        
        /**
         * Returns the preferred baseline dimensions required to make the clock legible.
         * @return Dimension of 250x250 pixels.
         */
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(250, 250);
        }

        /**
         * Renders the visible background clock-face circle.
         * @param g the Graphics context
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            // Engage anti-aliasing for smooth circular strokes
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Calculate a perfect bounding box for the circle within the panel limits
            int size = Math.min(getWidth(), getHeight());
            int padding = 10;
            int diameter = size - padding * 2;
            
            int x = (getWidth() - diameter) / 2;
            int y = (getHeight() - diameter) / 2;
            
            // Draw a background circle behind the buttons using custom color or darker default
            g2.setColor(clockColor != null ? clockColor : getBackground().darker());
            g2.fillOval(x, y, diameter, diameter);
            
            g2.dispose();
        }

        /**
         * Recalculates absolute XY coordinates for every clock button based on trigonometric logic,
         * scaling them seamlessly to fill whatever dimension the panel possesses.
         */
        @Override
        public void doLayout() {
            super.doLayout();
            
            int width = getWidth();
            int height = getHeight();
            int centerX = width / 2;
            int centerY = height / 2;
            
            int minDim = Math.min(width, height);
            
            // Define scalable radii variables to split inner and outer boundaries
            double outerRadius = (minDim / 2.0) * 0.75;
            double innerRadius = (minDim / 2.0) * 0.45;
            
            // Standardize dynamic button boundaries explicitly to window scale
            int btnSize = Math.max(25, (int)(minDim * 0.12));
            
            // Layout Outer Minutes (0 angle corresponds to the right side horizontally. Subtracting 90 degrees puts 0 index at top center).
            for (int i = 0; i < 12; i++) {
                double angle = Math.toRadians((i * 30) - 90);
                int btnX = centerX + (int)(outerRadius * Math.cos(angle)) - (btnSize / 2);
                int btnY = centerY + (int)(outerRadius * Math.sin(angle)) - (btnSize / 2);
                
                minuteButtons[i].setBounds(btnX, btnY, btnSize, btnSize);
            }
            
            // Layout Inner Hours
            for (int i = 0; i < 12; i++) {
                double angle = Math.toRadians((i * 30) - 90);
                int btnX = centerX + (int)(innerRadius * Math.cos(angle)) - (btnSize / 2);
                int btnY = centerY + (int)(innerRadius * Math.sin(angle)) - (btnSize / 2);
                
                hourButtons[i].setBounds(btnX, btnY, btnSize, btnSize);
            }
            
            // Center the AM/PM button, ensuring it is prominently sized
            int amPmSize = Math.max(35, (int)(minDim * 0.18));
            amPmButton.setBounds(centerX - (amPmSize / 2), centerY - (amPmSize / 2), amPmSize, amPmSize);
        }
    }

    // --- DEdit Overrides ---

    /**
     * Sets the underlying JComponent.
     * @param comp the component
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    @Override
    public DEditTime comp(JComponent comp) {
        super.comp(comp);
        return this;
    }

    /**
     * Sets the value of this field explicitly.
     * @param value the value to set
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    @Override
    public DEditTime value(Date value) {
        super.value(value);
        return this;
    }

    /**
     * Clears the selected date effectively wiping the UI tracking.
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    @Override
    public DEditTime clear() {
        super.clear();
        return this;
    }

    /**
     * Sets whether the clock component is globally enabled.
     * Overridden to propagate disabling flags to all internal buttons.
     * @param enabled true if enabled, false otherwise
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    @Override
    public DEditTime enabled(boolean enabled) {
        super.enabled(enabled);
        for (JToggleButton btn : hourButtons) {
            btn.setEnabled(enabled);
        }
        for (JToggleButton btn : minuteButtons) {
            btn.setEnabled(enabled);
        }
        amPmButton.setEnabled(enabled);
        return this;
    }

    /**
     * Sets whether the component is focusable.
     * @param focusable true if focusable, false otherwise
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    @Override
    public DEditTime focusable(boolean focusable) {
        super.focusable(focusable);
        return this;
    }

    /**
     * Requests focus for this component.
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    @Override
    public DEditTime requestFocus() {
        super.requestFocus();
        return this;
    }

    /**
     * Requests focus in window for this component.
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    @Override
    public DEditTime requestFocusInWindow() {
        super.requestFocusInWindow();
        return this;
    }

    /**
     * Sets the component name.
     * @param name the component name
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    @Override
    public DEditTime name(String name) {
        super.name(name);
        return this;
    }

    /**
     * Sets the tooltip hint for this component.
     * @param hint the tooltip hint
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    @Override
    public DEditTime hint(String hint) {
        super.hint(hint);
        return this;
    }

    /**
     * Adds an action listener to the root panel.
     * @param consumer the action consumer
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    @Override
    public DEditTime onAction(Consumer<ActionEvent> consumer) {
        super.onAction(consumer);
        return this;
    }

    /**
     * Adds a mouse clicked listener.
     * @param consumer the mouse event consumer
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    @Override
    public DEditTime onMouseClicked(Consumer<MouseEvent> consumer) {
        super.onMouseClicked(consumer);
        return this;
    }

    /**
     * Adds a mouse pressed listener.
     * @param consumer the mouse event consumer
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    @Override
    public DEditTime onMousePressed(Consumer<MouseEvent> consumer) {
        super.onMousePressed(consumer);
        return this;
    }

    /**
     * Adds a mouse released listener.
     * @param consumer the mouse event consumer
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    @Override
    public DEditTime onMouseReleased(Consumer<MouseEvent> consumer) {
        super.onMouseReleased(consumer);
        return this;
    }

    /**
     * Adds a mouse entered listener.
     * @param consumer the mouse event consumer
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    @Override
    public DEditTime onMouseEntered(Consumer<MouseEvent> consumer) {
        super.onMouseEntered(consumer);
        return this;
    }

    /**
     * Adds a mouse exited listener.
     * @param consumer the mouse event consumer
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    @Override
    public DEditTime onMouseExited(Consumer<MouseEvent> consumer) {
        super.onMouseExited(consumer);
        return this;
    }

    /**
     * Adds a key typed listener.
     * @param consumer the key event consumer
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    @Override
    public DEditTime onKeyTyped(Consumer<KeyEvent> consumer) {
        super.onKeyTyped(consumer);
        return this;
    }

    /**
     * Adds a key pressed listener.
     * @param consumer the key event consumer
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    @Override
    public DEditTime onKeyPressed(Consumer<KeyEvent> consumer) {
        super.onKeyPressed(consumer);
        return this;
    }

    /**
     * Adds a key released listener.
     * @param consumer the key event consumer
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    @Override
    public DEditTime onKeyReleased(Consumer<KeyEvent> consumer) {
        super.onKeyReleased(consumer);
        return this;
    }

    /**
     * Adds a focus gained listener.
     * @param consumer the focus event consumer
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    @Override
    public DEditTime onFocusGained(Consumer<FocusEvent> consumer) {
        super.onFocusGained(consumer);
        return this;
    }

    /**
     * Adds a focus lost listener.
     * @param consumer the focus event consumer
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    @Override
    public DEditTime onFocusLost(Consumer<FocusEvent> consumer) {
        super.onFocusLost(consumer);
        return this;
    }

    // --- Specific DEditTime Features ---

    /**
     * Sets the font for the internal clock components.
     * @param font the font to apply
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    public DEditTime font(java.awt.Font font) {
        for (JToggleButton btn : hourButtons) {
            btn.setFont(font);
        }
        for (JToggleButton btn : minuteButtons) {
            btn.setFont(font);
        }
        amPmButton.setFont(font.deriveFont(Font.BOLD));
        return this;
    }

    /**
     * Sets the background color for the overall clock panel root.
     * @param bg the background color
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    public DEditTime background(Color bg) {
        rootPanel.setBackground(bg);
        return this;
    }

    /**
     * Sets the foreground color for the clock toggle buttons.
     * @param fg the foreground color
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    public DEditTime foreground(Color fg) {
        for (JToggleButton btn : hourButtons) {
            btn.setForeground(fg);
        }
        for (JToggleButton btn : minuteButtons) {
            btn.setForeground(fg);
        }
        amPmButton.setForeground(fg);
        return this;
    }

    /**
     * Adds a listener that is notified whenever the user explicitly clicks a time toggle physically.
     * @param consumer the consumer to accept the newly selected time
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    public DEditTime onTimeSelected(Consumer<Date> consumer) {
        // Register the listener to all inner component toggle buttons explicitly
        java.awt.event.ActionListener listener = e -> consumer.accept(getValue());
        for (JToggleButton btn : hourButtons) {
            btn.addActionListener(listener);
        }
        for (JToggleButton btn : minuteButtons) {
            btn.addActionListener(listener);
        }
        amPmButton.addActionListener(listener);
        return this;
    }

    /**
     * Sets the background color specifically for the inner drawn clock face circle.
     * @param color the clock face color
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    public DEditTime clockColor(Color color) {
        this.clockColor = color;
        rootPanel.repaint();
        return this;
    }

    /**
     * Sets the hour explicitly programmatically.
     * @param hour the hour to select (1-12 range)
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    public DEditTime hour(int hour) {
        if (hour >= 1 && hour <= 12) {
            int index = (hour == 12) ? 0 : hour;
            hourButtons[index].setSelected(true);
            updateTimeFromSelection();
        }
        return this;
    }

    /**
     * Sets the minute explicitly programmatically. Snaps to the nearest 5-minute interval mechanically.
     * @param minute the minute to select (0-59 range)
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    public DEditTime minute(int minute) {
        if (minute >= 0 && minute <= 59) {
            int minIndex = Math.round(minute / 5.0f);
            if (minIndex == 12) minIndex = 0;
            minuteButtons[minIndex].setSelected(true);
            updateTimeFromSelection();
        }
        return this;
    }

    /**
     * Sets the clock to AM explicitly programmatically.
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    public DEditTime am() {
        amPmButton.setSelected(false);
        amPmButton.setText("AM");
        updateTimeFromSelection();
        return this;
    }

    /**
     * Sets the clock to PM explicitly programmatically.
     * @return This {@code DEditTime} instance for fluent chaining.
     */
    public DEditTime pm() {
        amPmButton.setSelected(true);
        amPmButton.setText("PM");
        updateTimeFromSelection();
        return this;
    }
}
