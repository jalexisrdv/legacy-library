package com.jardvcode.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jardvcode.business.exception.LibraryException;

@WebServlet(value = "/example")
public class ExampleServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	static Log logger = LogFactory.getLog(ExampleServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setAttribute("message", "Hello World!");
			RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/jsp/example.jsp");
			rd.forward(request, response);
		} catch (LibraryException e) {
			request.setAttribute("error", "Error message");
			logger.error(e.getMessage(), e);
			RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/jsp/error.jsp");
			rd.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}