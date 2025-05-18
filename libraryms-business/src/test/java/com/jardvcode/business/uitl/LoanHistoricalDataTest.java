package com.jardvcode.business.uitl;

import java.util.Date;

import com.jardvcode.model.entity.LoanHistoricalEntity;
import com.jardvcode.util.ExemplarBuilder;
import com.jardvcode.util.LoanHistoricalBuilder;
import com.jardvcode.util.UserBuilder;

public class LoanHistoricalDataTest {
	
	public static final LoanHistoricalEntity LOAN_HISTORICAL_SAVED = LoanHistoricalBuilder.createLoanHistorical(
			1L,
			new Date(), new Date(), new Date(), 
			UserBuilder.createStudentUser(1L), ExemplarBuilder.createExemplar(1L)
	);

}
