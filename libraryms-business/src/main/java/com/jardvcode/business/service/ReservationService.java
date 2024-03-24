package com.jardvcode.business.service;

import com.jardvcode.model.entity.ReservationEntity;

public interface ReservationService extends CrudService<ReservationEntity, Long> {

	public ReservationEntity findActiveReservationByBookIdAndUserId(Long bookId, Long userId);

}
