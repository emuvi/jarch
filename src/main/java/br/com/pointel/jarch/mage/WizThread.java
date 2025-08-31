package br.com.pointel.jarch.mage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WizThread {

    private static Logger log = LoggerFactory.getLogger(WizThread.class);

    @SafeVarargs
    public static <T> List<T> wait(Future<T>... futures) throws Exception {
        List<T> result = new ArrayList<>();
        for (Future<T> future : futures) {
            result.add(future.get());
        }
        return result;
    }

    public static void delay(int millis, Runnable runnable) {
        new Thread("WizLang Delay") {
            @Override
            public void run() {
                try {
                    Thread.sleep(millis);
                    runnable.run();
                } catch (Exception e) {
                    log.error("Could not delay", e);
                }
            }
        }.start();
    }

    @SafeVarargs
    public static <T> T getFirstNonNull(Future<T>... futures) throws Exception {
        while (true) {
            var anyWorking = false;
            for (Future<T> future : futures) {
                if (future.isDone()) {
                    if (future.get() != null) {
                        return future.get();
                    }
                } else {
                    anyWorking = true;
                }
            }
            if (!anyWorking) {
                break;
            }
        }
        return null;
    }

    private static ExecutorService executor;

    public static ExecutorService getExecutor() {
        if (executor == null) {
            executor = Executors.newCachedThreadPool();
        }
        return executor;
    }

    public static Future<?> submit(Runnable runnable) {
        return getExecutor().submit(runnable);
    }

    public static <T> Future<T> submit(Callable<T> callable) {
        return getExecutor().submit(callable);
    }
}
