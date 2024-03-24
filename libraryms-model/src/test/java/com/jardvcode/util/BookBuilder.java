package com.jardvcode.util;

import java.util.Date;

import com.jardvcode.model.entity.BookEntity;

public final class BookBuilder {

	private BookBuilder() {
		throw new RuntimeException("Can not be instantiated");
	}
	
	public static BookEntity createBook(Long id) {
		BookEntity book = new BookEntity();
		book.setId(id);
		return book;
	}
	
	public static BookEntity createBook(String isbn) {
		BookEntity book = new BookEntity();
		book.setIsbn(isbn);
		return book;
	}

	public static BookEntity createBook(Long id, String isbn) {
		BookEntity book = new BookEntity();
		book.setId(id);
		book.setIsbn(isbn);
		return book;
	}
	
	public static BookEntity createBook(Long id, String isbn, String title, String author, Integer pageNumber, Date registerDate, Integer stock) {
		BookEntity book = createBook(isbn, title, author, pageNumber, registerDate, stock);
		book.setId(id);
		return book;
	}
	
	public static BookEntity createBook(String isbn, String title, String author, Integer pageNumber, Date registerDate, Integer stock) {
		BookEntity book = new BookEntity();
		book.setIsbn(isbn);
		book.setTitles(title);
		book.setAuthor(author);
		book.setPageNumber(pageNumber);
		book.setRegisterDate(registerDate);
		book.setStock(stock);
		return book;
	}

}
