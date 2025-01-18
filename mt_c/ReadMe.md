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
  | Challenges                    | Solution                                                                                                                                                                                                                                                                        |
  |-------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
  | Shared-variable inconsistency | Every shared variable should be:<br/>- Guarded by a `synchronized` block or a lock<br/>- `volatile` ==> guaranteed order (or *happens-before*) ==> sequence of flanking statements of a `volatile` variable are guaranteed (i.e. excluded from re-ordering by JIT optimisation) |
  | Deadlock                      | ALWAYS use locks with the **same order** ==> avoid ***circular lock chain***                                                                                                                                                                                                    |


- Critical section of the code = code block that can lead to data race or racing condition


- Locking:
  - Coarse-grained locking: `synchronized` method --> slow
  - Fine-grained locking: `synchronized` sections --> faster, but can cause deadlock due to multiple locks


- Reentrant-lock = using the same lock, a thread can enter different `synchronized` sections/methods


- 