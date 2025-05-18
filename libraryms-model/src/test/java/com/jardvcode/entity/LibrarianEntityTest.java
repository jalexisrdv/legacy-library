package com.jardvcode.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jardvcode.model.entity.LibrarianEntity;
import com.jardvcode.util.LibrarianBuilder;

public class LibrarianEntityTest {

	@Test
	public void librarian_should_be_equal_when_nif_is_the_same() {
		LibrarianEntity librarianEntity1 = LibrarianBuilder.createLibrarian(1L, "123456789");
		LibrarianEntity librarianEntity2 = LibrarianBuilder.createLibrarian(2L, "123456789");
		LibrarianEntity librarianEntity3 = LibrarianBuilder.createLibrarian(3L, "987654321");
		
		assertTrue(librarianEntity1.equals(librarianEntity2));
		assertFalse(librarianEntity1.equals(librarianEntity3));
	}

}
