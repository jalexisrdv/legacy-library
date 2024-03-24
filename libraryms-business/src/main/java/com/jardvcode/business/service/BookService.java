package com.jardvcode.business.service;

import com.jardvcode.model.entity.BookEntity;

public interface BookService extends CrudService<BookEntity, Long> {
	
	public BookEntity findByIsbn(String isbn);

}
