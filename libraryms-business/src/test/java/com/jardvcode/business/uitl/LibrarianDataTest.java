package com.jardvcode.business.uitl;

import com.jardvcode.model.entity.LibrarianEntity;
import com.jardvcode.util.LibrarianBuilder;

public class LibrarianDataTest {
	
	public static final LibrarianEntity LIBRARIAN_SAVED = LibrarianBuilder.createLibrarian(
			1L,
			"chosen", "12345678", "9876435986"
	);

}
