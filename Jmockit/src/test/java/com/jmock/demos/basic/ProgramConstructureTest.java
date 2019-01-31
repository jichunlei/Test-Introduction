package com.jmock.demos.basic;

import org.junit.Test;

import com.jmock.demos.introduction.IntroductionJMockit;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

/**
 * jmockit基础：Jmockit程序结构
 * @author xianzilei
 *
 */
public class ProgramConstructureTest {
	
	//测试属性：测试类的一个属性，作用于测试类的所有方法
	@Mocked
	private IntroductionJMockit introductionJMockit;
	
	@Test
	public void test1(){
		//1.录制方法
		new Expectations() {
			{
				introductionJMockit.helloJMockit();
				result="1";
			}
		};
		
		//2.重放方法
		String result = introductionJMockit.helloJMockit();
		System.out.println(result);
		
		//3.验证方法
		new Verifications() {	
			{
				introductionJMockit.helloJMockit();
				times=1;
			}
		};
	}
	
	
	@Test
	public void test2(@Mocked IntroductionJMockit introductionJMockit/*测试参数:即测试方法的参数,它仅作用于当前测试方法*/){
		//1.录制方法
		new Expectations() {
			{
				introductionJMockit.helloJMockit();
				result="2";
			}
		};
		
		//2.重放方法
		String result = introductionJMockit.helloJMockit();
		System.out.println(result);
		
		//3.验证方法
		new Verifications() {	
			{
				introductionJMockit.helloJMockit();
				times=1;
			}
		};
	}
	
}
