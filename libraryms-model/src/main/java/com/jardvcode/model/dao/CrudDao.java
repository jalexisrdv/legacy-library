package com.jardvcode.model.dao;

import javax.persistence.EntityManager;

public interface CrudDao<T, K> {
	
	public EntityManager getEntityManager();
	
	public void setEntityManager(EntityManager entityManager);
	
	public abstract T findById(K id);
	
	public T create(T entity);
	
	public T update(T entity);
	
	public void delete(T entity);

}
