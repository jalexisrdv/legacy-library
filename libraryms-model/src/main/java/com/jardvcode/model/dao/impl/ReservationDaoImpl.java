package com.jardvcode.model.dao.impl;

import javax.persistence.EntityManager;

import com.jardvcode.model.dao.ReservationDao;
import com.jardvcode.model.entity.ReservationEntity;

public class ReservationDaoImpl extends CrudDaoImpl<ReservationEntity, Long> implements ReservationDao {

	public ReservationDaoImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public ReservationEntity findById(Long id) {
		try {
			return entityManager.find(ReservationEntity.class, id);
		} catch(Exception e) {
			return null;
		}
	}

	public ReservationEntity findActiveReservationByBookIdAndUserId(Long bookId, Long userId) {
		return null;
	}

	public Integer countOperationsByUserId(long l) {
		// TODO Auto-generated method stub
		return null;
	}

}
