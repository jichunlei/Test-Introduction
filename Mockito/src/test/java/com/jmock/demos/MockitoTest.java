package com.jmock.demos;

import static org.mockito.Mockito.*;

import java.util.List;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.InOrder;

import com.mockito.demos.Person;
import com.mockito.demos.PersonService;

/**
 * Mockito常见用法
 * @author xianzilei
 *
 */
public class MockitoTest {
	/**
	 * 验证行为
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testVefifyBehaviour() {
		//模拟创建一个List对象
		List mock = mock(List.class);
		//使用mock的对象
		mock.add(1);
		mock.add(2);
		mock.add(2);
		mock.clear();
		//验证是否调用过一次add(1)和clear()方法
		verify(mock).add(1);
		verify(mock).clear();
		//验证是否调用过两次add(2)方法
		verify(mock, times(2)).add(2);		
	}
	
	/**
	 * 模拟我们所期望的结果--正常情况
	 */
	@SuppressWarnings({"rawtypes" })
	@Test
	public void testWhenThenReturn() { 
		List mock = mock(List.class);
		//thenReturn多次时，之后的以最后一次为准
		when(mock.get(0)).thenReturn("hello ").thenReturn("world! ");
		System.out.println("mock.get(0)------"+mock.get(0).toString()+mock.get(0).toString()+mock.get(0).toString()+mock.get(0).toString());
		System.out.println("未mock的方法（返回类型object）----------"+mock.get(1));
		System.out.println("未mock的方法（返回类型int）----------"+mock.size());
		System.out.println("未mock的方法（返回类型boolean）----------"+mock.isEmpty());
		verify(mock, times(4)).get(0);
	}
	
	/**
	 * 模拟我们所期望的结果--抛异常情况
	 */
	@SuppressWarnings({"rawtypes" })
	@Test(expected=Exception.class)
	public void testWhenThenReturnThrowException() { 
		List mock = mock(List.class);
		//模拟抛出异常
		doThrow(new Exception("模拟异常！")).when(mock).get(0);
		mock.get(0);
	}
	
	/**
	 * RETURNS_DEEP_STUBS:创建mock对象时的备选参数，参数程序会自动进行mock所需的对象
	 */
	@Test
	public void testReturnsDeepStubs() { 
		PersonService mock = mock(PersonService.class,RETURNS_DEEP_STUBS);
		when(mock.getPersonDao().getPerson(1)).thenReturn(new Person(1, "zhangsan"));
		Person person = mock.getPersonDao().getPerson(1);
		System.out.println(person);
		verify(mock.getPersonDao()).getPerson(1);
	}
	
	/**
	 * 参数匹配:如果你使用了参数匹配器，那么所有参数都应该使用参数匹配器
	 */
	@Test
	public void testArgumentMatcher(){
		Person mock = mock(Person.class);
		when(mock.method(anyString())).thenReturn("hello");
		String str = mock.method("1");
		verify(mock).method(anyString());
		assertEquals("hello",str);
	}
	
	/**
	 * 验证准确的调用次数，最多、最少、从未等
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testInvocationTimes(){
		List mock = mock(List.class);
		mock.add("one time");
		mock.add("two times");
		mock.add("two times");
		mock.add("three times");
		mock.add("three times");
		mock.add("three times");
		
		//下面两个是等价的， 默认使用times(1)
		verify(mock).add("one time");
		verify(mock, times(1)).add("one time");
		
		//验证准确的调用次数
		verify(mock, times(2)).add("two times");
		verify(mock, times(3)).add("three times");
		
		//从未调用过，never()是times(0)的别名，等价
		verify(mock, times(0)).add("never");
		verify(mock, never()).add("never");
		
		//atLeast()/atMost()
		verify(mock, atLeast(1)).add("two times");
		verify(mock, atMost(5)).add("two times");
	}
	
	/**
	 * 验证调用顺序
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testVerificationInOrder(){
		//1.单个mock对象
		List mock = mock(List.class);
		mock.add(1);
		mock.add(2);
		//创建inOrder
		InOrder inOrder = inOrder(mock);
		//验证调用次数，若是调换两句，将会出错，因为mock.add(1)是先调用的
		inOrder.verify(mock).add(1);
		inOrder.verify(mock).add(2);
		
		//2.多个mock对象
		List mock1 = mock(List.class);
		List mock2 = mock(List.class);
		mock1.add(1);
		mock1.add(2);
		mock2.add(1);
		mock2.add(2);
		//创建多个mock对象的inOrder
		inOrder = inOrder(mock1,mock2);
		//验证firstMock先于secondMock调用
		inOrder.verify(mock1).add(1);
		inOrder.verify(mock2).add(2);
	}
	/**
	 * 验证mock对象没有产生过交互
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testInteractionNeverHappened() {
		List mock = mock(List.class);
		//测试通过
		verifyZeroInteractions(mock);
		
		mock.add(1);
		//测试不通过，因为mock已经发生过交互了
		verifyZeroInteractions(mock);
	}
	
	/**
	 * 查找是否有未验证的交互
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testFindingRedundantInvocations(){
		List mock = mock(List.class);
		mock.add(1);
		mock.add(1);
		mock.add(1);
		mock.add(2);
		mock.add(2);
		verify(mock,times(3)).add(1);
		//验证失败，因为mock.add(2)尚未验证
		verifyNoMoreInteractions(mock);
	}
	
	
}
