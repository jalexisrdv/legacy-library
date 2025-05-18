package com.jardvcode.business.service;

import java.util.List;

import com.jardvcode.model.entity.BookEntity;

public interface BookService extends CrudService<BookEntity, Long> {
	
	public BookEntity findByIsbn(String isbn);
	
	public Long countSearchedBooksByTitle(String title);
	
	public List<BookEntity> searchBooksByTitle(String title, Integer startLimit, Integer endLimit);

}
