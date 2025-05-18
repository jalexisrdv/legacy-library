package com.jardvcode.business.service.impl;

import com.jardvcode.business.service.BookService;
import com.jardvcode.business.service.PenaltyService;
import com.jardvcode.business.service.ReservationService;
import com.jardvcode.business.service.ReserverService;
import com.jardvcode.business.service.UserService;
import com.jardvcode.model.entity.ReservationEntity;

public class ReserverServiceImpl implements ReserverService {
	
	private ReservationService reservationService;

	public ReserverServiceImpl(BookService bookService, ReservationService reservationService,
			UserService userService, PenaltyService penaltyService) {
		this.reservationService = reservationService;
	}

	@Override
	public ReservationEntity reserveBook(ReservationEntity reservationEntity) {
		return null;
	}

	@Override
	public ReservationEntity cancelBookReservation(ReservationEntity reservationEntity) {
		return null;
	}

}
