package com.jmock.demos.usual;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mockit.Mock;
import mockit.MockUp;

/**
 * 用Expectations来Mock Spring Bean
 * @author xianzilei
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class SpringBeanMockingByMockUpTest {
	@Autowired
	private AnOrdinaryClass anOrdinaryClass;
	
	// 对AnOrdinaryClass的Class
    public static class AnOrdinaryClassMockUp extends MockUp<AnOrdinaryClass> {
        // Mock静态方法
        @Mock
        public static int staticMethod() {
            return 999;
        }
 
        // Mock普通方法
        @Mock
        public int ordinaryMethod() {
            return 888;
        }
 
        @Mock
        // Mock final方法
        public final int finalMethod() {
            return 777;
        }
 
        // Mock native方法
        @Mock
        public int navtiveMethod() {
            return 666;
        }
 
        // Mock private方法
        @Mock
        private int privateMethod() {
            return 555;
        }
    }
	
    @BeforeClass
    // 必须在Spring容器初始化前，就对Spring Bean的类做MockUp
    public static void init(){
    	new AnOrdinaryClassMockUp();
    }
    
	@Test
	public void testSpringBeanMockingByExpectations(){
		System.out.println(AnOrdinaryClass.staticMethod());
		System.out.println(anOrdinaryClass.ordinaryMethod());
		System.out.println(anOrdinaryClass.finalMethod());
		System.out.println(anOrdinaryClass.navtiveMethod());
		System.out.println(anOrdinaryClass.callPrivateMethod());
	}
}
