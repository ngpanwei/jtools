# jWatcher - A Simple Tool to mimic _ _LINE_ _, _ _FILE_ _, _ _func_ _ in Java

This project provides a library to mimic _ _LINE_ _, _ _FILE_ _ and _ _func_ _ in Java.

The displayed text is recognizeable by Eclipse console such that if you double click on the file.line, Eclipse will bring you to that source line for you. This is very convenient when you are tracing through your source code.

jWatcher comes with samples in the form of test cases. When running the following code: 
````java
class WatcherTest {
   public void testLog() {
      Watcher.log() ;
   }
}
````
You get the followng result printed:
````java
   WatcherTest.testLog(WatcherTest.java:10)
````
The examples also show how you can extend jWatcher to provide more sophisticated logging through Custom Watchers. 


