package com.jardvcode.web.mapper;

import com.jardvcode.model.entity.BookEntity;
import com.jardvcode.web.dto.BookDTO;

public class BookMapper extends Mapper<BookEntity, BookDTO> {

	@Override
	public BookDTO fromEntity(BookEntity entity) {
		BookDTO dto = new BookDTO();
		
		dto.setIsbn(entity.getIsbn());
		dto.setTitle(entity.getTitle());
		dto.setAuthor(entity.getAuthor());
		dto.setPages(entity.getPageNumber());
		dto.setStock(entity.getStock());
		
		return dto;
	}
	
	public BookEntity fromDTO(BookDTO dto) {
		return null;
	}

}
