package com.jmock.demos.basic;

import java.util.Calendar;

import org.junit.Test;

import mockit.Expectations;

/**
 * 通过构建函数注入类/对象来录制
 * @author xianzilei
 *
 */
public class ExpectationsConstructorTest {
	@Test
	public void testExpectationsConstructor1() {
		//Calendar是一个抽象类，不能直接实例化，Java提供了静态工厂方法getInstance()来实例化Calendar
		Calendar calendar=Calendar.getInstance();
		//传递类：可以达到只mock类的部分行为的目的
		new Expectations(Calendar.class) {
			{
				calendar.get(Calendar.YEAR);
				result=9999;
			}
		};
		Calendar now=Calendar.getInstance();
		//返回mock结果
		int year = now.get(Calendar.YEAR);
		System.out.println(year);
		//未mock的方法不受影响
		int hour = now.get(Calendar.HOUR_OF_DAY);
		System.out.println(hour);
		
	}
	
	@Test
	public void testExpectationsConstructor2() {
		//Calendar是一个抽象类，不能直接实例化，Java提供了静态工厂方法getInstance()来实例化Calendar
		Calendar calendar=Calendar.getInstance();
		//传递对象：可以达到只mock类的部分行为的目的，但只对这个对象影响
		new Expectations(calendar) {
			{
				calendar.get(Calendar.YEAR);
				result=9999;
			}
		};
		//返回mock结果
		int year1 = calendar.get(Calendar.YEAR);
		System.out.println(year1);
		//未mock的方法不受影响
		int hour1 = calendar.get(Calendar.HOUR_OF_DAY);
		System.out.println(hour1);
		
		//创建一个新的对象now
		Calendar now=Calendar.getInstance();
		//方法不受影响
		int year2 = now.get(Calendar.YEAR);
		System.out.println(year2);
		//方法不受影响
		int hour2 = now.get(Calendar.HOUR_OF_DAY);
		System.out.println(hour2);	
	}
}
