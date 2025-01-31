import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main4_Problem_Solution_Syncd_Object_lock {
    public static void main(String[] args) {
        Object lock = new Object();
        AtomicBoolean isGreenLight = new AtomicBoolean(false);

        // ExecutorService is best within try-with-resource
        try (ExecutorService executorService = Executors.newFixedThreadPool(2);) {
            executorService.submit(getDriver(lock, isGreenLight));
            executorService.submit(getGreenLight(lock, isGreenLight));

            executorService.awaitTermination(0, TimeUnit.MILLISECONDS);

        } catch (InterruptedException e) {
        }

    }

    private static Runnable getGreenLight(Object lock, AtomicBoolean isGreenLight) {
        Runnable greenLight = () -> {
            Thread.currentThread().setName("GreenLight");

            // Whenever you use Object lock, use 'synchronized'!
            synchronized (lock) {
                try {
                    TimeUnit.MILLISECONDS.sleep(4000);

                    isGreenLight.set(true);
                    System.out.printf("%s: GREEN!!%n", Thread.currentThread().getName());

                    lock.notify(); // Free waiting thread with the same Object lock
                    while (true) {

                    }
                } catch (InterruptedException e) {
                }
            }
        };
        return greenLight;
    }

    private static Runnable getDriver(Object lock, AtomicBoolean isGreenLight) {
        Runnable driver = () -> {
            Thread.currentThread().setName("Driver");

            // Whenever you use Object lock, use 'synchronized'!
            synchronized (lock) {
                while (!isGreenLight.get()) {
                    try {
                        System.out.printf("%s: waiting for green light...%n", Thread.currentThread().getName());
                        lock.wait(); // Wait to be notified
                    } catch (InterruptedException e) {
                    }
                }
            }
            System.out.printf("%s: VROOOOOM....%n", Thread.currentThread().getName());
        };
        return driver;
    }
}
