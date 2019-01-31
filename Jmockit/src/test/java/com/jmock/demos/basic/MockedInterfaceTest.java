package com.jmock.demos.basic;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.junit.Test;

import mockit.Mocked;

/**
 * mocked注解接口测试
 * @author xianzilei
 *
 */
public class MockedInterfaceTest {
	@Mocked
	private HttpSession httpSession;
	
	@Test
	public void testMockedInterface(){
		//返回类型String：返回null
		String id = httpSession.getId();
		System.out.println(id);
		
		//返回类型为原始类型（short,int,float,double,long）：返回0
		long creationTime = httpSession.getCreationTime();
		System.out.println(creationTime);
		
		//返回类型为非原始类型，非String类型，返回的对象不为空（这个对象是JMockit帮你实例化的，是一个Mocked对象）
		ServletContext servletContext = httpSession.getServletContext();
		System.out.println(servletContext);
		
		//Mocked对象返回的Mocked对象，（返回类型为String）的方法也不起作用了，返回了null
		String contextPath = httpSession.getServletContext().getContextPath();
		System.out.println(contextPath);
	}
}
