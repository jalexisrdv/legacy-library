package com.jardvcode.business.uitl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.mockito.InOrder;
import org.mockito.Mockito;

import com.jardvcode.model.dao.CrudDao;

public class OrderChecker<T, K> {
	
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;
	private CrudDao<T, K> dao;
	private InOrder order;
	
	public OrderChecker(CrudDao<T, K> dao) {
		this.entityManagerFactory = Mockito.mock(EntityManagerFactory.class);
		this.entityManager = Mockito.mock(EntityManager.class);
		this.entityTransaction = Mockito.mock(EntityTransaction.class);
		this.dao = dao;
		this.order = inOrder(entityManagerFactory, entityManager, entityTransaction, dao);
		mockPersistenceObjects();
	}
	
	private void mockPersistenceObjects() {
		when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
		when(entityManager.getTransaction()).thenReturn(entityTransaction);
		when(entityTransaction.isActive()).thenReturn(true);
	}

	public void verifyOrderWhenTransactionIsBegun() {
		verify(entityManagerFactory, times(1)).createEntityManager();
		verify(entityTransaction, times(1)).begin();
		verify(dao, times(1)).setEntityManager(entityManager);
		
		order.verify(entityManagerFactory).createEntityManager();
		order.verify(entityTransaction).begin();
		order.verify(dao).setEntityManager(any(EntityManager.class));
	}
	
	public void verifyOrderWhenTransactionIsCommit() {
		verify(entityTransaction, times(1)).commit();
		verify(entityManager, times(1)).close();
		
		order.verify(entityTransaction).commit();
		order.verify(entityManager).close();
	}
	
	public void verifyOrderWhenTransactionIsRollback() {
		verify(entityTransaction, times(1)).rollback();
		verify(entityManager, times(1)).close();
		
		order.verify(entityTransaction).rollback();
		order.verify(entityManager).close();
	}
	
	public InOrder getInOrder() {
		return order;
	}
	
	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

}
