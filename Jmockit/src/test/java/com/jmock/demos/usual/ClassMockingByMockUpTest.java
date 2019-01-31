package com.jmock.demos.usual;

import org.junit.Test;

import mockit.Mock;
import mockit.MockUp;

public class ClassMockingByMockUpTest {
	// AnOrdinaryClass的MockUp类，继承MockUp即可
	public static class AnOrdinaryClassMockUp extends MockUp<AnOrdinaryClass>{
		// mock静态方法
		@Mock
	    public static int staticMethod() {
	        return 999;
	    }
	 
	    // mock普通方法
		@Mock
	    public int ordinaryMethod() {
	        return 99;
	    }
	 
	    // mock final方法
		@Mock
	    public final int finalMethod() {
	        return 9;
	    }
	 
	    // mock native方法,返回4
		/*@Mock
	    public native int navtiveMethod();*/
	 
	    // mock private方法
		@Mock
	    private int privateMethod() {
	        return 9;
	    }
	}
	
	@Test
	public void testClassMockingByMockUp(){
		new AnOrdinaryClassMockUp();
		AnOrdinaryClass anOrdinaryClass1=new AnOrdinaryClass();
		System.out.println(AnOrdinaryClass.staticMethod());
		System.out.println(anOrdinaryClass1.ordinaryMethod());
		System.out.println(anOrdinaryClass1.finalMethod());
		//System.out.println(anOrdinaryClass1.navtiveMethod());
		System.out.println(anOrdinaryClass1.callPrivateMethod());
		
		AnOrdinaryClass anOrdinaryClass2=new AnOrdinaryClass();
		System.out.println(AnOrdinaryClass.staticMethod());
		System.out.println(anOrdinaryClass2.ordinaryMethod());
		System.out.println(anOrdinaryClass2.finalMethod());
		//System.out.println(anOrdinaryClass2.navtiveMethod());
		System.out.println(anOrdinaryClass2.callPrivateMethod());
	}
}
