package com.jardvcode.business.service.impl;

import javax.persistence.EntityManagerFactory;

import com.jardvcode.business.service.BookService;
import com.jardvcode.model.dao.BookDao;
import com.jardvcode.model.entity.BookEntity;

public class BookServiceImpl implements BookService {
	
	private EntityManagerFactory entityManagerFactory;
	
	private BookDao bookDao;
	
	public BookServiceImpl(EntityManagerFactory entityManagerFactory, BookDao bookDao) {
		this.entityManagerFactory = entityManagerFactory;
		this.bookDao = bookDao;
	}

	@Override
	public BookEntity create(BookEntity book) {
		return null;
	}
	
	@Override
	public BookEntity findById(Long id) {
		return null;
	}

	@Override
	public BookEntity findByIsbn(String isbn) {
		return null;
	}
	
	@Override
	public BookEntity updateById(BookEntity book) {
		return null;
	}

	@Override
	public void deleteById(Long id) {
		
	}

}
