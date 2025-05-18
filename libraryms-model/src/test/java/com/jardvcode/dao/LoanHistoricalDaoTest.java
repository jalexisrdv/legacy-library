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
import com.jardvcode.model.dao.impl.LoanHistoricalDaoImpl;
import com.jardvcode.model.entity.ExemplarEntity;
import com.jardvcode.model.entity.LoanHistoricalEntity;
import com.jardvcode.model.entity.UserEntity;
import com.jardvcode.model.util.DateUtil;
import com.jardvcode.util.ExemplarBuilder;
import com.jardvcode.util.LoanHistoricalBuilder;
import com.jardvcode.util.UserBuilder;

public class LoanHistoricalDaoTest {
	
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
	public void create_should_create_loan_when_all_attributes_are_fine() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		LoanHistoricalDaoImpl loanDao = new LoanHistoricalDaoImpl(entityManager);
		UserEntity userEntity = UserBuilder.createStudentUser(1L);
		ExemplarEntity exemplarEntity = ExemplarBuilder.createExemplar(1L);
		LoanHistoricalEntity loanEntity = LoanHistoricalBuilder.createLoanHistorical( new Date(), new Date(), new Date(), userEntity, exemplarEntity);
		loanDao.create(loanEntity);
		LoanHistoricalEntity loanEntityCreated = loanDao.findById(2L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(loanEntityCreated);
		assertTrue(loanEntityCreated.getId() == 2);
		assertEquals(DateUtil.toFormatYearMonthDay(new Date()), loanEntityCreated.getLendDate());
		assertEquals(DateUtil.toFormatYearMonthDay(new Date()), loanEntityCreated.getReturnDate());
		assertEquals(DateUtil.toFormatYearMonthDay(new Date()), loanEntityCreated.getRealReturnDate());
		assertNotNull(loanEntityCreated.getUser());
		assertTrue(loanEntityCreated.getUser().getId() == 1);
		assertNotNull(loanEntityCreated.getExemplaryBook());
		assertTrue(loanEntityCreated.getExemplaryBook().getId() == 1);
	}
	
	@Test
	public void find_should_find_loan_by_id() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		LoanHistoricalDaoImpl loanDao = new LoanHistoricalDaoImpl(entityManager);
		LoanHistoricalEntity loanEntityFound = loanDao.findById(1L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(loanEntityFound);
		assertTrue(loanEntityFound.getId() == 1);
	}
	
	@Test
	public void update_should_update_loan() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		LoanHistoricalDaoImpl loanDao = new LoanHistoricalDaoImpl(entityManager);
		LoanHistoricalEntity loanEntityToUpdate = loanDao.findById(1L);
		UserEntity userEntity = UserBuilder.createStudentUser(2L);
		loanEntityToUpdate.setUser(userEntity);
		LoanHistoricalEntity loanEntityUpdated = loanDao.update(loanEntityToUpdate);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(loanEntityUpdated);
		assertTrue(loanEntityUpdated.getId() == 1);
		assertNotNull(loanEntityUpdated.getUser());
		assertTrue(loanEntityUpdated.getUser().getId() == 2L);
	}
	
	@Test
	public void delete_should_delete_loan() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		LoanHistoricalDaoImpl loanDao = new LoanHistoricalDaoImpl(entityManager);
		LoanHistoricalEntity loanEntityToDelete = LoanHistoricalBuilder.createLoanHistorical(1L);
		loanDao.delete(loanEntityToDelete);
		LoanHistoricalEntity loanEntityDeleted = loanDao.findById(1L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNull(loanEntityDeleted);
	}

}
