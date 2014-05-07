package ngpanwei.jWebTestAgent.client;

public class WebAgentProxyFactory {
	@SuppressWarnings("unchecked")
	public static <T> T getProxy(Class<T> interfaze,Class<?> serviceClazz,IWebAgentClient webClient) {
		Class<?> clazz = interfaze ;
		Object proxy = WebAgentClientProxy.newProxy(clazz,serviceClazz, webClient) ;
		return (T)proxy ;
	}	
}
