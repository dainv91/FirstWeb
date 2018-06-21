package vn.iadd.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * Config utils
 * 
 * @author DaiNV
 * @since 20180411
 */
public class ConfigUtils {

	/**
	 * Config name
	 */
	public static final String CONFIG_NAME = "/application.properties";
	
	/**
	 * Properties
	 */
	private static Properties props;
	
	static {
		//loadProp(CONFIG_NAME);
	}
	
	/**
	 * Load all properties
	 * @author DaiNV
	 * @since 20180411
	 */
	public static void loadProp(String configName) {
		props = new Properties();
		try {
			props.load(ConfigUtils.class.getResourceAsStream(configName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Add load using input stream
	 * @param input InputStream
	 * @author DaiNV
	 * @since 20180620
	 */
	public static void loadProp(InputStream input) {
		props = new Properties();
		try {
			props.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get config using key
	 * @param key String
	 * @return String
	 * @author DaiNV
	 * @since 20180411
	 */
	public static String getConfig(String key) {
		return props.getProperty(key);
	}
	
	/**
	 * Get all configs
	 * @return Properties
	 * @author DaiNV
	 * @since 20180620
	 */
	public static Properties getConfigs() {
		return props;
	}
	
	/**
	 * Update config value
	 * @param key String
	 * @param value String
	 * @author DaiNV
	 * @since 20180608
	 */
	public static void setConfig(String key, String value) {
		props.setProperty(key, value);
	}
	
	/**
	 * Save config files
	 * @author DaiNV
	 * @throws URISyntaxException 
	 * @since 20180608
	 */
	public static void save() throws IOException, URISyntaxException {
		FileOutputStream out = new FileOutputStream(new File(ConfigUtils.class.getResource(CONFIG_NAME).toURI()));
		props.store(out, null);
	}
	
	static void testSave() throws Exception {
		ConfigUtils.loadProp(CONFIG_NAME);
		final String key = "WS_SERVER_LOCATION";
		String result = getConfig(key);
		System.out.println(result);
		setConfig(key, "value2 updated");
		save();
	}
	
	public static void main(String[] args) throws Exception {
		testSave();
	}
}
