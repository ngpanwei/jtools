package ngpanwei.jWebTestAgent.example.test.suites;

import ngpanwei.jWebTestAgent.example.test.tests.StudentClientProxyTest;
import ngpanwei.jWebTestAgent.example.test.tests.StudentClientTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ StudentClientTest.class, StudentClientProxyTest.class })
public class AllUnitTests {

}
