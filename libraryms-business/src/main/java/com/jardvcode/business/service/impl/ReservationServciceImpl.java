package com.jardvcode.business.service.impl;

import javax.persistence.EntityManagerFactory;

import com.jardvcode.business.service.ReservationService;
import com.jardvcode.model.dao.ReservationDao;
import com.jardvcode.model.entity.ReservationEntity;

public class ReservationServciceImpl implements ReservationService {
	
	private EntityManagerFactory entityManagerFactory;
	private ReservationDao reservationDao;

	public ReservationServciceImpl(EntityManagerFactory entityManagerFactory, ReservationDao reservationDao) {
		this.entityManagerFactory = entityManagerFactory;
		this.reservationDao = reservationDao;
	}

	@Override
	public ReservationEntity create(ReservationEntity entity) {
		return null;
	}

	@Override
	public ReservationEntity findById(Long id) {
		return null;
	}

	@Override
	public ReservationEntity updateById(ReservationEntity entity) {
		return null;
	}

	@Override
	public void deleteById(Long id) {
		
	}

	@Override
	public ReservationEntity findActiveReservationByBookIdAndUserId(Long bookId, Long userId) {
		return null;
	}

}
