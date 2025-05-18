package com.jardvcode.web.mapper;

import java.util.ArrayList;
import java.util.List;

public abstract class Mapper<E, D> {
	
	public abstract D fromEntity(E entity);
	
	public abstract E fromDTO(D dto);
	
	public List<E> fromDTO(List<D> dtos) {
		List<E> entities = new ArrayList<>();
		for(D dto: dtos) {
			entities.add(fromDTO(dto));
		}
		return entities;
	}
	
	public List<D> fromEntity(List<E> entities) {
		List<D> dtos = new ArrayList<>();
		for(E entity: entities) {
			dtos.add(fromEntity(entity));
		}
		return dtos;
	}

}
