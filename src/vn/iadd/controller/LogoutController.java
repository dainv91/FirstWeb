package vn.iadd.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vn.iadd.util.ServletUtil;

/**
 * Logout controller
 * @author DaiNV
 * @since 20180627
 */
public class LogoutController extends HttpServlet {

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
		HttpSession session = req.getSession();
		Object user = session.getAttribute("user");
		if (user == null) {
			// Not login
			ServletUtil.sendViewIndex(getServletContext(), req, resp);
			return;
		}
		session.invalidate();
		final String script = "<script>alert('Logout success');</script>";
		resp.getWriter().println(script);
		ServletUtil.clearCookies(req, resp);
		ServletUtil.sendViewIndex(getServletContext(), req, resp);
	}
}
