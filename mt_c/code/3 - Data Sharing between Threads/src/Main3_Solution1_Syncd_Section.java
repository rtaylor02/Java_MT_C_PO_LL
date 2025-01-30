/*
This code demonstrate solution 1 to a racing condition using synchronized sections
 */
public class Main3_Solution1_Syncd_Section {
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

        public synchronized void increment() {
            data++;
        }

        public synchronized void decrement() {
            data--;
        }
    }
}