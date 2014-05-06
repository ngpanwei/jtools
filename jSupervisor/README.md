# jSupervisor - An Integration Test Framework wih Automated Mocking

Suppose you are developing a feature (or a user story, or a use case, and so on). This feature would likely be realized by a collaboration of a few classes, let's say A, B, and C. You would likely want to write unit tests for these classes and the integration of these classes. So, for each test scenario (such as A calls B and B calls C), you would need to write the following tests:

1.    Test A with a mock of B (i.e. unit test)
1.    Test B with a mock of C
1.    Test C.
1.    Test A and B with Mock of C (i.e. partial integration test)
1.    Test A, B, and C together (i.e. integration test) 

As you can see, there are many combinations and certainly much potential duplication of testing code and effort if you were to write JUnit tests for each of them. So, we need a better way. That is where jSupervisor comes in. jSupervisor uses Java's dynamic proxy mechanism to specify scenarios, record scenarios, and execute scenarios with various combinations of test targets (i.e. the A, B and C we are testing.). With jSupervisor, you can define scenarios in two ways:

1.    specifying the invocation sequence in your JUnit test.
1.    recording invocation from an actual run of the test targets. 

During execution, you can define the combination of test targets you want. jSupervisor then plays the execution and compares the actual result with the specified or recorded sequence. There is no need to write mocks as they can be automatically inferred from the test scenario. In addition, jSupervisor implements a novel watching feature to quickly pin-point where testing errors occur by displaying file and line numbers understandable by the eclipse console parser. Now, with jSupervisor, you can do test driven development at the feature (or a user story, or a use case) level.

1.    You describe the acceptance test as a scenario (in code).
1.    You execute the scenario without any test targets.
1.    You allocate the implementation of the test targets to various team members.
1.    Each team member execute the scenario on the target he/she is responsible for. There is no need to write any mocks or any other test code.
1.    Each member implements the target code until the scenario is accepted for his/her target.
1.    You test the integration of the target by just attaching the actual target to the scenario.
1.    You continue the integration until the scenario is accepted for all targets. 

So, you are writing only ONE test, but the same test can be executed by different team members developing their own part of the system. 
