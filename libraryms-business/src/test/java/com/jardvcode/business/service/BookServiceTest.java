package com.jardvcode.business.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
import com.jardvcode.business.service.impl.BookServiceImpl;
import com.jardvcode.business.uitl.BookDataTest;
import com.jardvcode.business.uitl.OrderChecker;
import com.jardvcode.model.dao.BookDao;
import com.jardvcode.model.entity.BookEntity;
import com.jardvcode.util.BookBuilder;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Mock
	private BookDao bookDao;
	
	private BookService bookService;
	
	private OrderChecker<BookEntity, Long> orderChecker;
	
	@Before
	public void beforeEachTest() {
		orderChecker = new OrderChecker<BookEntity, Long>(bookDao);
		bookService = new BookServiceImpl(orderChecker.getEntityManagerFactory(), bookDao);
	}
	
	@Test
	public void create_should_register_book() {
		BookEntity bookEntityToCreate = BookDataTest.BOOK_SAVED;
		bookEntityToCreate.setId(null);
		
		when(bookDao.create(bookEntityToCreate)).thenAnswer(new Answer<BookEntity>() {

			@Override
			public BookEntity answer(InvocationOnMock invocation) throws Throwable {
				BookEntity bookEntity = invocation.getArgument(0);
				bookEntity.setId(1L);
				return bookEntity;
			}
			
		});
		
		BookEntity bookEntityCreated = bookService.create(bookEntityToCreate);
		
		assertNotNull(bookEntityCreated);
		assertEquals(new Long(1), bookEntityCreated.getId());
		assertEquals("9780135957059", bookEntityCreated.getIsbn());
		assertEquals("The Pragmatic Programmer", bookEntityCreated.getTitle());
		assertEquals("David Thomas, Andrew Hunt", bookEntityCreated.getAuthor());
		assertEquals(new Integer(320), bookEntityCreated.getPageNumber());
		assertNotNull(bookEntityCreated.getRegisterDate());
		assertEquals(new Integer(2), bookEntityCreated.getStock());
		
		verify(bookDao, times(1)).create(bookEntityCreated);
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(bookDao).create(any(BookEntity.class));
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void create_should_throw_exception_when_something_is_bad() {
		BookEntity bookEntityToCreate = BookDataTest.BOOK_SAVED;
		bookEntityToCreate.setId(null);
		
		RuntimeException exception = new RuntimeException("error");
		
		when(bookDao.create(bookEntityToCreate)).thenThrow(exception);
		
		try {
			bookService.create(bookEntityToCreate);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void findById_should_find_book_by_id() {
		BookEntity bookEntity = BookDataTest.BOOK_SAVED;
		
		Long id = 1L;
		
		when(bookDao.findById(id)).thenReturn(bookEntity);
		
		BookEntity bookEntityFound = bookService.findById(id);
		
		assertNotNull(bookEntityFound);
		assertEquals(id, bookEntityFound.getId());
		
		verify(bookDao, times(1)).findById(id);
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(bookDao).findById(anyLong());
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void findById_should_throw_exception_when_book_is_not_found() {
		when(bookDao.findById(1L)).thenReturn(null);
		
		expectedException.expect(NoDataFoundException.class);
		expectedException.expectMessage("Not found book with id 1");
		
		bookService.findById(1L);
	}
	
	@Test
	public void findById_should_throw_exception_when_something_is_bad() {
		RuntimeException exception = new RuntimeException("error");
		
		when(bookDao.findById(1L)).thenThrow(exception);
		
		try {
			bookService.findById(1L);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void findByIsbn_should_find_book_by_isbn() {
		BookEntity bookEntity = BookDataTest.BOOK_SAVED;
		
		String isbn = "9780135957059";
		
		when(bookDao.findByIsbn(isbn)).thenReturn(bookEntity);
		
		BookEntity bookEntityFound = bookService.findByIsbn(isbn);
		
		assertNotNull(bookEntityFound);
		assertEquals(isbn, bookEntityFound.getIsbn());
		
		verify(bookDao, times(1)).findByIsbn(isbn);
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(bookDao).findByIsbn(anyString());
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void findByIsbn_should_throw_exception_when_book_is_not_found() {
		when(bookDao.findByIsbn("9780135957059")).thenReturn(null);
		
		expectedException.expect(NoDataFoundException.class);
		expectedException.expectMessage("Not found book with isbn 9780135957059");
		
		bookService.findByIsbn("9780135957059");
	}
	
	@Test
	public void findByIsbn_should_throw_exception_when_something_is_bad() {
		RuntimeException exception = new RuntimeException("error");
		
		when(bookDao.findByIsbn("9780135957059")).thenThrow(exception);
		
		try {
			bookService.findByIsbn("9780135957059");
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void updateById_should_update_book() {
		BookEntity bookEntity = BookDataTest.BOOK_SAVED;
		
		BookEntity bookEntityToUpdate = BookBuilder.createBook(1L);
		bookEntityToUpdate.setStock(5);
		
		when(bookDao.findById(1L)).thenReturn(bookEntity);
		when(bookDao.update(bookEntity)).thenAnswer(new Answer<BookEntity>() {
			
			@Override
			public BookEntity answer(InvocationOnMock invocation) throws Throwable {
				BookEntity bookEntity = invocation.getArgument(0);
				return bookEntity;
			}
			
		});
		
		BookEntity bookEntityUpdated = bookService.updateById(bookEntityToUpdate);
		
		assertNotNull(bookEntityUpdated);
		assertEquals(new Long(1), bookEntityUpdated.getId());
		assertEquals(new Integer(5), bookEntityUpdated.getStock());
		
		verify(bookDao, times(1)).findById(anyLong());
		verify(bookDao, times(1)).update(any(BookEntity.class));
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(bookDao).findById(anyLong());
		orderChecker.getInOrder().verify(bookDao).update(any(BookEntity.class));
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void updateById_should_throw_exception_when_book_is_not_found() {
		BookEntity bookEntityToUpdate = BookBuilder.createBook(1L);
		bookEntityToUpdate.setStock(5);
		
		when(bookDao.findById(1L)).thenReturn(null);
		
		expectedException.expect(NoDataFoundException.class);
		expectedException.expectMessage("Not found book with id 1");
		
		bookService.updateById(bookEntityToUpdate);
	}
	
	@Test
	public void updateById_should_throw_exception_when_something_is_bad() {
		BookEntity bookEntityToUpdate = BookBuilder.createBook(1L);
		bookEntityToUpdate.setStock(5);
		
		RuntimeException exception = new RuntimeException("error");
		
		when(bookDao.findById(1L)).thenThrow(exception);
		
		try {
			bookService.updateById(bookEntityToUpdate);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}
	
	@Test
	public void deleteById_should_delete_book_by_id() {
		BookEntity bookEntity = BookDataTest.BOOK_SAVED;
		
		ArgumentCaptor<BookEntity> argumentCaptor = ArgumentCaptor.forClass(BookEntity.class);
		
		when(bookDao.findById(1L)).thenReturn(bookEntity);
		doNothing().when(bookDao).delete(argumentCaptor.capture());
		
		bookService.deleteById(1L);
		
		BookEntity bookEntityCaptured = argumentCaptor.getValue();
		
		assertNotNull(bookEntityCaptured);
		assertEquals(new Long(1), bookEntityCaptured.getId());
		
		verify(bookDao, times(1)).findById(anyLong());
		verify(bookDao, times(1)).delete(any(BookEntity.class));
		
		orderChecker.verifyOrderWhenTransactionIsBegun();
		orderChecker.getInOrder().verify(bookDao).findById(anyLong());
		orderChecker.getInOrder().verify(bookDao).delete(any(BookEntity.class));
		orderChecker.verifyOrderWhenTransactionIsCommit();
	}
	
	@Test
	public void deleteById_should_throw_exception_when_book_is_not_found() {
		when(bookDao.findById(1L)).thenReturn(null);
		
		expectedException.expect(NoDataFoundException.class);
		expectedException.expectMessage("Not found book with id 1");
		
		bookService.deleteById(1L);
	}
	
	@Test
	public void deleteById_should_throw_exception_when_something_is_bad() {
		RuntimeException exception = new RuntimeException("error");
		
		when(bookDao.findById(1L)).thenThrow(exception);
		
		try {
			bookService.deleteById(1L);
			fail();
		} catch(GeneralServiceException e) {
			assertEquals(exception.getMessage(), e.getMessage());
		} catch(Exception e) {
			fail();
		}
		
		orderChecker.verifyOrderWhenTransactionIsRollback();
	}

}
