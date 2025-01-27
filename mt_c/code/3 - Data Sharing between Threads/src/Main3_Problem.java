/*
This code demonstrate racing condition where the incrementing thread and decrementing thread are both accessing
non-atomic operation which leads to inconsistent state. With atomic operation (using synchronized section),
result will be consistent 0. To make the operation atomic:
1. use 'synchronized' keyword on method increment() and decrement, or
2. use AtomicInteger instead of int for inventory.data
 */
public class Main3_Problem {
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

        System.out.println(String.format("inventory.data = %d", inventory.data));
        //System.out.println(String.format("inventory.data = %d", inventory.data.intValue()));
    }

    private static class Inventory {
        //private AtomicInteger data = new AtomicInteger(0);
        private int data = 0;

        public void increment() {
        //public synchronized void increment() {
            data++;
            //data.incrementAndGet();
        }

        public void decrement() {
        //public synchronized void decrement() {
            data--;
            //data.decrementAndGet();
        }
    }
}