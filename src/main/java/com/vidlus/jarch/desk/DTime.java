package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.LayoutManager;
import javax.swing.Icon;
import javax.swing.border.Border;

/**
 * A custom clock component strictly for displaying the time of a
 * java.util.Date.
 * It provides the same visual concentric circular layout as DEditTime but is
 * read-only.
 */
public class DTime extends DPane {

    /** The main clock panel drawing the background and handling circular layout. */
    private final ClockPanel rootPanel;

    /** Internal calendar instance used for time calculations. */
    private final Calendar calendar;

    /** The date currently being displayed. */
    private Date displayDate;

    /** Array containing the 12 labels representing hours 1 through 12. */
    private final JLabel[] hourLabels;

    /**
     * Array containing the 12 labels representing minutes in 5-minute increments
     * (0-55).
     */
    private final JLabel[] minuteLabels;

    /** The central label indicating AM or PM. */
    private final JLabel amPmLabel;

    /**
     * Explicit color for the clock's circular face; if null, defaults to panel's
     * darker background.
     */
    private Color clockColor = null;

    /**
     * Color used to highlight the specific active hour, minute, and AM/PM state.
     */
    private Color highlightColor = Color.LIGHT_GRAY;

    /**
     * Constructs a read-only time view initialized to the current system time.
     */
    public DTime() {
        super();
        this.rootPanel = new ClockPanel();
        super.put(this.rootPanel);

        this.calendar = Calendar.getInstance();
        this.displayDate = calendar.getTime();

        // --- Hours (Inner Circle) ---
        hourLabels = new JLabel[12];
        for (int i = 0; i < 12; i++) {
            int hour = (i == 0) ? 12 : i;
            JLabel lbl = createCircleLabel(String.valueOf(hour));
            hourLabels[i] = lbl;
            rootPanel.add(lbl);
        }

        // --- Minutes (Outer Circle) ---
        minuteLabels = new JLabel[12];
        for (int i = 0; i < 12; i++) {
            int minute = i * 5;
            JLabel lbl = createCircleLabel(String.format("%02d", minute));
            minuteLabels[i] = lbl;
            rootPanel.add(lbl);
        }

        // --- AM / PM Label ---
        amPmLabel = createCircleLabel("AM");
        rootPanel.add(amPmLabel);

        syncUIWithDate();
    }

    /**
     * Helper method to generate uniformly styled labels for the clock.
     * 
     * @param text the text to display on the label
     * @return a properly styled JLabel
     */
    private JLabel createCircleLabel(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setOpaque(true); // Required for background highlighting
        return lbl;
    }

    /**
     * Synchronizes the UI labels with the current underlying displayDate object.
     * Calculates the nearest 5-minute interval for minutes and updates label
     * highlighting accordingly.
     */
    private void syncUIWithDate() {
        // Clear all highlights
        for (JLabel lbl : hourLabels)
            lbl.setBackground(null);
        for (JLabel lbl : minuteLabels)
            lbl.setBackground(null);
        amPmLabel.setBackground(null);

        if (displayDate == null) {
            amPmLabel.setText("");
            return;
        }

        calendar.setTime(displayDate);
        int hour = calendar.get(Calendar.HOUR); // 0-11 based on 12-hour AM/PM clock
        int minute = calendar.get(Calendar.MINUTE);
        int amPm = calendar.get(Calendar.AM_PM);

        // Highlight matching hour label (where index 0 is 12)
        hourLabels[hour].setBackground(highlightColor);

        // Snap raw minute reading to the nearest 5-minute index and highlight it
        int minIndex = Math.round(minute / 5.0f);
        if (minIndex == 12)
            minIndex = 0;
        minuteLabels[minIndex].setBackground(highlightColor);

        // Update the central AM/PM state
        if (amPm == Calendar.PM) {
            amPmLabel.setText("PM");
        } else {
            amPmLabel.setText("AM");
        }
        amPmLabel.setBackground(highlightColor);
    }

    /**
     * Retrieves the currently displayed date.
     * 
     * @return the displayed date, or null if cleared
     */
    public Date value() {
        return displayDate;
    }

    /**
     * Sets the currently displayed date and strictly updates the UI components.
     * 
     * @param value the date to show, or null to clear display
     * @return this DTime instance for fluent chaining
     */
    public DTime value(Date value) {
        this.displayDate = value;
        syncUIWithDate();
        return this;
    }

    /**
     * Sets the highlight color for the active time segments.
     * 
     * @param color the highlight color
     * @return this DTime instance for fluent chaining
     */
    public DTime highlightColor(Color color) {
        this.highlightColor = color;
        syncUIWithDate();
        return this;
    }

    // --- Custom Styling Methods ---

    /**
     * Sets the font for the internal clock components.
     * 
     * @param font the font to apply
     * @return this DTime instance for fluent chaining
     */
    public DTime font(Font font) {
        super.setFont(font);
        for (JLabel lbl : hourLabels) {
            lbl.setFont(font);
        }
        for (JLabel lbl : minuteLabels) {
            lbl.setFont(font);
        }
        amPmLabel.setFont(font.deriveFont(Font.BOLD));
        return this;
    }

    /**
     * Sets the font specifically for the hour labels in the inner circle.
     * 
     * @param font the new font
     * @return this DTime instance for fluent chaining
     */
    public DTime hourFont(Font font) {
        for (JLabel lbl : hourLabels)
            lbl.setFont(font);
        return this;
    }

    /**
     * Sets the font specifically for the minute labels in the outer circle.
     * 
     * @param font the new font
     * @return this DTime instance for fluent chaining
     */
    public DTime minuteFont(Font font) {
        for (JLabel lbl : minuteLabels)
            lbl.setFont(font);
        return this;
    }

    /**
     * Sets the font specifically for the central AM/PM label.
     * 
     * @param font the new font
     * @return this DTime instance for fluent chaining
     */
    public DTime amPmFont(Font font) {
        amPmLabel.setFont(font);
        return this;
    }

    /**
     * Sets the background color for the overall clock panel wrapper.
     * 
     * @param bg the background color
     * @return this DTime instance for fluent chaining
     */
    public DTime background(Color bg) {
        super.setBackground(bg);
        rootPanel.setBackground(bg);
        return this;
    }

    /**
     * Sets the text foreground color for all clock labels.
     * 
     * @param fg the foreground color
     * @return this DTime instance for fluent chaining
     */
    public DTime foreground(Color fg) {
        super.setForeground(fg);
        for (JLabel lbl : hourLabels) {
            lbl.setForeground(fg);
        }
        for (JLabel lbl : minuteLabels) {
            lbl.setForeground(fg);
        }
        amPmLabel.setForeground(fg);
        return this;
    }

    /**
     * Sets the text foreground color specifically for the inner circle hour labels.
     * 
     * @param fg the foreground color
     * @return this DTime instance for fluent chaining
     */
    public DTime hourForeground(Color fg) {
        for (JLabel lbl : hourLabels)
            lbl.setForeground(fg);
        return this;
    }

    /**
     * Sets the text foreground color specifically for the outer circle minute
     * labels.
     * 
     * @param fg the foreground color
     * @return this DTime instance for fluent chaining
     */
    public DTime minuteForeground(Color fg) {
        for (JLabel lbl : minuteLabels)
            lbl.setForeground(fg);
        return this;
    }

    /**
     * Sets the text foreground color specifically for the central AM/PM label.
     * 
     * @param fg the foreground color
     * @return this DTime instance for fluent chaining
     */
    public DTime amPmForeground(Color fg) {
        amPmLabel.setForeground(fg);
        return this;
    }

    /**
     * Specifies whether the hour labels are visually opaque.
     * 
     * @param opaque true to make opaque, false for transparent
     * @return this DTime instance for fluent chaining
     */
    public DTime hourOpaque(boolean opaque) {
        for (JLabel lbl : hourLabels)
            lbl.setOpaque(opaque);
        return this;
    }

    /**
     * Specifies whether the minute labels are visually opaque.
     * 
     * @param opaque true to make opaque, false for transparent
     * @return this DTime instance for fluent chaining
     */
    public DTime minuteOpaque(boolean opaque) {
        for (JLabel lbl : minuteLabels)
            lbl.setOpaque(opaque);
        return this;
    }

    /**
     * Specifies whether the AM/PM label is visually opaque.
     * 
     * @param opaque true to make opaque, false for transparent
     * @return this DTime instance for fluent chaining
     */
    public DTime amPmOpaque(boolean opaque) {
        amPmLabel.setOpaque(opaque);
        return this;
    }

    /**
     * Sets the background color specifically for the inner drawn clock face circle.
     * 
     * @param color the clock face color
     * @return this DTime instance for fluent chaining
     */
    public DTime clockColor(Color color) {
        this.clockColor = color;
        rootPanel.repaint();
        return this;
    }

    // --- Core Component Fluent API ---

    /**
     * Globally enables or disables the clock component and its internals.
     * 
     * @param enabled true if enabled, false otherwise
     * @return this DTime instance for fluent chaining
     */
    public DTime enabled(boolean enabled) {
        setEnabled(enabled);
        rootPanel.setEnabled(enabled);
        for (JLabel lbl : hourLabels)
            lbl.setEnabled(enabled);
        for (JLabel lbl : minuteLabels)
            lbl.setEnabled(enabled);
        amPmLabel.setEnabled(enabled);
        return this;
    }

    /**
     * Sets whether this component can receive focus.
     * 
     * @param focusable true if focusable, false otherwise
     * @return this DTime instance for fluent chaining
     */
    public DTime focusable(boolean focusable) {
        setFocusable(focusable);
        return this;
    }

    /**
     * Requests that this component get the input focus.
     * 
     * @return this DTime instance for fluent chaining
     */
    public DTime focus() {
        super.requestFocus();
        return this;
    }

    /**
     * Requests that this component get the input focus if its top-level window is
     * focused.
     * 
     * @return this DTime instance for fluent chaining
     */
    public DTime focusInWindow() {
        super.requestFocusInWindow();
        return this;
    }

    /**
     * Registers a callback for when the component is double-clicked or activated
     * via keyboard (Ctrl+Enter).
     * 
     * @param consumer the ActionEvent consumer
     * @return this DTime instance for fluent chaining
     */
    public DTime onAction(Consumer<ActionEvent> consumer) {
        var listenerMouse = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    consumer.accept(new ActionEvent(DTime.this, ActionEvent.ACTION_PERFORMED, "double-click"));
                }
            }
        };
        var listenerKeyboard = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown()) {
                    consumer.accept(new ActionEvent(DTime.this, ActionEvent.ACTION_PERFORMED, "enter-key"));
                }
            }
        };
        addMouseListener(listenerMouse);
        addKeyListener(listenerKeyboard);
        return this;
    }

    /**
     * Registers a callback for mouse click events on the clock background.
     * 
     * @param consumer the MouseEvent consumer
     * @return this DTime instance for fluent chaining
     */
    public DTime onMouseClicked(Consumer<MouseEvent> consumer) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    /**
     * Registers a callback for mouse press events.
     * 
     * @param consumer the MouseEvent consumer
     * @return this DTime instance for fluent chaining
     */
    public DTime onMousePressed(Consumer<MouseEvent> consumer) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    /**
     * Registers a callback for mouse release events.
     * 
     * @param consumer the MouseEvent consumer
     * @return this DTime instance for fluent chaining
     */
    public DTime onMouseReleased(Consumer<MouseEvent> consumer) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    /**
     * Registers a callback for when the mouse pointer enters the component.
     * 
     * @param consumer the MouseEvent consumer
     * @return this DTime instance for fluent chaining
     */
    public DTime onMouseEntered(Consumer<MouseEvent> consumer) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    /**
     * Registers a callback for when the mouse pointer exits the component.
     * 
     * @param consumer the MouseEvent consumer
     * @return this DTime instance for fluent chaining
     */
    public DTime onMouseExited(Consumer<MouseEvent> consumer) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    /**
     * Registers a callback for when a key is typed on the focused component.
     * 
     * @param consumer the KeyEvent consumer
     * @return this DTime instance for fluent chaining
     */
    public DTime onKeyTyped(Consumer<KeyEvent> consumer) {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    /**
     * Registers a callback for when a key is pressed.
     * 
     * @param consumer the KeyEvent consumer
     * @return this DTime instance for fluent chaining
     */
    public DTime onKeyPressed(Consumer<KeyEvent> consumer) {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    /**
     * Registers a callback for when a key is released.
     * 
     * @param consumer the KeyEvent consumer
     * @return this DTime instance for fluent chaining
     */
    public DTime onKeyReleased(Consumer<KeyEvent> consumer) {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    /**
     * Registers a callback for when the component gains input focus.
     * 
     * @param consumer the FocusEvent consumer
     * @return this DTime instance for fluent chaining
     */
    public DTime onFocusGained(Consumer<FocusEvent> consumer) {
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    /**
     * Registers a callback for when the component loses input focus.
     * 
     * @param consumer the FocusEvent consumer
     * @return this DTime instance for fluent chaining
     */
    public DTime onFocusLost(Consumer<FocusEvent> consumer) {
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    /**
     * Custom inner panel that strictly manages laying out labels dynamically
     * in concentric circles via trigonometric calculations and draws the clock
     * background.
     */
    private class ClockPanel extends JPanel {

        /**
         * Constructs a new ClockPanel with absolute positioning.
         */
        public ClockPanel() {
            super(null); // Absolute positioning required for trig layout
        }

        /**
         * Returns the preferred dimension for the clock, keeping a fixed square aspect ratio.
         * @return the preferred dimension
         */
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(250, 250);
        }

        /**
         * Custom paint method to draw the circular background of the clock.
         * @param g the Graphics context
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int size = Math.min(getWidth(), getHeight());
            int padding = 10;
            int diameter = size - padding * 2;

            int x = (getWidth() - diameter) / 2;
            int y = (getHeight() - diameter) / 2;

            g2.setColor(clockColor != null ? clockColor : getBackground().darker());
            g2.fillOval(x, y, diameter, diameter);

            g2.dispose();
        }

        /**
         * Calculates the dynamic placement of the hour, minute, and AM/PM labels
         * along circular paths using trigonometric functions.
         */
        @Override
        public void doLayout() {
            super.doLayout();

            int width = getWidth();
            int height = getHeight();
            int centerX = width / 2;
            int centerY = height / 2;

            int minDim = Math.min(width, height);

            double outerRadius = (minDim / 2.0) * 0.75;
            double innerRadius = (minDim / 2.0) * 0.45;

            int lblSize = Math.max(25, (int) (minDim * 0.12));

            // Layout Outer Minutes
            for (int i = 0; i < 12; i++) {
                double angle = Math.toRadians((i * 30) - 90);
                int lblX = centerX + (int) (outerRadius * Math.cos(angle)) - (lblSize / 2);
                int lblY = centerY + (int) (outerRadius * Math.sin(angle)) - (lblSize / 2);

                minuteLabels[i].setBounds(lblX, lblY, lblSize, lblSize);
            }

            // Layout Inner Hours
            for (int i = 0; i < 12; i++) {
                double angle = Math.toRadians((i * 30) - 90);
                int lblX = centerX + (int) (innerRadius * Math.cos(angle)) - (lblSize / 2);
                int lblY = centerY + (int) (innerRadius * Math.sin(angle)) - (lblSize / 2);

                hourLabels[i].setBounds(lblX, lblY, lblSize, lblSize);
            }

            // Center the AM/PM label
            int amPmSize = Math.max(35, (int) (minDim * 0.18));
            amPmLabel.setBounds(centerX - (amPmSize / 2), centerY - (amPmSize / 2), amPmSize, amPmSize);
        }
    }

    // --- Fluent Overrides for DPane ---

    /**
     * Sets the layout manager for this component.
     * @param layout the layout manager to use
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime layout(LayoutManager layout) {
        super.layout(layout);
        return this;
    }

    /**
     * Sets the border of this component.
     * @param border the border to set
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime border(Border border) {
        super.border(border);
        return this;
    }

    /**
     * Sets an empty border with the specified padding on all sides.
     * @param size the padding size
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderEmpty(int size) {
        super.borderEmpty(size);
        return this;
    }

    /**
     * Sets an empty border with specified padding for each side.
     * @param top the top padding
     * @param left the left padding
     * @param bottom the bottom padding
     * @param right the right padding
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderEmpty(int top, int left, int bottom, int right) {
        super.borderEmpty(top, left, bottom, right);
        return this;
    }

    /**
     * Sets a line border with the specified color.
     * @param color the line color
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderLine(Color color) {
        super.borderLine(color);
        return this;
    }

    /**
     * Sets a line border with the specified color and thickness.
     * @param color the line color
     * @param thickness the line thickness
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderLine(Color color, int thickness) {
        super.borderLine(color, thickness);
        return this;
    }

    /**
     * Sets a line border with the specified color, thickness, and rounded corners.
     * @param color the line color
     * @param thickness the line thickness
     * @param rounded true if corners should be rounded
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderLine(Color color, int thickness, boolean rounded) {
        super.borderLine(color, thickness, rounded);
        return this;
    }

    /**
     * Sets a default etched border.
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderEtched() {
        super.borderEtched();
        return this;
    }

    /**
     * Sets an etched border with the specified type (RAISED or LOWERED).
     * @param type the border type
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderEtched(int type) {
        super.borderEtched(type);
        return this;
    }

    /**
     * Sets an etched border with specific highlight and shadow colors.
     * @param highlight the highlight color
     * @param shadow the shadow color
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderEtched(Color highlight, Color shadow) {
        super.borderEtched(highlight, shadow);
        return this;
    }

    /**
     * Sets an etched border with specific type, highlight, and shadow colors.
     * @param type the border type
     * @param highlight the highlight color
     * @param shadow the shadow color
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderEtched(int type, Color highlight, Color shadow) {
        super.borderEtched(type, highlight, shadow);
        return this;
    }

    /**
     * Sets a raised bevel border.
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderBevelRaised() {
        super.borderBevelRaised();
        return this;
    }

    /**
     * Sets a lowered bevel border.
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderBevelLowered() {
        super.borderBevelLowered();
        return this;
    }

    /**
     * Sets a bevel border of the specified type (RAISED or LOWERED).
     * @param type the border type
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderBevel(int type) {
        super.borderBevel(type);
        return this;
    }

    /**
     * Sets a bevel border of the specified type with highlight and shadow colors.
     * @param type the border type
     * @param highlight the highlight color
     * @param shadow the shadow color
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderBevel(int type, Color highlight, Color shadow) {
        super.borderBevel(type, highlight, shadow);
        return this;
    }

    /**
     * Sets a bevel border of the specified type with detailed highlight and shadow colors.
     * @param type the border type
     * @param highlightOuter the outer highlight color
     * @param highlightInner the inner highlight color
     * @param shadowOuter the outer shadow color
     * @param shadowInner the inner shadow color
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderBevel(int type, Color highlightOuter, Color highlightInner, Color shadowOuter,
            Color shadowInner) {
        super.borderBevel(type, highlightOuter, highlightInner, shadowOuter, shadowInner);
        return this;
    }

    /**
     * Sets a soft bevel border of the specified type (RAISED or LOWERED).
     * @param type the border type
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderSoftBevel(int type) {
        super.borderSoftBevel(type);
        return this;
    }

    /**
     * Sets a soft bevel border of the specified type with highlight and shadow colors.
     * @param type the border type
     * @param highlight the highlight color
     * @param shadow the shadow color
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderSoftBevel(int type, Color highlight, Color shadow) {
        super.borderSoftBevel(type, highlight, shadow);
        return this;
    }

    /**
     * Sets a soft bevel border of the specified type with detailed highlight and shadow colors.
     * @param type the border type
     * @param highlightOuter the outer highlight color
     * @param highlightInner the inner highlight color
     * @param shadowOuter the outer shadow color
     * @param shadowInner the inner shadow color
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderSoftBevel(int type, Color highlightOuter, Color highlightInner, Color shadowOuter,
            Color shadowInner) {
        super.borderSoftBevel(type, highlightOuter, highlightInner, shadowOuter, shadowInner);
        return this;
    }

    /**
     * Sets a titled border with the specified title string.
     * @param title the title text
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderTitled(String title) {
        super.borderTitled(title);
        return this;
    }

    /**
     * Sets a titled border using the specified border.
     * @param border the border to title
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderTitled(Border border) {
        super.borderTitled(border);
        return this;
    }

    /**
     * Sets a titled border using the specified border and title string.
     * @param border the border to title
     * @param title the title text
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderTitled(Border border, String title) {
        super.borderTitled(border, title);
        return this;
    }

    /**
     * Sets a titled border with the specified title, justification, and position.
     * @param title the title text
     * @param justification the title justification
     * @param position the title position
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderTitled(String title, int justification, int position) {
        super.borderTitled(title, justification, position);
        return this;
    }

    /**
     * Sets a titled border with the specified title, justification, position, and font.
     * @param title the title text
     * @param justification the title justification
     * @param position the title position
     * @param font the title font
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderTitled(String title, int justification, int position, Font font) {
        super.borderTitled(title, justification, position, font);
        return this;
    }

    /**
     * Sets a titled border with the specified title, justification, position, font, and color.
     * @param title the title text
     * @param justification the title justification
     * @param position the title position
     * @param font the title font
     * @param color the title color
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderTitled(String title, int justification, int position, Font font, Color color) {
        super.borderTitled(title, justification, position, font, color);
        return this;
    }

    /**
     * Sets a titled border using the specified border, title, justification, position, font, and color.
     * @param border the border to title
     * @param title the title text
     * @param justification the title justification
     * @param position the title position
     * @param font the title font
     * @param color the title color
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderTitled(Border border, String title, int justification, int position,
            Font font, Color color) {
        super.borderTitled(border, title, justification, position, font, color);
        return this;
    }

    /**
     * Sets a matte border with the specified padding and color.
     * @param top the top padding
     * @param left the left padding
     * @param bottom the bottom padding
     * @param right the right padding
     * @param color the border color
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderMatte(int top, int left, int bottom, int right, Color color) {
        super.borderMatte(top, left, bottom, right, color);
        return this;
    }

    /**
     * Sets a matte border with the specified padding and tile icon.
     * @param top the top padding
     * @param left the left padding
     * @param bottom the bottom padding
     * @param right the right padding
     * @param tileIcon the icon to tile
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderMatte(int top, int left, int bottom, int right, Icon tileIcon) {
        super.borderMatte(top, left, bottom, right, tileIcon);
        return this;
    }

    /**
     * Sets a compound border composed of an outside and inside border.
     * @param outside the outside border
     * @param inside the inside border
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime borderCompound(Border outside, Border inside) {
        super.borderCompound(outside, inside);
        return this;
    }

    /**
     * Sets the internal component name.
     * @param name the component name
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime name(String name) {
        super.name(name);
        return this;
    }

    /**
     * Sets the tooltip hint text for this component.
     * @param hint the tooltip text
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime hint(String hint) {
        super.hint(hint);
        return this;
    }

    /**
     * Adds a component to this pane.
     * @param comp the component to add
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime put(Component comp) {
        super.put(comp);
        return this;
    }

    /**
     * Adds a DEdit component to this pane.
     * @param edit the DEdit to add
     * @return this DTime instance for fluent chaining
     */
    @Override
    public DTime put(DEdit<?> edit) {
        super.put(edit);
        return this;
    }
}
