package com.jmock.demos.usual;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;

/**
 * 用Expectations来mock接口
 * @author xianzilei
 *
 */
public class InterfaceMockingByExpectationsTest {
	@Injectable
	private AnOrdinaryInterface anOrdinaryInterface;
	
	@Test
	public void testInterfaceMockingByExpectations(){
		new Expectations() {
			{
				anOrdinaryInterface.method1();
				result=999;
				anOrdinaryInterface.method2();
				result=888;
			}
		};
		System.out.println(anOrdinaryInterface.method1());
		System.out.println(anOrdinaryInterface.method2());
	}
}
