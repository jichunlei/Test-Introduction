package com.jmock.demos.introduction;

import java.util.Locale;

/**
 * mock入门介绍
 * @author xianzilei
 *
 */
public class IntroductionJMockit {
	public String helloJMockit(){
		Locale locale=Locale.getDefault();
		if(locale.equals(Locale.CHINA)){
			return "你好，JMockit";
		}else{
			return "Hello JMockit";
		}
	}
}
