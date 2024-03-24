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
import com.jardvcode.model.dao.impl.UserDaoImpl;
import com.jardvcode.model.entity.AddressEntity;
import com.jardvcode.model.entity.UserEntity;
import com.jardvcode.model.entity.UserStateEnum;
import com.jardvcode.util.UserBuilder;

public class UserDaoTest {
	
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
	public void create_should_create_user_when_all_attributes_are_fine() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		AddressEntity address = new AddressEntity("20 DE NOVIEMBRE", "S/N", "S/N", "68410");
		UserEntity userEntity = UserBuilder.createStudentUser("elizabethmz", "12345678", "mail7@example.com", "elizabeth", "evangelista", "ramirez", UserStateEnum.ACTIVE, address);
		UserDaoImpl userDaoImpl = new UserDaoImpl(entityManager);
		userDaoImpl.create(userEntity);
		UserEntity userEntityCreated = userDaoImpl.findById(7L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(userEntityCreated);
		assertEquals(new Long(7), userEntityCreated.getId());
		assertEquals("elizabethmz", userEntityCreated.getNickname());
		assertEquals("12345678", userEntityCreated.getPassword());
		assertEquals("mail7@example.com", userEntityCreated.getMail());
		assertEquals("elizabeth", userEntityCreated.getName());
		assertEquals("evangelista", userEntityCreated.getFirstName());
		assertEquals("ramirez", userEntityCreated.getLastName());
	}
	
	@Test
	public void find_should_find_user_by_id() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		UserDaoImpl userDaoImpl = new UserDaoImpl(entityManager);
		UserEntity userEntityFound = userDaoImpl.findById(1L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(userEntityFound);
		assertTrue(userEntityFound.getId() == 1);
	}
	
	@Test
	public void update_should_update_user() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		UserDaoImpl userDaoImpl = new UserDaoImpl(entityManager);
		UserEntity userEntityToUpdate = userDaoImpl.findById(1L);
		userEntityToUpdate.setPassword("9876543210");
		userDaoImpl.update(userEntityToUpdate);
		UserEntity userEntityUpdated = userDaoImpl.findById(1L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNotNull(userEntityUpdated);
		assertTrue(userEntityUpdated.getId() == 1);
		assertEquals("9876543210", userEntityUpdated.getPassword());
	}
	
	@Test
	public void delete_should_delete_user() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		UserDaoImpl userDaoImpl = new UserDaoImpl(entityManager);
		UserEntity userEntityToDelete = UserBuilder.createStudentUser(2L);
		userDaoImpl.delete(userEntityToDelete);
		UserEntity userEntityDeleted = userDaoImpl.findById(2L);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		assertNull(userEntityDeleted);
	}
	
}
