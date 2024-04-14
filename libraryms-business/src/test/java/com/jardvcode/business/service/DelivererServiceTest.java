package com.jardvcode.business.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.jardvcode.business.exception.NoDataFoundException;
import com.jardvcode.business.service.impl.DelivererServiceImpl;
import com.jardvcode.business.uitl.DataTest;
import com.jardvcode.model.entity.BookEntity;
import com.jardvcode.model.entity.BookExemplaryEnum;
import com.jardvcode.model.entity.ExemplarEntity;
import com.jardvcode.model.entity.HistoricalStateEnum;
import com.jardvcode.model.entity.LoanHistoricalEntity;
import com.jardvcode.model.entity.LocationEnum;
import com.jardvcode.model.entity.PenaltyEntity;
import com.jardvcode.model.entity.StudentEntity;
import com.jardvcode.model.entity.UserEntity;
import com.jardvcode.model.entity.UserStateEnum;
import com.jardvcode.model.util.DateUtil;

@RunWith(MockitoJUnitRunner.class)
public class DelivererServiceTest {
	
	private static DelivererService delivererService;
	
	@Mock
	private UserService userService;
	
	@Mock
	private BookService bookService;
	
	@Mock
	private ExemplarService exemplarService;
	
	@Mock
	private LoanHistoricalService loanService;
	
	@Mock
	private PenaltyService penaltyService;
	
	@Before
	public void init() {
		delivererService = new DelivererServiceImpl(userService, bookService, exemplarService, loanService, penaltyService);
	}
	
	@Test
	public void returnExemplar_should_return_book_exemplar_then_the_book_stock_is_incremented() {
		ExemplarEntity exemplarEntityToFind = DataTest.STUDENT_A_BOOK_EXEMPLAR_TO_FIND;
		ExemplarEntity exemplaryEntityLent = DataTest.STUDENT_A_BOOK_EXEMPLAR_BORROWED;
		UserEntity userEntity = DataTest.STUDENT_USER;
		BookEntity bookEntity = DataTest.BOOK;
		bookEntity.setStock(1);
		
		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(bookService.findByIsbn("9780132350884")).thenReturn(bookEntity);
		when(exemplarService.findExemplarByBookIdAndExemplarId(1L, "A")).thenReturn(exemplaryEntityLent);
				
		delivererService.returnExemplar(exemplarEntityToFind);
		
		ArgumentCaptor<BookEntity> argumentCaptor = ArgumentCaptor.forClass(BookEntity.class);
		verify(bookService).updateById(argumentCaptor.capture());
		
		BookEntity bookEntityCaptured = argumentCaptor.getValue();
		
		assertNotNull(bookEntityCaptured);
		assertEquals(new Integer(2), bookEntityCaptured.getStock());
	}
	
	@Test
	public void returnExemplar_should_return_exemplar_then_exemplar_attributes_are_restored_to_delete_loan() {
		ExemplarEntity exemplarEntityToFind = DataTest.STUDENT_A_BOOK_EXEMPLAR_TO_FIND;
		ExemplarEntity exemplarEntityLent = DataTest.STUDENT_A_BOOK_EXEMPLAR_BORROWED;
		UserEntity userEntity = DataTest.STUDENT_USER;
		BookEntity bookEntity = DataTest.BOOK;
		
		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(bookService.findByIsbn("9780132350884")).thenReturn(bookEntity);
		when(exemplarService.findExemplarByBookIdAndExemplarId(1L, "A")).thenReturn(exemplarEntityLent);
		
		delivererService.returnExemplar(exemplarEntityToFind);
		
		ArgumentCaptor<ExemplarEntity> argumentCaptor = ArgumentCaptor.forClass(ExemplarEntity.class);
		verify(exemplarService).updateById(argumentCaptor.capture());
		
		ExemplarEntity bookExemplarEntityCaptured = argumentCaptor.getValue();
		
		assertNotNull(bookExemplarEntityCaptured);
		assertNull(bookExemplarEntityCaptured.getLendDate());
		assertNull(bookExemplarEntityCaptured.getReturnDate());
		assertNull(bookExemplarEntityCaptured.getUser());
		assertEquals(BookExemplaryEnum.AVAILABLE, bookExemplarEntityCaptured.getState());
		assertEquals(LocationEnum.SITTING_ROOM, bookExemplarEntityCaptured.getLocation());
	}
	
	@Test
	public void returnExemplar_should_return_exemplar_then_loan_historical_is_registered_with_the_book_exemplar_values() {
		ExemplarEntity exemplarEntityToFind = DataTest.STUDENT_A_BOOK_EXEMPLAR_TO_FIND;
		ExemplarEntity exemplarEntityLent = DataTest.STUDENT_A_BOOK_EXEMPLAR_BORROWED;
		UserEntity userEntity = DataTest.STUDENT_USER;
		BookEntity bookEntity = DataTest.BOOK;
		Date loanDateExpected = exemplarEntityLent.getLendDate();
		Date deliveryDateExpected = exemplarEntityLent.getReturnDate();
		
		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(bookService.findByIsbn("9780132350884")).thenReturn(bookEntity);
		when(exemplarService.findExemplarByBookIdAndExemplarId(1L, "A")).thenReturn(exemplarEntityLent);

		delivererService.returnExemplar(exemplarEntityToFind);
		
		ArgumentCaptor<LoanHistoricalEntity> argumentCaptor = ArgumentCaptor.forClass(LoanHistoricalEntity.class);
		verify(loanService).create(argumentCaptor.capture());
		LoanHistoricalEntity loanHistoricalEntityCaptured = argumentCaptor.getValue();
		
		assertNotNull(loanHistoricalEntityCaptured);
		assertEquals(loanDateExpected, loanHistoricalEntityCaptured.getLendDate());
		assertEquals(deliveryDateExpected, loanHistoricalEntityCaptured.getReturnDate());
		assertEquals(DateUtil.format("yyyy-MM-dd", new Date()), DateUtil.format("yyyy-MM-dd", loanHistoricalEntityCaptured.getRealReturnDate()));
		assertEquals(new Long(1), loanHistoricalEntityCaptured.getExemplaryBook().getId());
		assertEquals(new Long(1), loanHistoricalEntityCaptured.getUser().getId());
	}
	
	@Test
	public void returnExemplar_should_return_exemplar_when_the_book_is_delivered_after_delivery_date_then_the_user_state_is_set_to_penalized() {
		ExemplarEntity exemplarEntityToFind = DataTest.STUDENT_A_BOOK_EXEMPLAR_TO_FIND;
		ExemplarEntity exemplarEntityLent = DataTest.STUDENT_A_BOOK_EXEMPLAR_BORROWED_WITH_LATE_DELIVERY;
		UserEntity userEntity = DataTest.STUDENT_USER;
		BookEntity bookEntity = DataTest.BOOK;
		
		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(bookService.findByIsbn("9780132350884")).thenReturn(bookEntity);
		when(exemplarService.findExemplarByBookIdAndExemplarId(1L, "A")).thenReturn(exemplarEntityLent);
		
		delivererService.returnExemplar(exemplarEntityToFind);
		
		ArgumentCaptor<UserEntity> argumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
		verify(userService).updateById(argumentCaptor.capture());
		
		StudentEntity userEntityCaptured = (StudentEntity) argumentCaptor.getValue();
		
		assertNotNull(userEntityCaptured);
		assertEquals(UserStateEnum.PENALIZED, userEntityCaptured.getState());
	}
	
	@Test
	public void returnExemplar_should_return_exemplar_when_the_book_is_delivered_after_delivery_date_then_the_penalty_is_registered() {
		ExemplarEntity exemplarEntityToFind = DataTest.STUDENT_A_BOOK_EXEMPLAR_TO_FIND;
		ExemplarEntity exemplarEntityLent = DataTest.STUDENT_A_BOOK_EXEMPLAR_BORROWED_WITH_LATE_DELIVERY;
		UserEntity userEntity = DataTest.STUDENT_USER;
		BookEntity bookEntity = DataTest.BOOK;
		
		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(bookService.findByIsbn("9780132350884")).thenReturn(bookEntity);
		when(exemplarService.findExemplarByBookIdAndExemplarId(1L, "A")).thenReturn(exemplarEntityLent);
		
		delivererService.returnExemplar(exemplarEntityToFind);
		
		ArgumentCaptor<PenaltyEntity> argumentCaptor = ArgumentCaptor.forClass(PenaltyEntity.class);
		verify(penaltyService).create(argumentCaptor.capture());
		
		PenaltyEntity penaltyEntityCaptured = argumentCaptor.getValue();
		
		Integer delayDays = DateUtil.calculateDifferenceInDays(exemplarEntityLent.getReturnDate(), new Date());
		Date penaltyEndDateExpected = DateUtil.addDays(new Date(), delayDays * 2);
		
		assertNotNull(penaltyEntityCaptured);
		assertEquals(DateUtil.format("yyyy-MM-dd", new Date()), penaltyEntityCaptured.getStartDate());
		assertEquals("The penalty end date should be the delay days double", penaltyEndDateExpected, penaltyEntityCaptured.getEndDate());
		assertEquals(HistoricalStateEnum.ACTIVE, penaltyEntityCaptured.getState());
		assertNotNull(penaltyEntityCaptured.getUser());
		assertEquals(new Long(1), penaltyEntityCaptured.getUser().getId());
	}
	
	@Test
	public void returnExemplar_should_throw_exception_when_user_is_not_found() {
		NoDataFoundException exception = new NoDataFoundException("Not found user with nickname gabrielmar");
		
		ExemplarEntity exemplaryEntity = DataTest.STUDENT_A_BOOK_EXEMPLAR_TO_FIND;
		
		when(userService.findByNickname("gabrielmar")).thenThrow(exception);
		
		try {
			delivererService.returnExemplar(exemplaryEntity);
			fail();
		} catch(NoDataFoundException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
	}
	
	@Test
	public void returnExemplar_should_throw_exception_when_book_is_not_found() {
		NoDataFoundException exception = new NoDataFoundException("Not found book with isbn 9780132350884");
		
		ExemplarEntity exemplaryEntity = DataTest.STUDENT_A_BOOK_EXEMPLAR_TO_FIND;
		UserEntity userEntity = DataTest.STUDENT_USER;
		
		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(bookService.findByIsbn("9780132350884")).thenThrow(exception);
		
		try {
			delivererService.returnExemplar(exemplaryEntity);
			fail();
		} catch(NoDataFoundException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
	}
	
	@Test
	public void returnExemplar_should_throw_exception_when_exemplar_is_not_found() {
		NoDataFoundException exception = new NoDataFoundException("Not found exemplar with id 1");
		
		ExemplarEntity exemplaryEntity = DataTest.STUDENT_A_BOOK_EXEMPLAR_TO_FIND;
		UserEntity userEntity = DataTest.STUDENT_USER;
		BookEntity bookEntity = DataTest.BOOK;
		
		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(bookService.findByIsbn("9780132350884")).thenReturn(bookEntity);
		when(exemplarService.findExemplarByBookIdAndExemplarId(1L, "A")).thenThrow(exception);
		
		try {
			delivererService.returnExemplar(exemplaryEntity);
			fail();
		} catch(NoDataFoundException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
	}

}
