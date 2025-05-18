package com.jardvcode.dao;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PersistenceUnitTest {
	
	private static EntityManagerFactory emf;

	@BeforeClass
	public static void initDatabaseTest() {
		try {
			emf = Persistence.createEntityManagerFactory("library");
		} catch (Exception e) {
			fail("Could not create EntityManagerFactory");
		}
	}

	@AfterClass
	public static void closeEntityManagerFactory() {
		if (emf != null) emf.close();
	}

	@Test
	public void persistence_should_create_persistence_unit() {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.getTransaction().commit();
			em.close();
		}catch(Exception e) {
			fail("Could not open connection");
		}
	}

}
