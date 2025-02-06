import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        //Inter_Thread_Comms_With_Semaphore.main();

        Inter_Thread_Comms_With_Condition.main();

        //Inter_Thread_Comms_With_Object.main();
    }

    private static class Inter_Thread_Comms_With_Semaphore {
        public static void main(String... args) {
            // Sample of using Semaphore to create sequence of task executions among threads
            //SimpleProducerConsumer.main();

            // Sample of using Semaphore to coordinate tasks completion among threads
            SemaphoreAsBarrier.main();
        }

        private static class SimpleProducerConsumer {
            private static Semaphore fullSemaphore = new Semaphore(0);
            private static Semaphore emptySemaphore = new Semaphore(1); // Allowing initiation of filling the queue by Producer
            private static Queue<String> queue = new LinkedList<>();

            public static void main(String... args) {
                Producer producer = new Producer();
                Consumer consumer = new Consumer();

                producer.start();
                consumer.start();
            }

            private static class Producer extends Thread {
                private final String[] message = {"Hello", "this", "is", "cool"};

                @Override
                public void run() {
                    try {
                        emptySemaphore.acquire(); // will block if no semaphore (permit) is available
                        for (String s : message) {
                            queue.offer(s);
                        }
                        System.out.printf("%s: finished loading messages...%n", Thread.currentThread().getName());
                        fullSemaphore.release(); // Allow consumer to take messages
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            private static class Consumer extends Thread {
                @Override
                public void run() {
                    try {
                        fullSemaphore.acquire(); // Blocks on initial run as no permit is released until Producer fills the queue.
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

        private static class SemaphoreAsBarrier {
            public static final int TOTAL_WORKERS = 4;

            public static void main(String... args) {
                List<Thread> threads = new ArrayList<>();
                Task sharedTask = new Task();
                threads.add(new Thread(sharedTask));
                threads.add(new Thread(sharedTask));
                threads.add(new Thread(sharedTask));
                threads.add(new Thread(sharedTask));

                for (Thread t : threads) {
                    t.start();
                }
            }

            private static class Task implements Runnable {
                public static final int TOTAL_WORKERS = 4;
                private Barrier barrier = new Barrier(TOTAL_WORKERS);

                @Override
                public void run() {
                    System.out.printf("%s: completed task-1%n", Thread.currentThread().getName());

                    barrier.waitForOthers();

                    System.out.printf("%s: completed task-2%n", Thread.currentThread().getName());
                }
            }

            private static class Barrier {
                private int totalPermits;
                private int counter;
                private Lock lock = new ReentrantLock();
                private Semaphore barrier = new Semaphore(0);

                public Barrier(int totalPermits) {
                    this.totalPermits = totalPermits;
                }

                public void waitForOthers() {
                    boolean allThreadsExecutingThisSection = false;

                    lock.lock();
                    try {
                        counter++;

                        if (counter == totalPermits) {
                            allThreadsExecutingThisSection = true;
                        }
                    } finally {
                        lock.unlock();
                    }

                    if (allThreadsExecutingThisSection) {
                        System.out.printf("%s: releasing permits...%n", Thread.currentThread().getName());
                        barrier.release(counter - 1);
                    } else {
                        try {
                            System.out.printf("%s: waiting for permit...%n", Thread.currentThread().getName());
                            barrier.acquire(); // All threads are waiting here until the last thread releases all permits.
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private static class Inter_Thread_Comms_With_Condition {
        private static Database db;

        private record Database(String name, String idNo) { }

        public static void main(String... args) {
            final Lock lock = new ReentrantLock();
            final Condition detailsFilled = lock.newCondition();

            UserInputHandler userInputHandler = new UserInputHandler(lock, detailsFilled);
            DatabaseProcessor databaseProcessor = new DatabaseProcessor(lock, detailsFilled);

            ExecutorService service = Executors.newFixedThreadPool(2);
            service.submit(userInputHandler);
            service.submit(databaseProcessor);

            try {
                service.awaitTermination(10000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private static class UserInputHandler implements Runnable {
            private final Lock lock;
            private final Condition condition;

            public UserInputHandler(final Lock lock, final Condition condition) {
                this.lock = lock;
                this.condition = condition;
            }

            @Override
            public void run() {
                try {
                    lock.lock();

                    System.out.printf("Hello, this is %s processing your data...%n", Thread.currentThread().getName());
                    System.out.print("Enter name: ");
                    Scanner input = new Scanner(System.in);
                    String name = input.nextLine();

                    System.out.print("Enter id number: ");
                    String idNo = input.nextLine();

                    db = new Database(name, idNo);
                } finally {
                    lock.unlock();
                }
            }
        }

        private static class DatabaseProcessor implements Runnable {
            private final Lock lock;
            private final Condition condition;

            public DatabaseProcessor(final Lock lock, final Condition condition) {
                this.lock = lock;
                this.condition = condition;
            }

            @Override
            public void run() {
                try {
                    lock.lock();
                    while (db.idNo().equals(null) || db.name().equals(null)) {
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    displayData(db);
                } finally {
                    lock.unlock();
                }
            }

            private void displayData(Database database) {
                System.out.printf("%s: reading - name = %s, idNo = %s%n", Thread.currentThread().getName(), db.name(), db.idNo());
            }
        }
    }

    private static class Inter_Thread_Comms_With_Object {
        public static void main(String... args) {

        }
    }
}