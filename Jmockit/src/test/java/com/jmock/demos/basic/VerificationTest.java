package com.jmock.demos.basic;

import java.util.Calendar;

import org.junit.Test;

import mockit.Expectations;
import mockit.Verifications;

/**
 * Verification测试：验证Mock对象（即@Moked/@Injectable@Capturing修饰的或传入Expectation构造函数的对象)有没有调用过某方法，调用了多少次
 * @author xianzilei
 *
 */
public class VerificationTest {
	@Test
    public void testVerification() {
        // 录制阶段
        Calendar cal = Calendar.getInstance();
        new Expectations(Calendar.class) {
            {
                // 对cal.get方法进行录制，并匹配参数 Calendar.YEAR
                cal.get(Calendar.YEAR);
                result = 9999;// 年份不再返回当前小时。而是返回2016年
                cal.get(Calendar.HOUR_OF_DAY);
                result = 99;// 小时不再返回当前小时。而是返回早上7点钟
            }
        };
        // 重放阶段
        Calendar now = Calendar.getInstance();
        System.out.println(now.get(Calendar.YEAR));
        System.out.println(now.get(Calendar.HOUR_OF_DAY));
        // 验证阶段
        new Verifications() {
            {
                Calendar.getInstance();
                // 限定上面的方法只调用了1次，当然也可以不限定
                times = 1;
                cal.get(anyInt);
                // 限定上面的方法只调用了2次，当然也可以不限定
                times = 2;
            }
        }; 
    }
}
