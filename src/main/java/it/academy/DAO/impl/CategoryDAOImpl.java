package it.academy.DAO.impl;

import it.academy.DAO.CategoryDAO;
import it.academy.models.*;
import it.academy.models.embedded.CartItemPK;
import it.academy.models.embedded.CartItemPK_;
import it.academy.utilities.Constants;
import it.academy.utilities.TransactionHelper;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class CategoryDAOImpl extends DAOImpl<Category, Long> implements CategoryDAO {

    public CategoryDAOImpl(){
        super(Category.class);
    }
    public CategoryDAOImpl(TransactionHelper transactionHelper){
        super(Category.class, transactionHelper);
    }

    @Override
    public Category getCategoryByName(String categoryName) {

        CriteriaBuilder cb = transactionHelper.criteriaBuilder();
        CriteriaQuery<Category> cq = cb.createQuery(Category.class);
        Root<Category> root = cq.from(Category.class);

        cq.select(root)
                .where(cb.equal(root.get(Category_.CATEGORY_NAME), categoryName));

        return transactionHelper.entityManager().createQuery(cq).getSingleResult();
    }

    @Override
    public List<Category> getCategoriesPage() {
         return transactionHelper.entityManager()
                .createQuery(Constants.SELECT_FROM_CATEGORY, Category.class)
                .getResultList();
    }
}
