package com.jmock.demos.basic;

import java.util.Locale;

import org.junit.Test;

import mockit.Mocked;
/**
 * mocked注解类测试
 * @author xianzilei
 *
 */
public class MockedClassTest {
	@Mocked
	private Locale locale;

	@Test
	public void testMockedClass(){
		//静态方法被mock了，返回null
		Locale result1 = Locale.getDefault();
		System.out.println(result1);
		//非静态方法（原返回类型为String）被mock了，返回null
		String result2 = locale.getCountry();
		System.out.println(result2);
		//即使自己new个新的实例，也是被mock了的
		Locale locale=new Locale("zh", "CN");
		String result3 = locale.getCountry();
		System.out.println(result3);	
	}
}
