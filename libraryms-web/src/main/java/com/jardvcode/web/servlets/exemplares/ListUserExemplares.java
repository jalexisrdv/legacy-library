package com.jardvcode.web.servlets.exemplares;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jardvcode.business.exception.LibraryException;
import com.jardvcode.business.service.ExemplarService;
import com.jardvcode.business.util.ServiceFactory;
import com.jardvcode.model.entity.ExemplarEntity;
import com.jardvcode.web.dto.exemplar.ExemplarDTO;
import com.jardvcode.web.mapper.ExemplarMapper;

@WebServlet(value = "/user-exemplares")
public class ListUserExemplares extends HttpServlet {

	private static final long serialVersionUID = 1L;
	static Log logger = LogFactory.getLog(ListUserExemplares.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ExemplarService exemplarService = ServiceFactory.getExemplarService();
			List<ExemplarEntity> exemplares = exemplarService.findExemplaresByUserId(1L);
			List<ExemplarDTO> dtoExemplares = new ExemplarMapper().fromEntity(exemplares);
			request.setAttribute("exemplares", dtoExemplares);
			RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/jsp/exemplar/UserExemplares.jsp");
			rd.forward(request, response);
		} catch (LibraryException e) {
			request.setAttribute("error", "Error listing books");
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
