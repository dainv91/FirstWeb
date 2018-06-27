package vn.iadd.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import vn.iadd.helper.DbHelper;
import vn.iadd.model.DataShared;
import vn.iadd.util.LogUtil;
import vn.iadd.util.ObjectUtil;
import vn.iadd.util.ServletUtil;
import vn.iadd.util.StringUtil;

public class LoginController extends HttpServlet {

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doAll(req, resp);
	}

	public DbHelper getDbHelper() {
		ServletContext ctx = getServletContext();
		return ObjectUtil.tryGetValue(ctx.getAttribute(DataShared.C_DB_HELPER), DbHelper.class);
	}
	
	private void sendViewOnly(HttpServletRequest req, HttpServletResponse resp) {
		ServletUtil.sendViewIndex(getServletContext(), req, resp, "/index.html");
	}
	
	private void wrongUserPass(HttpServletRequest req, HttpServletResponse resp, boolean needAdlert) {
		PrintWriter out;
		if (!needAdlert) {
			sendViewOnly(req, resp);
			return;
		}
		try {
			out = resp.getWriter();
			final String script = "<script>alert('Either user name or password is wrong');</script>";
			//out.println("<font color=red>Either user name or password is wrong.</font>");
			out.println(script);
			sendViewOnly(req, resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void doAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final Logger logger = LogManager.getLogger(this.getClass());
		ServletContext ctx = getServletContext();
		String user = req.getParameter("txt_user");
		String pass = req.getParameter("txt_pass");
		
		LogUtil.debug(logger, "Login request with user [{}], pass [{}]", user, pass);
		
		String initUser = ctx.getInitParameter("ADMIN_USER");
		String initPass = ctx.getInitParameter("ADMIN_PASS");
		
		if (StringUtil.isEmpty(user) && StringUtil.isEmpty(pass)) {
			sendViewOnly(req, resp);
			return;
		}
		
		if (!initUser.equalsIgnoreCase(user)) {
			wrongUserPass(req, resp, true);
			return;
		}
		if (!initPass.equals(pass)) {
			wrongUserPass(req, resp, true);
			return;
		}
		HttpSession session = req.getSession();
		session.setAttribute("user", user);
		Cookie loginCookie = new Cookie("user", user);
		//setting cookie to expiry in 30 mins
		loginCookie.setMaxAge(30*60);
		resp.addCookie(loginCookie);
		resp.sendRedirect("list.html");
	}
}
