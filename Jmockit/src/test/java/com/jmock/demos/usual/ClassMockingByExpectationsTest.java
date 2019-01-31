package com.jmock.demos.usual;

import org.junit.Test;

import mockit.Expectations;

/**
 * Mock类测试
 * @author xianzilei
 *
 */
public class ClassMockingByExpectationsTest {
	
	@Test
	public void testClassMockingByExpectations(){
		AnOrdinaryClass anOrdinaryClass1=new AnOrdinaryClass();
		new Expectations(AnOrdinaryClass.class) {
			{
				//mock静态方法
				AnOrdinaryClass.staticMethod();
				result=99;
				//mock普通方法
				anOrdinaryClass1.ordinaryMethod();
				result=999;
				//mock final方法
				anOrdinaryClass1.finalMethod();
				result=9999;
				// native, private方法无法用Expectations来Mock
			}
		};
		AnOrdinaryClass anOrdinaryClass2=new AnOrdinaryClass();
		System.out.println(AnOrdinaryClass.staticMethod());
		System.out.println(anOrdinaryClass2.ordinaryMethod());
		System.out.println(anOrdinaryClass2.finalMethod());
		//System.out.println(anOrdinaryClass2.navtiveMethod());
		System.out.println(anOrdinaryClass2.callPrivateMethod());
		System.out.println(Integer.MAX_VALUE);
	}
	
	 /*@BeforeClass    
	    // 加载AnOrdinaryClass类的native方法的native实现    
	    public static void loadNative() throws Throwable {    
	        JNITools.loadNative();    
	    } */
}
