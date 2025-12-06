package main.model.threads;

public class ThreadUtils {

    public static void safeSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
            System.out.println("Thread interrupted safely.");
            Thread.currentThread().interrupt();
        }
    }

    public static void stopThread(Thread t) {
        if (t != null && t.isAlive()) {
            t.interrupt();
        }
    }
}
