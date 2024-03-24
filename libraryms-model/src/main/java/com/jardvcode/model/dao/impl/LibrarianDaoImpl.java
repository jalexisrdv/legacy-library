package com.jardvcode.model.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.jardvcode.model.dao.LibrarianDao;
import com.jardvcode.model.entity.LibrarianEntity;

public class LibrarianDaoImpl extends CrudDaoImpl<LibrarianEntity, Long> implements LibrarianDao {
	
	public LibrarianDaoImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public LibrarianEntity findById(Long id) {
		try {
			return entityManager.find(LibrarianEntity.class, id);
		} catch(Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<LibrarianEntity> findAll() {
		try {
			return entityManager.createNamedQuery("LibrarianEntity.findAll").getResultList();
		} catch(Exception e) {
			return null;
		}
	}

}
