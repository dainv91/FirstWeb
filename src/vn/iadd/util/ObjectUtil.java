package vn.iadd.util;

import java.lang.reflect.Field;

public class ObjectUtil {

	/**
	 * Set value for object
	 * @param obj Object
	 * @param fieldName String
	 * @param value Object
	 * @author KBSDaiNV
	 * @since 20171012
	 */
	public static void setValue(Object obj, String fieldName, Object value) {
		if (obj == null) {
			return;
		}
		Class<?> c = obj.getClass();
		try {
			Field field = c.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Try convert object to type
	 * @param obj Object
	 * @param clazz Class<T>
	 * @return T
	 * @param T type
	 */
	@SuppressWarnings("unchecked")
	public static <T> T tryGetValue(Object obj, Class<T> clazz) {
		if (obj == null) {
			return null;
		}
		T result = null;
		if (obj.getClass().isAssignableFrom(clazz)) {
			result = (T) obj;
		}
		return result;
	}
	
	/**
	 * Try close
	 * @param closeable AutoCloseable
	 */
	public static void tryClose(AutoCloseable closeable) {
		if (closeable == null) {
			return;
		}
		try {
			closeable.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
