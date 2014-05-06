# jShuffler - A Concurrent Testing Framework to Explore Thread Execution Combinations

Testing of concurrent applications is definitely not easy. 
The way most teams test multithreaded applications are as follows:

1.    Test the functionality of the application with a single user (i.e. single-thread, or at least in an environment whereby concurrency issues are not exercised).
1.    Test the functionality with many threads. This usually occurs as part of load /stress testing. Many threads or virtual users are created to use the application concurrently. 

It is in the second test when concurrency issues are exercised, but not completely. There are several reasons for this. The goal of such load/stress tests is to determine how much workload the system can withstand, and to tune the allocation of system resources and other execution parameters. The goal is not to test for concurrency problems. In fact, these tests assume that there are no concurrency problems in the first place.

In addition, such tests are conducted in a probabilistic manner. If a concurrency defect is found, it is found. If a defect is not found, there is no guarantee that there are no concurrency issues. Developers dismiss the defects as intermittent problems. Product owners have low confidence in the product.

When a concurrency defect is found, the test being probabilistic is not easily repeated. Fault isolation is not easy. The bottom line is this: The test is not designed to test for concurrent issues.

jShuffler is a framework for testing concurrency in applications. The term "shuffler" refers to the fact that jShuffler attempts to shuffle the execution sequence of more multiple threads over several execution runs. Before proceeding, I want to highlight several points:

1.    It is important to note that jShuffler does not test your threading mechanism (such as your own thread pool, etc.). Instead, jShuffler test the validity of your application when run within a multi-threaded environment.
1.    jShuffler provides a multi-threaded environment. jShuffler uses AspectJ to control the execution of your application. You can determine how detail the control is by defining pointcuts. If you do not known AspectJ, pointcuts are something like breakpoints. jShuffler uses AspectJ to inject breakpoints into the execution sequence, and through that control which thread can proceed.
1.    jShuffler uses an algorithm to span all possible execution paths across test runs.
1.    jShuffler applies effective separation of concerns by separating the thread execution control, execution shuffling, execution tracing.
1.    jShuffler uses jUnit as the test driver to run the tests. 

