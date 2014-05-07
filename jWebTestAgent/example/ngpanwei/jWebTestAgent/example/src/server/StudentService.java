/** 
 *  Copyright (c) 2014  Ng Pan Wei
 *  
 *  Permission is hereby granted, free of charge, to any person 
 *  obtaining a copy of this software and associated documentation files 
 *  (the "Software"), to deal in the Software without restriction, 
 *  including without limitation the rights to use, copy, modify, merge, 
 *  publish, distribute, sublicense, and/or sell copies of the Software, 
 *  and to permit persons to whom the Software is furnished to do so, 
 *  subject to the following conditions: 
 *  
 *  The above copyright notice and this permission notice shall be 
 *  included in all copies or substantial portions of the Software. 
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, 
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS 
 *  BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN 
 *  ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN 
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 *  SOFTWARE. 
 */
package ngpanwei.jWebTestAgent.example.src.server;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/students")
public class StudentService implements IStudentService {
	static HashMap<String,Student> studentMap ;
	static {
		studentMap = new HashMap<String,Student>() ;
		studentMap.put("John", new Student("John","Smith",11,5)) ;
		studentMap.put("Jim", new Student("Jim","Jones",12,8)) ;
	}
	@POST @Path("/student/{name}")
	// @Produces(MediaType.APPLICATION_JSON)
	public Student getStudent(@PathParam("name") String name) {
		System.err.println("/student/{name}=="+name) ;
		Student student = studentMap.get(name) ;
		return student;
	}
	
	@POST @Path("/add/{name}")
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	public Student addStudent(@PathParam("name") String name, Student student) {
		System.err.println("/add/{name}=="+name) ;
		studentMap.put(name,student) ;
		return student ;
		// return Response.status(200).entity(output).build();
	}
	@POST @Path("/size")
	// @Consumes(MediaType.APPLICATION_JSON)
	public int getNumStudents() {
		return studentMap.size() ;
	}

}
