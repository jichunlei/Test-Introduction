package com.jmock.demos.usual;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mockit.Expectations;

/**
 * 用Expectations来Mock Spring Bean
 * @author xianzilei
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class SpringBeanMockingByExpectationsTest {
	@Autowired
	private AnOrdinaryClass anOrdinaryClass;
	
	@Test
	public void testSpringBeanMockingByExpectations(){
		new Expectations(anOrdinaryClass) {
			{
				// 尽管这里也可以Mock静态方法，但不推荐在这里写。静态方法的Mock应该是针对类的
                // mock普通方法
				anOrdinaryClass.ordinaryMethod();
                result = 999;
                // mock final方法
                anOrdinaryClass.finalMethod();
                result = 888;
                // native, private方法无法用Expectations来Mock
			}
		};
		System.out.println(anOrdinaryClass.ordinaryMethod());
		System.out.println(anOrdinaryClass.finalMethod());
		//System.out.println(anOrdinaryClass.navtiveMethod());
		// 用Expectations无法mock private方法
		System.out.println(anOrdinaryClass.callPrivateMethod());
	}
}
