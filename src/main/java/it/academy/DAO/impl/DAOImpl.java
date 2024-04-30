package it.academy.DAO.impl;

import it.academy.DAO.DAO;
import it.academy.exceptions.NotFoundException;
import it.academy.exceptions.ProductNotFoundException;
import it.academy.models.Product;
import it.academy.utilities.Constants;
import it.academy.utilities.TransactionHelper;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class DAOImpl<T extends Serializable, R> implements DAO<T, R> {

    protected TransactionHelper transactionHelper;
    private final Class<T> tClass;

    public DAOImpl(Class<T> tClass) {
        transactionHelper = new TransactionHelper();
        this.tClass = tClass;
    }

    public DAOImpl(Class<T> tClass, TransactionHelper transactionHelper) {
        this.tClass = tClass;
        this.transactionHelper = transactionHelper;
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
                .createQuery(String.format(Constants.SELECT_FROM_GENERIC, tClass.getName()), tClass)
                .setFirstResult(countPerPage * (pageNum - 1))
                .setMaxResults(countPerPage)
                .getResultList();
    }

    @Override
    public Long getCountOf() {
        return transactionHelper.entityManager()
                .createQuery(String.format(Constants.SELECT_COUNT_FROM_GENERIC, tClass.getName()), Long.class)
                .getSingleResult();
    }
}
