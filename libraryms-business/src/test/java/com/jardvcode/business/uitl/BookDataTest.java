package com.jardvcode.business.uitl;

import java.util.Date;

import com.jardvcode.model.entity.BookEntity;
import com.jardvcode.util.BookBuilder;

public class BookDataTest {
	
	public static final BookEntity BOOK_SAVED = BookBuilder.createBook(
			1L, "9780135957059", "The Pragmatic Programmer", 
			"David Thomas, Andrew Hunt", 
			320, new Date(), 2
	);

}
