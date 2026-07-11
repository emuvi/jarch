package com.vidlus.jarch.desk;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.vidlus.jarch.mage.WizThread;

/**
 * A controller class that manages the execution flow of a background task
 * and its associated progress UI window ({@link RunChaseDesk}).
 * It provides methods to track progress, handle pause/resume logic, stop gracefully,
 * and report status information or errors.
 *
 * @author emuvi
 */
public class RunChase {
    
    /**
     * Initializes a new RunChase progress window and its controller.
     *
     * @param title the title for the progress window
     * @return a configured instance of RunChase bound to the new window
     */
    public static RunChase init(String title) {
        var desk = new RunChaseDesk(title);
        var chase = new RunChase(desk);
        desk.setChase(chase);
        desk.setVisible(true);
        return chase;
    }
    
    private final RunChaseDesk desk;
    
    private RunChase(RunChaseDesk desk) {
        this.desk = desk;
    }
    
    private final AtomicBoolean paused = new AtomicBoolean(false);
    private final AtomicBoolean stopped = new AtomicBoolean(false);
    private final AtomicBoolean finished = new AtomicBoolean(false);
    private final AtomicInteger progressSize = new AtomicInteger(100);
    private final AtomicInteger progressDone = new AtomicInteger(0);
    
    /**
     * Flags the process as paused and updates the UI accordingly.
     */
    public void pause() {
        paused.set(true);
        desk.setPaused();
    }
    
    /**
     * Flags the process as resumed and updates the UI accordingly.
     */
    public void resume() {
        paused.set(false);
        desk.setResumed();
    }
    
    /**
     * Toggles the current pause/resume state and updates the UI.
     */
    public void toggle() {
        synchronized (paused) {
            var actual = !paused.get();
            paused.set(actual);
            if (actual) {
                desk.setPaused();
            } else {
                desk.setResumed();
            }
        }
    }
    
    /**
     * Flags the process as stopped and updates the UI to disable further controls.
     */
    public void stop() {
        stopped.set(true);
        desk.setStopped();
    }
    
    /**
     * Increments the current progress by 1 and updates the UI.
     */
    public void advance() {
        var done = progressDone.incrementAndGet();
        desk.setProgressDone(done);
    }
    
    /**
     * Increments the progress by 1, updates the UI, and blocks the current thread if paused.
     */
    public void advanceWaitOnPause() {
        advance();
        waitOnPause();
    }
    
    /**
     * Increments the progress by 1, updates the UI, blocks if paused,
     * and throws an exception if the process was stopped.
     *
     * @throws Exception if the process is marked as stopped
     */
    public void advanceWaitOnPauseThrowOnStop() throws Exception {
        advance();
        waitOnPauseThrowOnStop();
    }
    
    /**
     * Flags the process as finished and updates the UI to indicate completion.
     */
    public void finish() {
        finished.set(true);
        desk.setFinished();
    }
    
    /**
     * Checks whether the process is currently paused.
     *
     * @return true if paused, false otherwise
     */
    public boolean isPaused() {
        return paused.get();
    }
    
    /**
     * Checks whether the process is currently stopped.
     *
     * @return true if stopped, false otherwise
     */
    public boolean isStopped() {
        return stopped.get();
    }
    
    /**
     * Sets the maximum expected progress size.
     *
     * @param size the maximum progress
     */
    public void setProgressSize(int size) {
        progressSize.set(size);
        desk.setProgressSize(size);
    }
    
    /**
     * Sets the current completed progress amount.
     *
     * @param done the completed progress
     */
    public void setProgressDone(int done) {
        progressDone.set(done);
        desk.setProgressDone(done);
    }
    
    /**
     * Gets the current maximum expected progress size.
     *
     * @return the maximum progress
     */
    public int getProgressSize() {
        return progressSize.get();
    }
    
    /**
     * Gets the current completed progress amount.
     *
     * @return the completed progress
     */
    public int getProgressDone() {
        return progressDone.get();
    }
    
    /**
     * Blocks the executing thread indefinitely while the process is in a paused state.
     */
    public void waitOnPause() {
        while (paused.get()) {
            WizThread.sleep(10);
        }
    }
    
    /**
     * Blocks the executing thread while paused, but interrupts and throws if stopped.
     *
     * @throws Exception if the process is marked as stopped
     */
    public void waitOnPauseThrowOnStop() throws Exception {
        while (paused.get()) {
            WizThread.sleep(10);
            if (stopped.get()) {
                throw new Exception("Process stopped.");
            }
        }
    }
    
    /**
     * Logs the specified information to the UI and then increments the progress by 1.
     *
     * @param info the message to log
     */
    public void putInfoAndAdvance(String info) {
        putInfo(info);
        advance();
    }
    
    /**
     * Formats and logs a message to both standard output and the UI text area.
     *
     * @param info the format string
     * @param args the format arguments
     */
    public void putInfo(String info, Object... args) {
        var message = String.format(info, args);
        System.out.println(message);
        desk.putInfo(message);
    }
    
    /**
     * Logs the stack trace of an exception to the UI text area.
     *
     * @param error the throwable to log
     */
    public void putError(Throwable error) {
        var sw = new StringWriter();
        var pw = new PrintWriter(sw);
        error.printStackTrace(pw);
        putInfo(pw.toString());
    }
    
    /** Clears all text currently displayed in the status text area. */
    public void clearInfo() {
        desk.clearInfo();
    }
    
    /** Sets whether the progress bar is in indeterminate mode. */
    public void setProgressIndeterminate(boolean indeterminate) {
        desk.setProgressIndeterminate(indeterminate);
    }
    
    /** Sets whether the progress bar should render a string over the bar. */
    public void setProgressStringPainted(boolean painted) {
        desk.setProgressStringPainted(painted);
    }
    
    /** Sets the custom text string to display on the progress bar. */
    public void setProgressString(String string) {
        desk.setProgressString(string);
    }
    
    /** Updates the main title text of the desk window. */
    public void setTitleText(String title) {
        desk.setTitleText(title);
    }
    
    /** Makes the desk window visible. */
    public void showDesk() {
        desk.showDesk();
    }
    
    /** Hides the desk window from the screen. */
    public void hideDesk() {
        desk.hideDesk();
    }
    
    /** Disposes the desk window, freeing its resources. */
    public void closeDesk() {
        desk.closeDesk();
    }
    
    /** Retrieves the entire text currently displayed in the status area. */
    public String getInfo() {
        return desk.getInfo();
    }
    
    /** Checks whether the process has been finished. */
    public boolean isFinished() {
        return finished.get();
    }
    
    /**
     * Throws an exception if the process is marked as stopped.
     *
     * @throws Exception if stopped
     */
    public void throwOnStop() throws Exception {
        if (stopped.get()) {
            throw new Exception("Process stopped.");
        }
    }
    
    /**
     * Throws an exception with a custom message if the process is marked as stopped.
     *
     * @param message the custom exception message
     * @throws Exception if stopped
     */
    public void throwOnStop(String message) throws Exception {
        if (stopped.get()) {
            throw new Exception(message);
        }
    }
    
    /**
     * Increments the current progress by the specified number of steps.
     *
     * @param steps the amount to add to the progress
     */
    public void advance(int steps) {
        var done = progressDone.addAndGet(steps);
        desk.setProgressDone(done);
    }
    
    /**
     * Updates both the completed and maximum size of the progress bar atomically.
     *
     * @param done the current progress value
     * @param size the maximum progress value
     */
    public void setProgress(int done, int size) {
        progressSize.set(size);
        progressDone.set(done);
        desk.setProgressSize(size);
        desk.setProgressDone(done);
    }
    
    /**
     * Logs the formatted message and increments the progress by 1.
     *
     * @param info the format string
     * @param args the format arguments
     */
    public void putInfoAndAdvance(String info, Object... args) {
        putInfo(info, args);
        advance();
    }
    
    /**
     * Logs the stack trace of an error and immediately stops the process.
     *
     * @param error the throwable to log
     */
    public void putErrorAndStop(Throwable error) {
        putError(error);
        stop();
    }

    /** Manually enables or disables the stop button. */
    public void setStopEnabled(boolean enabled) {
        desk.setStopEnabled(enabled);
    }
    
    /** Manually enables or disables the pause/resume button. */
    public void setPauseResumeEnabled(boolean enabled) {
        desk.setPauseResumeEnabled(enabled);
    }
    
}
