jWebTestAgent
=============

It is always good to continuously test your system. 
However, not everyone does this. 
Some web-based systems are not designed with testability in mind and have
intricate dependencies between their components and their operating environments and frameworks.
 
jWebTestAgent provides a way to expose testability interfaces to facilitate effective testing.
jWebTestAgent is a jersey-based servlet that you can embed into your J2EE applications to invoke components and gather returned results.
Through jWebTestAgent, your acceptance or unit tests can then easily invoke your components.
Since jWebTestAgent runs as part of your application, there is little need to create mocks.

 
