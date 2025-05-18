package com.jardvcode.util;

import com.jardvcode.model.entity.LibrarianEntity;

public final class LibrarianBuilder {
	
	private LibrarianBuilder() {
		throw new RuntimeException("Can not be instantiated");
	}
	
	public static LibrarianEntity createLibrarian(Long id) {
		LibrarianEntity librarian = new LibrarianEntity();
		librarian.setId(id);
		return librarian;
	}
	
	public static LibrarianEntity createLibrarian(Long id, String nif) {
		LibrarianEntity librarian = new LibrarianEntity();
		librarian.setId(id);
		librarian.setNif(nif);
		return librarian;
	}
	
	public static LibrarianEntity createLibrarian(Long id, String nickname, String password, String nif) {
		LibrarianEntity librarian = createLibrarian(nickname, password, nif);
		librarian.setId(id);
		return librarian;
	}
	
	public static LibrarianEntity createLibrarian(String nickname, String password, String nif) {
		LibrarianEntity librarian = new LibrarianEntity();
		librarian.setNickname(nickname);
		librarian.setPassword(password);
		librarian.setNif(nif);
		return librarian;
	}

}
