package it.academy.DAO.impl;

import it.academy.DAO.CategoryDAO;
import it.academy.models.Category;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CategoryDAOImpl extends DAOImpl<Category, Long> implements CategoryDAO {

    public CategoryDAOImpl(){
        super(Category.class);
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        TypedQuery<Category> query = transactionHelper.entityManager()
                .createQuery("select c from Category c where categoryName = :categoryName", Category.class);
        query.setParameter("categoryName", categoryName);
        return query.getSingleResult();
    }

    @Override
    public Long countOf() {
        return transactionHelper.entityManager()
                .createQuery("select count (c) from Category c", Long.class)
                .getSingleResult();
    }

    @Override
    public List<Category> getCategoriesPage(Integer countPerPage, Integer pageNum) {
         return transactionHelper.entityManager()
                .createQuery("select c from Category c", Category.class)
                .setFirstResult(countPerPage * (pageNum - 1))
                .setMaxResults(countPerPage)
                .getResultList();
    }
}
