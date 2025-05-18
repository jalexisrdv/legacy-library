package com.jardvcode.model.dao;

import java.util.List;

import com.jardvcode.model.entity.BookEntity;

public interface BookDao extends CrudDao<BookEntity, Long> {
	
	public BookEntity findByIsbn(String isbn);
	
	public Long countSearchedBooksByTitle(String title);
	
	public List<BookEntity> searchBooksByTitle(String title, Integer startLimit, Integer endLimit);

}
