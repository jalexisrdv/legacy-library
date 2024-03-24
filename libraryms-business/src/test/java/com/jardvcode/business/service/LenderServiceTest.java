package com.jardvcode.business.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.jardvcode.business.configuration.ConstantsTest;
import com.jardvcode.business.exception.LoanException;
import com.jardvcode.business.exception.NoDataFoundException;
import com.jardvcode.business.exception.OperationException;
import com.jardvcode.business.exception.PenaltyException;
import com.jardvcode.business.service.impl.LenderServiceImpl;
import com.jardvcode.business.uitl.DataTest;
import com.jardvcode.model.entity.BookEntity;
import com.jardvcode.model.entity.BookExemplaryEnum;
import com.jardvcode.model.entity.ExemplarEntity;
import com.jardvcode.model.entity.FinalizationEnum;
import com.jardvcode.model.entity.HistoricalStateEnum;
import com.jardvcode.model.entity.LocationEnum;
import com.jardvcode.model.entity.PenaltyEntity;
import com.jardvcode.model.entity.ReservationEntity;
import com.jardvcode.model.entity.UserEntity;
import com.jardvcode.model.entity.UserStateEnum;
import com.jardvcode.model.util.DateUtil;

@RunWith(MockitoJUnitRunner.class)
public class LenderServiceTest {
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	private static LenderService lenderService;
	
	@Mock
	private UserService userService;
	
	@Mock
	private PenaltyService penaltyService;
	
	@Mock
	private ReservationService reservationService;
	
	@Mock
	private BookService bookService;
	
	@Mock
	private ExemplarService exemplarService;
	
	@Before
	public void init() {
		lenderService = new LenderServiceImpl(userService, penaltyService, reservationService, bookService, exemplarService);
	}
	
	@Test
	public void lendExemplar_should_lend_exemplar_then_exemplar_attributes_are_set_to_indicate_loan() {
		ExemplarEntity exemplarEntityToFind = DataTest.STUDENT_A_BOOK_EXEMPLAR_TO_FIND;
		ExemplarEntity exemplarEntityNotLent = DataTest.STUDENT_A_BOOK_EXEMPLAR_NOT_BORROWED;
		UserEntity userEntity = DataTest.STUDENT_USER;
		BookEntity bookEntity = DataTest.BOOK;
		
		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(penaltyService.findActivePenaltyByUserId(1L)).thenReturn(null);
		when(userService.countOperationsByUserId(1L)).thenReturn(0);
		when(bookService.findByIsbn("9780132350884")).thenReturn(bookEntity);
		when(exemplarService.findExemplarBorrowedByBookIdAndUserId(1L, 1L)).thenReturn(null);
		when(exemplarService.findExemplarByBookIdAndExemplarId(1L, "A")).thenReturn(exemplarEntityNotLent);
		when(reservationService.findActiveReservationByBookIdAndUserId(1L, 1L)).thenReturn(null);
		
		lenderService.lendExemplar(exemplarEntityToFind);
		
		ArgumentCaptor<ExemplarEntity> argumentCaptor = ArgumentCaptor.forClass(ExemplarEntity.class);
		verify(exemplarService).updateById(argumentCaptor.capture());
		
		ExemplarEntity exemplarEntityCaptured = argumentCaptor.getValue();
		
		assertNotNull(exemplarEntityCaptured);
		assertNotNull(exemplarEntityCaptured.getLendDate());
		assertNotNull(exemplarEntityCaptured.getReturnDate());
		assertNotNull(exemplarEntityCaptured.getUser());
		assertEquals(new Long(1), exemplarEntityCaptured.getUser().getId());
		assertNotNull(exemplarEntityCaptured.getBook());
		assertEquals(new Long(1), exemplarEntityCaptured.getBook().getId());
		assertEquals(BookExemplaryEnum.LENT, exemplarEntityCaptured.getState());
		assertEquals(LocationEnum.HOME, exemplarEntityCaptured.getLocation());
	}
	
	@Test
	public void lendExemplar_should_lend_exemplar_then_delivery_date_attribute_is_set_according_to_student_user() {
		ExemplarEntity exemplarEntityToFind = DataTest.STUDENT_A_BOOK_EXEMPLAR_TO_FIND;
		ExemplarEntity exemplarEntityNotLent = DataTest.STUDENT_A_BOOK_EXEMPLAR_NOT_BORROWED;
		UserEntity userEntity = DataTest.STUDENT_USER;
		BookEntity bookEntity = DataTest.BOOK;
		
		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(penaltyService.findActivePenaltyByUserId(1L)).thenReturn(null);
		when(userService.countOperationsByUserId(1L)).thenReturn(0);
		when(bookService.findByIsbn("9780132350884")).thenReturn(bookEntity);
		when(exemplarService.findExemplarBorrowedByBookIdAndUserId(1L, 1L)).thenReturn(null);
		when(exemplarService.findExemplarByBookIdAndExemplarId(1L, "A")).thenReturn(exemplarEntityNotLent);
		when(reservationService.findActiveReservationByBookIdAndUserId(1L, 1L)).thenReturn(null);
		
		lenderService.lendExemplar(exemplarEntityToFind);
		
		ArgumentCaptor<ExemplarEntity> argumentCaptor = ArgumentCaptor.forClass(ExemplarEntity.class);
		verify(exemplarService).updateById(argumentCaptor.capture());
		
		ExemplarEntity exemplarEntityCaptured = argumentCaptor.getValue();
		
		Date deliveryDateExpected = DateUtil.addDays(exemplarEntityCaptured.getLendDate(), ConstantsTest.STUDENT_BOOK_LOAN_DAYS);

		assertNotNull(exemplarEntityCaptured);
		assertEquals(deliveryDateExpected, exemplarEntityCaptured.getReturnDate());
	}
	
	@Test
	public void lendExemplar_should_lend_exemplar_then_exemplar_delivery_date_is_set_according_to_teacher_user() {
		ExemplarEntity exemplarEntityToFind = DataTest.TEACHER_A_BOOK_EXEMPLAR_TO_FIND;
		ExemplarEntity exemplarEntityFound = DataTest.TEACHER_A_BOOK_EXEMPLAR_NOT_BORROWED;
		UserEntity userEntity = DataTest.TEACHER_USER;
		BookEntity bookEntity = DataTest.BOOK;
		
		when(userService.findByNickname("adelamot")).thenReturn(userEntity);
		when(penaltyService.findActivePenaltyByUserId(1L)).thenReturn(null);
		when(userService.countOperationsByUserId(1L)).thenReturn(0);
		when(bookService.findByIsbn("9780132350884")).thenReturn(bookEntity);
		when(exemplarService.findExemplarBorrowedByBookIdAndUserId(1L, 1L)).thenReturn(null);
		when(exemplarService.findExemplarByBookIdAndExemplarId(1L, "B")).thenReturn(exemplarEntityFound);
		when(reservationService.findActiveReservationByBookIdAndUserId(1L, 1L)).thenReturn(null);
		
		lenderService.lendExemplar(exemplarEntityToFind);
		
		ArgumentCaptor<ExemplarEntity> argumentCaptor = ArgumentCaptor.forClass(ExemplarEntity.class);
		verify(exemplarService).updateById(argumentCaptor.capture());
		
		ExemplarEntity exemplarEntityCaptured = argumentCaptor.getValue();
		
		Date deliveryDateExpected = DateUtil.addDays(exemplarEntityCaptured.getLendDate(), ConstantsTest.TEACHER_BOOK_LOAN_DAYS);

		assertNotNull(exemplarEntityCaptured);
		assertEquals(deliveryDateExpected, exemplarEntityCaptured.getReturnDate());
	}
	
	@Test
	public void lendExemplar_should_lend_exemplar_then_book_stock_attribute_is_decremented() {
		ExemplarEntity exemplarEntityToFind = DataTest.STUDENT_A_BOOK_EXEMPLAR_TO_FIND;
		ExemplarEntity exemplarEntityFound = DataTest.STUDENT_A_BOOK_EXEMPLAR_NOT_BORROWED;
		UserEntity userEntity = DataTest.STUDENT_USER;
		BookEntity bookEntity = DataTest.BOOK;
		
		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(penaltyService.findActivePenaltyByUserId(1L)).thenReturn(null);
		when(userService.countOperationsByUserId(1L)).thenReturn(0);
		when(bookService.findByIsbn("9780132350884")).thenReturn(bookEntity);
		when(exemplarService.findExemplarBorrowedByBookIdAndUserId(1L, 1L)).thenReturn(null);
		when(exemplarService.findExemplarByBookIdAndExemplarId(1L, "A")).thenReturn(exemplarEntityFound);
		when(reservationService.findActiveReservationByBookIdAndUserId(1L, 1L)).thenReturn(null);
		
		lenderService.lendExemplar(exemplarEntityToFind);
		
		ArgumentCaptor<BookEntity> argumentCaptor = ArgumentCaptor.forClass(BookEntity.class);
		verify(bookService).updateById(argumentCaptor.capture());
		
		BookEntity bookEntityCaptured = argumentCaptor.getValue();
		
		assertNotNull(bookEntityCaptured);
		assertEquals(new Long(1), bookEntityCaptured.getStock());
	}
	
	@Test
	public void lendExemplar_should_lend_exemplar_when_exist_an_active_reservation_then_reservation_attributes_are_set_to_indicate_historical() {
		ExemplarEntity exemplarEntityToFind = DataTest.STUDENT_A_BOOK_EXEMPLAR_TO_FIND;
		ExemplarEntity exemplarEntityFound = DataTest.STUDENT_A_BOOK_EXEMPLAR_NOT_BORROWED;
		UserEntity userEntity = DataTest.STUDENT_USER;
		BookEntity bookEntity = DataTest.BOOK;
		ReservationEntity reservationEntity = DataTest.STUDENT_ACTIVE_BOOK_RESERVATION;
		
		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(penaltyService.findActivePenaltyByUserId(1L)).thenReturn(null);
		when(userService.countOperationsByUserId(1L)).thenReturn(0);
		when(bookService.findByIsbn("9780132350884")).thenReturn(bookEntity);
		when(exemplarService.findExemplarBorrowedByBookIdAndUserId(1L, 1L)).thenReturn(null);
		when(exemplarService.findExemplarByBookIdAndExemplarId(1L, "A")).thenReturn(exemplarEntityFound);
		when(reservationService.findActiveReservationByBookIdAndUserId(1L, 1L)).thenReturn(reservationEntity);
		
		lenderService.lendExemplar(exemplarEntityToFind);
		
		ArgumentCaptor<ReservationEntity> argumentCaptor = ArgumentCaptor.forClass(ReservationEntity.class);
		verify(reservationService).updateById(argumentCaptor.capture());
		
		ReservationEntity reservationEntityCaptured = argumentCaptor.getValue();
		
		assertNotNull(reservationEntityCaptured);
		assertNotNull(reservationEntityCaptured.getStartDate());
		assertNotNull(reservationEntityCaptured.getEndDate());
		assertEquals(FinalizationEnum.DONE, reservationEntityCaptured.getFinalizationState());
		assertEquals(HistoricalStateEnum.HISTORICAL, reservationEntityCaptured.getHistoricalState());
	}
	
	@Test
	public void lendExemplar_should_lend_exemplar_when_exist_an_expired_penalty_then_user_state_is_set_to_active_to_delete_penalty() {
		ExemplarEntity exemplarEntityToFind = DataTest.STUDENT_A_BOOK_EXEMPLAR_TO_FIND;
		ExemplarEntity exemplarEntityFound = DataTest.STUDENT_A_BOOK_EXEMPLAR_NOT_BORROWED;
		UserEntity userEntity = DataTest.STUDENT_PENALIZED_USER;
		BookEntity bookEntity = DataTest.BOOK;
		PenaltyEntity penaltyEntity = DataTest.STUDENT_ACTIVE_PENALTY_EXPIRED;
		
		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(penaltyService.findActivePenaltyByUserId(1L)).thenReturn(penaltyEntity);
		when(bookService.findByIsbn("9780132350884")).thenReturn(bookEntity);
		when(exemplarService.findExemplarByBookIdAndExemplarId(1L, "A")).thenReturn(exemplarEntityFound);
		
		lenderService.lendExemplar(exemplarEntityToFind);
		
		ArgumentCaptor<UserEntity> argumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
		verify(userService).updateById(argumentCaptor.capture());
		
		UserEntity userEntityCaptured = argumentCaptor.getValue();
		
		assertNotNull(userEntityCaptured);
		assertEquals(UserStateEnum.ACTIVE, userEntityCaptured.getState());
	}
	
	@Test
	public void lendExemplar_should_lend_exemplar_when_exist_an_expired_penalty_then_penalty_state_is_set_to_indicate_historical() {
		ExemplarEntity exemplarEntityToFind = DataTest.STUDENT_A_BOOK_EXEMPLAR_TO_FIND;
		ExemplarEntity exemplarEntityFound = DataTest.STUDENT_A_BOOK_EXEMPLAR_NOT_BORROWED;
		UserEntity userEntity = DataTest.STUDENT_PENALIZED_USER;
		BookEntity bookEntity = DataTest.BOOK;
		PenaltyEntity penaltyEntity = DataTest.STUDENT_ACTIVE_PENALTY_EXPIRED;
		
		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(penaltyService.findActivePenaltyByUserId(1L)).thenReturn(penaltyEntity);
		when(bookService.findByIsbn("9780132350884")).thenReturn(bookEntity);
		when(exemplarService.findExemplarByBookIdAndExemplarId(1L, "A")).thenReturn(exemplarEntityFound);
		
		lenderService.lendExemplar(exemplarEntityToFind);
		
		ArgumentCaptor<PenaltyEntity> argumentCaptor = ArgumentCaptor.forClass(PenaltyEntity.class);
		verify(penaltyService).updateById(argumentCaptor.capture());
		
		PenaltyEntity penaltyEntityCaptured = argumentCaptor.getValue();
		
		assertNotNull(penaltyEntityCaptured);
		assertEquals(HistoricalStateEnum.HISTORICAL, penaltyEntityCaptured.getState());
	}
	
	@Test
	public void lendExemplar_should_throw_exception_when_user_is_not_found() {
		ExemplarEntity exemplarEntityToFind = DataTest.STUDENT_A_BOOK_EXEMPLAR_TO_FIND;
		
		when(userService.findByNickname("gabrielmar")).thenReturn(null);
		
		expectedException.expect(NoDataFoundException.class);
		expectedException.expectMessage("The user with nickname gabrielmar is not found");
		
		lenderService.lendExemplar(exemplarEntityToFind);
	}
	
	@Test
	public void lendExemplar_should_throw_exception_when_user_is_penalized_or_defaulted() {
		ExemplarEntity exemplaryEntity = DataTest.STUDENT_A_BOOK_EXEMPLAR_TO_FIND;
		UserEntity userEntity = DataTest.STUDENT_USER;
		PenaltyEntity penaltyEntity = DataTest.STUDENT_ACTIVE_PENALTY;
		
		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(penaltyService.findActivePenaltyByUserId(1L)).thenReturn(penaltyEntity);
		
		expectedException.expect(PenaltyException.class);
		expectedException.expectMessage("The user can not take a book exemplary because has an active penalty");
		
		lenderService.lendExemplar(exemplaryEntity);
	}
	
	@Test
	public void lendExemplar_should_throw_exception_when_all_available_student_user_operations_are_used() {
		ExemplarEntity exemplaryEntity = DataTest.STUDENT_A_BOOK_EXEMPLAR_TO_FIND;
		UserEntity userEntity = DataTest.STUDENT_USER;
		
		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(penaltyService.findActivePenaltyByUserId(1L)).thenReturn(null);
		when(userService.countOperationsByUserId(1L)).thenReturn(ConstantsTest.STUDENT_BOOK_LOAN_LIMIT);
		
		expectedException.expect(OperationException.class);
		expectedException.expectMessage("The user has used his possible loan operations");
		
		lenderService.lendExemplar(exemplaryEntity);
	}
	
	@Test
	public void lendExemplar_should_throw_exception_when_all_available_teacher_user_operations_are_used() {
		ExemplarEntity exemplaryEntity = DataTest.TEACHER_A_BOOK_EXEMPLAR_TO_FIND;
		UserEntity userEntity = DataTest.TEACHER_USER;
		
		when(userService.findByNickname("adelamot")).thenReturn(userEntity);
		when(penaltyService.findActivePenaltyByUserId(2L)).thenReturn(null);
		when(userService.countOperationsByUserId(2L)).thenReturn(ConstantsTest.TEACHER_BOOK_LOAN_LIMIT);
		
		expectedException.expect(OperationException.class);
		expectedException.expectMessage("The user has used his possible loan operations");
		
		lenderService.lendExemplar(exemplaryEntity);
	}
	
	@Test
	public void lendExemplar_should_throw_exception_when_book_is_not_found() {
		ExemplarEntity exemplarEntityToFind = DataTest.STUDENT_A_BOOK_EXEMPLAR_TO_FIND;
		UserEntity userEntity = DataTest.STUDENT_USER;
		
		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(penaltyService.findActivePenaltyByUserId(1L)).thenReturn(null);
		when(userService.countOperationsByUserId(1L)).thenReturn(0);
		when(bookService.findByIsbn("9780132350884")).thenReturn(null);
		
		expectedException.expect(NoDataFoundException.class);
		expectedException.expectMessage("The book with isbn 9780132350884 is not found");
		
		lenderService.lendExemplar(exemplarEntityToFind);
	}
	
	@Test
	public void lendExemplar_should_throw_exception_when_book_does_not_have_stock() {
		ExemplarEntity exemplaryEntity = DataTest.STUDENT_A_BOOK_EXEMPLAR_TO_FIND;
		UserEntity userEntity = DataTest.STUDENT_USER;
		BookEntity bookEntity = DataTest.BOOK;
		bookEntity.setStock(0);
		
		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(penaltyService.findActivePenaltyByUserId(1L)).thenReturn(null);
		when(userService.countOperationsByUserId(1L)).thenReturn(0);
		when(bookService.findByIsbn("9780132350884")).thenReturn(bookEntity);
		
		expectedException.expect(LoanException.class);
		expectedException.expectMessage("There aren't book exemplaries availables, the book exemplaries are on loan");
		
		lenderService.lendExemplar(exemplaryEntity);
	}
	
	@Test
	public void lendExemplar_should_throw_exception_when_the_book_is_already_lent_to_the_user() {
		ExemplarEntity exemplarEntityToFind = DataTest.STUDENT_A_BOOK_EXEMPLAR_NOT_BORROWED;
		ExemplarEntity exemplaryEntityLent = DataTest.STUDENT_A_BOOK_EXEMPLAR_BORROWED;
		UserEntity userEntity = DataTest.STUDENT_USER;
		BookEntity bookEntity = DataTest.BOOK;
		
		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(penaltyService.findActivePenaltyByUserId(1L)).thenReturn(null);
		when(userService.countOperationsByUserId(1L)).thenReturn(0);
		when(bookService.findByIsbn("9780132350884")).thenReturn(bookEntity);
		when(exemplarService.findExemplarBorrowedByBookIdAndUserId(1L, 1L)).thenReturn(exemplaryEntityLent);
		
		expectedException.expect(LoanException.class);
		expectedException.expectMessage("The exemplary B can not be borrow because the user has borrowed exemplary A of book 9780132350884 currently");
		
		lenderService.lendExemplar(exemplarEntityToFind);
	}
	
	@Test
	public void lendExemplar_should_throw_exception_when_exemplar_is_not_found() {
		ExemplarEntity exemplarEntityToFind = DataTest.STUDENT_A_BOOK_EXEMPLAR_TO_FIND;
		UserEntity userEntity = DataTest.STUDENT_USER;
		BookEntity bookEntity = DataTest.BOOK;
		
		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(penaltyService.findActivePenaltyByUserId(1L)).thenReturn(null);
		when(userService.countOperationsByUserId(1L)).thenReturn(0);
		when(bookService.findByIsbn("9780132350884")).thenReturn(bookEntity);
		when(exemplarService.findExemplarBorrowedByBookIdAndUserId(1L, 1L)).thenReturn(null);
		when(exemplarService.findExemplarByBookIdAndExemplarId(1L, "A")).thenReturn(null);
		
		expectedException.expect(NoDataFoundException.class);
		expectedException.expectMessage("The exemplary A of book with isbn 9780132350884 is not found");
		
		lenderService.lendExemplar(exemplarEntityToFind);
	}
	
	@Test
	public void lendExemplar_should_throw_exception_when_exemplar_is_not_available() {
		ExemplarEntity exemplarEntityToFind = DataTest.STUDENT_A_BOOK_EXEMPLAR_TO_FIND;
		ExemplarEntity exemplarEntityFound = DataTest.TEACHER_A_BOOK_EXEMPLAR_BORROWED;
		UserEntity userEntity = DataTest.STUDENT_USER;
		BookEntity bookEntity = DataTest.BOOK;
		
		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(penaltyService.findActivePenaltyByUserId(1L)).thenReturn(null);
		when(userService.countOperationsByUserId(1L)).thenReturn(0);
		when(bookService.findByIsbn("9780132350884")).thenReturn(bookEntity);
		when(exemplarService.findExemplarBorrowedByBookIdAndUserId(1L, 1L)).thenReturn(null);
		when(exemplarService.findExemplarByBookIdAndExemplarId(1L, "A")).thenReturn(exemplarEntityFound);
		
		expectedException.expect(LoanException.class);
		expectedException.expectMessage("The exemplary A of book with isbn 9780132350884 is not available, it is borrowed");
		
		lenderService.lendExemplar(exemplarEntityToFind);
	}

}
