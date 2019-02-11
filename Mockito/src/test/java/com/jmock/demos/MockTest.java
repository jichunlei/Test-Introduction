package com.jmock.demos;
/**
 * mock注解测试
 * @author xianzilei
 *
 */
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.mockito.demos.Person;

public class MockTest {
	@Mock
	private Person mock;
	
	@Before
	public void initMocks() {
		//必须,否则注解无效
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testMock() {
		when(mock.method("1")).thenReturn("2");
		assertEquals("2", mock.method("1"));
		verify(mock).method("1");
	}
}
