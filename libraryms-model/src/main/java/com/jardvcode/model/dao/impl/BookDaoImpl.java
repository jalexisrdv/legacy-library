package com.jardvcode.model.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jardvcode.model.dao.BookDao;
import com.jardvcode.model.entity.BookEntity;

public class BookDaoImpl extends CrudDaoImpl<BookEntity, Long> implements BookDao {

	public BookDaoImpl() {
		super();
	}
	
	public BookDaoImpl(EntityManager entityManager) {
		super(entityManager);
	}

	public BookEntity findById(Long id) {
		try {
			return entityManager.find(BookEntity.class, id);
		} catch(Exception e) {
			return null;
		}
	}
	
	public BookEntity findByIsbn(String isbn) {
		try {
			Query query = entityManager.createQuery("SELECT e FROM BookEntity e WHERE e.isbn = :isbn");
			query.setParameter("isbn", isbn);
			return (BookEntity) query.getSingleResult();
		} catch(Exception e) {
			return null;
		}
	}

	@Override
	public Long countSearchedBooksByTitle(String title) {
		try {
			Query query = entityManager.createQuery("SELECT COUNT(*) FROM BookEntity e WHERE e.title LIKE :title");
			query.setParameter("title", "%"+ title + "%");
			return (Long) query.getSingleResult();
		} catch(Exception e) {
			return null;
		}
	}

	@Override
	public List<BookEntity> searchBooksByTitle(String title, Integer startLimit, Integer endLimit) {
		try {
			Query query = entityManager.createQuery("SELECT e FROM BookEntity e WHERE e.title LIKE :title");
			query.setParameter("title", "%"+ title + "%");
			query.setFirstResult(startLimit);
			query.setMaxResults(endLimit);
			return (List<BookEntity>) query.getResultList();
		} catch(Exception e) {
			return null;
		}
	}

}
