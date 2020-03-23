package common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	private CommonUtil() {
	    throw new AssertionError();
	}

	/**
	 * 字符串转日期
	 * @param str 字符串形式的日期
	 * @return 日期对象
	 */
	public static Date stringToDate(String str) {
	    try {
	        return formatter.parse(str);
	    } catch (ParseException e) {
	        throw new RuntimeException(e);
	    }
	}

	/**
	 * 日期转字符串
	 * @param date 日期对象
	 * @return 字符串形式的日期
	 */
	public static String dateToString(Date date) {
	    return formatter.format(date);
	}

}
