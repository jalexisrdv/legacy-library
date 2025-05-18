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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.jardvcode.business.exception.GeneralServiceException;
import com.jardvcode.business.exception.NoDataFoundException;
import com.jardvcode.business.service.impl.LoanHistoricalServiceImpl;
import com.jardvcode.business.uitl.LoanHistoricalDataTest;
import com.jardvcode.business.uitl.OrderChecker;
import com.jardvcode.model.dao.LoanHistoricalDao;
import com.jardvcode.model.entity.LoanHistoricalEntity;
import com.jardvcode.util.LoanHistoricalBuilder;
import com.jardvcode.util.UserBuilder;

@RunWith(MockitoJUnitRunner.class)
public class LoanHistoricalServiceTest {

	@Mock
	private LoanHistoricalDao loanHistoricalDao;
	
	private LoanHistoricalService loanHistoricalService;
	
	private OrderChecker<LoanHistoricalEntity, Long> orderChecker;
	
	@Before
	public void beforeEachTest() {
		orderChecker = new OrderChecker<LoanHistoricalEntity, Long>(loanHistoricalDao);
		loanHistoricalService = new LoanHistoricalServiceImpl(orderChecker.getEntityManagerFactory(), loanHistoricalDao);
	}
	
	@Test
	public void create_should_register_loan() {
		LoanHistoricalEntity loanHistoricalEntityToCreate = LoanHistoricalDataTest.LOAN_HISTORICAL_SAVED;
		loanHistoricalEntityToCreate.setId(null);
		
		when(loanHistoricalDao.create(loanHistoricalEntityToCreate)).thenAnswer(new Answer<LoanHistoricalEntity>() {

			@Override
			public LoanHistoricalEntity answer(InvocationOnMock invocation) throws Throwable {
				LoanHistoricalEntity loanHistoricalEntity = invocation.getArgument(0);
				loanHistoricalEntity.setId(1L);
				return loanHistoricalEntity;
			}
			
		});
		
		LoanHistoricalEntity loanHistoricalEntityCreated = loanHistoricalService.create(loanHistoricalEntityToCreate);
		
		assertNotNull(loanHistoricalEntityCreated);
		assertEquals(new Long(1), loanHistoricalEntityCreated.getId());
		assertNotNull(loanHistoricalEntityCreated.getLendDate());
		assertNotNull(loanHistoricalEntityCreated.getReturnDate());
		assertNotNull(loanHistoricalEntityCreated.getRealReturnDate());
		assertNotNull(loanHistoricalEntityCreated.getUser());
		assertEquals(new Long(1), loanHistoricalEntityCreated.getUser().getId());
		assertNotNull(loanHistoricalEntityCreated.getExemplaryBook());
		assertEquals(new Long(1), loanHistoricalEntityCreated.getExemplaryBook().getId());
		
		verify(loanHistoricalDao, times(1)).create(loanHistoricalEntityCreated);
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(loanHistoricalDao).create(any(LoanHistoricalEntity.class));
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void create_should_throw_exception_when_something_is_bad() {
		LoanHistoricalEntity loanHistoricalEntityToCreate = LoanHistoricalDataTest.LOAN_HISTORICAL_SAVED;
		loanHistoricalEntityToCreate.setId(null);
		
		RuntimeException exception = new RuntimeException("error");
		
		when(loanHistoricalDao.create(loanHistoricalEntityToCreate)).thenThrow(exception);
		
		try {
			loanHistoricalService.create(loanHistoricalEntityToCreate);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void findById_should_find_loan_by_id() {
		LoanHistoricalEntity loanHistoricalEntity = LoanHistoricalDataTest.LOAN_HISTORICAL_SAVED;
		
		Long id = 1L;
		
		when(loanHistoricalDao.findById(id)).thenReturn(loanHistoricalEntity);
		
		LoanHistoricalEntity loanHistoricalEntityFound = loanHistoricalService.findById(id);
		
		assertNotNull(loanHistoricalEntityFound);
		assertEquals(id, loanHistoricalEntityFound.getId());
		
		verify(loanHistoricalDao, times(1)).findById(id);
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(loanHistoricalDao).findById(anyLong());
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void findById_should_throw_exception_when_loan_is_not_found() {
		NoDataFoundException exception = new NoDataFoundException("error");
		
		when(loanHistoricalDao.findById(1L)).thenThrow(exception);
		
		try {
			loanHistoricalService.findById(1L);
			fail();
		} catch(NoDataFoundException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void findById_should_throw_exception_when_something_is_bad() {
		RuntimeException exception = new RuntimeException("error");
		
		when(loanHistoricalDao.findById(1L)).thenThrow(exception);
		
		try {
			loanHistoricalService.findById(1L);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void updateById_should_update_loan() {
		LoanHistoricalEntity loanHistoricalEntity = LoanHistoricalDataTest.LOAN_HISTORICAL_SAVED;
		
		LoanHistoricalEntity loanHistoricalEntityToUpdate = LoanHistoricalBuilder.createLoanHistorical(1L);
		loanHistoricalEntityToUpdate.setUser(UserBuilder.createTeacherUser(2L));
		
		when(loanHistoricalDao.findById(1L)).thenReturn(loanHistoricalEntity);
		when(loanHistoricalDao.update(loanHistoricalEntity)).thenAnswer(new Answer<LoanHistoricalEntity>() {
			
			@Override
			public LoanHistoricalEntity answer(InvocationOnMock invocation) throws Throwable {
				LoanHistoricalEntity loanHistoricalEntity = invocation.getArgument(0);
				return loanHistoricalEntity;
			}
			
		});
		
		LoanHistoricalEntity loanHistoricalEntityUpdated = loanHistoricalService.updateById(loanHistoricalEntityToUpdate);
		
		assertNotNull(loanHistoricalEntityUpdated);
		assertEquals(new Long(1), loanHistoricalEntityUpdated.getId());
		assertNotNull(loanHistoricalEntityUpdated.getUser());
		assertEquals(new Long(2), loanHistoricalEntityUpdated.getUser().getId());
		
		verify(loanHistoricalDao, times(1)).findById(anyLong());
		verify(loanHistoricalDao, times(1)).update(any(LoanHistoricalEntity.class));
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(loanHistoricalDao).findById(anyLong());
		orderChecker.getInOrder().verify(loanHistoricalDao).update(any(LoanHistoricalEntity.class));
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void updateById_should_throw_exception_when_loan_is_not_found() {
		LoanHistoricalEntity loanHistoricalEntityToUpdate = LoanHistoricalBuilder.createLoanHistorical(1L);
		loanHistoricalEntityToUpdate.setUser(UserBuilder.createTeacherUser(2L));
		
		NoDataFoundException exception = new NoDataFoundException("error");
		
		when(loanHistoricalDao.findById(1L)).thenThrow(exception);
		
		try {
			loanHistoricalService.updateById(loanHistoricalEntityToUpdate);
			fail();
		} catch(NoDataFoundException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void updateById_should_throw_exception_when_something_is_bad() {
		LoanHistoricalEntity loanHistoricalEntityToUpdate = LoanHistoricalBuilder.createLoanHistorical(1L);
		loanHistoricalEntityToUpdate.setUser(UserBuilder.createTeacherUser(2L));
		
		RuntimeException exception = new RuntimeException("error");
		
		when(loanHistoricalDao.findById(1L)).thenThrow(exception);
		
		try {
			loanHistoricalService.updateById(loanHistoricalEntityToUpdate);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void deleteById_should_delete_loan_by_id() {
		LoanHistoricalEntity loanHistoricalEntity = LoanHistoricalDataTest.LOAN_HISTORICAL_SAVED;
		
		ArgumentCaptor<LoanHistoricalEntity> argumentCaptor = ArgumentCaptor.forClass(LoanHistoricalEntity.class);
		
		when(loanHistoricalDao.findById(1L)).thenReturn(loanHistoricalEntity);
		doNothing().when(loanHistoricalDao).delete(argumentCaptor.capture());
		
		loanHistoricalService.deleteById(1L);
		
		LoanHistoricalEntity loanHistoricalEntityCaptured = argumentCaptor.getValue();
		
		assertNotNull(loanHistoricalEntityCaptured);
		assertEquals(new Long(1), loanHistoricalEntityCaptured.getId());
		
		verify(loanHistoricalDao, times(1)).findById(anyLong());
		verify(loanHistoricalDao, times(1)).delete(any(LoanHistoricalEntity.class));
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(loanHistoricalDao).findById(anyLong());
		orderChecker.getInOrder().verify(loanHistoricalDao).delete(any(LoanHistoricalEntity.class));
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void deleteById_should_throw_exception_when_loan_is_not_found() {
		NoDataFoundException exception = new NoDataFoundException("error");
		
		when(loanHistoricalDao.findById(1L)).thenThrow(exception);
		
		try {
			loanHistoricalService.deleteById(1L);
			fail();
		} catch(NoDataFoundException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void deleteById_should_throw_exception_when_something_is_bad() {
		RuntimeException exception = new RuntimeException("error");
		
		when(loanHistoricalDao.findById(1L)).thenThrow(exception);
		
		try {
			loanHistoricalService.deleteById(1L);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
}
