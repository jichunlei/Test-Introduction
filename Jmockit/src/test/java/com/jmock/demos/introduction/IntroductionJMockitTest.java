package com.jmock.demos.introduction;

import java.util.Locale;
import org.junit.Test;
import mockit.Expectations;
/**
 * JMcokit入门介绍测试类
 * @author xianzilei
 *
 */
public class IntroductionJMockitTest {
	
	@Test
	public void testHelloJMockitInChina(){
		new Expectations(Locale.class) {
			
			{
				Locale.getDefault();
				result=Locale.CHINA;
			}
		};
		IntroductionJMockit introductionJMockitTest=new IntroductionJMockit();
		System.out.println(introductionJMockitTest.helloJMockit());
	}
	
	@Test
	public void testHelloJMockitInOthers(){
		new Expectations(Locale.class) {
			
			{
				Locale.getDefault();
				result=Locale.US;
			}
		};
		IntroductionJMockit introductionJMockitTest=new IntroductionJMockit();
		System.out.println(introductionJMockitTest.helloJMockit());
	}
}
