package vn.iadd.util;

import java.util.List;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonUtil {

	private static Gson gson = null;
	
	private static Gson getGson() {
		if (gson == null) {
			gson = new GsonBuilder().serializeNulls().create();
		}
		return gson;
	}
	
	public static String toJson(Object obj) {
		if (obj == null || obj.toString().equals("")) {
			return null;
		}
		String json = getGson().toJson(obj);
		return json;
	}
	
	public static <T> T fromJson(String json, Class<T> clazz) {
		Type t = new TypeToken<T>() {}.getType();
		return fromJson(json, t);
	}
	
	public static <T> T fromJson(String json, Type type) {
		T obj = null;
		try {
			obj = getGson().fromJson(json, type);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return obj;
	}
	
	public static void main(String[] args) {
		String str = "[{\"name\":\"iPad 5\", \"addr\":\"HN\",\"qty\":100},{\"name\":\"iPad 4\",\"qty\":100}]";
		List<?> obj = fromJson(str, List.class);
		System.out.println(obj);
	}
}
