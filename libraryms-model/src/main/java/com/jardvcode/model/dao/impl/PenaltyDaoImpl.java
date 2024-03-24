package com.jardvcode.model.dao.impl;

import javax.persistence.EntityManager;

import com.jardvcode.model.dao.PenaltyDao;
import com.jardvcode.model.entity.PenaltyEntity;

public class PenaltyDaoImpl extends CrudDaoImpl<PenaltyEntity, Long> implements PenaltyDao {

	public PenaltyDaoImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public PenaltyEntity findById(Long id) {
		try {
			return entityManager.find(PenaltyEntity.class, id);
		} catch(Exception e) {
			return null;
		}
	}

	public PenaltyEntity findActivePenaltyByUserId(long l) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
