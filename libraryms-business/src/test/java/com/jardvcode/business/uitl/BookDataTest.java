package com.jardvcode.business.uitl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.jardvcode.model.entity.BookEntity;
import com.jardvcode.util.BookBuilder;

public class BookDataTest {
	
	public static final BookEntity BOOK_SAVED = BookBuilder.createBook(
			1L, "9780135957059", "The Pragmatic Programmer", 
			"David Thomas, Andrew Hunt", 
			320, new Date(), 2
	);
	
	public static final List<BookEntity> BOOKS = Arrays.asList(BOOK_SAVED);

}
