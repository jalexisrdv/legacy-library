package com.jardvcode.business.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.jardvcode.business.configuration.ConstantsTest;
import com.jardvcode.business.exception.NoDataFoundException;
import com.jardvcode.business.exception.OperationException;
import com.jardvcode.business.exception.PenaltyException;
import com.jardvcode.business.service.impl.ReserverServiceImpl;
import com.jardvcode.business.uitl.DataTest;
import com.jardvcode.model.entity.BookEntity;
import com.jardvcode.model.entity.HistoricalStateEnum;
import com.jardvcode.model.entity.PenaltyEntity;
import com.jardvcode.model.entity.ReservationEntity;
import com.jardvcode.model.entity.UserEntity;

@RunWith(MockitoJUnitRunner.class)
public class ReserverServiceTest {
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	private static ReserverService reserverService;
	
	@Mock
	private BookService bookService;
	
	@Mock
	private ReservationService reservationService;
	
	@Mock
	private UserService userService;
	
	@Mock
	private PenaltyService penaltyService;
	
	@Before
	public void init() {
		reserverService = new ReserverServiceImpl(bookService, reservationService, userService, penaltyService);
	}
	
	@Test
	public void reserveBook_should_reserve_book_then_the_reservation_is_registered() {
		UserEntity userEntity = DataTest.STUDENT_USER;
		BookEntity bookEntity = DataTest.BOOK;
		ReservationEntity reservationEntity = DataTest.STUDENT_ACTIVE_BOOK_RESERVATION;
		
		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(penaltyService.findActivePenaltyByUserId(1L)).thenReturn(null);
		when(userService.countOperationsByUserId(1L)).thenReturn(0);
		when(bookService.findByIsbn("9780132350884")).thenReturn(bookEntity);
		
		reserverService.reserveBook(reservationEntity);
		
		ArgumentCaptor<ReservationEntity> argumentCaptor = ArgumentCaptor.forClass(ReservationEntity.class);
		verify(reservationService).create(argumentCaptor.capture());
		
		ReservationEntity reservationEntityCaptured = argumentCaptor.getValue();
		
		assertNotNull(reservationEntityCaptured);
		assertNotNull(reservationEntityCaptured.getStartDate());
		assertNull(reservationEntityCaptured.getEndDate());
		assertNull(reservationEntityCaptured.getFinalizationState());
		assertEquals(HistoricalStateEnum.ACTIVE, reservationEntityCaptured.getHistoricalState());
		assertNotNull(reservationEntityCaptured.getBook());
		assertEquals(new Long(1), reservationEntityCaptured.getBook().getId());
		assertNotNull(reservationEntityCaptured.getUser());
		assertEquals(new Long(1), reservationEntityCaptured.getUser().getId());
	}
	
	@Test
	public void reserveBook_should_throw_exception_when_user_is_not_found() {
		ReservationEntity reservationEntity = DataTest.STUDENT_ACTIVE_BOOK_RESERVATION;

		when(userService.findByNickname("gabrielmar")).thenReturn(null);
		
		expectedException.expect(NoDataFoundException.class);
		expectedException.expectMessage("The user with nickname gabrielmar is not found");
		
		reserverService.reserveBook(reservationEntity);
	}
	
	@Test
	public void reserveBook_should_throw_exception_when_user_is_penalized() {
		UserEntity userEntity = DataTest.STUDENT_USER;
		PenaltyEntity penaltyEntity = DataTest.STUDENT_ACTIVE_PENALTY;
		ReservationEntity reservationEntity = DataTest.STUDENT_ACTIVE_BOOK_RESERVATION;
		
		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(penaltyService.findActivePenaltyByUserId(1L)).thenReturn(penaltyEntity);

		expectedException.expect(PenaltyException.class);
		expectedException.expectMessage("The user can not do a reservation because has an active penalty");
		
		reserverService.reserveBook(reservationEntity);
	}
	
	@Test
	public void reserveBook_should_throw_exception_when_all_available_student_user_operations_are_used() {
		UserEntity userEntity = DataTest.STUDENT_USER;
		ReservationEntity reservationEntity = DataTest.STUDENT_ACTIVE_BOOK_RESERVATION;

		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(penaltyService.findActivePenaltyByUserId(1L)).thenReturn(null);
		when(userService.countOperationsByUserId(1L)).thenReturn(ConstantsTest.STUDENT_BOOK_LOAN_LIMIT);
		
		expectedException.expect(OperationException.class);
		expectedException.expectMessage("The user has used his possible loan operations");
		
		reserverService.reserveBook(reservationEntity);
	}
	
	@Test
	public void reserveBook_should_throw_exception_when_all_available_teacher_user_operations_are_used() {
		UserEntity userEntity = DataTest.TEACHER_USER;
		ReservationEntity reservationEntity = DataTest.TEACHER_ACTIVE_BOOK_RESERVATION;

		when(userService.findByNickname("adelamot")).thenReturn(userEntity);
		when(penaltyService.findActivePenaltyByUserId(1L)).thenReturn(null);
		when(userService.countOperationsByUserId(1L)).thenReturn(ConstantsTest.TEACHER_BOOK_LOAN_LIMIT);
		
		expectedException.expect(OperationException.class);
		expectedException.expectMessage("The user has used his possible loan operations");
		
		reserverService.reserveBook(reservationEntity);
	}
	

	@Test
	public void reserveBook_should_throw_exception_when_book_is_not_found() {
		UserEntity userEntity = DataTest.STUDENT_USER;
		ReservationEntity reservationEntity = DataTest.STUDENT_ACTIVE_BOOK_RESERVATION;

		when(userService.findByNickname("gabrielmar")).thenReturn(userEntity);
		when(penaltyService.findActivePenaltyByUserId(1L)).thenReturn(null);
		when(userService.countOperationsByUserId(1L)).thenReturn(0);
		when(bookService.findByIsbn("9780132350884")).thenReturn(null);
		
		expectedException.expect(NoDataFoundException.class);
		expectedException.expectMessage("The book with isbn 9780132350884 is not found");
		
		reserverService.reserveBook(reservationEntity);
	}
	
	@Test
	public void cancelBookReservation_should_cancel_book_reservation_then_reservation_attributes_are_set_to_indicate_cancelation() {
		ReservationEntity reservationEntityToFind = DataTest.STUDENT_ACTIVE_BOOK_RESERVATION_TO_FIND;
		ReservationEntity reservationEntity = DataTest.STUDENT_ACTIVE_BOOK_RESERVATION;
		
		when(reservationService.findById(1L)).thenReturn(reservationEntity);
		
		reserverService.cancelBookReservation(reservationEntityToFind);
		
		ArgumentCaptor<ReservationEntity> argumentCaptor = ArgumentCaptor.forClass(ReservationEntity.class);
		verify(reservationService).updateById(argumentCaptor.capture());
		
		ReservationEntity reservationEntityCaptured = argumentCaptor.getValue();
		
		assertNotNull(reservationEntityCaptured);
		assertNotNull(reservationEntityCaptured.getEndDate());
		assertNotNull(reservationEntityCaptured.getFinalizationState());
		assertEquals(HistoricalStateEnum.HISTORICAL, reservationEntityCaptured.getHistoricalState());
	}
	
	@Test
	public void cancelBookReservation_should_throw_exception_when_reservation_is_not_found() {
		ReservationEntity reservationEntityToFind = DataTest.STUDENT_ACTIVE_BOOK_RESERVATION_TO_FIND;

		when(reservationService.findById(anyLong())).thenReturn(null);
		
		expectedException.expect(NoDataFoundException.class);
		expectedException.expectMessage("The reservation is not found");

		reserverService.cancelBookReservation(reservationEntityToFind);
	}

}
