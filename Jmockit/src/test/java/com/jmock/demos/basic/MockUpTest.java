package com.jmock.demos.basic;

import java.util.Calendar;
import java.util.Locale;

import org.junit.Test;

import mockit.Mock;
import mockit.MockUp;


/**
 * MockUp注解测试
 * 缺陷：   1.一个类有多个实例。只对其中某1个实例进行mock。 最新版的JMockit已经让MockUp不再支持对实例的Mock了。1.19之前的老版本仍支持。
		2.AOP动态生成类的Mock。
		3.对类的所有方法都需要Mock时，书写MockUp的代码量太大。比如web程序中，经常需要对HttpSession进行Mock。若用MockUp你要写大量的代码，可是用@Mocked就一行代码就可以搞定。
 * @author xianzilei
 *
 */
public class MockUpTest {
	@Test
	public void testMockUp(){
		new MockUp<Calendar>(Calendar.class) {
			@Mock
			public int get(int unit){
				if (unit == Calendar.YEAR) {
                    return 9999;
                }
                if (unit == Calendar.MONDAY) {
                    return 999;
                }
                if (unit == Calendar.DAY_OF_MONTH) {
                    return 99;
                }
                if (unit == Calendar.HOUR_OF_DAY) {
                    return 9;
                }
                return 0;
			}
		};
		Calendar calendar = Calendar.getInstance(Locale.FRANCE);
		System.out.println(calendar.get(Calendar.YEAR));
		System.out.println(calendar.get(Calendar.MONDAY));
		System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
		System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
		System.out.println(calendar.get(Calendar.ERA));
		//其他方法不受影响
		System.out.println(calendar.getFirstDayOfWeek());
		
		//创建个新对象，还是被mock了
		Calendar calendar2 = Calendar.getInstance(Locale.FRANCE);
		System.out.println(calendar2.get(Calendar.YEAR));
		System.out.println(calendar2.get(Calendar.MONDAY));
		System.out.println(calendar2.get(Calendar.DAY_OF_MONTH));
		System.out.println(calendar2.get(Calendar.HOUR_OF_DAY));
		System.out.println(calendar2.get(Calendar.ERA));
		//其他方法不受影响
		System.out.println(calendar2.getFirstDayOfWeek());
	}
}
