package br.com.pointel.jarch.gear;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import br.com.pointel.jarch.mage.WizThread;

/**
 *
 * @author emuvi
 */
public class RunChase {
    
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
    
    public void pause() {
        paused.set(true);
        desk.setPaused();
    }
    
    public void resume() {
        paused.set(false);
        desk.setResumed();
    }
    
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
    
    public void stop() {
        stopped.set(true);
        desk.setStopped();
    }
    
    public void advance() {
        var done = progressDone.incrementAndGet();
        desk.setProgressDone(done);
    }
    
    public void advanceWaitOnPause() {
        advance();
        waitOnPause();
    }
    
    public void advanceWaitOnPauseThrowOnStop() throws Exception {
        advance();
        waitOnPauseThrowOnStop();
    }
    
    public void finish() {
        finished.set(true);
        desk.setFinished();
    }
    
    public boolean isPaused() {
        return paused.get();
    }
    
    public boolean isStopped() {
        return stopped.get();
    }
    
    public void setProgressSize(int size) {
        progressSize.set(size);
        desk.setProgressSize(size);
    }
    
    public void setProgressDone(int done) {
        progressDone.set(done);
        desk.setProgressDone(done);
    }
    
    public int getProgressSize() {
        return progressSize.get();
    }
    
    public int getProgressDone() {
        return progressDone.get();
    }
    
    public void waitOnPause() {
        while (paused.get()) {
            WizThread.sleep(10);
        }
    }
    
    public void waitOnPauseThrowOnStop() throws Exception {
        while (paused.get()) {
            WizThread.sleep(10);
            if (stopped.get()) {
                throw new Exception("Process stopped.");
            }
        }
    }
    
    public void putInfoAndAdvance(String info) {
        putInfo(info);
        advance();
    }
    
    public void putInfo(String info, Object... args) {
        var message = String.format(info, args);
        System.out.println(message);
        desk.putInfo(message);
    }
    
    public void putError(Throwable error) {
        var sw = new StringWriter();
        var pw = new PrintWriter(sw);
        error.printStackTrace(pw);
        putInfo(pw.toString());
    }
    
}
