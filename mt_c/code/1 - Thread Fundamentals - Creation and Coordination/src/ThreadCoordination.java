import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/*
2 ways to coordinate threads:
1. By threadObject.interrupt()
2. By threadObject.join()
 */
public class ThreadCoordination {
    public static void main(String[] args) {
        // Using interrupt to coordinate a thread
        ByInterrupt.main();

        // Using join to coordinate threads
        //ByJoin.main();
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
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    System.out.println(String.format("%s: getting inside infinite loop...", Thread.currentThread().getName()));
                    while (true) {
                        if (Thread.currentThread().isInterrupted()) {
                            System.out.println(String.format("%s: interruption detected", Thread.currentThread().getName()));
                            break;
                        }
                    }
                    System.out.println(String.format("%s: broke out of infinite loop for graceful escape", Thread.currentThread().getName()));
                }
            };

            Thread t1 = Thread.ofPlatform().name("T1").start(task);
            Thread t2 = Thread.ofPlatform().name("T2").start(task);

            t1.interrupt(); // NOTE: t2 is still running
            System.out.println(String.format("%s: ending.. NOTE: t2 is still running", Thread.currentThread().getName()));
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
                System.out.println(String.format("%s: waiting for all threads", Thread.currentThread().getName()));
                t1.join();
                t2.join();
                System.out.println(String.format("%s: finally!", Thread.currentThread().getName()));
            } catch (InterruptedException e) {
                System.out.println(String.format("%s: oh no..", Thread.currentThread().getName()));
            }
        }
    }

    private static Runnable getRunnable(final long sleepTime) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        System.out.println(String.format("%s: %d", Thread.currentThread().getName(), i));
                        Thread.sleep(sleepTime);
                    }
                } catch (InterruptedException ie) {
                    System.out.println(String.format("%s is interrupted!", Thread.currentThread().getName()));
                    System.out.println(String.format("%s is having graceful termination...", Thread.currentThread().getName()));
                }
                System.out.println(String.format("%s: finished", Thread.currentThread().getName()));
            }
        };
        return task;
    }
}
