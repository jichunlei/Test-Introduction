package com.jmock.demos.basic;

import java.util.Locale;

import org.junit.Test;

import mockit.Injectable;
import mockit.Mocked;

/**
 * Mocked与Injectable注解的区别：
 * 1.Injectable：是告诉 JMockit生成一个Mocked对象，但@Injectable只是针对其修饰的实例，
 * 2.Mocked：是针对其修饰类的所有实例。
 * 3.此外，@Injectable对类的静态方法，构造函数没有影响。因为它只影响某一个实例。
 * @author xianzilei
 *
 */
public class MockedAndInjectableTest {
	@Test
	public void testMocked(@Mocked Locale locale){
		//静态方法,方法被mock，返回null
		Locale result1 = Locale.getDefault();
		System.out.println(result1);
		//非静态方法（返回String类型），方法被mock了
		String result2 = locale.getCountry();
		System.out.println(result2);
		//新实例，方法也被mock了
		Locale locale2=new Locale("zh", "CN");
		String result3 = locale2.getCountry();
		System.out.println(result3);
	}
	
	@Test
	public void testInjectable(@Injectable Locale locale){
		//静态方法,方法没有被mock，返回正常值
		Locale result1 = Locale.getDefault();
		System.out.println(result1);
		//非静态方法（返回String类型），方法被mock了
		String result2 = locale.getCountry();
		System.out.println(result2);
		//新实例，不受影响
		Locale locale2=new Locale("zh", "CN");
		String result3 = locale2.getCountry();
		System.out.println(result3);
	}
}
