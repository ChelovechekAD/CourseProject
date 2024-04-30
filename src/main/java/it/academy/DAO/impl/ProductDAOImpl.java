package it.academy.DAO.impl;

import it.academy.DAO.ProductDAO;
import it.academy.models.Category_;
import it.academy.models.Product;
import it.academy.models.Product_;
import it.academy.utilities.Constants;
import it.academy.utilities.TransactionHelper;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class ProductDAOImpl extends DAOImpl<Product, Long> implements ProductDAO {

    public ProductDAOImpl() {
        super(Product.class);
    }

    public ProductDAOImpl(TransactionHelper transactionHelper) {
        super(Product.class, transactionHelper);
    }

    @Override
    public boolean existProductsByCategory(Long categoryId) {
        CriteriaBuilder cb = transactionHelper.criteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Product> root = cq.from(Product.class);
        cq.select(cb.count(root))
                .where(cb.equal(root.get(Product_.CATEGORY_ID).get(Category_.ID), categoryId));
        return transactionHelper.entityManager().createQuery(cq).getSingleResult() != 0;
    }

    @Override
    public List<Product> getPageOfProduct(int page, int countPerPage) {
        TypedQuery<Product> typedQuery = transactionHelper.entityManager()
                .createQuery(Constants.SELECT_FROM_PRODUCT, Product.class);
        typedQuery.setMaxResults(countPerPage);
        typedQuery.setFirstResult(countPerPage * (page - 1));
        return typedQuery.getResultList();
    }

}
