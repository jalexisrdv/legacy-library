package com.jardvcode.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jardvcode.model.entity.BookEntity;
import com.jardvcode.model.entity.ExemplarEntity;
import com.jardvcode.model.entity.LoanHistoricalEntity;
import com.jardvcode.util.BookBuilder;
import com.jardvcode.util.ExemplarBuilder;
import com.jardvcode.util.LoanHistoricalBuilder;
import com.jardvcode.util.UserBuilder;

public class BookExemplaryEntityTest {
	
	@Test
	public void exemplar_should_be_equal_when_isbn_and_exemplarId_is_the_same() {
		BookEntity bookEntity1 = BookBuilder.createBook(1L, "123456789");
		ExemplarEntity exemplarEntity1 = ExemplarBuilder.createExemplar(1L, "A", bookEntity1);
		BookEntity bookEntity2 = BookBuilder.createBook(1L, "123456789");
		ExemplarEntity exemplarEntity2 = ExemplarBuilder.createExemplar(2L, "A", bookEntity2);
		BookEntity bookEntity3 = BookBuilder.createBook(1L, "987654321");
		ExemplarEntity exemplarEntity3 = ExemplarBuilder.createExemplar(3L, "A", bookEntity3);
		
		assertTrue(exemplarEntity1.equals(exemplarEntity2));
		assertFalse(exemplarEntity3.equals(exemplarEntity1));
		exemplarEntity2.setExemplaryId("B");
		assertFalse(exemplarEntity2.equals(exemplarEntity1));
	}
	
	@Test
	public void exemplar_should_find_loan_by_id() {
		LoanHistoricalEntity loanEntity = LoanHistoricalBuilder.createLoanHistorical(1L);
		
		ExemplarEntity exemplarEntity = new ExemplarEntity();
		exemplarEntity.getLends().add(loanEntity);
		
		LoanHistoricalEntity loanEntityToFind = LoanHistoricalBuilder.createLoanHistorical(1L);
		
		assertTrue(exemplarEntity.getLends().contains(loanEntityToFind));
		loanEntityToFind.setId(3L);
		assertFalse(exemplarEntity.getLends().contains(loanEntityToFind));
	}
	
	@Test
	public void exemplar_should_not_duplicate_loan_with_same_id() {
		LoanHistoricalEntity loanEntity1 = LoanHistoricalBuilder.createLoanHistorical(1L);
		LoanHistoricalEntity loanEntity2 = LoanHistoricalBuilder.createLoanHistorical(2L);
		loanEntity2.setUser(UserBuilder.createStudentUser(1L, "alexis"));
		LoanHistoricalEntity loanEntity3 = LoanHistoricalBuilder.createLoanHistorical(2L);
		
		ExemplarEntity exemplarEntity = new ExemplarEntity();
		exemplarEntity.getLends().add(loanEntity1);
		exemplarEntity.getLends().add(loanEntity2);
		exemplarEntity.getLends().add(loanEntity3);
		
		assertTrue(exemplarEntity.getLends().size() == 2);
	}

}
