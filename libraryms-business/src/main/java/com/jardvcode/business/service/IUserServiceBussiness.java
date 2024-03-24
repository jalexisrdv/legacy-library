package com.jardvcode.business.service;

import java.util.List;

import com.jardvcode.model.entity.BookEntity;
import com.jardvcode.model.entity.ExemplarEntity;
import com.jardvcode.model.entity.LoanHistoricalEntity;
import com.jardvcode.model.entity.PenaltyEntity;
import com.jardvcode.model.entity.ReservationEntity;
import com.jardvcode.model.entity.UserEntity;

public interface IUserServiceBussiness {
	
	public BookEntity findBookByIsbn(String isbn);
	
	public List<BookEntity> findBookByTitle(String title);
	
	public List<BookEntity> searchBook(String keyWord);
	
	public List<BookEntity> listBookByTitle(Integer firstResult, Integer maxResult);
	
	public List<ExemplarEntity> findAllBookExemplaries(UserEntity user);
	
	public List<ExemplarEntity> findAllBookExemplaries(BookEntity book);
	
	public List<ExemplarEntity> availableBookExemplaries(BookEntity book);
	
	public ReservationEntity requestBookReservation(ReservationEntity exemplary);
	
	public void requestBookExemplaryLoan(ExemplarEntity bookExemplary);
	
	public void removeBookReservation(BookEntity book);
	
	public List<LoanHistoricalEntity> findAllLoanHistorical(UserEntity user);
	
	public List<ReservationEntity> findAllReservations(UserEntity user);
	
	public PenaltyEntity findActivePenalty(UserEntity user);
	
	public List<PenaltyEntity> findPenaltyHistorical(UserEntity user);

}
