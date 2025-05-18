package com.jardvcode.business.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.jardvcode.business.exception.GeneralServiceException;
import com.jardvcode.business.exception.NoDataFoundException;
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

	@Override
	public List<ExemplarEntity> findExemplaresByUserId(Long userId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			exemplarDao.setEntityManager(entityManager);
			List<ExemplarEntity> exemplares = exemplarDao.findExemplaresByUserId(userId);
			entityManager.getTransaction().commit();
			
			if(exemplares == null) throw new NoDataFoundException("Not found exemplares for user with id " + userId);
			
			return exemplares;
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
