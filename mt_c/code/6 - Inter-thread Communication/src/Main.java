import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Inter_Thread_Comms_With_Semaphore.main();
        Inter_Thread_Comms_With_Condition.main();
        Inter_Thread_Comms_With_Object.main();
    }

    private static class Inter_Thread_Comms_With_Semaphore {
        private static Semaphore fullSemaphore = new Semaphore(0);
        private static Semaphore emptySemaphore = new Semaphore(1);
        private static Queue<String> queue = new LinkedList<>();

        public static void main(String... args) {
            Producer producer = new Producer();
            Consumer consumer = new Consumer();

            producer.start();
            consumer.start();
        }

        private static class Producer extends Thread {
            private String[] message = {"Hello", "this", "is", "cool"};

            @Override
            public void run() {
                try {
                    emptySemaphore.acquire();
                    for (String s : message) {
                        queue.offer(s);
                    }
                    fullSemaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private static class Consumer extends Thread {

            @Override
            public void run() {
                try {
                    fullSemaphore.acquire();
                    int totalMessages = queue.size();
                    for (int i = 0; i < totalMessages; i++) {
                        System.out.printf("%s: %s%n", Thread.currentThread().getName(), queue.poll());
                    }
                    emptySemaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Inter_Thread_Comms_With_Condition {
        public static void main(String... args) {

        }
    }

    private static class Inter_Thread_Comms_With_Object {
        public static void main(String... args) {

        }
    }
}