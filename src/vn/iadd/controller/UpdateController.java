package vn.iadd.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vn.iadd.model.DataShared;

public class UpdateController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//super.doGet(req, resp);
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//super.doPost(req, resp);
		doAll(req, resp);
	}
	
	private void doAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final String next = "/run_result.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(next);
		
		final String attr = "txt_name";
		String name = req.getParameter(attr);
		req.setAttribute(attr,name);
		DataShared.NAME = name;
		dispatcher.forward(req, resp);
	}

}
