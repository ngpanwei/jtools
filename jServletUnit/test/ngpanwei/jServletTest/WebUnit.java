package ngpanwei.jServletTest;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.junit.Test;

public class WebUnit {

	@Test
	public void invoke() throws Exception {
		// Get a Client
		HttpClient httpClient = new HttpClient();

		// Start server
		httpClient.start();

		// do a get request
		ContentResponse response = httpClient.GET("http://localhost:8080/test") ;
		System.err.println(response.getContentAsString());
	}
}
