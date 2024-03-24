package com.jardvcode.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jardvcode.model.entity.LoanHistoricalEntity;
import com.jardvcode.util.LoanHistoricalBuilder;

public class LoanHistoricalEntityTest {

	@Test
	public void loan_should_be_equal_when_id_is_the_same() {
		LoanHistoricalEntity loanEntity1 = LoanHistoricalBuilder.createLoanHistorical(1L);
		LoanHistoricalEntity loanEntity2 = LoanHistoricalBuilder.createLoanHistorical(1L);
		LoanHistoricalEntity loanEntity3 = LoanHistoricalBuilder.createLoanHistorical(2L);
		
		assertTrue(loanEntity1.equals(loanEntity2));
		assertFalse(loanEntity1.equals(loanEntity3));
	}

}
