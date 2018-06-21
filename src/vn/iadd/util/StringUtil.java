package vn.iadd.util;

public class StringUtil {

	public static boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}
	
	public static String escape(Object obj) {
		if (obj == null) {
			return "NULL";
		}
		return "'" + obj + "'";
	}
	
	public static int toNumber(String str, int defaultValue) {
		if (str == null || str.trim().isEmpty()) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(str);
		} catch (Exception ex) {
			// Skip
		}
		return defaultValue;
	}
}
