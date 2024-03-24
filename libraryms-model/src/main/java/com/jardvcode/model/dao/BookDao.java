package com.jardvcode.model.dao;

import com.jardvcode.model.entity.BookEntity;

public interface BookDao extends CrudDao<BookEntity, Long> {
	
	public BookEntity findByIsbn(String isbn);

}
