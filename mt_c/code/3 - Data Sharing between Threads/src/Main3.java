/*
This code demonstrate racing condition where the incrementing thread and decrementing thread are both accessing
non-atomic operation which leads to inconsistent state. With atomic operation (using synchronized section),
result will be consistent 0.
 */
public class Main3 {
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
    }

    private static class Inventory {
        private int data = 0;

        public void increment() {
        //public synchronized void increment() {
            data++;
        }

        public void decrement() {
        //public synchronized void decrement() {
            data--;
        }
    }
}