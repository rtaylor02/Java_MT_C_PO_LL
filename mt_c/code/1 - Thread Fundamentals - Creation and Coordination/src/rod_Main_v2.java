import java.util.Random;

public class rod_Main_v2 {
    private static final int MAX_PASSWORD = 9999;

    public static void main(String[] args) {
        Vault vault = new Vault(new Random().nextInt(MAX_PASSWORD));

        Runnable descTask = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ": starting...");
                for (int i = MAX_PASSWORD; i > 0; i--) {
                    if (vault.isCorrectPassword(i)) {
                        System.out.println(Thread.currentThread().getName() + " cracked it: " + i);
                        System.exit(0);
                    }
                }
            }
        };

        Runnable ascTask = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ": starting...");
                for (int i = 0; i < MAX_PASSWORD; i++) {
                    if (vault.isCorrectPassword(i)) {
                        System.out.println(Thread.currentThread().getName() + " cracked it: " + i);
                        System.exit(0);
                    }
                }
            }
        };

        Runnable police = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + ": starting...");
                    for (int i = 10; i > 0; i--) {
                        Thread.sleep(1000);
                        System.out.println(i);
                    }
                    System.out.println(Thread.currentThread().getName() + ": Got you hackers!");
                    System.exit(0);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };

        Thread.ofPlatform().name("Asc").priority(Thread.MAX_PRIORITY).start(ascTask);
        Thread.ofPlatform().name("Desc").priority(Thread.MAX_PRIORITY).start(descTask);
        Thread.ofPlatform().name("Police").priority(Thread.MAX_PRIORITY).start(police);
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
