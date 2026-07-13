package com.vidlus.jarch.desk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;
import java.util.function.Consumer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.LayoutManager;
import javax.swing.Icon;
import javax.swing.border.Border;

/**
 * A custom calendar component strictly for displaying a java.time.LocalDate.
 * It provides the same visual style and grid layout as DEditDate but is
 * read-only,
 * removing all interactive editor components like spinners or dropdowns.
 */
public class DDate extends DPane {

    /** The label that shows the month and year text (e.g. "January 2026"). */
    private final JLabel headerLabel;

    /** The panel holding the grid of days. */
    private final JPanel daysPanel;

    /** The date currently being displayed. */
    private LocalDate displayDate;

    /**
     * The grid of 42 labels (spanning up to 6 weeks) representing days of the
     * month.
     */
    private final JLabel[] dayLabels;

    /** Color used to highlight the specific active day matching the date. */
    private Color highlightColor = Color.LIGHT_GRAY;

    /**
     * Constructs a read-only date view initialized to the current date.
     */
    public DDate() {
        super(new BorderLayout());

        displayDate = LocalDate.now();

        // --- North Panel: Month and Year Header ---
        headerLabel = new JLabel("", SwingConstants.CENTER);
        headerLabel.setFont(headerLabel.getFont().deriveFont(Font.BOLD));
        add(headerLabel, BorderLayout.NORTH);

        // --- Center Panel: Days Grid ---
        daysPanel = new JPanel(new GridLayout(7, 7));

        // Add headers for days of the week (Sun-Sat)
        String[] shortWeekdays = new DateFormatSymbols(Locale.getDefault()).getShortWeekdays();
        for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
            JLabel dayLabel = new JLabel(shortWeekdays[i], SwingConstants.CENTER);
            daysPanel.add(dayLabel);
        }

        dayLabels = new JLabel[42];
        for (int i = 0; i < 42; i++) {
            JLabel lbl = new JLabel("", SwingConstants.CENTER);
            lbl.setOpaque(true); // Required for background colors to render
            dayLabels[i] = lbl;
            daysPanel.add(lbl);
        }

        add(daysPanel, BorderLayout.CENTER);

        updateCalendar();
    }

    /**
     * Computes the offsets for the month and updates the 42 day labels,
     * highlighting the currently selected day.
     */
    private void updateCalendar() {
        if (displayDate == null) {
            headerLabel.setText("");
            for (JLabel lbl : dayLabels) {
                lbl.setText("");
                lbl.setBackground(null);
            }
            return;
        }

        int targetYear = displayDate.getYear();
        int targetMonth = displayDate.getMonthValue() - 1; // 0-11 for DateFormatSymbols
        int targetDay = displayDate.getDayOfMonth();

        // Update Header
        String[] months = new DateFormatSymbols(Locale.getDefault()).getMonths();
        headerLabel.setText(months[targetMonth] + " " + targetYear);

        LocalDate firstOfMonth = displayDate.withDayOfMonth(1);
        int firstDayOfWeek = firstOfMonth.getDayOfWeek().getValue(); // 1=Monday, 7=Sunday
        int startOffset = firstDayOfWeek == 7 ? 0 : firstDayOfWeek;
        int daysInMonth = displayDate.lengthOfMonth();

        for (int i = 0; i < 42; i++) {
            JLabel lbl = dayLabels[i];
            lbl.setBackground(null); // Clear background highlight

            if (i >= startOffset && i < startOffset + daysInMonth) {
                int day = i - startOffset + 1;
                lbl.setText(String.valueOf(day));

                // Highlight the target day of the displayed date
                if (day == targetDay) {
                    lbl.setBackground(highlightColor);
                }
            } else {
                lbl.setText("");
            }
        }
    }

    /**
     * Sets the date to display in this component.
     * 
     * @param date the date to show
     * @return this DDate instance for fluent chaining
     */
    public DDate value(LocalDate date) {
        this.displayDate = date;
        updateCalendar();
        return this;
    }

    /**
     * Retrieves the currently displayed date.
     * 
     * @return the displayed date
     */
    public LocalDate value() {
        return displayDate;
    }

    /**
     * Sets the highlight color for the active day.
     * 
     * @param color the highlight color
     * @return this DDate instance for fluent chaining
     */
    public DDate highlightColor(Color color) {
        this.highlightColor = color;
        updateCalendar();
        return this;
    }

    // --- Style Setters (Non-DPane, Custom) ---

    /**
     * Sets the font for the calendar components.
     * 
     * @param font the font to apply
     * @return this DDate instance for fluent chaining
     */
    public DDate font(Font font) {
        super.setFont(font);
        headerLabel.setFont(font.deriveFont(Font.BOLD));
        for (Component comp : daysPanel.getComponents()) {
            comp.setFont(font);
        }
        return this;
    }

    /**
     * Sets the background color for the calendar panels.
     * 
     * @param bg the background color
     * @return this DDate instance for fluent chaining
     */
    public DDate background(Color bg) {
        super.setBackground(bg);
        daysPanel.setBackground(bg);
        return this;
    }

    /**
     * Sets the foreground color for the calendar components.
     * 
     * @param fg the foreground color
     * @return this DDate instance for fluent chaining
     */
    public DDate foreground(Color fg) {
        super.setForeground(fg);
        headerLabel.setForeground(fg);
        for (Component comp : daysPanel.getComponents()) {
            comp.setForeground(fg);
        }
        return this;
    }

    /**
     * Sets the font for the header label showing the month and year.
     * 
     * @param font the new font
     * @return this DDate instance for fluent chaining
     */
    public DDate headerFont(Font font) {
        headerLabel.setFont(font);
        return this;
    }

    /**
     * Sets the text foreground color for the header label.
     * 
     * @param fg the new foreground color
     * @return this DDate instance for fluent chaining
     */
    public DDate headerForeground(Color fg) {
        headerLabel.setForeground(fg);
        return this;
    }

    /**
     * Sets the background color for the header label.
     * 
     * @param bg the new background color
     * @return this DDate instance for fluent chaining
     */
    public DDate headerBackground(Color bg) {
        headerLabel.setBackground(bg);
        return this;
    }

    /**
     * Specifies whether the header label is visually opaque.
     * 
     * @param opaque true to make opaque, false for transparent
     * @return this DDate instance for fluent chaining
     */
    public DDate headerOpaque(boolean opaque) {
        headerLabel.setOpaque(opaque);
        return this;
    }

    /**
     * Sets the font for all labels inside the days grid.
     * 
     * @param font the new font
     * @return this DDate instance for fluent chaining
     */
    public DDate daysFont(Font font) {
        for (Component comp : daysPanel.getComponents()) {
            comp.setFont(font);
        }
        return this;
    }

    /**
     * Sets the text foreground color for all labels inside the days grid.
     * 
     * @param fg the new foreground color
     * @return this DDate instance for fluent chaining
     */
    public DDate daysForeground(Color fg) {
        for (Component comp : daysPanel.getComponents()) {
            comp.setForeground(fg);
        }
        return this;
    }

    /**
     * Sets the background color for the panel containing the days grid.
     * 
     * @param bg the new background color
     * @return this DDate instance for fluent chaining
     */
    public DDate daysBackground(Color bg) {
        daysPanel.setBackground(bg);
        return this;
    }

    /**
     * Specifies whether the days grid components are visually opaque.
     * 
     * @param opaque true to make opaque, false for transparent
     * @return this DDate instance for fluent chaining
     */
    public DDate daysOpaque(boolean opaque) {
        daysPanel.setOpaque(opaque);
        for (JLabel lbl : dayLabels) {
            lbl.setOpaque(opaque);
        }
        return this;
    }

    // --- Component features in fluent style ---

    /**
     * Globally enables or disables the calendar component and its internals.
     * 
     * @param enabled true if enabled, false otherwise
     * @return this DDate instance for fluent chaining
     */
    public DDate enabled(boolean enabled) {
        setEnabled(enabled);
        headerLabel.setEnabled(enabled);
        daysPanel.setEnabled(enabled);
        for (Component comp : daysPanel.getComponents()) {
            comp.setEnabled(enabled);
        }
        return this;
    }

    /**
     * Sets whether this component can receive focus.
     * 
     * @param focusable true if focusable, false otherwise
     * @return this DDate instance for fluent chaining
     */
    public DDate focusable(boolean focusable) {
        setFocusable(focusable);
        return this;
    }

    /**
     * Requests that this component get the input focus.
     * 
     * @return this DDate instance for fluent chaining
     */
    public DDate focus() {
        super.requestFocus();
        return this;
    }

    /**
     * Requests that this component get the input focus if its top-level window is
     * focused.
     * 
     * @return this DDate instance for fluent chaining
     */
    public DDate focusInWindow() {
        super.requestFocusInWindow();
        return this;
    }

    /**
     * Registers a callback for when the component is double-clicked or activated
     * via keyboard (Ctrl+Enter).
     * 
     * @param consumer the ActionEvent consumer
     * @return this DDate instance for fluent chaining
     */
    public DDate onAction(Consumer<ActionEvent> consumer) {
        var listenerMouse = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    consumer.accept(new ActionEvent(DDate.this, ActionEvent.ACTION_PERFORMED, "double-click"));
                }
            }
        };
        var listenerKeyboard = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown()) {
                    consumer.accept(new ActionEvent(DDate.this, ActionEvent.ACTION_PERFORMED, "enter-key"));
                }
            }
        };
        addMouseListener(listenerMouse);
        addKeyListener(listenerKeyboard);
        return this;
    }

    /**
     * Registers a callback for mouse click events on the calendar background.
     * 
     * @param consumer the MouseEvent consumer
     * @return this DDate instance for fluent chaining
     */
    public DDate onMouseClicked(Consumer<MouseEvent> consumer) {
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
     * @return this DDate instance for fluent chaining
     */
    public DDate onMousePressed(Consumer<MouseEvent> consumer) {
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
     * @return this DDate instance for fluent chaining
     */
    public DDate onMouseReleased(Consumer<MouseEvent> consumer) {
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
     * @return this DDate instance for fluent chaining
     */
    public DDate onMouseEntered(Consumer<MouseEvent> consumer) {
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
     * @return this DDate instance for fluent chaining
     */
    public DDate onMouseExited(Consumer<MouseEvent> consumer) {
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
     * @return this DDate instance for fluent chaining
     */
    public DDate onKeyTyped(Consumer<KeyEvent> consumer) {
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
     * @return this DDate instance for fluent chaining
     */
    public DDate onKeyPressed(Consumer<KeyEvent> consumer) {
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
     * @return this DDate instance for fluent chaining
     */
    public DDate onKeyReleased(Consumer<KeyEvent> consumer) {
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
     * @return this DDate instance for fluent chaining
     */
    public DDate onFocusGained(Consumer<FocusEvent> consumer) {
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
     * @return this DDate instance for fluent chaining
     */
    public DDate onFocusLost(Consumer<FocusEvent> consumer) {
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    // --- Fluent Overrides for DPane ---

    /**
     * Sets the layout manager for this component.
     * @param layout the layout manager to use
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate layout(LayoutManager layout) {
        super.layout(layout);
        return this;
    }

    /**
     * Sets the border of this component.
     * @param border the border to set
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate border(Border border) {
        super.border(border);
        return this;
    }

    /**
     * Sets an empty border with the specified padding on all sides.
     * @param size the padding size
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderEmpty(int size) {
        super.borderEmpty(size);
        return this;
    }

    /**
     * Sets an empty border with specified padding for each side.
     * @param top the top padding
     * @param left the left padding
     * @param bottom the bottom padding
     * @param right the right padding
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderEmpty(int top, int left, int bottom, int right) {
        super.borderEmpty(top, left, bottom, right);
        return this;
    }

    /**
     * Sets a line border with the specified color.
     * @param color the line color
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderLine(Color color) {
        super.borderLine(color);
        return this;
    }

    /**
     * Sets a line border with the specified color and thickness.
     * @param color the line color
     * @param thickness the line thickness
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderLine(Color color, int thickness) {
        super.borderLine(color, thickness);
        return this;
    }

    /**
     * Sets a line border with the specified color, thickness, and rounded corners.
     * @param color the line color
     * @param thickness the line thickness
     * @param rounded true if corners should be rounded
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderLine(Color color, int thickness, boolean rounded) {
        super.borderLine(color, thickness, rounded);
        return this;
    }

    /**
     * Sets a default etched border.
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderEtched() {
        super.borderEtched();
        return this;
    }

    /**
     * Sets an etched border with the specified type (RAISED or LOWERED).
     * @param type the border type
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderEtched(int type) {
        super.borderEtched(type);
        return this;
    }

    /**
     * Sets an etched border with specific highlight and shadow colors.
     * @param highlight the highlight color
     * @param shadow the shadow color
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderEtched(Color highlight, Color shadow) {
        super.borderEtched(highlight, shadow);
        return this;
    }

    /**
     * Sets an etched border with specific type, highlight, and shadow colors.
     * @param type the border type
     * @param highlight the highlight color
     * @param shadow the shadow color
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderEtched(int type, Color highlight, Color shadow) {
        super.borderEtched(type, highlight, shadow);
        return this;
    }

    /**
     * Sets a raised bevel border.
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderBevelRaised() {
        super.borderBevelRaised();
        return this;
    }

    /**
     * Sets a lowered bevel border.
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderBevelLowered() {
        super.borderBevelLowered();
        return this;
    }

    /**
     * Sets a bevel border of the specified type (RAISED or LOWERED).
     * @param type the border type
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderBevel(int type) {
        super.borderBevel(type);
        return this;
    }

    /**
     * Sets a bevel border of the specified type with highlight and shadow colors.
     * @param type the border type
     * @param highlight the highlight color
     * @param shadow the shadow color
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderBevel(int type, Color highlight, Color shadow) {
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
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderBevel(int type, Color highlightOuter, Color highlightInner, Color shadowOuter,
            Color shadowInner) {
        super.borderBevel(type, highlightOuter, highlightInner, shadowOuter, shadowInner);
        return this;
    }

    /**
     * Sets a soft bevel border of the specified type (RAISED or LOWERED).
     * @param type the border type
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderSoftBevel(int type) {
        super.borderSoftBevel(type);
        return this;
    }

    /**
     * Sets a soft bevel border of the specified type with highlight and shadow colors.
     * @param type the border type
     * @param highlight the highlight color
     * @param shadow the shadow color
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderSoftBevel(int type, Color highlight, Color shadow) {
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
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderSoftBevel(int type, Color highlightOuter, Color highlightInner, Color shadowOuter,
            Color shadowInner) {
        super.borderSoftBevel(type, highlightOuter, highlightInner, shadowOuter, shadowInner);
        return this;
    }

    /**
     * Sets a titled border with the specified title string.
     * @param title the title text
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderTitled(String title) {
        super.borderTitled(title);
        return this;
    }

    /**
     * Sets a titled border using the specified border.
     * @param border the border to title
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderTitled(Border border) {
        super.borderTitled(border);
        return this;
    }

    /**
     * Sets a titled border using the specified border and title string.
     * @param border the border to title
     * @param title the title text
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderTitled(Border border, String title) {
        super.borderTitled(border, title);
        return this;
    }

    /**
     * Sets a titled border with the specified title, justification, and position.
     * @param title the title text
     * @param justification the title justification
     * @param position the title position
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderTitled(String title, int justification, int position) {
        super.borderTitled(title, justification, position);
        return this;
    }

    /**
     * Sets a titled border with the specified title, justification, position, and font.
     * @param title the title text
     * @param justification the title justification
     * @param position the title position
     * @param font the title font
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderTitled(String title, int justification, int position, Font font) {
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
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderTitled(String title, int justification, int position, Font font, Color color) {
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
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderTitled(Border border, String title, int justification, int position,
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
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderMatte(int top, int left, int bottom, int right, Color color) {
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
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderMatte(int top, int left, int bottom, int right, Icon tileIcon) {
        super.borderMatte(top, left, bottom, right, tileIcon);
        return this;
    }

    /**
     * Sets a compound border composed of an outside and inside border.
     * @param outside the outside border
     * @param inside the inside border
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate borderCompound(Border outside, Border inside) {
        super.borderCompound(outside, inside);
        return this;
    }

    /**
     * Sets the internal component name.
     * @param name the component name
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate name(String name) {
        super.name(name);
        return this;
    }

    /**
     * Sets the tooltip hint text for this component.
     * @param hint the tooltip text
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate hint(String hint) {
        super.hint(hint);
        return this;
    }

    /**
     * Adds a component to this pane.
     * @param comp the component to add
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate put(Component comp) {
        super.put(comp);
        return this;
    }

    /**
     * Adds a DEdit component to this pane.
     * @param edit the DEdit to add
     * @return this DDate instance for fluent chaining
     */
    @Override
    public DDate put(DEdit<?> edit) {
        super.put(edit);
        return this;
    }
}
