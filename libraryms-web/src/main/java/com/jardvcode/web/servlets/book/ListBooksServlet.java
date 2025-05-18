package com.jardvcode.web.servlets.book;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jardvcode.business.service.BookService;
import com.jardvcode.business.util.ServiceFactory;
import com.jardvcode.model.entity.BookEntity;
import com.jardvcode.web.WebConfiguration;
import com.jardvcode.web.dto.BookDTO;
import com.jardvcode.web.mapper.BookMapper;
import com.jardvcode.web.shared.domain.ParameterError;
import com.jardvcode.web.util.RegexUtil;

@WebServlet(value = "/books")
public class ListBooksServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static Log logger = LogFactory.getLog(ListBooksServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String titleParameter = request.getParameter("title");
			String pageParameter = (String) request.getParameter("page");
			
			boolean wasGetMethodSentFromForm = request.getParameterMap().size() >= 1 && pageParameter == null;
			if(titleParameter == null) titleParameter = "";
			if(pageParameter == null) pageParameter = "1";
			
			Map<String, ParameterError> validationErrors = new HashMap<>();
			
			if(!RegexUtil.match("[1-9][0-9]*", pageParameter)) {
				validationErrors.put("page", new ParameterError(pageParameter, "page value is not valid"));
				pageParameter = "1";
			}
			
			if(wasGetMethodSentFromForm && !RegexUtil.match("[a-zA-Z][a-zA-Z ]+", titleParameter)) {
				validationErrors.put("title", new ParameterError(titleParameter, "title value is not valid"));
				titleParameter = "";
			}
			
			request.setAttribute("validationErrors", validationErrors);
			request.setAttribute("titleParameter", titleParameter);
			
			Integer page = Integer.valueOf(pageParameter);
			
			BookService bookService = ServiceFactory.getBookService();
			
			Integer paginationSize = WebConfiguration.PAGINATION_SIZE;
			Long pageNumber = bookService.countSearchedBooksByTitle(titleParameter);
			pageNumber = WebConfiguration.getPaginationSize(pageNumber);
			
			List<BookEntity> books = bookService.searchBooksByTitle(titleParameter, (page - 1) * paginationSize, paginationSize);
			List<BookDTO> dtoBooks = new BookMapper().fromEntity(books);
			
			request.setAttribute("pageNumber", pageNumber);
			request.setAttribute("books", dtoBooks);
			
			RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/jsp/book/Books.jsp");
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
			request.setAttribute("error", "Error listing books");
			logger.error(e.getMessage(), e);
			RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/jsp/error.jsp");
			requestDispatcher.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	

}
