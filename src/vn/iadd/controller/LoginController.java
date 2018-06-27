package vn.iadd.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
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

	private void wrongUserPass(HttpServletRequest req, HttpServletResponse resp) {
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.html");
		PrintWriter out;
		try {
			out = resp.getWriter();
			final String script = "<script>alert('Either user name or password is wrong');</script>";
			//out.println("<font color=red>Either user name or password is wrong.</font>");
			out.println(script);
			rd.include(req, resp);
		} catch (IOException | ServletException e) {
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
		
		if (!initUser.equalsIgnoreCase(user)) {
			wrongUserPass(req, resp);
			return;
		}
		if (!initPass.equals(pass)) {
			wrongUserPass(req, resp);
			return;
		}
		HttpSession session = req.getSession();
		session.setAttribute("user", user);
		Cookie loginCookie = new Cookie("user",user);
		//setting cookie to expiry in 30 mins
		loginCookie.setMaxAge(30*60);
		resp.addCookie(loginCookie);
		resp.sendRedirect("list.html");
	}
}
