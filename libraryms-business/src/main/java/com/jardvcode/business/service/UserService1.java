package com.jardvcode.business.service;

import java.util.List;

import com.jardvcode.model.entity.BookEntity;
import com.jardvcode.model.entity.ExemplarEntity;
import com.jardvcode.model.entity.LoanHistoricalEntity;
import com.jardvcode.model.entity.PenaltyEntity;
import com.jardvcode.model.entity.ReservationEntity;
import com.jardvcode.model.entity.UserEntity;

public class UserService1 implements IUserServiceBussiness {

	@Override
	public BookEntity findBookByIsbn(String isbn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookEntity> findBookByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookEntity> searchBook(String keyWord) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookEntity> listBookByTitle(Integer firstResult, Integer maxResult) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExemplarEntity> findAllBookExemplaries(UserEntity user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExemplarEntity> findAllBookExemplaries(BookEntity book) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExemplarEntity> availableBookExemplaries(BookEntity book) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReservationEntity requestBookReservation(ReservationEntity exemplary) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void requestBookExemplaryLoan(ExemplarEntity bookExemplary) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeBookReservation(BookEntity book) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<LoanHistoricalEntity> findAllLoanHistorical(UserEntity user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReservationEntity> findAllReservations(UserEntity user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PenaltyEntity findActivePenalty(UserEntity user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PenaltyEntity> findPenaltyHistorical(UserEntity user) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
