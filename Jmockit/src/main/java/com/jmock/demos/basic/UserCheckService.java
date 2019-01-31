package com.jmock.demos.basic;

/**
 * 用户身份校验类
 * @author xianzilei
 *
 */
public interface UserCheckService {

	/**
	 * 校验某个用户是否是合法用户
	 * 
	 * @param userId
	 *            用户ID
	 * @return 合法的就返回true,否则返回false 
	 */
	public boolean check(long userId);
}
