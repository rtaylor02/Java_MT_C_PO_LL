import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;

import java.io.IOException;

public class Latencies {
    /*
    Run this program with the following JVM options:
    -Djava.net.preferIPv4Stack=true
    -XX:+FlightRecorder
    -XX:FlightRecorderOptions=stackdepth=28
    -XX:+UnlockDiagnosticVMOptions
    -XX:+DebugNonSafePoints
    -XX:StartFlightRecording=delay=2s,duration=60s,name=latency,filename=${workspace_log}/recordings/Latency.jfr,setting=profile
    -Xlog:jfr=info
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        ThreadGroup threadGroup = new ThreadGroup("Workers");
        Thread.sleep(2000);
        Thread[] threads = new Thread[20];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(threadGroup, new Worker(i, 30_000_000), "Worker Thread" + i);
            threads[i].setDaemon(true);
            System.out.println("Starting " + threads[i].getName() + "...");
            threads[i].start();
        }

        System.out.println("All prepared!");
        System.out.println("Press <enter> to quit");
        System.out.flush();
        System.in.read();
    }

    private record Worker(int id, int loopCount) implements Runnable {
        public final static Logger LOGGER = Logger.getLogger();

        @Override
        public void run() {
            while (true) {
                WorkEvent event = new WorkEvent();
                event.begin();
                int x = 1;
                int y = 1;
                for (int i = 1; i < loopCount; i++) {
                    x += 1;
                    y = y % (this.loopCount + 3);
                    if (x % (this.loopCount + 4) == 0 || y == 0) {
                        System.out.println("Should not happen");
                    }
                }
                event.end();
                event.commit();
                LOGGER.log("Worker " + id + " reporting work done");
                Thread.yield();
            }
        }

        /**
         * Example event showing that not much is needed to time stuff using the JDK Flight Recorder.
         */
        @Label("Work")
        @Category("03_JFR_Latencies")
        @Description("A piece of work executed")
        private static class WorkEvent extends Event {
        }
    }

    private static class Logger {
        private static Logger myInstance = new Logger();

        public static Logger getLogger() {
            return myInstance;
        }

        public synchronized void log(String text) {
            LogEvent event = new LogEvent();
            event.begin();

            // Do logging here
            // Write the text to a database or similar...
            try {
                // Simulate that this takes a little while
                Thread.sleep(20);
                System.out.println(Thread.currentThread().getName() + ": logging...");
            } catch (InterruptedException e) {
                // Handle the interruption
            }

            event.end();
            event.commit();
        }

        private static class LogEvent extends Event {

        }
    }
}
