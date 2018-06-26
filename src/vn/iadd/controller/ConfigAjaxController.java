package vn.iadd.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import vn.iadd.helper.DbHelper;
import vn.iadd.model.DataShared;
import vn.iadd.model.OimWsConfiguration;
import vn.iadd.util.JsonUtil;
import vn.iadd.util.LogUtil;
import vn.iadd.util.ObjectUtil;
import vn.iadd.util.StringUtil;

public class ConfigAjaxController extends HttpServlet {
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 1L;

	final Logger logger = LogManager.getLogger(this.getClass());
	
	private static String targetSystem; 

	@Override
	public void init() throws ServletException {
		super.init();
		targetSystem = getServletContext().getInitParameter("TARGET_SYSTEM");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doAll(req, resp);
	}

	private DbHelper getDbHelper() {
		ServletContext ctx = getServletContext();
		return ObjectUtil.tryGetValue(ctx.getAttribute(DataShared.C_DB_HELPER), DbHelper.class);
	}
	
	private boolean isLogged(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Object user = session.getAttribute("user");
		if (user == null) {
			return false;
		}
		return true;
	}
	
	private void doAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!isLogged(req)) {
			//wrongUserPass(req, resp);
			resp.getWriter().write(Boolean.FALSE.toString());
			return;
		}
		String action = req.getParameter("action");
		
		String keyStr = req.getParameter("key");
		int key = StringUtil.toNumber(keyStr, 0);
		String name = req.getParameter("name");
		String value = req.getParameter("value");
		
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		LogUtil.info(logger, "ConfigAjax action: [{}], key: [{}], name: [{}], value: [{}]", action, keyStr, name, value);
		if (action != null) {
			if (action.equalsIgnoreCase("update")) {
				boolean updated = DataShared.updateConfig(getDbHelper(), targetSystem, key, name, value);
				resp.getWriter().write(JsonUtil.toJson(updated));
				return;
			} else if (action.equals("add")) {
				OimWsConfiguration obj = OimWsConfiguration.fromMapProperties(getParameterAsMap(req));
				obj.setTargetSystem(targetSystem);
				boolean updated = DataShared.addConfig(getDbHelper(), targetSystem, obj);
				resp.getWriter().write(JsonUtil.toJson(updated));
				return;
			} else if (action.equals("delete")) {
					OimWsConfiguration obj = OimWsConfiguration.fromMapProperties(getParameterAsMap(req));
					obj.setTargetSystem(targetSystem);
					boolean updated = DataShared.delConfig(getDbHelper(), targetSystem, obj);
					resp.getWriter().write(JsonUtil.toJson(updated));
					return;
			}
		}
		resp.getWriter().write(getConfig(getForce(req)));
	}
	
	private Map<String, Object> getParameterAsMap(HttpServletRequest req) {
		Map<String, Object> map = new HashMap<>();
		Map<String, String[]> parameters = req.getParameterMap();
		for (String key: parameters.keySet()) {
			String[] values = parameters.get(key);
			if (values != null && values.length > 0) {
				map.put(key, values[0]);
			}
		}
		return map;
	}
	
	private boolean getForce(HttpServletRequest req) {
		boolean force = false;
		String f = req.getParameter("force");
		force = Boolean.parseBoolean(f);
		return force;
	}
	
	private String getConfig(boolean force) {
		String json = null;
		json = JsonUtil.toJson(DataShared.getAllConfigs(getDbHelper(), targetSystem, force));
		return json;
	}
}
