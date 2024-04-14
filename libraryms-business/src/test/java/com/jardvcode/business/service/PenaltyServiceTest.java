package com.jardvcode.business.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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
import com.jardvcode.business.service.impl.PenaltyServiceImpl;
import com.jardvcode.business.uitl.OrderChecker;
import com.jardvcode.business.uitl.PenaltyDataTest;
import com.jardvcode.model.dao.PenaltyDao;
import com.jardvcode.model.entity.HistoricalStateEnum;
import com.jardvcode.model.entity.PenaltyEntity;
import com.jardvcode.util.PenaltyBuilder;
import com.jardvcode.util.UserBuilder;

@RunWith(MockitoJUnitRunner.class)
public class PenaltyServiceTest {
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Mock
	private PenaltyDao penaltyDao;
	
	private PenaltyService penaltyService;
	
	private OrderChecker<PenaltyEntity, Long> orderChecker;
	
	@Before
	public void beforeEachTest() {
		orderChecker = new OrderChecker<PenaltyEntity, Long>(penaltyDao);
		penaltyService = new PenaltyServiceImpl(orderChecker.getEntityManagerFactory(), penaltyDao);
	}
	
	@Test
	public void create_should_register_penalty() {
		PenaltyEntity penaltyEntityToCreate = PenaltyDataTest.PENALTY_SAVED;
		penaltyEntityToCreate.setId(null);
		
		when(penaltyDao.create(penaltyEntityToCreate)).thenAnswer(new Answer<PenaltyEntity>() {

			@Override
			public PenaltyEntity answer(InvocationOnMock invocation) throws Throwable {
				PenaltyEntity penaltyEntity = invocation.getArgument(0);
				penaltyEntity.setId(1L);
				return penaltyEntity;
			}
			
		});
		
		PenaltyEntity penaltyEntityCreated = penaltyService.create(penaltyEntityToCreate);
		
		assertNotNull(penaltyEntityCreated);
		assertTrue(penaltyEntityCreated.getId() == 2);
		assertNotNull(penaltyEntityCreated.getStartDate());
		assertNotNull(penaltyEntityCreated.getEndDate());
		assertEquals(HistoricalStateEnum.ACTIVE, penaltyEntityCreated.getState());
		assertNotNull(penaltyEntityCreated);
		assertEquals(new Long(1), penaltyEntityCreated.getUser().getId());
		
		verify(penaltyDao, times(1)).create(penaltyEntityCreated);
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(penaltyDao).create(any(PenaltyEntity.class));
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void create_should_throw_exception_when_something_is_bad() {
		PenaltyEntity penaltyEntityToCreate = PenaltyDataTest.PENALTY_SAVED;
		penaltyEntityToCreate.setId(null);
		
		RuntimeException exception = new RuntimeException("error");
		
		when(penaltyDao.create(penaltyEntityToCreate)).thenThrow(exception);
		
		try {
			penaltyService.create(penaltyEntityToCreate);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void findById_should_find_penalty_by_id() {
		PenaltyEntity penaltyEntity = PenaltyDataTest.PENALTY_SAVED;
		
		Long id = 1L;
		
		when(penaltyDao.findById(id)).thenReturn(penaltyEntity);
		
		PenaltyEntity penaltyEntityFound = penaltyService.findById(id);
		
		assertNotNull(penaltyEntityFound);
		assertEquals(id, penaltyEntityFound.getId());
		
		verify(penaltyDao, times(1)).findById(id);
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(penaltyDao).findById(anyLong());
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void findById_should_throw_exception_when_penalty_is_not_found() {
		when(penaltyDao.findById(1L)).thenReturn(null);
		
		expectedException.expect(NoDataFoundException.class);
		expectedException.expectMessage("Not found penalty with id 1");
		
		penaltyService.findById(1L);
	}
	
	@Test
	public void findById_should_throw_exception_when_something_is_bad() {
		RuntimeException exception = new RuntimeException("error");
		
		when(penaltyDao.findById(1L)).thenThrow(exception);
		
		try {
			penaltyService.findById(1L);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void updateById_should_update_penalty() {
		PenaltyEntity penaltyEntity = PenaltyDataTest.PENALTY_SAVED;
		
		PenaltyEntity penaltyEntityToUpdate = PenaltyBuilder.createPenalty(1L);
		penaltyEntityToUpdate.setUser(UserBuilder.createTeacherUser(2L));
		
		when(penaltyDao.findById(1L)).thenReturn(penaltyEntity);
		when(penaltyDao.update(penaltyEntity)).thenAnswer(new Answer<PenaltyEntity>() {
			
			@Override
			public PenaltyEntity answer(InvocationOnMock invocation) throws Throwable {
				PenaltyEntity penaltyEntity = invocation.getArgument(0);
				return penaltyEntity;
			}
			
		});
		
		PenaltyEntity penaltyEntityUpdated = penaltyService.updateById(penaltyEntityToUpdate);
		
		assertNotNull(penaltyEntityUpdated);
		assertEquals(new Long(1), penaltyEntityUpdated.getId());
		assertNotNull(penaltyEntityUpdated.getUser());
		assertEquals(new Long(2), penaltyEntityUpdated.getUser().getId());
		
		verify(penaltyDao, times(1)).findById(anyLong());
		verify(penaltyDao, times(1)).update(any(PenaltyEntity.class));
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(penaltyDao).findById(anyLong());
		orderChecker.getInOrder().verify(penaltyDao).update(any(PenaltyEntity.class));
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void updateById_should_throw_exception_when_penalty_is_not_found() {
		PenaltyEntity penaltyEntityToUpdate = PenaltyBuilder.createPenalty(1L);
		penaltyEntityToUpdate.setUser(UserBuilder.createTeacherUser(2L));

		when(penaltyDao.findById(1L)).thenReturn(null);
		
		expectedException.expect(NoDataFoundException.class);
		expectedException.expectMessage("Not found penalty with id 1");
		
		penaltyService.updateById(penaltyEntityToUpdate);
	}
	
	@Test
	public void updateById_should_throw_exception_when_something_is_bad() {
		PenaltyEntity penaltyEntityToUpdate = PenaltyBuilder.createPenalty(1L);
		penaltyEntityToUpdate.setUser(UserBuilder.createTeacherUser(2L));
		
		RuntimeException exception = new RuntimeException("error");
		
		when(penaltyDao.findById(1L)).thenThrow(exception);
		
		try {
			penaltyService.updateById(penaltyEntityToUpdate);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void deleteById_should_delete_penalty_by_id() {
		PenaltyEntity penaltyEntity = PenaltyDataTest.PENALTY_SAVED;
		
		ArgumentCaptor<PenaltyEntity> argumentCaptor = ArgumentCaptor.forClass(PenaltyEntity.class);
		
		when(penaltyDao.findById(1L)).thenReturn(penaltyEntity);
		doNothing().when(penaltyDao).delete(argumentCaptor.capture());
		
		penaltyService.deleteById(1L);
		
		PenaltyEntity penaltyEntityCaptured = argumentCaptor.getValue();
		
		assertNotNull(penaltyEntityCaptured);
		assertEquals(new Long(1), penaltyEntityCaptured.getId());
		
		verify(penaltyDao, times(1)).findById(anyLong());
		verify(penaltyDao, times(1)).delete(any(PenaltyEntity.class));
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(penaltyDao).findById(anyLong());
		orderChecker.getInOrder().verify(penaltyDao).delete(any(PenaltyEntity.class));
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void deleteById_should_throw_exception_when_penalty_is_not_found() {
		when(penaltyDao.findById(1L)).thenReturn(null);
		
		expectedException.expect(NoDataFoundException.class);
		expectedException.expectMessage("Not found penalty with id 1");
		
		penaltyService.deleteById(1L);
	}
	
	@Test
	public void deleteById_should_throw_exception_when_something_is_bad() {
		RuntimeException exception = new RuntimeException("error");
		
		when(penaltyDao.findById(1L)).thenThrow(exception);
		
		try {
			penaltyService.deleteById(1L);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}

}
