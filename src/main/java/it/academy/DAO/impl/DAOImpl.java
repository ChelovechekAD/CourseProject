package it.academy.DAO.impl;

import it.academy.DAO.DAO;
import it.academy.exceptions.NotFoundException;
import it.academy.utilities.TransactionHelper;

import java.io.Serializable;
import java.util.List;

public class DAOImpl<T extends Serializable, R> implements DAO<T, R> {

    protected TransactionHelper transactionHelper = TransactionHelper.getTransactionHelper();
    private final Class<T> tClass;

    public DAOImpl(Class<T> tClass){
        this.tClass = tClass;
    }

    @Override
    public T create(T obj) {
        return transactionHelper.persist(obj);
    }

    @Override
    public T get(R id) {
        return transactionHelper.find(tClass, id);
    }

    @Override
    public T update(T obj) {
        return transactionHelper.merge(obj);
    }

    @Override
    public boolean delete(R id) {
        T obj = transactionHelper.find(tClass, id);
        if (obj == null) {
            throw new NotFoundException();
        }
        transactionHelper.remove(obj);
        return transactionHelper.find(tClass, id) == null;
    }

    @Override
    public List<T> getPage(Integer countPerPage, Integer pageNum) {
        return transactionHelper.entityManager()
                .createQuery("select o from Order o", tClass)
                .setFirstResult(countPerPage * (pageNum - 1))
                .setMaxResults(countPerPage)
                .getResultList();
    }

    @Override
    public Long getCountOf() {
        return transactionHelper.entityManager()
                .createQuery(String.format("select count(i) from %s i", tClass.getName()), Long.class)
                .getSingleResult();
    }
}
