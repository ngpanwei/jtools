# jWise - Pair-Wise Test Case Generation

This is a library that generates test cases given a set of input parameter ranges using Java. In essence, it is an implementation of all-pairs or pairwise testing When working on this, I was rather surprised by the seemingly large number of academic research in this area. The papers do take a little while to digest and exceeds my patience.

I took a challenge to devise an algorithm myself. Certainly not fantastic, but works to a cetain extent. What I have here is rather basic. It guarantees coverage of pairs, but it does not guarantee that it has the minimum number of combinations to cover all pairs. A simple example is shown in [PairTest.java](https://github.com/ngpanwei/tools/blob/master/jwise/test/core/PairTest.java).

1. the parameter to BasicAlgorithm constuctor is a number that affects the picking of combinations. You can try different values to see the result.
1. The test produces a list of combinations. An underscore in the generated result signifies a don't care value. 

