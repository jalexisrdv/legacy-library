package com.jardvcode.web.mapper;

import java.util.ArrayList;
import java.util.List;

import com.jardvcode.model.entity.ExemplarEntity;
import com.jardvcode.web.dto.ExemplarDTO;

public class ExemplarMapper {
	
	public ExemplarDTO fromEntity(ExemplarEntity entity) {
		ExemplarDTO dto = new ExemplarDTO();
		
		dto.setIsbn(entity.getBook().getIsbn());
		dto.setExemplarId(String.valueOf(entity.getId()));
		dto.setTitle(entity.getBook().getTitle());
		dto.setState(entity.getState().toString());
		
		return dto;
	}
	
	public ExemplarEntity fromDTO(ExemplarDTO dto) {
		return null;
	}
	
	public List<ExemplarEntity> fromDTO(List<ExemplarDTO> dtos) {
		List<ExemplarEntity> entities = new ArrayList<>();
		for(ExemplarDTO dto: dtos) {
			entities.add(fromDTO(dto));
		}
		return entities;
	}
	
	public List<ExemplarDTO> fromEntity(List<ExemplarEntity> entities) {
		List<ExemplarDTO> dtos = new ArrayList<>();
		for(ExemplarEntity entity: entities) {
			dtos.add(fromEntity(entity));
		}
		return dtos;
	}

}
