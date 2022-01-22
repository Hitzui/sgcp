package com.dysconcsa.pancasan.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public abstract class DataEntity<T> {

    private final EntityManager entityManager;
    private final EntityTransaction transaction;

    public DataEntity() {
        var dataAccess = DataAccess.getInstance();
        entityManager = dataAccess.entityManager();
        transaction = entityManager.getTransaction();
    }

    public void save(T entity) {
        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
            entityManager.persist(entity);
            transaction.commit();
        } catch (Exception exception) {
            transaction.rollback();
        }
    }

    @SuppressWarnings("unchecked")
    protected List<T> findAll(String query) {
        return entityManager.createQuery(query).getResultList();
    }

    public void delete(T entity) {
        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
            entityManager.remove(entity);
            transaction.commit();
        } catch (Exception exception) {
            transaction.rollback();
        }
    }

    public void update(T entity) {
        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
            entityManager.merge(entity);
            entityManager.flush();
            transaction.commit();
        } catch (Exception exception) {
            transaction.rollback();
        }
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public EntityTransaction getTransaction() {
        return transaction;
    }

}
