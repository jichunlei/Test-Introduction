package com.jmock.demos.usual;

import org.junit.Test;

import mockit.Expectations;

/**
 * mock实例测试
 * @author xianzilei
 *
 */
public class InstanceMockingByExpectationsTest {
	@Test
	public void testInstanceMockingByExpectations(){
		AnOrdinaryClass anOrdinaryClass=new AnOrdinaryClass();
		new Expectations(anOrdinaryClass) {
			{
				// 尽管这里也可以Mock静态方法，但不推荐在这里写。静态方法的Mock应该是针对类的
                // mock普通方法
				anOrdinaryClass.ordinaryMethod();
                result = 999;
                // mock final方法
                anOrdinaryClass.finalMethod();
                result = 999;
                // native, private方法无法用Expectations来Mock
			}
		};
		System.out.println(anOrdinaryClass.ordinaryMethod());
		System.out.println(anOrdinaryClass.finalMethod());
		//System.out.println(anOrdinaryClass.navtiveMethod());
		System.out.println(anOrdinaryClass.callPrivateMethod());
		
		AnOrdinaryClass anOrdinaryClass2=new AnOrdinaryClass();
		System.out.println(AnOrdinaryClass.staticMethod());
		System.out.println(anOrdinaryClass2.ordinaryMethod());
		System.out.println(anOrdinaryClass2.finalMethod());
		//System.out.println(anOrdinaryClass2.navtiveMethod());
		System.out.println(anOrdinaryClass2.callPrivateMethod());
	}
	
}
