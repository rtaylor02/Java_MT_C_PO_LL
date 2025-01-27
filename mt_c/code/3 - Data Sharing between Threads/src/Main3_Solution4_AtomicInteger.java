import java.util.concurrent.atomic.AtomicInteger;

/*
This code demonstrate racing condition solution sing AtomicInteger class. NOTE: the class provides operations equivalent
to ++ and --.
 */
public class Main3_Solution4_AtomicInteger {
    public static void main(String[] args) throws InterruptedException {
        Inventory inventory = new Inventory();

        Thread incrementingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    inventory.increment();
                }
            }
        });

        Thread decrementingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    inventory.decrement();
                }
            }
        });

        incrementingThread.start();
        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();

        System.out.println(String.format("inventory.data = %d", inventory.data.intValue()));
    }

    private static class Inventory {
        private AtomicInteger data = new AtomicInteger(0);

        public void increment() {
            data.incrementAndGet();
        }

        public void decrement() {
            data.decrementAndGet();
        }
    }
}