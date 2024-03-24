package com.jardvcode.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jardvcode.configuration.DBUnitConfiguration;
import com.jardvcode.model.dao.impl.ExemplarDaoImpl;
import com.jardvcode.model.entity.BookEntity;
import com.jardvcode.model.entity.BookExemplaryEnum;
import com.jardvcode.model.entity.ExemplarEntity;
import com.jardvcode.model.entity.LocationEnum;
import com.jardvcode.model.entity.UserEntity;
import com.jardvcode.model.util.DateUtil;
import com.jardvcode.util.BookBuilder;
import com.jardvcode.util.ExemplarBuilder;
import com.jardvcode.util.UserBuilder;

public class BookExemplarDaoTest {
	
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
	public void create_should_create_exemplar_when_all_attributes_are_fine() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		ExemplarDaoImpl exemplarDao = new ExemplarDaoImpl(entityManager);
		BookEntity bookEntity = BookBuilder.createBook(1L);
		UserEntity userEntity = UserBuilder.createStudentUser(1L);
		ExemplarEntity exemplarEntity = ExemplarBuilder.createExemplar( "C", new Date(), "New Book",
				new Date(), new Date(),
				BookExemplaryEnum.AVAILABLE, LocationEnum.SITTING_ROOM, bookEntity, userEntity);
		exemplarDao.create(exemplarEntity);
		ExemplarEntity exemplarEntityCreated = exemplarDao.findById(12L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(exemplarEntityCreated);
		assertTrue(exemplarEntityCreated.getId() == 12);
		assertEquals("C", exemplarEntityCreated.getExemplaryId());
		assertEquals(DateUtil.toFormatYearMonthDay(new Date()), exemplarEntityCreated.getAcquisitionDate());
		assertEquals("New Book", exemplarEntityCreated.getObservation());
		assertEquals(DateUtil.toFormatYearMonthDay(new Date()), exemplarEntityCreated.getLendDate());
		assertEquals(DateUtil.toFormatYearMonthDay(new Date()), exemplarEntityCreated.getReturnDate());
		assertEquals(BookExemplaryEnum.AVAILABLE, exemplarEntityCreated.getState());
		assertEquals(LocationEnum.SITTING_ROOM, exemplarEntityCreated.getLocation());
		assertNotNull(exemplarEntityCreated.getBook());
		assertTrue(exemplarEntityCreated.getBook().getId() == 1);
		assertNotNull(exemplarEntityCreated.getUser());
		assertTrue(exemplarEntityCreated.getUser().getId() == 1);
	}
	
	@Test
	public void find_should_find_exemplar_by_id() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		ExemplarDaoImpl exemplarDao = new ExemplarDaoImpl(entityManager);
		ExemplarEntity exemplarEntityFound = exemplarDao.findById(1L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(exemplarEntityFound);
		assertTrue(exemplarEntityFound.getId() == 1);
	}
	
	@Test
	public void update_should_update_exemplar() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		ExemplarDaoImpl exemplarDao = new ExemplarDaoImpl(entityManager);
		ExemplarEntity exemplarEntityToUpdate = exemplarDao.findById(1L);
		exemplarEntityToUpdate.setObservation("DAMAGED BOOK");
		ExemplarEntity exemplarEntityUpdated = exemplarDao.update(exemplarEntityToUpdate);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(exemplarEntityUpdated);
		assertTrue(exemplarEntityUpdated.getId() == 1);
		assertEquals("DAMAGED BOOK", exemplarEntityUpdated.getObservation());
	}
	
	@Test
	public void delete_should_delete_exemplar() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		ExemplarDaoImpl exemplarDao = new ExemplarDaoImpl(entityManager);
		ExemplarEntity exemplarEntityToDelete = exemplarDao.findById(2L);
		exemplarDao.delete(exemplarEntityToDelete);
		ExemplarEntity exemplarEntityDeleted = exemplarDao.findById(2L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNull(exemplarEntityDeleted);
	}

}
