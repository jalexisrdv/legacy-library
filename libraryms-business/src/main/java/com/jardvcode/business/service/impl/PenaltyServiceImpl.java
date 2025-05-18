package com.jardvcode.business.service.impl;

import javax.persistence.EntityManagerFactory;

import com.jardvcode.business.service.PenaltyService;
import com.jardvcode.model.dao.PenaltyDao;
import com.jardvcode.model.entity.PenaltyEntity;

public class PenaltyServiceImpl implements PenaltyService {
	
	private EntityManagerFactory entityManagerFactory;
	private PenaltyDao penaltyDao;

	public PenaltyServiceImpl(EntityManagerFactory entityManagerFactory, PenaltyDao penaltyDao) {
	}

	@Override
	public PenaltyEntity create(PenaltyEntity entity) {
		return null;
	}

	@Override
	public PenaltyEntity findById(Long id) {
		return null;
	}

	@Override
	public PenaltyEntity updateById(PenaltyEntity entity) {
		return null;
	}

	@Override
	public void deleteById(Long id) {
		
	}

	@Override
	public PenaltyEntity findActivePenaltyByUserId(Long userId) {
		return null;
	}

}
