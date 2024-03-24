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
import com.jardvcode.model.dao.impl.ReservationDaoImpl;
import com.jardvcode.model.entity.BookEntity;
import com.jardvcode.model.entity.FinalizationEnum;
import com.jardvcode.model.entity.HistoricalStateEnum;
import com.jardvcode.model.entity.ReservationEntity;
import com.jardvcode.model.entity.UserEntity;
import com.jardvcode.model.util.DateUtil;
import com.jardvcode.util.BookBuilder;
import com.jardvcode.util.ReservationBuilder;
import com.jardvcode.util.UserBuilder;

public class ReservationDaoTest {
	
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
	public void create_should_create_reservation_when_all_attributes_are_fine() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		ReservationDaoImpl reservationDao = new ReservationDaoImpl(entityManager);
		BookEntity bookEntity = BookBuilder.createBook(1L);
		UserEntity userEntity = UserBuilder.createStudentUser(1L);
		ReservationEntity reservationEntity = ReservationBuilder.createReservation( new Date(), new Date(),
				FinalizationEnum.DONE, HistoricalStateEnum.HISTORICAL, bookEntity, userEntity);
		reservationDao.create(reservationEntity);
		ReservationEntity reservationEntityCreated = reservationDao.findById(2L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(reservationEntityCreated);
		assertTrue(reservationEntityCreated.getId() == 2);
		assertEquals(DateUtil.toFormatYearMonthDay(new Date()), reservationEntityCreated.getStartDate());
		assertEquals(DateUtil.toFormatYearMonthDay(new Date()), reservationEntityCreated.getEndDate());
		assertEquals(FinalizationEnum.DONE, reservationEntityCreated.getFinalizationState());
		assertEquals(HistoricalStateEnum.HISTORICAL, reservationEntityCreated.getHistoricalState());
		assertNotNull(reservationEntityCreated.getBook());
		assertTrue(reservationEntityCreated.getBook().getId() == 1);
		assertNotNull(reservationEntityCreated.getUser());
		assertTrue(reservationEntityCreated.getUser().getId() == 1);
	}
	
	@Test
	public void find_should_find_reservation_by_id() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		ReservationDaoImpl reservationDao = new ReservationDaoImpl(entityManager);
		ReservationEntity reservationEntityFound = reservationDao.findById(1L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(reservationEntityFound);
		assertTrue(reservationEntityFound.getId() == 1);
	}
	
	@Test
	public void update_should_update_reservation() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		ReservationDaoImpl reservationDao = new ReservationDaoImpl(entityManager);
		ReservationEntity reservationEntityToUpdate = reservationDao.findById(1L);
		reservationEntityToUpdate.setHistoricalState(HistoricalStateEnum.HISTORICAL);
		reservationDao.update(reservationEntityToUpdate);
		ReservationEntity reservationEntityUpdate = reservationDao.findById(1L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(reservationEntityUpdate);
		assertTrue(reservationEntityUpdate.getId() == 1);
		assertEquals(HistoricalStateEnum.HISTORICAL, reservationEntityUpdate.getHistoricalState());
	}
	
	@Test
	public void delete_should_delete_reservation() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		ReservationDaoImpl reservationDao = new ReservationDaoImpl(entityManager);
		ReservationEntity reservationEntityToDelete = ReservationBuilder.createReservation(1L);
		reservationDao.delete(reservationEntityToDelete);
		ReservationEntity reservationEntityDeleted = reservationDao.findById(1L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNull(reservationEntityDeleted);
	}

}
