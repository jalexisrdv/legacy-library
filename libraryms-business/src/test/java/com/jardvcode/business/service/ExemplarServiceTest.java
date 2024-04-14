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
import com.jardvcode.business.service.impl.ExemplarServiceImpl;
import com.jardvcode.business.uitl.ExemplarDataTest;
import com.jardvcode.business.uitl.OrderChecker;
import com.jardvcode.model.dao.ExemplarDao;
import com.jardvcode.model.entity.BookExemplaryEnum;
import com.jardvcode.model.entity.ExemplarEntity;
import com.jardvcode.model.entity.LocationEnum;
import com.jardvcode.util.ExemplarBuilder;

@RunWith(MockitoJUnitRunner.class)
public class ExemplarServiceTest {
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Mock
	private ExemplarDao exemplarDao;
	
	private ExemplarService exemplarService;
	
	private OrderChecker<ExemplarEntity, Long> orderChecker;
	
	@Before
	public void beforeEachTest() {
		orderChecker = new OrderChecker<ExemplarEntity, Long>(exemplarDao);
		exemplarService = new ExemplarServiceImpl(orderChecker.getEntityManagerFactory(), exemplarDao);
	}
	
	@Test
	public void create_should_register_exemplar() {
		ExemplarEntity exemplarEntityToCreate = ExemplarDataTest.EXEMPLAR_SAVED;
		exemplarEntityToCreate.setId(null);
		
		when(exemplarDao.create(exemplarEntityToCreate)).thenAnswer(new Answer<ExemplarEntity>() {

			@Override
			public ExemplarEntity answer(InvocationOnMock invocation) throws Throwable {
				ExemplarEntity exemplarEntity = invocation.getArgument(0);
				exemplarEntity.setId(1L);
				return exemplarEntity;
			}
			
		});
		
		ExemplarEntity exemplarEntityCreated = exemplarService.create(exemplarEntityToCreate);
		
		assertNotNull(exemplarEntityCreated);
		assertEquals(new Long(1), exemplarEntityCreated.getId());
		assertEquals("C", exemplarEntityCreated.getExemplaryId());
		assertNotNull(exemplarEntityCreated.getAcquisitionDate());
		assertEquals("New Book", exemplarEntityCreated.getObservation());
		assertNotNull(exemplarEntityCreated.getLendDate());
		assertNotNull(exemplarEntityCreated.getReturnDate());
		assertEquals(BookExemplaryEnum.AVAILABLE, exemplarEntityCreated.getState());
		assertEquals(LocationEnum.SITTING_ROOM, exemplarEntityCreated.getLocation());
		assertNotNull(exemplarEntityCreated.getBook());
		assertEquals(new Long(1), exemplarEntityCreated.getBook().getId());
		assertNotNull(exemplarEntityCreated.getUser());
		assertEquals(new Long(1), exemplarEntityCreated.getUser().getId());
		
		verify(exemplarDao, times(1)).create(exemplarEntityCreated);
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(exemplarDao).create(any(ExemplarEntity.class));
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void create_should_throw_exception_when_something_is_bad() {
		ExemplarEntity exemplarEntityToCreate = ExemplarDataTest.EXEMPLAR_SAVED;
		exemplarEntityToCreate.setId(null);
		
		RuntimeException exception = new RuntimeException("error");
		
		when(exemplarDao.create(exemplarEntityToCreate)).thenThrow(exception);
		
		try {
			exemplarService.create(exemplarEntityToCreate);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void findById_should_find_exemplar_by_id() {
		ExemplarEntity exemplarEntity = ExemplarDataTest.EXEMPLAR_SAVED;
		
		Long id = 1L;
		
		when(exemplarDao.findById(id)).thenReturn(exemplarEntity);
		
		ExemplarEntity exemplarEntityFound = exemplarService.findById(id);
		
		assertNotNull(exemplarEntityFound);
		assertEquals(id, exemplarEntityFound.getId());
		
		verify(exemplarDao, times(1)).findById(id);
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(exemplarDao).findById(anyLong());
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void findById_should_throw_exception_when_exemplar_is_not_found() {
		when(exemplarDao.findById(1L)).thenReturn(null);
		
		expectedException.expect(NoDataFoundException.class);
		expectedException.expectMessage("Not found exemplar with id 1");
		
		exemplarService.findById(1L);
	}
	
	@Test
	public void findById_should_throw_exception_when_something_is_bad() {
		RuntimeException exception = new RuntimeException("error");
		
		when(exemplarDao.findById(1L)).thenThrow(exception);
		
		try {
			exemplarService.findById(1L);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void updateById_should_update_exemplar() {
		ExemplarEntity exemplarEntity = ExemplarDataTest.EXEMPLAR_SAVED;
		
		ExemplarEntity exemplarEntityToUpdate = ExemplarBuilder.createExemplar(1L);
		exemplarEntityToUpdate.setObservation("Damaged Exemplar");
		
		when(exemplarDao.findById(1L)).thenReturn(exemplarEntity);
		when(exemplarDao.update(exemplarEntity)).thenAnswer(new Answer<ExemplarEntity>() {
			
			@Override
			public ExemplarEntity answer(InvocationOnMock invocation) throws Throwable {
				ExemplarEntity exemplarEntity = invocation.getArgument(0);
				return exemplarEntity;
			}
			
		});
		
		ExemplarEntity exemplarEntityUpdated = exemplarService.updateById(exemplarEntityToUpdate);
		
		assertNotNull(exemplarEntityUpdated);
		assertEquals(new Long(1), exemplarEntityUpdated.getId());
		assertEquals("Damaged Exemplar", exemplarEntityUpdated.getObservation());
		
		verify(exemplarDao, times(1)).findById(anyLong());
		verify(exemplarDao, times(1)).update(any(ExemplarEntity.class));
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(exemplarDao).findById(anyLong());
		orderChecker.getInOrder().verify(exemplarDao).update(any(ExemplarEntity.class));
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void updateById_should_throw_exception_when_exemplar_is_not_found() {
		ExemplarEntity exemplarEntityToUpdate = ExemplarBuilder.createExemplar(1L);
		exemplarEntityToUpdate.setObservation("Damaged Exemplar");

		when(exemplarDao.findById(1L)).thenReturn(null);
		
		expectedException.expect(NoDataFoundException.class);
		expectedException.expectMessage("Not found exemplar with id 1");
		
		exemplarService.updateById(exemplarEntityToUpdate);
	}
	
	@Test
	public void updateById_should_throw_exception_when_something_is_bad() {
		ExemplarEntity exemplarEntityToUpdate = ExemplarBuilder.createExemplar(1L);
		exemplarEntityToUpdate.setObservation("Damaged Exemplar");
		
		RuntimeException exception = new RuntimeException("error");
		
		when(exemplarDao.findById(1L)).thenThrow(exception);
		
		try {
			exemplarService.updateById(exemplarEntityToUpdate);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void deleteById_should_delete_exemplar_by_id() {
		ExemplarEntity exemplarEntity = ExemplarDataTest.EXEMPLAR_SAVED;
		
		ArgumentCaptor<ExemplarEntity> argumentCaptor = ArgumentCaptor.forClass(ExemplarEntity.class);
		
		when(exemplarDao.findById(1L)).thenReturn(exemplarEntity);
		doNothing().when(exemplarDao).delete(argumentCaptor.capture());
		
		exemplarService.deleteById(1L);
		
		ExemplarEntity exemplarEntityCaptured = argumentCaptor.getValue();
		
		assertNotNull(exemplarEntityCaptured);
		assertEquals(new Long(1), exemplarEntityCaptured.getId());
		
		verify(exemplarDao, times(1)).findById(anyLong());
		verify(exemplarDao, times(1)).delete(any(ExemplarEntity.class));
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(exemplarDao).findById(anyLong());
		orderChecker.getInOrder().verify(exemplarDao).delete(any(ExemplarEntity.class));
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void deleteById_should_throw_exception_when_exemplar_is_not_found() {
		when(exemplarDao.findById(1L)).thenReturn(null);
		
		expectedException.expect(NoDataFoundException.class);
		expectedException.expectMessage("Not found exemplar with id 1");
		
		exemplarService.deleteById(1L);
	}
	
	@Test
	public void deleteById_should_throw_exception_when_something_is_bad() {
		RuntimeException exception = new RuntimeException("error");
		
		when(exemplarDao.findById(1L)).thenThrow(exception);
		
		try {
			exemplarService.deleteById(1L);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}

}
