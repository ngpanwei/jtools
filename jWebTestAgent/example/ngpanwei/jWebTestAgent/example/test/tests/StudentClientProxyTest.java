package ngpanwei.jWebTestAgent.example.test.tests;

import ngpanwei.jWebTestAgent.client.WebAgentProxyFactory;
import ngpanwei.jWebTestAgent.client.jersey.JerseyWebAgentClient;
import ngpanwei.jWebTestAgent.example.src.server.IStudentService;
import ngpanwei.jWebTestAgent.example.src.server.Student;
import ngpanwei.jWebTestAgent.example.src.server.StudentService;

import org.junit.Test;

/**
 * Test class to invoke Jersey Reset Web Agent through a dynamic proxy.
 */
public class StudentClientProxyTest {
	JerseyWebAgentClient jrClient = null ;
	IStudentService studentService = null ;
	public StudentClientProxyTest() {
		jrClient = new JerseyWebAgentClient("http://localhost:8080/jCuke") ;
		studentService = WebAgentProxyFactory.getProxy(IStudentService.class, StudentService.class,jrClient) ;
	}
	@Test
	public void shouldReturnStudentWhenInvokeMethod() throws Throwable {
		int size = studentService.getNumStudents() ;
		System.err.println("Size "+size);
		
		Student s1 = studentService.getStudent("John") ;
		System.err.println(s1) ;
		
		Student s2 = new Student("James","Brown",12,8) ;
		Student s3 = studentService.addStudent("James", s2) ;
		System.err.println(s3) ;
		
		size = studentService.getNumStudents() ;
		System.err.println("Size "+size);
		
	}
}
