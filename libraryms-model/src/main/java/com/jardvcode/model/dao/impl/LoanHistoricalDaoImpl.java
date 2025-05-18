package com.jardvcode.model.dao.impl;

import javax.persistence.EntityManager;

import com.jardvcode.model.dao.LoanHistoricalDao;
import com.jardvcode.model.entity.LoanHistoricalEntity;

public class LoanHistoricalDaoImpl extends CrudDaoImpl<LoanHistoricalEntity, Long> implements LoanHistoricalDao {

	public LoanHistoricalDaoImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public LoanHistoricalEntity findById(Long id) {
		try {
			return entityManager.find(LoanHistoricalEntity.class, id);
		} catch(Exception e) {
			return null;
		}
	}

}
