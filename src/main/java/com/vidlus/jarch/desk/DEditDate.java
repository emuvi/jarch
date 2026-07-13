package com.vidlus.jarch.desk;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.Locale;
import java.util.function.Consumer;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

/**
 * A custom calendar component for selecting a java.time.LocalDate.
 * Extends DEdit to integrate with the jarch framework and provides a fluent API.
 */
public class DEditDate extends DEdit<LocalDate> {

    /** The main wrapper panel containing the entire calendar layout. */
    private final JPanel rootPanel;
    
    /** The dropdown list allowing selection of the month. */
    private final JComboBox<String> monthCombo;
    
    /** The spinner component allowing selection of the year. */
    private final JSpinner yearSpinner;
    
    /** The grid panel that holds the day-of-week headers and day toggle buttons. */
    private final JPanel daysPanel;
    
    /** The currently selected date in the component. */
    private LocalDate selectedDate;
    
    /** Array storing the 42 toggle buttons for days of the month (spanning up to 6 weeks). */
    private final JToggleButton[] dayButtons;
    
    /** ButtonGroup ensuring that only one day can be selected at a time in the calendar grid. */
    private final ButtonGroup buttonGroup;

    /**
     * Constructs a new DEditDate initialized to the current date.
     * Sets up the UI layout with a header for the month/year and a grid for the days.
     */
    public DEditDate() {
        super(new JPanel(new BorderLayout()));
        this.rootPanel = (JPanel) super.comp();
        
        // Initialize current selection
        this.selectedDate = LocalDate.now();
        
        // --- North Panel: Month and Year ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        
        // Retrieve localized month names.
        String[] months = new DateFormatSymbols(Locale.getDefault()).getMonths();
        // DateFormatSymbols.getMonths() returns 13 elements, the last is empty for leap months in some calendars. We truncate it to 12.
        String[] cleanMonths = new String[12];
        System.arraycopy(months, 0, cleanMonths, 0, 12);
        
        // Setup month combo box
        monthCombo = new JComboBox<>(cleanMonths);
        monthCombo.setSelectedIndex(this.selectedDate.getMonthValue() - 1);
        
        // Setup year spinner (default 1900-2100)
        int currentYear = this.selectedDate.getYear();
        yearSpinner = new JSpinner(new SpinnerNumberModel(currentYear, 1900, 2100, 1));
        yearSpinner.setEditor(new JSpinner.NumberEditor(yearSpinner, "#"));
        
        headerPanel.add(monthCombo, BorderLayout.CENTER);
        headerPanel.add(yearSpinner, BorderLayout.EAST);
        
        rootPanel.add(headerPanel, BorderLayout.NORTH);
        
        // --- Center Panel: Days Grid ---
        // 7 columns for days of week. 7 rows: 1 for header, up to 6 for weeks.
        daysPanel = new JPanel(new GridLayout(7, 7));
        
        // Add headers for days of the week (Sun-Sat)
        String[] shortWeekdays = new DateFormatSymbols(Locale.getDefault()).getShortWeekdays();
        // Calendar days are 1-indexed (Sunday=1). shortWeekdays length is 8.
        for (int i = java.util.Calendar.SUNDAY; i <= java.util.Calendar.SATURDAY; i++) {
            JLabel dayLabel = new JLabel(shortWeekdays[i], SwingConstants.CENTER);
            daysPanel.add(dayLabel);
        }
        
        // Initialize button arrays
        dayButtons = new JToggleButton[42];
        buttonGroup = new ButtonGroup();
        
        // Create 42 toggle buttons representing all possible day positions in a 6-week view
        for (int i = 0; i < 42; i++) {
            JToggleButton btn = new JToggleButton();
            btn.setMargin(new java.awt.Insets(2, 2, 2, 2));
            btn.setFocusPainted(false);
            
            dayButtons[i] = btn;
            buttonGroup.add(btn);
            daysPanel.add(btn);
            
            // Add action listener to register explicit user selection
            btn.addActionListener(e -> {
                int day = Integer.parseInt(btn.getText());
                int year = (Integer) yearSpinner.getValue();
                int month = monthCombo.getSelectedIndex() + 1;
                selectedDate = LocalDate.of(year, month, day);
            });
        }
        
        rootPanel.add(daysPanel, BorderLayout.CENTER);
        
        // --- Event Listeners ---
        // Repopulate days when the month or year selection changes
        monthCombo.addActionListener(e -> updateCalendar());
        yearSpinner.addChangeListener(e -> updateCalendar());
        
        // Initial setup of day buttons
        updateCalendar();
    }

    /**
     * Updates the days panel by calculating the first day of the selected month
     * and correctly placing the numbers on the toggle buttons.
     * Buttons outside the active month's range are hidden.
     */
    private void updateCalendar() {
        int selectedMonth = monthCombo.getSelectedIndex();
        int selectedYear = (Integer) yearSpinner.getValue();
        
        LocalDate firstOfMonth = LocalDate.of(selectedYear, selectedMonth + 1, 1);
        int firstDayOfWeek = firstOfMonth.getDayOfWeek().getValue(); // 1=Monday, 7=Sunday
        
        // Calculate offset (e.g., if month starts on Tuesday, offset is 2)
        int startOffset = firstDayOfWeek == 7 ? 0 : firstDayOfWeek;
        int daysInMonth = firstOfMonth.lengthOfMonth();
        
        // Validate if there's currently a valid selection in this specific year/month view
        boolean hasSelectionInThisMonth = false;
        if (selectedDate != null) {
            if (selectedDate.getYear() == selectedYear && selectedDate.getMonthValue() == selectedMonth + 1) {
                hasSelectionInThisMonth = true;
            }
        }
        
        // Clear old selection
        buttonGroup.clearSelection();
        
        // Populate the toggle buttons sequentially
        for (int i = 0; i < 42; i++) {
            JToggleButton btn = dayButtons[i];
            
            if (i >= startOffset && i < startOffset + daysInMonth) {
                // Button is an active day of the month
                int day = i - startOffset + 1;
                btn.setText(String.valueOf(day));
                btn.setVisible(true);
                
                // Select if it matches the recorded selection
                if (hasSelectionInThisMonth && selectedDate.getDayOfMonth() == day) {
                    btn.setSelected(true);
                }
            } else {
                // Button is outside the month boundary
                btn.setText("");
                btn.setVisible(false);
            }
        }
    }

    /**
     * Retrieves the currently selected date.
     * @return the selected date, or null if cleared
     */
    @Override
    public LocalDate getValue() {
        return selectedDate;
    }

    /**
     * Sets the currently selected date and updates the UI components.
     * Updates the inner spinner and combo box synchronously.
     * @param value the date to select, or null to clear selection
     */
    @Override
    public void setValue(LocalDate value) {
        this.selectedDate = value;
        if (value != null) {
            yearSpinner.setValue(value.getYear());
            monthCombo.setSelectedIndex(value.getMonthValue() - 1);
        } else {
            buttonGroup.clearSelection();
        }
        updateCalendar();
    }

    // --- DEdit Overrides ---

    /**
     * Sets the underlying JComponent.
     * @param comp the component
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    @Override
    public DEditDate comp(JComponent comp) {
        super.comp(comp);
        return this;
    }

    /**
     * Sets the value of this field.
     * @param value the value to set
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    @Override
    public DEditDate value(LocalDate value) {
        super.value(value);
        return this;
    }

    /**
     * Clears the selected date.
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    @Override
    public DEditDate clear() {
        super.clear();
        return this;
    }

    /**
     * Sets whether the calendar component is enabled.
     * @param enabled true if enabled, false otherwise
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    @Override
    public DEditDate enabled(boolean enabled) {
        super.enabled(enabled);
        monthCombo.setEnabled(enabled);
        yearSpinner.setEnabled(enabled);
        for (JToggleButton btn : dayButtons) {
            btn.setEnabled(enabled);
        }
        return this;
    }

    /**
     * Sets whether the component is focusable.
     * @param focusable true if focusable, false otherwise
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    @Override
    public DEditDate focusable(boolean focusable) {
        super.focusable(focusable);
        return this;
    }

    /**
     * Requests focus for this component.
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    @Override
    public DEditDate requestFocus() {
        super.requestFocus();
        return this;
    }

    /**
     * Requests focus in window for this component.
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    @Override
    public DEditDate requestFocusInWindow() {
        super.requestFocusInWindow();
        return this;
    }

    /**
     * Sets the component name.
     * @param name the component name
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    @Override
    public DEditDate name(String name) {
        super.name(name);
        return this;
    }

    /**
     * Sets the tooltip hint for this component.
     * @param hint the tooltip hint
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    @Override
    public DEditDate hint(String hint) {
        super.hint(hint);
        return this;
    }

    /**
     * Adds an action listener to the root panel.
     * @param consumer the action consumer
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    @Override
    public DEditDate onAction(Consumer<ActionEvent> consumer) {
        super.onAction(consumer);
        return this;
    }

    /**
     * Adds a mouse clicked listener.
     * @param consumer the mouse event consumer
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    @Override
    public DEditDate onMouseClicked(Consumer<MouseEvent> consumer) {
        super.onMouseClicked(consumer);
        return this;
    }

    /**
     * Adds a mouse pressed listener.
     * @param consumer the mouse event consumer
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    @Override
    public DEditDate onMousePressed(Consumer<MouseEvent> consumer) {
        super.onMousePressed(consumer);
        return this;
    }

    /**
     * Adds a mouse released listener.
     * @param consumer the mouse event consumer
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    @Override
    public DEditDate onMouseReleased(Consumer<MouseEvent> consumer) {
        super.onMouseReleased(consumer);
        return this;
    }

    /**
     * Adds a mouse entered listener.
     * @param consumer the mouse event consumer
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    @Override
    public DEditDate onMouseEntered(Consumer<MouseEvent> consumer) {
        super.onMouseEntered(consumer);
        return this;
    }

    /**
     * Adds a mouse exited listener.
     * @param consumer the mouse event consumer
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    @Override
    public DEditDate onMouseExited(Consumer<MouseEvent> consumer) {
        super.onMouseExited(consumer);
        return this;
    }

    /**
     * Adds a key typed listener.
     * @param consumer the key event consumer
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    @Override
    public DEditDate onKeyTyped(Consumer<KeyEvent> consumer) {
        super.onKeyTyped(consumer);
        return this;
    }

    /**
     * Adds a key pressed listener.
     * @param consumer the key event consumer
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    @Override
    public DEditDate onKeyPressed(Consumer<KeyEvent> consumer) {
        super.onKeyPressed(consumer);
        return this;
    }

    /**
     * Adds a key released listener.
     * @param consumer the key event consumer
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    @Override
    public DEditDate onKeyReleased(Consumer<KeyEvent> consumer) {
        super.onKeyReleased(consumer);
        return this;
    }

    /**
     * Adds a focus gained listener.
     * @param consumer the focus event consumer
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    @Override
    public DEditDate onFocusGained(Consumer<FocusEvent> consumer) {
        super.onFocusGained(consumer);
        return this;
    }

    /**
     * Adds a focus lost listener.
     * @param consumer the focus event consumer
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    @Override
    public DEditDate onFocusLost(Consumer<FocusEvent> consumer) {
        super.onFocusLost(consumer);
        return this;
    }

    // --- Specific DEditDate Features ---

    /**
     * Sets the minimum year allowed in the year spinner.
     * @param minYear the minimum year
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    public DEditDate minYear(int minYear) {
        SpinnerNumberModel model = (SpinnerNumberModel) yearSpinner.getModel();
        model.setMinimum(minYear);
        return this;
    }

    /**
     * Sets the maximum year allowed in the year spinner.
     * @param maxYear the maximum year
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    public DEditDate maxYear(int maxYear) {
        SpinnerNumberModel model = (SpinnerNumberModel) yearSpinner.getModel();
        model.setMaximum(maxYear);
        return this;
    }

    /**
     * Sets the font for the calendar components.
     * @param font the font to apply
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    public DEditDate font(java.awt.Font font) {
        monthCombo.setFont(font);
        yearSpinner.setFont(font);
        for (java.awt.Component comp : daysPanel.getComponents()) {
            comp.setFont(font);
        }
        return this;
    }

    /**
     * Sets the background color for the calendar panels.
     * @param bg the background color
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    public DEditDate background(java.awt.Color bg) {
        rootPanel.setBackground(bg);
        daysPanel.setBackground(bg);
        return this;
    }

    /**
     * Sets the foreground color for the calendar components.
     * @param fg the foreground color
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    public DEditDate foreground(java.awt.Color fg) {
        monthCombo.setForeground(fg);
        yearSpinner.setForeground(fg);
        for (java.awt.Component comp : daysPanel.getComponents()) {
            comp.setForeground(fg);
        }
        return this;
    }

    /**
     * Adds a listener that is notified whenever the user explicitly selects a date.
     * @param consumer the consumer to accept the newly selected date
     * @return This {@code DEditDate} instance for fluent chaining.
     */
    public DEditDate onDateSelected(Consumer<LocalDate> consumer) {
        for (JToggleButton btn : dayButtons) {
            btn.addActionListener(e -> {
                if (btn.isSelected()) {
                    consumer.accept(getValue());
                }
            });
        }
        return this;
    }
}
