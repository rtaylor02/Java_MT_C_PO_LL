import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class rod_Main {
    private static final int MAX_PASSWORD = 9999;
    private static Vault vault = new Vault(new Random().nextInt(MAX_PASSWORD));

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        threads.add(new DescendingHacker("Desc"));
        threads.add(new AscendingHacker("Asc"));
        threads.add(new Police("Police"));

        threads.stream().forEach(t -> t.start());
    }

    private static class Vault {
        private final int password;

        public Vault(int password) {
            this.password = password;
            System.out.println("password: " + this.password);
        }

        public boolean isCorrectPassword(int password) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return this.password == password;
        }
    }

    private static class Police extends Thread {
        public Police(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                for (int i = 10; i > 0; i--) {
                    Thread.sleep(1000);
                    System.out.println(i);
                }
                System.out.println("Police: got you hackers!");
                System.exit(0);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    private static class Hacker extends Thread {
        public Hacker(String name) {
            super(name);
        }

        @Override
        public void start() {
            System.out.println(this.getName() + ": start hacking...");
            super.start();
        }

        public void success(int password) {
            System.out.println(this.getName() + ": cracked it: " + password);
            System.exit(0);
        }
    }

    private static class AscendingHacker extends Hacker {
        public AscendingHacker(String name) {
            super(name);
        }

        @Override
        public void run() {
            for (int i = 0; i < MAX_PASSWORD; i++) {
                if (vault.isCorrectPassword(i)) {
                    success(i);
                }
            }
        }
    }

    private static class DescendingHacker extends Hacker {
        public DescendingHacker(String name) {
            super(name);
        }

        @Override
        public void run() {
            for (int i = MAX_PASSWORD; i > 0; i--) {
                if (vault.isCorrectPassword(i)) {
                    success(i);
                }
            }
        }
    }
}
