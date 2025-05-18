package com.jardvcode.business.util;

import com.jardvcode.business.service.BookService;
import com.jardvcode.business.service.ExemplarService;
import com.jardvcode.business.service.impl.BookServiceImpl;
import com.jardvcode.business.service.impl.ExemplarServiceImpl;
import com.jardvcode.model.dao.impl.BookDaoImpl;
import com.jardvcode.model.dao.impl.ExemplarDaoImpl;
import com.jardvcode.model.util.PersistenceManager;

public final class ServiceFactory {
	
	private ServiceFactory() {
		throw new RuntimeException("Can not be instantiated");
	}
	
	public static BookService getBookService() {
		BookService bookService = new BookServiceImpl(PersistenceManager.getInstance().createEntityManagerFactory(), new BookDaoImpl());
		return bookService;
	}
	
	public static ExemplarService getExemplarService() {
		ExemplarService exemplarService = new ExemplarServiceImpl(PersistenceManager.getInstance().createEntityManagerFactory(), new ExemplarDaoImpl());
		return exemplarService;
	}

}
