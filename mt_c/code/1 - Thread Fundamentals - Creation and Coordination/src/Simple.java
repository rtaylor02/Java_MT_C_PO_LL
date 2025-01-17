public class Simple {
    // Notice the messages from the 2 executing threads (1st_way and 2nd_way) are interleaving even after main thread finishes.
    // 1st_way thread prints 1-10
    // 2nd_way thread prints 101-110
    // Play around with each thread's daemon status to see the lifecycle of that thread. NOTE: ONLY when both
    // threads are daemon then both application will terminate as soon as main terminates. If one is daemon and
    // the other is not, main will wait.
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + ": starting...");

        // 1st way: create a Runnable and pass it as constructor arg of Thread object
        byPassingARunnable();

        // 2nd way: extend Thread and override run()
        byExtendingThread();

        // 3rd way: by thread pool
        byThreadPool();

        System.out.println(Thread.currentThread().getName() + ": finishing...");
    }

    private static void byThreadPool() {

    }

    private static void byExtendingThread() {
        Thread anonymousThreadSubClass = new Thread() {
            // Define the task in run()
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ": starting...");
                try {
                    for (int i = 100; i < 110; i++) {
                        System.out.println(i);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException ie) {
                    // Do nothing
                }
                System.out.println(Thread.currentThread().getName() + ": finishing...");
            }
        };

        anonymousThreadSubClass.setName("2nd_way");
        anonymousThreadSubClass.setDaemon(true);
        anonymousThreadSubClass.setPriority(Thread.MAX_PRIORITY);
        anonymousThreadSubClass.start();
    }

    private static void byPassingARunnable() {
        Thread thread = new Thread(createTask());
        thread.setName("1st_way");
        thread.setDaemon(false);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }

    private static Runnable createTask() {
        // The real task
        return () -> {
            System.out.println(Thread.currentThread().getName() + ": starting...");
            try {
                for (int i = 0; i < 3; i++) {
                    System.out.println(i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException ie) {
                // Do nothing
            }
            System.out.println(Thread.currentThread().getName() + ": finishing...");
        };
    }
}
