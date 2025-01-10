package po_ll.JMC_codes.cameron_mckenzie;


import java.util.*;

public class HotJavaMethodRunner implements Runnable {
    private static final int NUMBER_OF_THREADS = 8;

    public static void main(String[] args) throws Exception{
        ThreadGroup threadGroup = new ThreadGroup("Workers");
        Thread[] threads = new Thread[NUMBER_OF_THREADS];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(threadGroup, new HotJavaMethodRunner(), "Worker thread " + i);
            threads[i].setDaemon(true);
            threads[i].start();
        }

        System.out.println("Press enter to quit");
        System.out.flush();
        System.in.read();
    }

    @Override
    public void run() {
        WorkEvent event = new WorkEvent();
        event.begin();
        Collection<Integer> firstBunch = new LinkedList<>();
        Collection<Integer> secondBunch = new LinkedList<>();
        // Collection<Integer> firstBunch = new HashSet<>();
        // Collection<Integer> secondBunch = new HashSet<>();

        initialise(firstBunch, 3);
        initialise(secondBunch, 2);
        int intersectionSize = countMatches(firstBunch, secondBunch);

        event.setIntersectionSize(intersectionSize);
        event.end();
        event.commit();
        Thread.yield();
    }

    public void initialise(Collection<Integer> collection, int modulus) {
        collection.clear();
        for (int i = 1; i < 100_000; i++) {
            if ((i % modulus) != 0) {
                collection.add(i);
            }
        }
    }

    public int countMatches(Collection<Integer> first, Collection<Integer> second) {
        int count = 0;
        for (Integer i : first) {
            if (second.contains(i)) {
                count++;
            }
        }
        System.out.println(count);
        return count;
    }
}
