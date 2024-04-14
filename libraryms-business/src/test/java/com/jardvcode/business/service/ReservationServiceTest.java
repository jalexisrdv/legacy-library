package com.jardvcode.business.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.jardvcode.business.exception.GeneralServiceException;
import com.jardvcode.business.exception.NoDataFoundException;
import com.jardvcode.business.service.impl.ReservationServciceImpl;
import com.jardvcode.business.uitl.OrderChecker;
import com.jardvcode.business.uitl.ReservationDataTest;
import com.jardvcode.model.dao.ReservationDao;
import com.jardvcode.model.entity.FinalizationEnum;
import com.jardvcode.model.entity.HistoricalStateEnum;
import com.jardvcode.model.entity.ReservationEntity;
import com.jardvcode.util.ReservationBuilder;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Mock
	private ReservationDao reservationDao;
	
	private ReservationService reservationService;
	
	private OrderChecker<ReservationEntity, Long> orderChecker;
	
	@Before
	public void beforeEachTest() {
		orderChecker = new OrderChecker<ReservationEntity, Long>(reservationDao);
		reservationService = new ReservationServciceImpl(orderChecker.getEntityManagerFactory(), reservationDao);
	}
	
	@Test
	public void create_should_register_reservation() {
		ReservationEntity reservationEntityToCreate = ReservationDataTest.RESERVATION_SAVED;
		reservationEntityToCreate.setId(null);
		
		when(reservationDao.create(reservationEntityToCreate)).thenAnswer(new Answer<ReservationEntity>() {

			@Override
			public ReservationEntity answer(InvocationOnMock invocation) throws Throwable {
				ReservationEntity reservationEntity = invocation.getArgument(0);
				reservationEntity.setId(1L);
				return reservationEntity;
			}
			
		});
		
		ReservationEntity reservationEntityCreated = reservationService.create(reservationEntityToCreate);
		
		assertNotNull(reservationEntityCreated);
		assertEquals(new Long(1), reservationEntityCreated.getId());
		assertNotNull(reservationEntityCreated.getStartDate());
		assertNotNull(reservationEntityCreated.getEndDate());
		assertEquals(FinalizationEnum.DONE, reservationEntityCreated.getFinalizationState());
		assertEquals(HistoricalStateEnum.HISTORICAL, reservationEntityCreated.getHistoricalState());
		assertNotNull(reservationEntityCreated.getBook());
		assertEquals(new Long(1), reservationEntityCreated.getBook().getId());
		assertNotNull(reservationEntityCreated.getUser());
		assertEquals(new Long(1), reservationEntityCreated.getUser().getId());
		
		verify(reservationDao, times(1)).create(reservationEntityCreated);
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(reservationDao).create(any(ReservationEntity.class));
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void create_should_throw_exception_when_something_is_bad() {
		ReservationEntity reservationEntityToCreate = ReservationDataTest.RESERVATION_SAVED;
		reservationEntityToCreate.setId(null);
		
		RuntimeException exception = new RuntimeException("error");
		
		when(reservationDao.create(reservationEntityToCreate)).thenThrow(exception);
		
		try {
			reservationService.create(reservationEntityToCreate);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void findById_should_find_reservation_by_id() {
		ReservationEntity reservationEntity = ReservationDataTest.RESERVATION_SAVED;
		
		Long id = 1L;
		
		when(reservationDao.findById(id)).thenReturn(reservationEntity);
		
		ReservationEntity reservationEntityFound = reservationService.findById(id);
		
		assertNotNull(reservationEntityFound);
		assertEquals(id, reservationEntityFound.getId());
		
		verify(reservationDao, times(1)).findById(id);
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(reservationDao).findById(anyLong());
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void findById_should_throw_exception_when_reservation_is_not_found() {
		when(reservationDao.findById(1L)).thenReturn(null);
		
		expectedException.expect(NoDataFoundException.class);
		expectedException.expectMessage("Not found reservation with id 1");
		
		reservationService.findById(1L);
	}
	
	@Test
	public void findById_should_throw_exception_when_something_is_bad() {
		RuntimeException exception = new RuntimeException("error");
		
		when(reservationDao.findById(1L)).thenThrow(exception);
		
		try {
			reservationService.findById(1L);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void updateById_should_update_reservation() {
		ReservationEntity reservationEntity = ReservationDataTest.RESERVATION_SAVED;
		
		ReservationEntity reservationEntityToUpdate = ReservationBuilder.createReservation(1L);
		reservationEntityToUpdate.setFinalizationState(FinalizationEnum.CANCELED);
		
		when(reservationDao.findById(1L)).thenReturn(reservationEntity);
		when(reservationDao.update(reservationEntity)).thenAnswer(new Answer<ReservationEntity>() {
			
			@Override
			public ReservationEntity answer(InvocationOnMock invocation) throws Throwable {
				ReservationEntity reservationEntity = invocation.getArgument(0);
				return reservationEntity;
			}
			
		});
		
		ReservationEntity reservationEntityUpdated = reservationService.updateById(reservationEntityToUpdate);
		
		assertNotNull(reservationEntityUpdated);
		assertEquals(new Long(1), reservationEntityUpdated.getId());
		assertEquals(FinalizationEnum.CANCELED, reservationEntityUpdated.getFinalizationState());
		
		verify(reservationDao, times(1)).findById(anyLong());
		verify(reservationDao, times(1)).update(any(ReservationEntity.class));
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(reservationDao).findById(anyLong());
		orderChecker.getInOrder().verify(reservationDao).update(any(ReservationEntity.class));
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void updateById_should_throw_exception_when_reservation_is_not_found() {
		ReservationEntity reservationEntityToUpdate = ReservationBuilder.createReservation(1L);
		reservationEntityToUpdate.setFinalizationState(FinalizationEnum.CANCELED);

		when(reservationDao.findById(1L)).thenReturn(null);
		
		expectedException.expect(NoDataFoundException.class);
		expectedException.expectMessage("Not found reservation with id 1");
		
		reservationService.updateById(reservationEntityToUpdate);
	}
	
	@Test
	public void updateById_should_throw_exception_when_something_is_bad() {
		ReservationEntity reservationEntityToUpdate = ReservationBuilder.createReservation(1L);
		reservationEntityToUpdate.setFinalizationState(FinalizationEnum.CANCELED);
		
		RuntimeException exception = new RuntimeException("error");
		
		when(reservationDao.findById(1L)).thenThrow(exception);
		
		try {
			reservationService.updateById(reservationEntityToUpdate);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void deleteById_should_delete_reservation_by_id() {
		ReservationEntity reservationEntity = ReservationDataTest.RESERVATION_SAVED;
		
		ArgumentCaptor<ReservationEntity> argumentCaptor = ArgumentCaptor.forClass(ReservationEntity.class);
		
		when(reservationDao.findById(1L)).thenReturn(reservationEntity);
		doNothing().when(reservationDao).delete(argumentCaptor.capture());
		
		reservationService.deleteById(1L);
		
		ReservationEntity reservationEntityCaptured = argumentCaptor.getValue();
		
		assertNotNull(reservationEntityCaptured);
		assertEquals(new Long(1), reservationEntityCaptured.getId());
		
		verify(reservationDao, times(1)).findById(anyLong());
		verify(reservationDao, times(1)).delete(any(ReservationEntity.class));
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(reservationDao).findById(anyLong());
		orderChecker.getInOrder().verify(reservationDao).delete(any(ReservationEntity.class));
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void deleteById_should_throw_exception_when_reservation_is_not_found() {
		when(reservationDao.findById(1L)).thenReturn(null);
		
		expectedException.expect(NoDataFoundException.class);
		expectedException.expectMessage("Not found reservation with id 1");
		
		reservationService.deleteById(1L);
	}
	
	@Test
	public void deleteById_should_throw_exception_when_something_is_bad() {
		RuntimeException exception = new RuntimeException("error");
		
		when(reservationDao.findById(1L)).thenThrow(exception);
		
		try {
			reservationService.deleteById(1L);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}

}
