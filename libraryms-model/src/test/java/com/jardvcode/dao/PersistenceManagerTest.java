package com.jardvcode.dao;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.jardvcode.model.util.PersistenceManager;

public class PersistenceManagerTest {

	@Test
	public void persistence_manager_should_create_entity_manager_factory() {
		try {
			EntityManager em = PersistenceManager.getInstance().createEntityManager();
			em.getTransaction().begin();
			em.getTransaction().commit();
			em.close();
		}catch(Exception e) {
			fail("Could not open connection");
		}
	}

}
