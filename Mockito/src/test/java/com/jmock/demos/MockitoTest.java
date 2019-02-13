package com.jmock.demos;

import static org.mockito.Mockito.*;//静态导入
import static org.junit.Assert.*;//静态导入

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.mockito.demos.Person;
import com.mockito.demos.PersonService;

/**
 * Mockito常见用法
 * 
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
		// 模拟创建一个List对象
		List mock = mock(List.class);
		// 使用mock的对象
		mock.add(1);
		mock.add(2);
		mock.add(2);
		mock.clear();
		// 验证是否调用过一次add(1)和clear()方法
		verify(mock).add(1);
		verify(mock).clear();
		// 验证是否调用过两次add(2)方法
		verify(mock, times(2)).add(2);
	}

	/**
	 * 模拟我们所期望的结果--正常情况
	 */
	@SuppressWarnings({ "rawtypes" })
	@Test
	public void testWhenThenReturn() {
		List mock = mock(List.class);
		// thenReturn多次时，之后的以最后一次为准
		when(mock.get(0)).thenReturn("hello ").thenReturn("world! ");
		System.out.println("mock.get(0)------" + mock.get(0).toString() + mock.get(0).toString()
				+ mock.get(0).toString() + mock.get(0).toString());
		System.out.println("未mock的方法（返回类型object）----------" + mock.get(1));
		System.out.println("未mock的方法（返回类型int）----------" + mock.size());
		System.out.println("未mock的方法（返回类型boolean）----------" + mock.isEmpty());
		verify(mock, times(4)).get(0);
	}

	/**
	 * 模拟我们所期望的结果--抛异常情况
	 */
	@SuppressWarnings({ "rawtypes" })
	@Test(expected = Exception.class)
	public void testWhenThenReturnThrowException() {
		List mock = mock(List.class);
		// 模拟抛出异常
		doThrow(new Exception("模拟异常！")).when(mock).get(0);
		mock.get(0);
	}

	/**
	 * RETURNS_SMART_NULLS:实现了Answer接口的对象，它是创建mock对象时的一个可选参数，mock(Class,Answer)。
	 * 在创建mock对象时，有的方法我们没有进行stubbing， 所以调用时会放回Null这样在进行操作是很可能抛出NullPointerException。
	 * 如果通过RETURNS_SMART_NULLS参数创建的mock对象在没有调用stubbed方法时会返回SmartNull。
	 * 例如：返回类型是String，会返回"";是int，会返回0；是List，会返回空的List。 另外，在控制台窗口中可以看到SmartNull的友好提示。
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testReturnsSmartNulls() {
		// 使用RETURNS_SMART_NULLS参数创建的mock对象，不会抛出NullPointerException异常。
		// 另外控制台窗口会提示信息“SmartNull returned by unstubbed method on a mock”
		List mock = mock(List.class, RETURNS_SMART_NULLS);
		System.out.println(mock.get(0));
		System.out.println(mock.size());
	}

	/**
	 * RETURNS_DEEP_STUBS:创建mock对象时的备选参数，参数程序会自动进行mock所需的对象
	 */
	@Test
	public void testReturnsDeepStubs() {
		PersonService mock = mock(PersonService.class, RETURNS_DEEP_STUBS);
		when(mock.getPersonDao().getPerson(1)).thenReturn(new Person(1, "zhangsan"));
		Person person = mock.getPersonDao().getPerson(1);
		System.out.println(person);
		verify(mock.getPersonDao()).getPerson(1);
	}

	/**
	 * 参数匹配:如果你使用了参数匹配器，那么所有参数都应该使用参数匹配器
	 */
	@Test
	public void testArgumentMatcher() {
		Person mock = mock(Person.class);
		when(mock.method(anyString())).thenReturn("hello");
		String str = mock.method("1");
		verify(mock).method(anyString());
		assertEquals("hello", str);
	}

	/**
	 * 验证准确的调用次数，最多、最少、从未等
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testInvocationTimes() {
		List mock = mock(List.class);
		mock.add("one time");
		mock.add("two times");
		mock.add("two times");
		mock.add("three times");
		mock.add("three times");
		mock.add("three times");

		// 下面两个是等价的， 默认使用times(1)
		verify(mock).add("one time");
		verify(mock, times(1)).add("one time");

		// 验证准确的调用次数
		verify(mock, times(2)).add("two times");
		verify(mock, times(3)).add("three times");

		// 从未调用过，never()是times(0)的别名，等价
		verify(mock, times(0)).add("never");
		verify(mock, never()).add("never");

		// atLeast()/atMost()
		verify(mock, atLeast(1)).add("two times");
		verify(mock, atMost(5)).add("two times");
	}

	/**
	 * 验证调用顺序
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testVerificationInOrder() {
		// 1.单个mock对象
		List mock = mock(List.class);
		mock.add(1);
		mock.add(2);
		// 创建inOrder
		InOrder inOrder = inOrder(mock);
		// 验证调用次数，若是调换两句，将会出错，因为mock.add(1)是先调用的
		inOrder.verify(mock).add(1);
		inOrder.verify(mock).add(2);

		// 2.多个mock对象
		List mock1 = mock(List.class);
		List mock2 = mock(List.class);
		mock1.add(1);
		mock1.add(2);
		mock2.add(1);
		mock2.add(2);
		// 创建多个mock对象的inOrder
		inOrder = inOrder(mock1, mock2);
		// 验证firstMock先于secondMock调用
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
		// 测试通过
		verifyZeroInteractions(mock);

		mock.add(1);
		// 测试不通过，因为mock已经发生过交互了
		verifyZeroInteractions(mock);
	}

	/**
	 * 查找是否有未验证的交互
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testFindingRedundantInvocations() {
		List mock = mock(List.class);
		mock.add(1);
		mock.add(1);
		mock.add(1);
		mock.add(2);
		mock.add(2);
		verify(mock, times(3)).add(1);
		// 验证失败，因为mock.add(2)尚未验证
		verifyNoMoreInteractions(mock);
	}

	/**
	 * 根据调用顺序设置不同的stubbing
	 */
	@SuppressWarnings("rawtypes")
	@Test(expected = Exception.class)
	public void testStubbingConsecutiveCalls() {
		// 1.写法一
		List mock = mock(List.class);
		when(mock.get(0)).thenReturn(1).thenReturn(2).thenThrow(new Exception("模拟抛异常！"));
		// 第1次调用
		assertEquals(1, mock.get(0));
		// 第2次调用
		assertEquals(2, mock.get(0));
		// 第3次调用
		mock.get(0);
		// 验证调用次数是否为3次
		verify(mock, times(3)).get(0);

		// 2.写法二
		List mock2 = mock(List.class);
		when(mock2.get(0)).thenReturn(4, 5, 6);
		assertEquals(4, mock2.get(0));
		// 第2次调用
		assertEquals(5, mock2.get(0));
		// 第3次调用
		assertEquals(6, mock2.get(0));
		// 验证调用次数是否为3次
		verify(mock2, times(3)).get(0);
	}

	/**
	 * 用spy监控真实对象 1.Mock不是真实的对象，它只是用类型的class创建了一个虚拟对象，并可以设置对象行为
	 * 2.Spy是一个真实的对象，但它可以设置对象行为 3.InjectMocks创建这个类的对象并自动将标记@Mock、@Spy等注解的属性值注入到这个中
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test(expected = IndexOutOfBoundsException.class)
	public void testSpy() {
		List list = new ArrayList();
		List spy = spy(list);
		// 下面这句会抛异常，因为会调用真实对象的get(0)方法，会抛出越界异常
		when(spy.get(0)).thenReturn(9);

		// 使用doReturn-when可以避免when-thenReturn调用真实对象api
		doReturn(88).when(spy).get(0);
		assertEquals(88, spy.get(0));

		// 预设size()的期望值
		when(spy.size()).thenReturn(100);

		spy.add(1);
		spy.add(2);
		assertEquals(100, spy.size());
		assertEquals(1, spy.get(0));
		assertEquals(2, spy.get(1));
		verify(spy).add(1);
		verify(spy).add(2);
		assertEquals(999, spy.get(999));
		spy.get(2);
	}

	/**
	 * 真实的部分mock
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testRealPartialMock() {
		// 1.通过spy来调用真实的api
		List spy = spy(new ArrayList());
		spy.add(1);
		assertEquals(true, spy.contains(1));

		// 2.通过thenCallRealMethod来调用真实的api
		Person mock = mock(Person.class);
		when(mock.method(anyString())).thenCallRealMethod();
		assertEquals("abc", mock.method("abc"));
	}

	/**
	 * 重置mock
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testResetMock() {
		List mock = mock(List.class);
		when(mock.size()).thenReturn(100);
		mock.add(1);
		assertEquals(100, mock.size());
		// 重置mock，清除所有的互动和预设
		reset(mock);
		assertEquals(0, mock.size());
	}

	/**
	 * 连续调用
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testConsecutiveCalls() {
		// 1.模拟连续调用返回期望值，如果分开，则只有最后一个有效
		List mock1 = mock(List.class);
		when(mock1.get(0)).thenReturn(1);
		when(mock1.get(0)).thenReturn(2);
		when(mock1.get(0)).thenReturn(3);
		when(mock1.get(0)).thenReturn(4);
		assertEquals(4, mock1.get(0));

		// 2.模拟连续调用返回期望值，如果分开，则只有最后一个有效
		List mock2 = mock(List.class);
		when(mock2.get(0)).thenReturn(1).thenReturn(2).thenReturn(3);
		assertEquals(1, mock2.get(0));
		assertEquals(2, mock2.get(0));
		assertEquals(3, mock2.get(0));
		// 第三次或多次调用返回结果跟最后一次的模拟值一样
		assertEquals(3, mock2.get(0));
	}

	/**
	 * doReturn(Object)用法
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testDoReturn() {
		List mock1 = mock(List.class);
		doReturn(2).when(mock1).get(0);
		assertEquals(2, mock1.get(0));

		List mock2 = mock(List.class);
		doReturn(3).doReturn(4).when(mock2).get(1);
		assertEquals(3, mock2.get(1));
		assertEquals(4, mock2.get(1));
	}

	/**
	 * doThrow(Object)用法
	 */
	@SuppressWarnings("rawtypes")
	@Test(expected=RuntimeException.class)
	public void testDoThrow() {
		List mock1 = mock(List.class);
		doThrow(new RuntimeException()).when(mock1).get(0);
		mock1.get(0);
	}
	
	/**
	 * doNothing(Object)用法
	 */
	@SuppressWarnings("rawtypes")
	@Test(expected=RuntimeException.class)
	public void testDoNothing() {
		List mock1 = mock(List.class);
		//迭代
		doNothing().doThrow(new RuntimeException()).when(mock1).clear();
		mock1.clear();
		mock1.clear();
	}
	
	/**
	 * Answer的用法
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testAnswer() {
		List<String> mock = mock(List.class);
		when(mock.get(anyInt())).thenAnswer(new Answer<String>() {
			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				Integer num=(Integer)args[0];
				if(num>3) {
					return "yes";
				}else {					
					return "no";
				}
			}
		});
		assertEquals("yes", mock.get(4));
		assertEquals("no", mock.get(3));
	}
	
	/**
	 * 超时验证
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testTimeOut() {
		List mock = mock(List.class);
		when(mock.get(0)).thenReturn(88,99,111);
		mock.get(0);
		mock.get(0);
		mock.get(0);
		//验证在一毫秒内是否执行了三次
		verify(mock, timeout(1).times(3)).get(0);
	}
}
