package com.jardvcode.business.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.jardvcode.model.entity.BookEntity;
import com.jardvcode.model.entity.ExemplarEntity;
import com.jardvcode.model.entity.LoanHistoricalEntity;
import com.jardvcode.model.entity.PenaltyEntity;
import com.jardvcode.model.entity.ReservationEntity;
import com.jardvcode.model.entity.UserEntity;

public interface ILibrarianService {
	
	public ExemplarEntity lendExemplar(ExemplarEntity bookExemplary);

	public LoanHistoricalEntity returnBookExemplary(ExemplarEntity bookExemplary);
	
	public BookEntity createBook(BookEntity book);
	
	public ExemplarEntity createBookExemplary(ExemplarEntity bookExemplary);
	
	public void removeBook(Long id);
	
	public void removeBookExemplary(Long id);
	
	public BookEntity updateBook(BookEntity book);
	
	public ExemplarEntity updateBookExemplary(ExemplarEntity bookExemplary);
	
	public List<ExemplarEntity> findLentBookExemplaries(BookEntity book);
	public List<ExemplarEntity> findLentBookExemplariesOf(UserEntity user);
	public ExemplarEntity findLentBookExemplaryOfUser(ExemplarEntity bookExemplary);
	
	public List<BookEntity> findReservedBooks(BookEntity book);
	public List<ReservationEntity> findReservedBooksOf(UserEntity user);
	
	public ReservationEntity findReservedBookByUser(UserEntity user, BookEntity book);
	
	public List<ExemplarEntity> findAvailableBookExemplaries(BookEntity book);
	
	public PenaltyEntity findActivePenalty(UserEntity user);
	
	public List<PenaltyEntity> findPenaltyHistorical(UserEntity user);
	
	public List<ReservationEntity> findReservationHistorical(UserEntity user);
	
	public List<LoanHistoricalEntity> findLoanHistorical(UserEntity user);
	
	public BookEntity findBookByIsbn(String isbn);
	
	public List<BookEntity> findBookByTitle(String title);
	
	public List<BookEntity> searchBook(String keyWord);
	
	public List<BookEntity> listBookByTitle(Integer firstResult, Integer maxResult);
	
	public ExemplarEntity findExemplaryById(Long id);
	public ExemplarEntity findExemplaryByBookIdAndExemplaryId(BookEntity book, ExemplarEntity exemplary);
	
	public UserEntity createUser(UserEntity user);
	
	public void removeUser();
	
	public UserEntity updateUser();
	
	public UserEntity findUserByNickname(String nickname);

	public ReservationEntity findBookReservationByUser(UserEntity user, BookEntity book);

	public Set<ReservationEntity> findActiveReservationsOf(UserEntity user);

	public ReservationEntity findActiveReservationOf(UserEntity user, BookEntity book);

	public ReservationEntity findReservationOf(UserEntity user, BookEntity book);

	public ReservationEntity findReservationById(Long id);

	public LoanHistoricalEntity findLoanHistoricalById(Long id);
	
	public PenaltyEntity createPenalty(PenaltyEntity penalty);

	public void updateUser(UserEntity userFound);
	
}
