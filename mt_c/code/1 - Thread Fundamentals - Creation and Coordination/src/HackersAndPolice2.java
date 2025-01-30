import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HackersAndPolice2 {
    private static final int MAX_PASSWORD = 9999;

    public static void main(String[] args) {
        Vault vault = new Vault(new Random().nextInt(MAX_PASSWORD));

        // Create tasks for each thread
        Runnable descTask = getDescendingHackerTask(vault);
        Runnable ascTask = getAscendingHackerTask(vault);
        Runnable police = getPoliceTask();

        //// Pass tasks to each thread to execute
        //Thread.ofPlatform().name("AscHacker").priority(Thread.MAX_PRIORITY).start(ascTask);
        //Thread.ofPlatform().name("DescHacker").priority(Thread.MAX_PRIORITY).start(descTask);
        //Thread.ofPlatform().name("Police").priority(Thread.MAX_PRIORITY).start(police);

        // Using thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(descTask);
        executorService.submit(ascTask);
        executorService.submit(police);
    }

    private static Runnable getPoliceTask() {
        Runnable police = new Runnable() {
            @Override
            public void run() {
                // Setting name - useful for traceability
                if (!Thread.currentThread().getName().equals("Police")) {
                    Thread.currentThread().setName("Police");
                }

                try {
                    System.out.println(Thread.currentThread().getName() + ": starting...");
                    for (int i = 10; i > 0; i--) {
                        Thread.sleep(1000);
                        System.out.println(i);
                    }
                    System.out.println(Thread.currentThread().getName() + ": Got you hackers!");
                    System.exit(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        return police;
    }

    private static Runnable getAscendingHackerTask(Vault vault) {
        Runnable ascTask = new Runnable() {
            @Override
            public void run() {
                // Setting name - useful for traceability
                if (!Thread.currentThread().getName().equals("AscHacker")) {
                    Thread.currentThread().setName("AscHacker");
                }

                System.out.println(Thread.currentThread().getName() + ": starting...");
                for (int i = 0; i < MAX_PASSWORD; i++) {
                    if (vault.isCorrectPassword(i)) {
                        System.out.println(Thread.currentThread().getName() + " cracked it: " + i);
                        System.exit(0);
                    }
                }
            }
        };
        return ascTask;
    }

    private static Runnable getDescendingHackerTask(Vault vault) {
        Runnable descTask = new Runnable() {
            @Override
            public void run() {
                // Setting name - useful for traceability
                if (!Thread.currentThread().getName().equals("DescHacker")) {
                    Thread.currentThread().setName("DescHacker");
                }

                System.out.println(Thread.currentThread().getName() + ": starting...");
                for (int i = MAX_PASSWORD; i > 0; i--) {
                    if (vault.isCorrectPassword(i)) {
                        System.out.println(Thread.currentThread().getName() + " cracked it: " + i);
                        System.exit(0);
                    }
                }
            }
        };
        return descTask;
    }

    private static class Vault {
        private final int password;

        public Vault(int password) {
            this.password = password;
            System.out.println("Vault: " + this.password);
        }

        public boolean isCorrectPassword(int guess) {
            try {
                Thread.sleep(1, 500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return this.password == guess;
        }
    }
}
