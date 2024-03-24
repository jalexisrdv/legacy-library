package com.jardvcode.model.dao.impl;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TransactionRequiredException;

import com.jardvcode.model.dao.CrudDao;
import com.jardvcode.model.exception.DaoException;

public abstract class CrudDaoImpl<T, K> implements CrudDao<T, K> {

	protected EntityManager entityManager;

	public CrudDaoImpl(EntityManager entityManager) {
		if (!entityManager.getTransaction().isActive())
			throw new TransactionRequiredException("Is requiered an active transaction to build the DAO");
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public T create(T t) {
		try {
			entityManager.persist(t);
			entityManager.flush();
			entityManager.refresh(t);
			return t;
		} catch (EntityExistsException ex) {
			throw new DaoException(ex.getMessage(), ex);
		}
	}

	public T update(T t) {
		return (T) entityManager.merge(t);
	}

	public void delete(T t) {
		t = entityManager.merge(t);
		entityManager.remove(t);
	}

}
