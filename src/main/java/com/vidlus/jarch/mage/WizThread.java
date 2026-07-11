package com.vidlus.jarch.mage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A utility class providing common thread and concurrency management tools.
 * It provides a globally accessible cached thread pool executor, and includes
 * helper methods to simplify task execution, thread sleeping, and Future management.
 */
public class WizThread {

    private static Logger log = LoggerFactory.getLogger(WizThread.class);
    
    private static ExecutorService executor;

    private WizThread() {}

    /**
     * Puts the current thread to sleep for the specified duration.
     * Safely catches and logs InterruptedException, preserving the interrupt flag.
     *
     * @param millis the number of milliseconds to sleep
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Thread sleep interrupted", e);
        } catch(Exception e) {
            log.error("Unexpected error during sleep", e);
        }
    }

    /**
     * Attempts to resolve the system's primary "main" thread by inspecting active stack traces.
     *
     * @return the main Thread object, or the first available thread if "main" is not found.
     */
    public static Thread getMainThread() {
        var threads = Thread.getAllStackTraces().keySet();
        for (Thread thread : threads) {
            if ("main".equals(thread.getName())) {
                return thread;
            }
        }
        return threads.stream().findFirst().orElse(null);
    }

    /**
     * Instantiates and immediately starts a new background daemon thread.
     * Daemon threads do not prevent the JVM from exiting when the program finishes.
     *
     * @param task the Runnable task to execute
     * @param name the name of the new thread for debugging purposes
     * @return the started Thread instance
     */
    public static Thread startDaemon(Runnable task, String name) {
        var result = new Thread(task, name);
        result.setDaemon(true);
        result.start();
        return result;
    }

    /**
     * Waits for a sequence of Future tasks to fully complete and returns their results.
     * Blocks the current thread until all tasks are finished.
     *
     * @param <T>     the return type of the Futures
     * @param futures a varargs array of pending Future objects
     * @return a List containing the results in the order they were passed
     * @throws Exception if any of the Futures encounter an execution error or are interrupted
     */
    @SafeVarargs
    public static <T> List<T> wait(Future<T>... futures) throws Exception {
        List<T> result = new ArrayList<>();
        if (futures == null) return result;
        for (Future<T> future : futures) {
            result.add(future.get()); // Blocks until each completes
        }
        return result;
    }

    /**
     * Asynchronously executes a Runnable task after a specific time delay.
     * The delay and execution run on a separate, dedicated thread.
     *
     * @param millis   the number of milliseconds to wait before executing
     * @param runnable the task to execute
     */
    public static void delay(int millis, Runnable runnable) {
        new Thread("WizThread Delay") {
            @Override
            public void run() {
                try {
                    Thread.sleep(millis);
                    if (runnable != null) {
                        runnable.run();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error("Delay interrupted", e);
                } catch (Exception e) {
                    log.error("Could not delay task execution", e);
                }
            }
        }.start();
    }

    /**
     * Actively polls a collection of Future tasks and returns the first non-null result it finds.
     * If all tasks complete but return null, this method also returns null.
     *
     * @param <T>     the return type of the Futures
     * @param futures a varargs array of pending Future objects
     * @return the first successfully evaluated non-null result, or null if none exist
     * @throws Exception if fetching a completed Future throws an exception
     */
    @SafeVarargs
    public static <T> T getFirstNonNull(Future<T>... futures) throws Exception {
        if (futures == null || futures.length == 0) return null;
        while (true) {
            var anyWorking = false;
            for (Future<T> future : futures) {
                if (future.isDone()) {
                    var result = future.get();
                    if (result != null) {
                        return result;
                    }
                } else {
                    anyWorking = true;
                }
            }
            if (!anyWorking) {
                break;
            }
            // Small sleep to prevent aggressive CPU spinning
            sleep(10);
        }
        return null;
    }

    /**
     * Retrieves a globally accessible, lazily initialized cached thread pool.
     * Cached thread pools automatically create new threads as needed, but will reuse 
     * previously constructed threads when they are available.
     *
     * @return the shared ExecutorService instance
     */
    public static ExecutorService getExecutor() {
        if (executor == null) {
            executor = Executors.newCachedThreadPool();
        }
        return executor;
    }

    /**
     * Submits a Runnable task to the global ExecutorService for background processing.
     *
     * @param runnable the task to execute
     * @return a Future representing the pending completion of the task
     */
    public static Future<?> submit(Runnable runnable) {
        return getExecutor().submit(runnable);
    }

    /**
     * Submits a value-returning Callable task to the global ExecutorService for background processing.
     *
     * @param <T>      the type of the task's result
     * @param callable the task to execute
     * @return a Future representing the pending completion of the task
     */
    public static <T> Future<T> submit(Callable<T> callable) {
        return getExecutor().submit(callable);
    }
    
    /**
     * Closes an AutoCloseable resource asynchronously on a separate thread to prevent 
     * blocking the current thread during teardown.
     *
     * @param closeable the resource to close
     */
    public static void closeAside(AutoCloseable closeable) {
        if (closeable == null) return;
        new Thread(() -> {
            try {
                closeable.close();
            } catch (Exception ex) {
                log.error("Error asynchronously closing resource", ex);
            }
        }, "WizThread Async Closer").start();
    }

}
