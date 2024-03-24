package com.jardvcode.business.service;

import com.jardvcode.model.entity.ReservationEntity;

public interface ReserverService {

	public ReservationEntity reserveBook(ReservationEntity reservationEntity);
	
	public ReservationEntity cancelBookReservation(ReservationEntity reservationEntity);
	
}
