/*
This code demonstrate racing condition solution using synchronized sections with a lock object (rather than itself)
 */
public class Main3_Solution3_Syncd_Lock_Object {
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
        private Object lock = new Object();

        public void increment() {
            synchronized (lock) {
                data++;
            }
        }

        public void decrement() {
            synchronized (lock) {
                data--;
            }
        }
    }
}