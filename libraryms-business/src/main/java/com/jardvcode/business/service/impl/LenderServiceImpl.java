package com.jardvcode.business.service.impl;

import com.jardvcode.business.service.BookService;
import com.jardvcode.business.service.ExemplarService;
import com.jardvcode.business.service.LenderService;
import com.jardvcode.business.service.PenaltyService;
import com.jardvcode.business.service.ReservationService;
import com.jardvcode.business.service.UserService;
import com.jardvcode.model.entity.ExemplarEntity;

public class LenderServiceImpl implements LenderService {

	private UserService userService;
	private PenaltyService penaltyService;
	private ReservationService reservationService;
	private BookService bookService;
	private ExemplarService exemplarService;
	
	public LenderServiceImpl(UserService userService, PenaltyService penaltyService,
			ReservationService reservationService, BookService bookService, ExemplarService exemplarService) {
		this.userService = userService;
		this.penaltyService = penaltyService;
		this.reservationService = reservationService;
		this.bookService = bookService;
		this.exemplarService = exemplarService;
	}

	@Override
	public void lendExemplar(ExemplarEntity exemplarEntity) {
		
	}

}
