package com.jmock.demos;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONObject;
/**
 * 
 * @author 郭鹏飞
 * @date  2019-01-14
 * @com   恒生电子
 *
 */
public class WorkDayTest {
	public static void main(String args[]) throws Exception {
		WorkDayTest workDayTest = new WorkDayTest();

		Integer year = 2019;
		for (Integer month = 1; month <= 3; month++) {
			List<String> days = workDayTest.getMonthFullDay(year, month);
			for (String day : days) {
				String dayStatus = workDayTest.queryIsWorkDay(day);
				System.out.println(dayStatus);
			}
		}
	}

	/**
	 * 查询是否属于工作日
	 * @param day
	 * @return
	 * @throws Exception
	 */
	public String queryIsWorkDay(String day) throws Exception {
		URL u = new URL(
				"http://api.k780.com/?app=life.workday&date="
						+ day
						+ "&appkey=39541&sign=479ab1aa3c92e279bf167b3a91e14de6&format=json");
		InputStream in = u.openStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			byte buf[] = new byte[1024];
			int read = 0;
			while ((read = in.read(buf)) > 0) {
				out.write(buf, 0, read);
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
		byte b[] = out.toByteArray();
		String resultMsg = new String(b, "utf-8");
		JSONObject jsonObject = new JSONObject(resultMsg);
		String resultCode = jsonObject.getString("success");
		if ("1".equals(resultCode)) {
			JSONObject resultJsonObject = jsonObject.getJSONObject("result");
			String workType = resultJsonObject.getString("workmk");
			workType = workType.equals("2") ? "0" : "1";
			String aaString =  "<items xsi:type=\"singleTable:MasterItem\" biz_date=\""+day+"\" workday_flag=\""+workType+"\"/>";;
			return aaString;
		} else {
			return null;
		}
	}

	/**
	 * java 获取 获取某年某月 所有日期（yyyymmdd格式字符串）
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public List<String> getMonthFullDay(int year, int month) {
		SimpleDateFormat dateFormatYYYYMMDD = new SimpleDateFormat("yyyyMMdd");
		List<String> fullDayList = new ArrayList<>(32);
		// 获得当前日期对象
		Calendar cal = Calendar.getInstance();
		cal.clear();// 清除信息
		cal.set(Calendar.YEAR, year);
		// 1月从0开始
		cal.set(Calendar.MONTH, month - 1);
		// 当月1号
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int j = 1; j <= count; j++) {
			fullDayList.add(dateFormatYYYYMMDD.format(cal.getTime()));
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		return fullDayList;
	}
}
