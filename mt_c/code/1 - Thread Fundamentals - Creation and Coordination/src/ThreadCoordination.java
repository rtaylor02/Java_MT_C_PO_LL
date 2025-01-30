import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
2 ways to coordinate threads:
1. By threadObject.interrupt()
2. By threadObject.join()
 */
public class ThreadCoordination {
    public static void main(String[] args) {
        // Using interrupt to coordinate a thread
        //ByInterrupt.main();

        // Using join to coordinate threads
        //ByJoin.main();

        // Using Object's wait() and notify()/notifyAll()
        ByWaitAndNotify.main();
    }

    private static Runnable getRunnable(final long sleepTime) {
        Runnable task = () -> {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println(String.format("%s: %d", Thread.currentThread().getName(), i));
                    TimeUnit.MILLISECONDS.sleep(sleepTime);
                }
            } catch (InterruptedException ie) {
                System.out.println(String.format("%s is interrupted!", Thread.currentThread().getName()));
                System.out.println(String.format("%s is having graceful termination...", Thread.currentThread().getName()));
            }
            System.out.println(String.format("%s: finished", Thread.currentThread().getName()));
        };
        return task;
    }

    // Using interrupt to coordinate a thread
    private static class ByInterrupt {
        private static void main() {
            // 1st way: by catching InterruptedException
            //byCatchingIE();

            // 2nd way: via flag method isInterrupted()
            byManualDetection();
        }

        private static void byManualDetection() {
            Runnable task = () -> {
                System.out.format("%s: getting inside infinite loop...%n", Thread.currentThread().getName());
                while (true) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.format("%s: interruption detected%n", Thread.currentThread().getName());
                        break;
                    }
                }
                System.out.printf("%s: broke out of infinite loop for graceful escape%n%n", Thread.currentThread().getName());
            };

            Thread t1 = Thread.ofPlatform().name("T1").start(task);
            Thread t2 = Thread.ofPlatform().name("T2").start(task);

            t1.interrupt(); // NOTE: t2 is still running
            System.out.format("%s: ending.. NOTE: t2 is still running%n", Thread.currentThread().getName());
        }

        private static void byCatchingIE() {
            Runnable task = getRunnable(500);

            Thread t1 = Thread.ofPlatform().name("T1").start(task);
            Thread t2 = Thread.ofPlatform().name("T2").start(task);

            try {
                Thread.sleep(1500);
                t1.interrupt(); // Interrupt t1 but not t2
                System.out.println(String.format("%s: finished", Thread.currentThread().getName()));
            } catch (InterruptedException e) {
                System.out.println(String.format("%s: oh no..", Thread.currentThread().getName()));
            }
        }

        private static void sample2() {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(String.format("%s: interruption detected", Thread.currentThread().getName()));
                    break;
                }
            }
        }
    }

    // Using join to coordinate threads
    private static class ByJoin {
        private static void main() {
            System.out.println(String.format("%s: starting...", Thread.currentThread().getName()));

            Thread t1 = Thread.ofPlatform().name("T1").start(getRunnable(150));
            Thread t2 = Thread.ofPlatform().name("T2").start(getRunnable(500));

            try {
                System.out.printf("%s: waiting for all threads%n", Thread.currentThread().getName());
                t1.join();
                t2.join();
                System.out.println(String.format("%s: finally!", Thread.currentThread().getName()));
            } catch (InterruptedException e) {
                System.out.println(String.format("%s: oh no..", Thread.currentThread().getName()));
            }
        }
    }

    private static class ByWaitAndNotify {
        public static void main(String... args) {


        }
    }
}
