package com.jardvcode.business.service.impl;

import com.jardvcode.business.service.BookService;
import com.jardvcode.business.service.DelivererService;
import com.jardvcode.business.service.ExemplarService;
import com.jardvcode.business.service.LoanHistoricalService;
import com.jardvcode.business.service.PenaltyService;
import com.jardvcode.business.service.UserService;
import com.jardvcode.model.entity.ExemplarEntity;

public class DelivererServiceImpl implements DelivererService {

	private UserService userService;
	private BookService bookService;
	private ExemplarService exemplarService;
	private LoanHistoricalService loanService;
	private PenaltyService penaltyService;
	
	public DelivererServiceImpl(UserService userService, BookService bookService,
			ExemplarService exemplarService, LoanHistoricalService loanService, PenaltyService penaltyService) {
		this.userService = userService;
		this.bookService = bookService;
		this.exemplarService = exemplarService;
		this.loanService = loanService;
		this.penaltyService = penaltyService;
	}

	@Override
	public void returnExemplar(ExemplarEntity exemplarEntity) {
		
	}

}
