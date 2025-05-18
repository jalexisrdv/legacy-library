package com.jardvcode.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jardvcode.configuration.DBUnitConfiguration;
import com.jardvcode.model.dao.impl.LibrarianDaoImpl;
import com.jardvcode.model.entity.LibrarianEntity;
import com.jardvcode.util.LibrarianBuilder;

public class LibrarianDaoTest {
	
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
	public void create_should_create_librarian_when_all_attributes_are_fine() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		LibrarianEntity librarianEntity = LibrarianBuilder.createLibrarian("chosen", "12345678", "9876435986");
		LibrarianDaoImpl librarianDao = new LibrarianDaoImpl(entityManager);
		librarianDao.create(librarianEntity);
		LibrarianEntity librarianEntityCreated = librarianDao.findById(2L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(librarianEntityCreated);
		assertTrue(librarianEntityCreated.getId() == 2);
		assertEquals("chosen", librarianEntityCreated.getNickname());
		assertEquals("12345678", librarianEntityCreated.getPassword());
		assertEquals("9876435986", librarianEntityCreated.getNif());
	}
	
	@Test
	public void find_should_find_librarian_by_id() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		LibrarianDaoImpl librarianDao = new LibrarianDaoImpl(entityManager);
		LibrarianEntity librarianEntityFound = librarianDao.findById(1L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(librarianEntityFound);
		assertTrue(librarianEntityFound.getId() == 1);
	}
	
	@Test
	public void update_should_update_librarian() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		LibrarianDaoImpl librarianDao = new LibrarianDaoImpl(entityManager);
		LibrarianEntity librarianEntityToUpdate = librarianDao.findById(1L);
		librarianEntityToUpdate.setPassword("9876543210");
		librarianDao.update(librarianEntityToUpdate);
		LibrarianEntity librarianEntityUpdated = librarianDao.findById(1L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(librarianEntityUpdated);
		assertTrue(librarianEntityUpdated.getId() == 1);
		assertEquals("9876543210", librarianEntityUpdated.getPassword());
	}
	
	@Test
	public void delete_should_delete_librarian() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		LibrarianDaoImpl librarianDao = new LibrarianDaoImpl(entityManager);
		LibrarianEntity librarianEntityToDelete = LibrarianBuilder.createLibrarian(1L);
		librarianDao.delete(librarianEntityToDelete);
		LibrarianEntity librarianEntityDeleted = librarianDao.findById(1L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNull(librarianEntityDeleted);
	}

}
