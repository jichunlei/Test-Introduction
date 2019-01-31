package com.jmock.demos.usual;

import org.junit.Test;

import mockit.Mock;
import mockit.MockUp;

/**
 * 用MockUp来mock接口
 * @author xianzilei
 *
 */
public class InterfaceMockingByMockUpTest {
	@Test
	public void testInterfaceMockingByMockUp(){
		//
		AnOrdinaryInterface anOrdinaryInterface=new MockUp<AnOrdinaryInterface>(AnOrdinaryInterface.class) {
			// 对方法Mock
	        @Mock
	        public int method1() {
	            return 888;
	        }

	        @Mock
	        public int method2() {
	            return 88;
	        }
		}.getMockInstance();
		System.out.println(anOrdinaryInterface.method1());
		System.out.println(anOrdinaryInterface.method2());
	}
}
