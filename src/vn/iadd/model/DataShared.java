package vn.iadd.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import vn.iadd.helper.DbHelper;
import vn.iadd.util.ObjectUtil;
import vn.iadd.util.StringUtil;

public class DataShared {
	
	public static final String C_DB_HELPER = "DBHelper";
	
	public static String NAME;
	
	private static final Map<String, OimWsConfiguration> mapConfigs = new LinkedHashMap<>();
	
	public static void load(DbHelper helper, String targetSystem) {
		getConfigInDb(helper, targetSystem);
	}
	
	public static Collection<OimWsConfiguration> getAllConfigs(DbHelper dbHelper, String targetSystem, boolean force) {
		if (force) {
			return getConfigInDb(dbHelper, targetSystem);
		}
		return getConfigInCache();
	}
	
	private static Collection<OimWsConfiguration> getConfigInDb(DbHelper helper, String targetSystem) {
		mapConfigs.clear();
		final String query = "select * from oim_ws_configuration where target_system = " + StringUtil.escape(targetSystem) + " order by id asc";
		Map<String, Map<String, Object>> result = helper.execQueryReturnMap(query);
		tryClose(helper);
		if (result.isEmpty()) {
			return mapConfigs.values();
		}
		Iterator<Map<String, Object>> it = result.values().iterator();
		while (it.hasNext()) {
			Map<String, Object> map = it.next();
			OimWsConfiguration obj = OimWsConfiguration.fromMapDb(map);
			mapConfigs.put(String.valueOf(obj.getId()), obj);
		}
		return mapConfigs.values();
	}
	
	private static Collection<OimWsConfiguration> getConfigInCache() {
		return mapConfigs.values();
	}

	public static boolean updateConfig(DbHelper helper, String targetSystem, int key, String name, Object value) {
		boolean result = true;
		OimWsConfiguration obj = new OimWsConfiguration();
		obj.setId(key);
		obj.setTargetSystem(targetSystem);
		ObjectUtil.setValue(obj, name, value);
		String query = obj.toUpdateQuery();
		if (StringUtil.isEmpty(query)) {
			return false;
		}
		result = helper.execUpdate(query);
		load(helper, targetSystem);
		return result;
	}
	
	public static boolean addConfig(DbHelper helper, String targetSystem, OimWsConfiguration obj) {
		boolean result = true;
		String query = obj.toInsertQuery();
		if (StringUtil.isEmpty(query)) {
			return false;
		}
		result = helper.execUpdate(query);
		load(helper, targetSystem);
		return result;
	}
	public static boolean delConfig(DbHelper helper, String targetSystem, OimWsConfiguration obj) {
		boolean result = true;
		String query = obj.toDeleteQuery();
		if (StringUtil.isEmpty(query)) {
			return false;
		}
		result = helper.execUpdate(query);
		load(helper, targetSystem);
		return result;
	}
	
	private static void tryClose(AutoCloseable closeable) {
		ObjectUtil.tryClose(closeable);
	}
	
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		sb.append("name = 1,");
		sb.append("value = 2,");
		sb.delete(sb.length() - 1, sb.length());
		System.out.println(sb.toString());
	}
}
