package com.jardvcode.business.service.impl;

import javax.persistence.EntityManagerFactory;

import com.jardvcode.business.service.LibrarianService;
import com.jardvcode.model.dao.LibrarianDao;
import com.jardvcode.model.entity.LibrarianEntity;

public class LibrarianServiceImpl implements LibrarianService {
	
	private EntityManagerFactory entityManagerFactory;
	private LibrarianDao librarianDao;

	public LibrarianServiceImpl(EntityManagerFactory entityManagerFactory, LibrarianDao librarianDao) {
		this.entityManagerFactory = entityManagerFactory;
		this.librarianDao = librarianDao;
	}

	@Override
	public LibrarianEntity create(LibrarianEntity entity) {
		return null;
	}

	@Override
	public LibrarianEntity findById(Long id) {
		return null;
	}

	@Override
	public LibrarianEntity updateById(LibrarianEntity entity) {
		return null;
	}

	@Override
	public void deleteById(Long id) {
		
	}

}
