package com.vidlus.jarch.desk;

import javax.swing.SwingUtilities;

import com.vidlus.jarch.mage.WizGUI;

/**
 * The graphical user interface (GUI) component for the RunChase process.
 * Provides a window with a progress bar, status text area, and control buttons
 * to pause, resume, or stop the background task.
 */
public class SwingChaseDesk extends javax.swing.JFrame {

    private SwingChase chase;
    
    /**
     * Creates a new RunChaseDesk with the specified title.
     * Initializes components and applies standard GUI configurations.
     *
     * @param title the title to be displayed on the window frame
     */
    SwingChaseDesk(String title) {
        initComponents();
        setTitle(title + " - RunChase");
        initFrame();
    }
    
    /**
     * Initializes the frame settings such as position and escaper behavior.
     */
    private void initFrame() {
        WizGUI.initFrame(this);
        WizGUI.initEscaper(this);
    }
    
    /**
     * Sets the underlying RunChase controller logic instance.
     *
     * @param chase the RunChase controller instance
     */
    void setChase(SwingChase chase) {
        this.chase = chase;
    }
    
    /**
     * Updates the UI state to reflect a paused process.
     * Changes the pause/resume button text to "Resume".
     */
    void setPaused() {
        SwingUtilities.invokeLater(() -> {
            buttonPauseResume.setText("Resume");
        });
    }
    
    /**
     * Updates the UI state to reflect a resumed (running) process.
     * Changes the pause/resume button text to "Pause".
     */
    void setResumed() {
        SwingUtilities.invokeLater(() -> {
            buttonPauseResume.setText("Pause");
        });
    }
    
    /**
     * Updates the UI state to reflect a stopped process.
     * Disables the control buttons and sets the stop button text to "Stopped".
     */
    void setStopped() {
        SwingUtilities.invokeLater(() -> {
            buttonPauseResume.setEnabled(false);
            buttonStop.setEnabled(false);
            buttonStop.setText("Stopped");
        });
    }
    
    /**
     * Updates the UI state to reflect a finished process.
     * Disables the control buttons, sets the pause/resume button to "Done",
     * and fills the progress bar.
     */
    void setFinished() {
        SwingUtilities.invokeLater(() -> {
            buttonPauseResume.setEnabled(false);
            buttonStop.setEnabled(false);
            buttonPauseResume.setText("Done");
            viewProgress.setValue(viewProgress.getMaximum());
        });
    }
    
    /**
     * Sets the maximum size (total work) of the progress bar.
     *
     * @param size the maximum progress value
     */
    void setProgressSize(int size) {
        SwingUtilities.invokeLater(() -> {
            viewProgress.setMaximum(size);
        });
    }
    
    /**
     * Sets the current completed amount on the progress bar.
     *
     * @param done the current progress value
     */
    void setProgressDone(int done) {
        SwingUtilities.invokeLater(() -> {
            viewProgress.setValue(done);
        });
    }
    
    /**
     * Appends an informational message to the status text area and scrolls to the bottom.
     *
     * @param info the message to append
     */
    void putInfo(String info) {
        SwingUtilities.invokeLater(() -> {
            fieldStatus.append(info);
            fieldStatus.append("\n");
            fieldStatus.setCaretPosition(fieldStatus.getDocument().getLength());
        });    
    }
    
    /**
     * Clears all text currently displayed in the status text area.
     */
    void clearInfo() {
        SwingUtilities.invokeLater(() -> {
            fieldStatus.setText("");
        });
    }
    
    /**
     * Sets whether the progress bar is in indeterminate mode.
     *
     * @param indeterminate true to set indeterminate mode, false otherwise
     */
    void setProgressIndeterminate(boolean indeterminate) {
        SwingUtilities.invokeLater(() -> {
            viewProgress.setIndeterminate(indeterminate);
        });
    }
    
    /**
     * Sets whether the progress bar should render a string over the bar.
     *
     * @param painted true to paint the string, false otherwise
     */
    void setProgressStringPainted(boolean painted) {
        SwingUtilities.invokeLater(() -> {
            viewProgress.setStringPainted(painted);
        });
    }
    
    /**
     * Sets the custom text string to display on the progress bar.
     *
     * @param string the custom text string
     */
    void setProgressString(String string) {
        SwingUtilities.invokeLater(() -> {
            viewProgress.setString(string);
        });
    }
    
    /**
     * Updates the main title text of the desk window.
     *
     * @param title the new title text
     */
    void setTitleText(String title) {
        SwingUtilities.invokeLater(() -> {
            setTitle(title);
        });
    }
    
    /**
     * Makes the desk window visible.
     */
    void showDesk() {
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
        });
    }
    
    /**
     * Hides the desk window from the screen.
     */
    void hideDesk() {
        SwingUtilities.invokeLater(() -> {
            setVisible(false);
        });
    }
    
    /**
     * Disposes the desk window, freeing its resources.
     */
    void closeDesk() {
        SwingUtilities.invokeLater(() -> {
            dispose();
        });
    }

    /**
     * Retrieves the entire text currently displayed in the status area.
     *
     * @return the status text content
     */
    String getInfo() {
        return fieldStatus.getText();
    }

    /**
     * Sets the enabled state of the Stop button.
     *
     * @param enabled true to enable, false to disable
     */
    void setStopEnabled(boolean enabled) {
        SwingUtilities.invokeLater(() -> {
            buttonStop.setEnabled(enabled);
        });
    }

    /**
     * Sets the enabled state of the Pause/Resume button.
     *
     * @param enabled true to enable, false to disable
     */
    void setPauseResumeEnabled(boolean enabled) {
        SwingUtilities.invokeLater(() -> {
            buttonPauseResume.setEnabled(enabled);
        });
    }

    private javax.swing.JButton buttonPauseResume;
    private javax.swing.JButton buttonStop;
    private javax.swing.JTextArea fieldStatus;
    private javax.swing.JScrollPane scrollStatus;
    private javax.swing.JProgressBar viewProgress;

    /**
     * Initializes the GUI components of the frame including layout and event bindings.
     */
    private void initComponents() {

        buttonPauseResume = new javax.swing.JButton();
        buttonStop = new javax.swing.JButton();
        scrollStatus = new javax.swing.JScrollPane();
        fieldStatus = new javax.swing.JTextArea();
        viewProgress = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("RunChase");

        buttonPauseResume.setText("Pause");
        buttonPauseResume.addActionListener(evt -> chase.toggle());

        buttonStop.setText("Stop");
        buttonStop.addActionListener(evt -> chase.stop());

        fieldStatus.setEditable(false);
        fieldStatus.setColumns(20);
        fieldStatus.setRows(5);
        scrollStatus.setViewportView(fieldStatus);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonPauseResume)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonStop)
                        .addGap(18, 18, 18)
                        .addComponent(viewProgress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(buttonPauseResume)
                        .addComponent(buttonStop))
                    .addComponent(viewProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scrollStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }
}
