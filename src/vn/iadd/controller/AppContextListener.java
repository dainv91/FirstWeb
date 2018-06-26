package vn.iadd.controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import vn.iadd.helper.DbHelper;
import vn.iadd.model.DataShared;
import vn.iadd.util.ConfigUtils;
import vn.iadd.util.LogUtil;
import vn.iadd.util.ObjectUtil;

@WebListener
public class AppContextListener implements ServletContextListener {
	
	final Logger logger = LogManager.getLogger(this.getClass());
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		final String path = "/WEB-INF/" + ConfigUtils.CONFIG_NAME;
		ServletContext ctx = sce.getServletContext();
		
		// 1. Load configuration file
		ConfigUtils.loadProp(ctx.getResourceAsStream(path));
		LogUtil.info(logger, "Configurations: [{}]", ConfigUtils.getConfigs());
		//System.out.println(ConfigUtils.CONFIG_NAME + " loaded.");
		
		// 2. Create DbHelper object
		DbHelper dbHelper = new DbHelper(ConfigUtils.getConfig("DB_HOST"), ConfigUtils.getConfig("DB_USER"), ConfigUtils.getConfig("DB_PASS"));
		
		// 3. Load data first
		final String target = ctx.getInitParameter("TARGET_SYSTEM");
		LogUtil.info(logger, "Target system [{}]", target);
		DataShared.load(dbHelper, target);
		
		// 4. Store DbHelper object to Servlet context
		ctx.setAttribute(DataShared.C_DB_HELPER, dbHelper);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ServletContext ctx = sce.getServletContext();
		DbHelper dbHelper = ObjectUtil.tryGetValue(ctx.getAttribute(DataShared.C_DB_HELPER), DbHelper.class);
		ObjectUtil.tryClose(dbHelper);
	}
}
