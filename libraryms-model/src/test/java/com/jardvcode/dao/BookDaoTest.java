package com.jardvcode.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jardvcode.configuration.DBUnitConfiguration;
import com.jardvcode.model.dao.impl.BookDaoImpl;
import com.jardvcode.model.entity.BookEntity;
import com.jardvcode.model.util.DateUtil;
import com.jardvcode.util.BookBuilder;

public class BookDaoTest {
	
	private static DBUnitConfiguration unitDatabaseConfigurator;
	private static EntityManagerFactory entityManagerFactory;

	@BeforeClass
	public static void initDatabaseTest() {
		unitDatabaseConfigurator = new DBUnitConfiguration();
		try {
			entityManagerFactory = unitDatabaseConfigurator.createEntityManagerFactory();
		} catch (Exception ex) {
			fail("Could not create EntityManagerFactory");
		}
	}

	@AfterClass
	public static void closeEntityManagerFactory() throws Exception {
		unitDatabaseConfigurator.closeEntityManagerFactory();
	}

	@Before
	public void cleanAndInsertDB() throws Exception {
		unitDatabaseConfigurator.cleanAndInsertDB();
	}
	
	@Test
	public void create_should_create_book_when_all_attributes_are_fine() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		BookDaoImpl bookDao = new BookDaoImpl(entityManager);
		BookEntity bookEntity = BookBuilder.createBook("9780135957059", "The Pragmatic Programmer", "David Thomas, Andrew Hunt", 
				320, new Date(), 2);
		bookDao.create(bookEntity);
		BookEntity bookEntityCreated = bookDao.findById(11L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(bookEntityCreated);
		assertTrue(bookEntityCreated.getId() == 11);
		assertEquals("9780135957059", bookEntityCreated.getIsbn());
		assertEquals("The Pragmatic Programmer", bookEntityCreated.getTitle());
		assertEquals("David Thomas, Andrew Hunt", bookEntityCreated.getAuthor());
		assertTrue(bookEntityCreated.getPageNumber() == 320);
		assertEquals(DateUtil.toFormatYearMonthDay(new Date()), bookEntityCreated.getRegisterDate());
		assertTrue(bookEntityCreated.getStock() == 2);
	}
	
	@Test
	public void find_should_find_book_by_id() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		BookDaoImpl bookDao = new BookDaoImpl(entityManager);
		BookEntity bookEntityFound = bookDao.findById(1L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(bookEntityFound);
		assertTrue(bookEntityFound.getId() == 1);
	}
	
	@Test
	public void update_should_update_book() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		BookDaoImpl bookDao = new BookDaoImpl(entityManager);
		BookEntity bookEntityToUpdate = bookDao.findById(1L);
		bookEntityToUpdate.setStock(5);
		bookDao.update(bookEntityToUpdate);
		BookEntity bookEntityUpdated = bookDao.findById(1L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(bookEntityUpdated);
		assertTrue(bookEntityUpdated.getId() == 1);
		assertTrue(bookEntityUpdated.getStock() == 5);
	}
	
	@Test
	public void delete_should_delete_book() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		BookDaoImpl bookDao = new BookDaoImpl(entityManager);
		BookEntity bookEntityToDelete = bookDao.findById(2L);
		bookDao.delete(bookEntityToDelete);
		BookEntity bookEntityDeleted = bookDao.findById(2L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNull(bookEntityDeleted);
	}
	
	@Test
	public void findByIsbn_should_find_book_by_isbn() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		BookDaoImpl bookDao = new BookDaoImpl(entityManager);
		BookEntity bookEntityFound = bookDao.findByIsbn("9780132350884");
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(bookEntityFound);
		assertEquals("9780132350884", bookEntityFound.getIsbn());
	}
	
	@Test
	public void countFoundBooksByTitle_should_count_searched_books_by_title() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		BookDaoImpl bookDao = new BookDaoImpl(entityManager);
		Long searchedBooks = bookDao.countSearchedBooksByTitle("Clean");
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(searchedBooks);
		assertEquals(3L, searchedBooks.longValue());
	}
	
	@Test
	public void findBooksByTitle_should_find_books_by_title() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		BookDaoImpl bookDao = new BookDaoImpl(entityManager);
		List<BookEntity> books = bookDao.searchBooksByTitle("Clean", 0, 1);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(books);
		assertEquals(3, books.size());
	}

}
