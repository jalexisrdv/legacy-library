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
import com.jardvcode.model.dao.impl.PenaltyDaoImpl;
import com.jardvcode.model.entity.HistoricalStateEnum;
import com.jardvcode.model.entity.PenaltyEntity;
import com.jardvcode.model.entity.UserEntity;
import com.jardvcode.model.util.DateUtil;
import com.jardvcode.util.PenaltyBuilder;
import com.jardvcode.util.UserBuilder;

public class PenaltyDaoTest {
	
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
	public void create_should_create_penalty_when_all_attributes_are_fine() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		PenaltyDaoImpl penaltyDao = new PenaltyDaoImpl(entityManager);
		UserEntity userEntity = UserBuilder.createStudentUser(1L);
		PenaltyEntity penaltyEntity = PenaltyBuilder.createPenalty(new Date(), new Date(), HistoricalStateEnum.ACTIVE, userEntity);
		penaltyDao.create(penaltyEntity);
		PenaltyEntity penaltyEntityCreated = penaltyDao.findById(2L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(penaltyEntityCreated);
		assertTrue(penaltyEntityCreated.getId() == 2);
		assertEquals(DateUtil.toFormatYearMonthDay(new Date()), penaltyEntityCreated.getStartDate());
		assertEquals(DateUtil.toFormatYearMonthDay(new Date()), penaltyEntityCreated.getEndDate());
		assertEquals(HistoricalStateEnum.ACTIVE, penaltyEntityCreated.getState());
		assertNotNull(penaltyEntityCreated);
		assertTrue(penaltyEntityCreated.getUser().getId() == 1);
	}
	
	@Test
	public void find_should_find_penalty_by_id() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		PenaltyDaoImpl penaltyDao = new PenaltyDaoImpl(entityManager);
		PenaltyEntity penaltyEntityFound = penaltyDao.findById(1L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(penaltyEntityFound);
		assertTrue(penaltyEntityFound.getId() == 1);
	}
	
	@Test
	public void update_should_update_penalty() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		PenaltyDaoImpl penaltyDao = new PenaltyDaoImpl(entityManager);
		PenaltyEntity penaltyEntityToUpdate = penaltyDao.findById(1L);
		penaltyEntityToUpdate.setState(HistoricalStateEnum.HISTORICAL);
		penaltyDao.update(penaltyEntityToUpdate);
		PenaltyEntity penaltyEntityUpdated = penaltyDao.findById(1L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(penaltyEntityUpdated);
		assertTrue(penaltyEntityUpdated.getId() == 1);
		assertEquals(HistoricalStateEnum.HISTORICAL, penaltyEntityUpdated.getState());
	}
	
	@Test
	public void delete_should_delete_penalty() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		PenaltyDaoImpl penaltyDao = new PenaltyDaoImpl(entityManager);
		PenaltyEntity penaltyEntityToDelete = PenaltyBuilder.createPenalty(1L);
		penaltyDao.delete(penaltyEntityToDelete);
		PenaltyEntity penaltyEntityFound = penaltyDao.findById(1L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNull(penaltyEntityFound);
	}

}
