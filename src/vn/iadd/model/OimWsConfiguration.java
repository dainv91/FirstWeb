package vn.iadd.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import vn.iadd.util.StringUtil;

public class OimWsConfiguration implements Serializable {

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String EMPTY = "";

	public static final String TABLE_NAME = "OIM_WS_CONFIGURATION";

	private int id;
	private String name;
	private String value;
	private String dataType;
	private String targetSystem;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getTargetSystem() {
		return targetSystem;
	}

	public void setTargetSystem(String targetSystem) {
		this.targetSystem = targetSystem;
	}

	public String toUpdateQuery() {
		if (this.id == 0) {
			return EMPTY;
		}
		Map<String, String> mapData = new HashMap<>();
		
		StringBuilder sb = new StringBuilder();
		sb.append("update oim_ws_configuration ");
		sb.append(" set ");
		if (name != null) {
			mapData.put("NAME", name);
		}
		
		if (value != null) {
			mapData.put("VALUE", value);
		}
		
		if (dataType != null) {
			mapData.put("DATA_TYPE", dataType);
		}
		
		if (targetSystem != null) {
			mapData.put("TARGET_SYSTEM", targetSystem);
		}
		if (mapData.isEmpty()) {
			return null;
		}
		sb.append(mapToQueryString(mapData));
		sb.append(" where id = ").append(id);
		sb.append(System.lineSeparator());
		String query = sb.toString();
		return query;
	}
	
	public String toInsertQuery() {
		if (this.id == 0) {
			return EMPTY;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("insert into oim_ws_configuration(");
		sb.append("id, name, value, data_type, target_system");
		sb.append(") values (");
		sb.append(id).append(",");
		sb.append(StringUtil.escape(name)).append(",");
		sb.append(StringUtil.escape(value)).append(",");
		sb.append(StringUtil.escape(dataType)).append(",");
		sb.append(StringUtil.escape(targetSystem));
		sb.append(")");
		sb.append(System.lineSeparator());
		String query = sb.toString();
		return query;
	}
	
	public String toDeleteQuery() {
		if (this.id == 0) {
			return EMPTY;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("delete oim_ws_configuration ");
		sb.append("where 1=1 ");
		if (targetSystem != null) {
			sb.append(" and target_system = ").append(StringUtil.escape(targetSystem));
		}
		sb.append(" and id = ").append(id);
		sb.append(System.lineSeparator());
		String query = sb.toString();
		return query;
	}
	
	public static OimWsConfiguration fromMapDb(Map<String, Object> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		OimWsConfiguration obj = new OimWsConfiguration();
		obj.setId(toNumber(map.get("ID") + ""));
		obj.setName(convert(map.get("NAME"), String.class));
		obj.setValue(convert(map.get("VALUE"), String.class));
		obj.setDataType(convert(map.get("DATA_TYPE"), String.class));
		obj.setTargetSystem(convert(map.get("TARGET_SYSTEM"), String.class));
		return obj;
	}
	
	public static OimWsConfiguration fromMapProperties(Map<String, Object> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		OimWsConfiguration obj = new OimWsConfiguration();
		obj.setId(toNumber(map.get("id") + ""));
		obj.setName(convert(map.get("name"), String.class));
		obj.setValue(convert(map.get("value"), String.class));
		obj.setDataType(convert(map.get("dataType"), String.class));
		obj.setTargetSystem(convert(map.get("targetSystem"), String.class));
		return obj;
	}
	
	private String mapToQueryString(Map<String, String> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (String key: map.keySet()) {
			sb.append(key).append(" = ");
			sb.append(StringUtil.escape(map.get(key)));
			sb.append(",");
		}
		sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	private static <T>T convert(Object obj, Class<T> c) {
		if (obj == null) {
			return null;
		}
		Class<?> clazz = obj.getClass();
		if (clazz.isAssignableFrom(c)) {
			return (T) obj;
		}
		return null;
	}
	
	private static int toNumber(String str) {
		int num = 0;
		if (str == null || str.trim().isEmpty()) {
			return num;
		}
		try {
			num = Integer.parseInt(str);
		} catch (Exception ex) {
			// Skip
		}
		return num;
	}

	@Override
	public String toString() {
		return "OimWsConfiguration [id=" + id + ", name=" + name + ", value=" + value + ", dataType=" + dataType
				+ ", targetSystem=" + targetSystem + "]";
	}
}
