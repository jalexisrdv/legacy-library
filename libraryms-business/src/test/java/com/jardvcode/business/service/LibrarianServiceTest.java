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
import com.jardvcode.business.service.impl.LibrarianServiceImpl;
import com.jardvcode.business.uitl.LibrarianDataTest;
import com.jardvcode.business.uitl.OrderChecker;
import com.jardvcode.model.dao.LibrarianDao;
import com.jardvcode.model.entity.LibrarianEntity;
import com.jardvcode.util.LibrarianBuilder;

@RunWith(MockitoJUnitRunner.class)
public class LibrarianServiceTest {
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Mock
	private LibrarianDao librarianDao;
	
	private LibrarianService librarianService;
	
	private OrderChecker<LibrarianEntity, Long> orderChecker;
	
	@Before
	public void beforeEachTest() {
		orderChecker = new OrderChecker<LibrarianEntity, Long>(librarianDao);
		librarianService = new LibrarianServiceImpl(orderChecker.getEntityManagerFactory(), librarianDao);
	}
	
	@Test
	public void create_should_register_librarian() {
		LibrarianEntity librarianEntityToCreate = LibrarianDataTest.LIBRARIAN_SAVED;
		librarianEntityToCreate.setId(null);
		
		when(librarianDao.create(librarianEntityToCreate)).thenAnswer(new Answer<LibrarianEntity>() {

			@Override
			public LibrarianEntity answer(InvocationOnMock invocation) throws Throwable {
				LibrarianEntity librarianEntity = invocation.getArgument(0);
				librarianEntity.setId(1L);
				return librarianEntity;
			}
			
		});
		
		LibrarianEntity librarianEntityCreated = librarianService.create(librarianEntityToCreate);
		
		assertNotNull(librarianEntityCreated);
		assertEquals(new Long(2), librarianEntityCreated.getId());
		assertEquals("chosen", librarianEntityCreated.getNickname());
		assertEquals("12345678", librarianEntityCreated.getPassword());
		assertEquals("9876435986", librarianEntityCreated.getNif());
		
		verify(librarianDao, times(1)).create(librarianEntityCreated);
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(librarianDao).create(any(LibrarianEntity.class));
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void create_should_throw_exception_when_something_is_bad() {
		LibrarianEntity librarianEntityToCreate = LibrarianDataTest.LIBRARIAN_SAVED;
		librarianEntityToCreate.setId(null);
		
		RuntimeException exception = new RuntimeException("error");
		
		when(librarianDao.create(librarianEntityToCreate)).thenThrow(exception);
		
		try {
			librarianService.create(librarianEntityToCreate);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void findById_should_find_librarian_by_id() {
		LibrarianEntity librarianEntity = LibrarianDataTest.LIBRARIAN_SAVED;
		
		Long id = 1L;
		
		when(librarianDao.findById(id)).thenReturn(librarianEntity);
		
		LibrarianEntity librarianEntityFound = librarianService.findById(id);
		
		assertNotNull(librarianEntityFound);
		assertEquals(id, librarianEntityFound.getId());
		
		verify(librarianDao, times(1)).findById(id);
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(librarianDao).findById(anyLong());
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void findById_should_throw_exception_when_librarian_is_not_found() {
		when(librarianDao.findById(1L)).thenReturn(null);
		
		expectedException.expect(NoDataFoundException.class);
		expectedException.expectMessage("Not found librarian with id 1");
		
		librarianService.findById(1L);
	}
	
	@Test
	public void findById_should_throw_exception_when_something_is_bad() {
		RuntimeException exception = new RuntimeException("error");
		
		when(librarianDao.findById(1L)).thenThrow(exception);
		
		try {
			librarianService.findById(1L);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void updateById_should_update_librarian() {
		LibrarianEntity librarianEntity = LibrarianDataTest.LIBRARIAN_SAVED;
		
		LibrarianEntity librarianEntityToUpdate = LibrarianBuilder.createLibrarian(1L);
		librarianEntityToUpdate.setPassword("9876543210");
		
		when(librarianDao.findById(1L)).thenReturn(librarianEntity);
		when(librarianDao.update(librarianEntity)).thenAnswer(new Answer<LibrarianEntity>() {
			
			@Override
			public LibrarianEntity answer(InvocationOnMock invocation) throws Throwable {
				LibrarianEntity librarianEntity = invocation.getArgument(0);
				return librarianEntity;
			}
			
		});
		
		LibrarianEntity librarianEntityUpdated = librarianService.updateById(librarianEntityToUpdate);
		
		assertNotNull(librarianEntityUpdated);
		assertEquals(new Long(1), librarianEntityUpdated.getId());
		assertEquals("9876543210", librarianEntityUpdated.getPassword());
		
		verify(librarianDao, times(1)).findById(anyLong());
		verify(librarianDao, times(1)).update(any(LibrarianEntity.class));
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(librarianDao).findById(anyLong());
		orderChecker.getInOrder().verify(librarianDao).update(any(LibrarianEntity.class));
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void updateById_should_throw_exception_when_librarian_is_not_found() {
		LibrarianEntity librarianEntityToUpdate = LibrarianBuilder.createLibrarian(1L);
		librarianEntityToUpdate.setPassword("9876543210");

		when(librarianDao.findById(1L)).thenReturn(null);
		
		expectedException.expect(NoDataFoundException.class);
		expectedException.expectMessage("Not found librarian with id 1");
		
		librarianService.updateById(librarianEntityToUpdate);
	}
	
	@Test
	public void updateById_should_throw_exception_when_something_is_bad() {
		LibrarianEntity librarianEntityToUpdate = LibrarianBuilder.createLibrarian(1L);
		librarianEntityToUpdate.setPassword("9876543210");
		
		RuntimeException exception = new RuntimeException("error");
		
		when(librarianDao.findById(1L)).thenThrow(exception);
		
		try {
			librarianService.updateById(librarianEntityToUpdate);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void deleteById_should_delete_librarian_by_id() {
		LibrarianEntity librarianEntity = LibrarianDataTest.LIBRARIAN_SAVED;
		
		ArgumentCaptor<LibrarianEntity> argumentCaptor = ArgumentCaptor.forClass(LibrarianEntity.class);
		
		when(librarianDao.findById(1L)).thenReturn(librarianEntity);
		doNothing().when(librarianDao).delete(argumentCaptor.capture());
		
		librarianService.deleteById(1L);
		
		LibrarianEntity librarianEntityCaptured = argumentCaptor.getValue();
		
		assertNotNull(librarianEntityCaptured);
		assertEquals(new Long(1), librarianEntityCaptured.getId());
		
		verify(librarianDao, times(1)).findById(anyLong());
		verify(librarianDao, times(1)).delete(any(LibrarianEntity.class));
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(librarianDao).findById(anyLong());
		orderChecker.getInOrder().verify(librarianDao).delete(any(LibrarianEntity.class));
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void deleteById_should_throw_exception_when_librarian_is_not_found() {
		when(librarianDao.findById(1L)).thenReturn(null);
		
		expectedException.expect(NoDataFoundException.class);
		expectedException.expectMessage("Not found librarian with id 1");
		
		librarianService.deleteById(1L);
	}
	
	@Test
	public void deleteById_should_throw_exception_when_something_is_bad() {
		RuntimeException exception = new RuntimeException("error");
		
		when(librarianDao.findById(1L)).thenThrow(exception);
		
		try {
			librarianService.deleteById(1L);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}

}
