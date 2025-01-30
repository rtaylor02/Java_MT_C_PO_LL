# Introduction
All multi-threading and concurrency material.

The folder structure resembles [Michael Pogrebinsky course](https://ba.udemy.com/course/java-multithreading-concurrency-performance-optimization/learn/lecture/11200058#overview) as the structure k
is considered good.

# Source
1. [Java Multithreading, Concurrency & Performance Optimisation](https://ba.udemy.com/course/java-multithreading-concurrency-performance-optimization/learn/lecture/11200058#overview) by Michael Pogrebinski
2. [Java Concurrency and Multithreading in Practice](https://learning.oreilly.com/videos/-/9781789806410/) by Tatiana Fesenko
3. [Java Thread Demystified](https://learning.oreilly.com/live-events/java-threads-demystified/0642572004012/) by Maurice Naftalin

# Pointers
## 1 - Thread Fundamentals - Creation and Coordination
- Motivation for multi-threading application:
  - responsiveness
  - performance  


- Forms of thread coordination:
  - interrupt: 
    - `thread.interrupt()`
    - `thread.isInterrupted()`
  - waiting:
    - `thread.join([long[, int]])`
    - `object.wait([long[, int]])` & `object.notify()` / `object.notifyAll()`

## 2 - Performance Optimisation - Latency and Throughput
- Throughput = #transaction / unit time


- For optimised throughput:
    - \# threads = # cores. More is counterproductive
    - Use Fixed Thread Pool ==> eliminate the cost of recreating the threads
    - Best strategy: each request is handled by 1 thread


- Use JMeter to measure throughput of our application:
    ![JMeter_test_plan.png](img/JMeter_test_plan.png)

## 3 - Data Sharing between Threads
- Memory regions:
    - heap (shared): objects, class members, static variables
    - stack (exclusive): local primitive types, local references (to objects on heap)


- Atomic operation ==> single step - ***'ALL or NOTHING'***

## 4 - Concurrency Challenges and Solutions
  | Challenges                                                                                              | Solution                                                                                                                                                                                                                                                                        |
  |---------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
  | Shared-resource inconsistency (Racing Condition & Data Race) - at least 1 thread modifying the resource | Every shared variable should be:<br/>- Guarded by a `synchronized` block or a lock<br/>- `volatile` ==> guaranteed order (or *happens-before*) ==> sequence of flanking statements of a `volatile` variable are guaranteed (i.e. excluded from re-ordering by JIT optimisation) |
  | Deadlock                                                                                                | ALWAYS use locks with the **same order** ==> avoid ***circular lock chain***                                                                                                                                                                                                    |


- Racing condition and data race:
  - Racing condition: due to non-atomic operation, e.g. value++
  - Data race: due to CPU optimisation, e.g. re-ordering instructions, for optimised HW utilisation


- Critical section of the code = code block that can lead to data race or racing condition


- Atomic operation = an operation done within 1 CPU clock.
  - Primitive (except `long` and `double`) read & write ==> use `volatile` to guarantee atomic `long` & `double` R/W ops.
  - Reference read & write


- Locking:
  - Coarse-grained locking: `synchronized` method --> slow
  - Fine-grained locking: `synchronized` sections --> faster, but can cause deadlock due to multiple locks


- Reentrant-lock = using the same lock, a thread can enter different `synchronized` sections/methods



# Advanced Locking
- [ReentrantLock](https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/util/concurrent/locks/ReentrantLock.html):
  - just like `sychronized(lockObject) {...}` but with `lock()`-`unlock()` and control on fairness - `ReentrantLock(true)`. Also, with useful methods:
    - `getQueuedThreads()`
    - `getOwner()`
    - `isHeldByCurrentThread()`
    - `isLocked()`
  - when not careful, possible *deadlock* issue. To prevent:
    - ALWAYS put it within `try-finally` block ==> so lock is ALWAYS released in the event of exception or mishap of forgetting
    - use of `lockInterruptibly()` or `tryLock(long, TimeUnit)` ==> so application is never gone into deadlock, or not blocked at all
   
 
- [ReentrantReadWriteLock](https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/util/concurrent/locks/ReentrantReadWriteLock.html):
  - Just like ReentrantLock but with `readLock()` and `writeLock()` as `Lock` objects to specify reading and writing operations respectively
  - Read section protected by read-lock can be entered by multiple threads but only 1 thread can enter writing section protected by write-lock. Mutual exclusivity between locks are maintained:
    - No threads can enter read-locked section when write-lock is acquired
    - No threads can enter write-locked section when read-lock is acquired
  - Use it for better performance when reading operation is predominant and short. ***NOTE***: better performance is not guaranteed. Always measure and validate 
  - 
   