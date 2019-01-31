package com.jmock.demos.basic;

import java.util.Calendar;

import org.junit.Test;

import mockit.Expectations;
import mockit.Mocked;
/**
 * 通过引用外部类的Mock对象(@Injectabe,@Mocked,@Capturing)来录制
 * @author xianzilei
 *
 */
public class ExpectationsTest {
	@Mocked
	private Calendar calendar;
	
	@Test
	public void testExpectations(){
		new Expectations() {			
			{
				calendar.get(Calendar.YEAR);
				result=9012;
				
				calendar.get(Calendar.HOUR_OF_DAY);
				result=14;
			}
		};
		//第一次调用
		int year1 = calendar.get(Calendar.YEAR);
		System.out.println(year1);
		int hour1 = calendar.get(Calendar.HOUR_OF_DAY);
		System.out.println(hour1);
		//第二次调用
		int year2 = calendar.get(Calendar.YEAR);
		System.out.println(year2);
		int hour2 = calendar.get(Calendar.HOUR_OF_DAY);
		System.out.println(hour2);
	}
}
