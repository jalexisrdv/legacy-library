package com.jardvcode.business.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.jardvcode.business.exception.GeneralServiceException;
import com.jardvcode.business.exception.NoDataFoundException;
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

	@Override
	public Long countSearchedBooksByTitle(String title) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			bookDao.setEntityManager(entityManager);
			Long count = bookDao.countSearchedBooksByTitle(title);
			entityManager.getTransaction().commit();
			
			if(count == null) throw new NoDataFoundException("Not found books");
			
			return count;
		} catch(NoDataFoundException e) {
			throw e;
		} catch(Exception e) {
			if(entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
			throw new GeneralServiceException(e.getMessage());
		} finally {
			entityManager.close();
		}
	}

	@Override
	public List<BookEntity> searchBooksByTitle(String title, Integer startLimit, Integer endLimit) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			bookDao.setEntityManager(entityManager);
			List<BookEntity> books = bookDao.searchBooksByTitle(title, startLimit, endLimit);
			entityManager.getTransaction().commit();
			
			if(books == null) throw new NoDataFoundException("Not found books");
			
			return books;
		} catch(NoDataFoundException e) {
			throw e;
		} catch(Exception e) {
			if(entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
			throw new GeneralServiceException(e.getMessage());
		} finally {
			entityManager.close();
		}
	}

}
