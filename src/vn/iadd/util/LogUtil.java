package vn.iadd.util;

import org.apache.logging.log4j.Level;

/**
 * LogUtil - Wrapper log4j
 * @author DaiNV
 * @since 20180626
 */
public class LogUtil {

	public static void log(org.apache.logging.log4j.Logger logger, String msg, Object...params) {
		log(logger, Level.ALL, msg, params);
	}
	
	public static void debug(org.apache.logging.log4j.Logger logger, String msg, Object...params) {
		log(logger, Level.DEBUG, msg, params);
	}
	
	public static void error(org.apache.logging.log4j.Logger logger, String msg, Object...params) {
		log(logger, Level.ERROR, msg, params);
	}
	
	public static void info(org.apache.logging.log4j.Logger logger, String msg, Object...params) {
		log(logger, Level.INFO, msg, params);
	}
	
	public static void warn(org.apache.logging.log4j.Logger logger, String msg, Object...params) {
		log(logger, Level.WARN, msg, params);
	}
	
	public static void log(org.apache.logging.log4j.Logger logger, Level level, String msg, Object...params) {
		if (logger == null) {
			return;
		}
		logger.log(level, msg, params);
	}
}
