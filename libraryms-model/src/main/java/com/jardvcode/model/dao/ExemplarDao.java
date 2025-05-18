package com.jardvcode.model.dao;

import java.util.List;

import com.jardvcode.model.entity.ExemplarEntity;

public interface ExemplarDao extends CrudDao<ExemplarEntity, Long> {
	
	public List<ExemplarEntity> findExemplaresByUserId(Long userId);

}
