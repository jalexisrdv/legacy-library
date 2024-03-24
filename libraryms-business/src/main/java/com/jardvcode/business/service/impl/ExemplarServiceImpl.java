package com.jardvcode.business.service.impl;

import javax.persistence.EntityManagerFactory;

import com.jardvcode.business.service.ExemplarService;
import com.jardvcode.model.dao.ExemplarDao;
import com.jardvcode.model.entity.ExemplarEntity;

public class ExemplarServiceImpl implements ExemplarService {
	
	private EntityManagerFactory entityManagerFactory;
	private ExemplarDao exemplarDao;
	
	public ExemplarServiceImpl(EntityManagerFactory entityManagerFactory, ExemplarDao exemplarDao) {
		this.entityManagerFactory = entityManagerFactory;
		this.exemplarDao = exemplarDao;
	}

	@Override
	public ExemplarEntity findExemplarBorrowedByBookIdAndUserId(Long bookId, Long userId) {
		return null;
	}

	@Override
	public ExemplarEntity findExemplarByBookIdAndExemplarId(Long bookId, String exemplarId) {
		return null;
	}

	@Override
	public ExemplarEntity create(ExemplarEntity exemplar) {
		return null;
	}

	@Override
	public ExemplarEntity findById(Long id) {
		return null;
	}

	@Override
	public ExemplarEntity updateById(ExemplarEntity exemplar) {
		return null;
	}

	@Override
	public void deleteById(Long id) {
		
	}

}
