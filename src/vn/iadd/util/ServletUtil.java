package vn.iadd.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet util
 * @author DaiNV
 * @since 20180627
 */
public class ServletUtil {

	public static void sendViewIndex(ServletContext ctx, HttpServletRequest req, HttpServletResponse resp) {
		sendViewIndex(ctx, req, resp, "/index.html");
	}
	
	public static void sendViewIndex(ServletContext ctx, HttpServletRequest req, HttpServletResponse resp, String view) {
		if (ctx == null) {
			return;
		}
		try {
			RequestDispatcher rd = ctx.getRequestDispatcher(view);
			rd.include(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void clearCookies(HttpServletRequest req, HttpServletResponse resp) {
		if (req == null || resp == null) {
			return;
		}
		Cookie[] cookies = req.getCookies();
		if (cookies == null || cookies.length == 0) {
			return;
		}
		for (Cookie c: cookies) {
			c.setMaxAge(0);
			resp.addCookie(c);
		}
	}
}
