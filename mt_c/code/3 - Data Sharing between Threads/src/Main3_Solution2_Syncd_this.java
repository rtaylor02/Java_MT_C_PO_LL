/*
This code demonstrate racing condition solution using synchronized section on object (this).
 */
public class Main3_Solution2_Syncd_this {
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
            // Protect critical section using this object
            synchronized (this) {
                data++;
            }
        }

        public void decrement() {
            // Protect critical section using this object
            synchronized (this) {
                data--;
            }
        }
    }
}