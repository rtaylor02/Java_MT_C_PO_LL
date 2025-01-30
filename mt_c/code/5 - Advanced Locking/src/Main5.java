/*
This code is to show the use of normal lock (ReentrantLock) and ReadWriteReentrantLock.
Also the difference in their performance.
 */

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main5 {
    public static void main(String[] args) {
        final int TOTAL_ITEMS = 100_000;
        final int HIGHEST_PRICE = 1000;
        InventoryDatabase inventoryDatabase = new InventoryDatabase(TOTAL_ITEMS, HIGHEST_PRICE);

        // Writer thread
        Thread writer = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                while (true) {
                    try {
                        int priceToAdd = random.nextInt();
                        if (priceToAdd > 0 && priceToAdd < HIGHEST_PRICE) {
                            inventoryDatabase.addItem(priceToAdd);
                        }

                        int priceToRemove = random.nextInt();
                        if (priceToRemove > 0 && priceToRemove < HIGHEST_PRICE) {
                            inventoryDatabase.removeItem(priceToRemove);
                        }

                        Thread.sleep(10);
                    } catch (InterruptedException interruptedException) {

                    }
                }
            }
        });

        writer.setDaemon(true);
        writer.start();

        // Read task
        Runnable task = new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                for (int i = 0; i < TOTAL_ITEMS; i++) {
                    int higherValue = random.nextInt(HIGHEST_PRICE);
                    if (higherValue > 0) {
                        inventoryDatabase.getNumberOfItemsInPriceRange(0, higherValue);
                    }
                }
            }
        };

        // Reader threads
        final int TOTAL_THREADS = 7;
        //Thread[] readers = new Thread[TOTAL_THREADS];
        List<Thread> readers = new ArrayList<>(TOTAL_THREADS);
        for (int i = 0; i < TOTAL_THREADS; i++) {
            Thread reader = new Thread(task);
            reader.setDaemon(true);
            //readers[i] = reader;
            readers.add(reader);
        }

        long startTime = System.currentTimeMillis();
        //for (Thread t : Arrays.asList(readers)) {
        //    t.start();
        //}
        for (Thread t : readers) {
            t.start();
        }

        //for (Thread t : Arrays.asList(readers)) {
        //    try {
        //        t.join();
        //    } catch (InterruptedException e) {
        //        throw new RuntimeException(e);
        //    }
        //}
        for (Thread t : readers) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println(String.format("Duration: %dms", (endTime - startTime)));
    }

    public static class InventoryDatabase {
        TreeMap<Integer, Integer> priceToCountMap = new TreeMap<>();
        public ReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        public Lock readLock = reentrantReadWriteLock.readLock();
        public Lock writeLock = reentrantReadWriteLock.writeLock();
        public Lock lock = new ReentrantLock(); // Worse performance

        private InventoryDatabase(int totalItems, int highestPrice) {
            initialiseInventory(totalItems, highestPrice);
        }

        public int getNumberOfItemsInPriceRange(int lowerPrice, int higherPrice) {
            readLock.lock();
            //lock.lock();
            try {
                Integer fromKey = priceToCountMap.ceilingKey(lowerPrice);
                Integer toKey = priceToCountMap.floorKey(higherPrice);
                if (fromKey == null || toKey == null) {
                    return 0;
                }

                NavigableMap<Integer, Integer> itemsInPriceRange = priceToCountMap.subMap(fromKey, true, toKey, true);

                //SortedMap<Integer, Integer> itemsInPriceRange = priceToCountMap.subMap(lowerPrice, higherPrice);
                //NavigableMap<Integer, Integer> itemsInPriceRange = priceToCountMap.subMap(lowerPrice, true, higherPrice, true);

                int sum = 0;
                for (Integer i : itemsInPriceRange.keySet()) {
                    sum += itemsInPriceRange.get(i);
                }

                return sum;
            } finally {
                readLock.unlock();
                //lock.unlock();
            }
        }

        // Initialise inventory
        private void initialiseInventory(int totalItems, int highestPrice) {
            Random random = new Random();
            for (int i = 0; i < totalItems; i++) {
                //int price = random.nextInt(highestPrice);
                //if (price > 0 && price < highestPrice) {
                    addItem(random.nextInt(highestPrice));
                //}
            }
        }

        // Add an item into inventory
        private void addItem(int key) {
            writeLock.lock();
            //lock.lock();
            try {
                Integer numberOfItemForPrice = priceToCountMap.get(key);
                if (numberOfItemForPrice == null) {
                    priceToCountMap.put(key, 1);
                } else {
                    priceToCountMap.put(key, (numberOfItemForPrice + 1));
                }
            } finally {
                writeLock.unlock();
                //lock.unlock();
            }
        }

        // Remove an item from inventory
        private void removeItem(int key) {
            writeLock.lock();
            //lock.lock();
            try {
                //if (priceToCountMap.containsKey(key) && (priceToCountMap.get(key) > 0)) {
                //    priceToCountMap.put(key, (priceToCountMap.get(key) - 1));
                //}
                Integer numberOfItemsForPrice = priceToCountMap.get(key);
                if (numberOfItemsForPrice == null || numberOfItemsForPrice == 1) {
                    priceToCountMap.remove(key);
                } else {
                    priceToCountMap.put(key, numberOfItemsForPrice - 1);
                }
            } finally {
                writeLock.unlock();
                //lock.unlock();
            }
        }

    }
}
