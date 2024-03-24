package com.jardvcode.model.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jardvcode.model.dao.BookDao;
import com.jardvcode.model.entity.BookEntity;

public class BookDaoImpl extends CrudDaoImpl<BookEntity, Long> implements BookDao {

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

}
