package com.jardvcode.model.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jardvcode.model.dao.ExemplarDao;
import com.jardvcode.model.entity.ExemplarEntity;

public class ExemplarDaoImpl extends CrudDaoImpl<ExemplarEntity, Long> implements ExemplarDao {

	public ExemplarDaoImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public ExemplarEntity findById(Long id) {
		try {
			return entityManager.find(ExemplarEntity.class, id);
		} catch(Exception e) {
			return null;
		}
	}
	
	public ExemplarEntity findExemplaryByBookIdAndExemplaryId(Long bookId, String exemplaryId) {
		try {
			Query query = entityManager.createQuery("SELECT e FROM BookExemplaryEntity e WHERE e.book.id = :bookId AND e.exemplaryId = :exemplaryId");
			query.setParameter("bookId", bookId);
			query.setParameter("exemplaryId", exemplaryId);
			return (ExemplarEntity) query.getSingleResult();
		} catch(Exception e) {
			return null;
		}
	}

	public ExemplarEntity findBookBorrowedByBookIdAndUserId(long l, long m) {
		return null;
	}

}
