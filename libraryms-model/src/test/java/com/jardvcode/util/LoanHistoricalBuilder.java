package com.jardvcode.util;
import java.util.Date;

import com.jardvcode.model.entity.ExemplarEntity;
import com.jardvcode.model.entity.LoanHistoricalEntity;
import com.jardvcode.model.entity.UserEntity;

public final class LoanHistoricalBuilder {
	
	private LoanHistoricalBuilder() {
		throw new RuntimeException("Can not be instantiated");
	}
	
	public static LoanHistoricalEntity createLoanHistorical(Long id) {
		LoanHistoricalEntity loan = new LoanHistoricalEntity();
		loan.setId(id);
		return loan;
	}
	
	public static LoanHistoricalEntity createLoanHistorical(Long id, Date loanDate, Date expectedDeliveryDate, Date realDeliveryDate, UserEntity user, ExemplarEntity exemplar) {
		LoanHistoricalEntity loan = createLoanHistorical(loanDate, expectedDeliveryDate, realDeliveryDate, user, exemplar);
		loan.setId(id);
		return loan;
	}
	
	public static LoanHistoricalEntity createLoanHistorical(Date loanDate, Date expectedDeliveryDate, Date realDeliveryDate, UserEntity user, ExemplarEntity exemplar) {
		LoanHistoricalEntity loan = new LoanHistoricalEntity();
		loan.setLendDate(loanDate);
		loan.setReturnDate(expectedDeliveryDate);
		loan.setRealReturnDate(realDeliveryDate);
		loan.setUser(user);
		loan.setExemplaryBook(exemplar);
		return loan;
	}

}
