package com.jardvcode.business.service.impl;

import javax.persistence.EntityManagerFactory;

import com.jardvcode.business.service.LoanHistoricalService;
import com.jardvcode.model.dao.LoanHistoricalDao;
import com.jardvcode.model.entity.LoanHistoricalEntity;

public class LoanHistoricalServiceImpl implements LoanHistoricalService {
	
	private EntityManagerFactory entityManagerFactory;
	private LoanHistoricalDao loanHistoricalDao;

	public LoanHistoricalServiceImpl(EntityManagerFactory entityManagerFactory, LoanHistoricalDao loanHistoricalDao) {
		this.entityManagerFactory = entityManagerFactory;
		this.loanHistoricalDao = loanHistoricalDao;
	}

	@Override
	public LoanHistoricalEntity create(LoanHistoricalEntity loan) {
		return null;
	}

	@Override
	public LoanHistoricalEntity findById(Long id) {
		return null;
	}

	@Override
	public LoanHistoricalEntity updateById(LoanHistoricalEntity loan) {
		return null;
	}

	@Override
	public void deleteById(Long id) {
		
	}

}
