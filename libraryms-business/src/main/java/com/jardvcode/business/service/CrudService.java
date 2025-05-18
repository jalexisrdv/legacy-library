package com.jardvcode.business.service;

public interface CrudService<T, K> {
	
	public T create(T entity);

	public T findById(K id);
	
	public T updateById(T entity);
	
	public void deleteById(K id);

}
