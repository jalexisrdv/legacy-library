package com.jardvcode.model.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jardvcode.model.dao.ExemplarDao;
import com.jardvcode.model.entity.ExemplarEntity;

public class ExemplarDaoImpl extends CrudDaoImpl<ExemplarEntity, Long> implements ExemplarDao {
	
	public ExemplarDaoImpl() {
		
	}

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

	@Override
	public List<ExemplarEntity> findExemplaresByUserId(Long userId) {
		try {
			Query query = entityManager.createQuery("SELECT e FROM ExemplarEntity e WHERE e.user.id = :userId");
			query.setParameter("userId", userId);
			return (List<ExemplarEntity>) query.getResultList();
		} catch(Exception e) {
			return null;
		}
	}

}
