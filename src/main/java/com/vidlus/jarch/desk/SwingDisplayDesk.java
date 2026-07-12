package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.vidlus.jarch.mage.WizGUI;

/**
 * A graphical interface for visually selecting a region on a specific display.
 * Useful for screen capture, region selection, and layout visualization.
 */
public class SwingDisplayDesk extends javax.swing.JFrame {

    /** The display being captured and displayed in this desk. */
    private final SwingDisplay display;
    /** The callback executed when the user accepts a selected region. */
    private final Consumer<Rectangle> onAccept;
    /** The screenshot image captured from the target display. */
    private final BufferedImage captured;
    /** The custom panel that handles rendering and mouse interaction for selection. */
    private final Editor editor;

    /**
     * Creates a new SwingDisplayDesk for a given display.
     *
     * @param display the display to select a region from
     * @param initial the initially selected region (in global screen coordinates), or null
     * @param onAccept callback to invoke when the user accepts a selection
     * @throws Exception if screen capture fails
     */
    public SwingDisplayDesk(SwingDisplay display, Rectangle initial, Consumer<Rectangle> onAccept) throws Exception {
        this.display = display;
        this.onAccept = onAccept;
        this.captured = display.capture();
        this.editor = new Editor(initial);
        initComponents();
        scrollDisplay.setViewportView(editor);
        setTitle(display.toString());
        WizGUI.initFrame(this);
    }

    /**
     * Shows the desk window.
     */
    public void showDesk() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    /**
     * Hides the desk window.
     */
    public void hideDesk() {
        SwingUtilities.invokeLater(() -> setVisible(false));
    }

    /**
     * Closes the desk window and disposes its resources.
     */
    public void closeDesk() {
        SwingUtilities.invokeLater(this::dispose);
    }

    /**
     * Programmatically accepts the current selection.
     */
    public void accept() {
        SwingUtilities.invokeLater(() -> buttonAcceptActionPerformed(null));
    }

    /**
     * Programmatically cancels the selection.
     */
    public void cancel() {
        SwingUtilities.invokeLater(() -> buttonCancelActionPerformed(null));
    }

    /**
     * Scrolls the display view to the top.
     */
    public void scrollUp() {
        SwingUtilities.invokeLater(() -> buttonUpActionPerformed(null));
    }

    /**
     * Scrolls the display view to the bottom.
     */
    public void scrollDown() {
        SwingUtilities.invokeLater(() -> buttonDownActionPerformed(null));
    }

    /**
     * Sets the currently selected region.
     *
     * @param selection the region to select, in global screen coordinates
     */
    public void setSelection(Rectangle selection) {
        SwingUtilities.invokeLater(() -> {
            editor.setSelection(selection);
            editor.repaint();
        });
    }

    /**
     * Gets the currently selected region.
     *
     * @return the selected region, in global screen coordinates
     */
    public Rectangle getSelection() {
        return editor.getSelected();
    }

    /** Button to confirm the current selection. */
    private javax.swing.JButton buttonAccept;
    /** Button to cancel the selection process. */
    private javax.swing.JButton buttonCancel;
    /** Button to scroll the display view down. */
    private javax.swing.JButton buttonDown;
    /** Button to scroll the display view up. */
    private javax.swing.JButton buttonUp;
    /** Scroll pane that contains the editor panel displaying the captured screenshot. */
    private javax.swing.JScrollPane scrollDisplay;

    /**
     * Initializes the GUI components for this frame.
     */
    private void initComponents() {

        scrollDisplay = new javax.swing.JScrollPane();
        buttonAccept = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();
        buttonUp = new javax.swing.JButton();
        buttonDown = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Display");
        setLocationByPlatform(true);

        buttonAccept.setMnemonic('A');
        buttonAccept.setText("Accept");
        buttonAccept.addActionListener(evt -> buttonAcceptActionPerformed(evt));

        buttonCancel.setMnemonic('C');
        buttonCancel.setText("Cancel");
        buttonCancel.addActionListener(evt -> buttonCancelActionPerformed(evt));

        buttonUp.setMnemonic('U');
        buttonUp.setText("Up");
        buttonUp.addActionListener(evt -> buttonUpActionPerformed(evt));

        buttonDown.setMnemonic('D');
        buttonDown.setText("Down");
        buttonDown.addActionListener(evt -> buttonDownActionPerformed(evt));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(buttonUp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonDown)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 288, Short.MAX_VALUE)
                        .addComponent(buttonAccept)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonCancel))
                    .addComponent(scrollDisplay))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCancel)
                    .addComponent(buttonAccept)
                    .addComponent(buttonUp)
                    .addComponent(buttonDown))
                .addContainerGap())
        );

        pack();
    }

    /**
     * Handles the action event for the Accept button.
     * Invokes the callback with the selected region and closes the window.
     *
     * @param evt the action event
     */
    private void buttonAcceptActionPerformed(java.awt.event.ActionEvent evt) {
        onAccept.accept(editor.getSelected());
        WizGUI.close(this);
    }

    /**
     * Handles the action event for the Cancel button.
     * Discards the selection and closes the window.
     *
     * @param evt the action event
     */
    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {
        WizGUI.close(this);
    }

    /**
     * Handles the action event for the Up button.
     * Scrolls the view to the top.
     *
     * @param evt the action event
     */
    private void buttonUpActionPerformed(java.awt.event.ActionEvent evt) {
        scrollDisplay.getVerticalScrollBar().setValue(0);
    }

    /**
     * Handles the action event for the Down button.
     * Scrolls the view to the bottom.
     *
     * @param evt the action event
     */
    private void buttonDownActionPerformed(java.awt.event.ActionEvent evt) {
        scrollDisplay.getVerticalScrollBar().setValue(scrollDisplay.getVerticalScrollBar().getMaximum());
    }

    /**
     * The internal panel responsible for rendering the display capture and handling
     * mouse interactions to draw the selection bounding box.
     */
    private class Editor extends JPanel {

        /** The top-left point of the selection relative to the captured image. */
        private Point pointTopLeft = null;
        /** The bottom-right point of the selection relative to the captured image. */
        private Point pointBottomRight = null;

        /**
         * Creates a new Editor panel.
         *
         * @param initial the initial selection region to display, or null
         */
        public Editor(Rectangle initial) {
            var dimension = new Dimension(captured.getWidth(), captured.getHeight());
            setSelection(initial);
            setPreferredSize(dimension);
            setMinimumSize(dimension);
            setMaximumSize(dimension);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        if (e.isControlDown()) {
                            pointTopLeft = e.getPoint();
                        } else if (e.isAltDown()) {
                            pointBottomRight = e.getPoint();
                        }
                        SwingUtilities.updateComponentTreeUI(scrollDisplay);
                    }
                }
            });
        }

        /**
         * Updates the current visual selection bounding box.
         *
         * @param rect the new selection region in global screen coordinates
         */
        public void setSelection(Rectangle rect) {
            if (rect != null) {
                var initialX = rect.x - display.getBounds().x;
                var initialY = rect.y - display.getBounds().y;
                pointTopLeft = new Point(initialX, initialY);
                pointBottomRight = new Point(initialX + rect.width, initialY + rect.height);
            } else {
                pointTopLeft = null;
                pointBottomRight = null;
            }
        }

        /**
         * Paints the captured display image and the selection points/box.
         *
         * @param g the Graphics context
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(captured, 0, 0, null);
            if (pointTopLeft != null) {
                g.setColor(Color.GREEN);
                g.fillRoundRect(pointTopLeft.x - 3, pointTopLeft.y - 3, 6, 6, 4, 4);
                g.setColor(Color.BLACK);
                g.drawRoundRect(pointTopLeft.x - 3, pointTopLeft.y - 3, 6, 6, 4, 4);
            }
            if (pointBottomRight != null) {
                g.setColor(Color.RED);
                g.fillRoundRect(pointBottomRight.x - 3, pointBottomRight.y - 3, 6, 6, 4, 4);
                g.setColor(Color.BLACK);
                g.drawRoundRect(pointBottomRight.x - 3, pointBottomRight.y - 3, 6, 6, 4, 4);
            }
        }

        /**
         * Calculates and returns the currently selected region.
         *
         * @return the selected Rectangle in global screen coordinates, or null if incomplete
         */
        public Rectangle getSelected() {
            if (pointTopLeft == null || pointBottomRight == null) {
                return null;
            }
            return new Rectangle(
                    display.getBounds().x + Math.min(pointTopLeft.x, pointBottomRight.x),
                    display.getBounds().y + Math.min(pointTopLeft.y, pointBottomRight.y),
                    Math.abs(pointBottomRight.x - pointTopLeft.x),
                    Math.abs(pointBottomRight.y - pointTopLeft.y)
            );
        }

    }

}
