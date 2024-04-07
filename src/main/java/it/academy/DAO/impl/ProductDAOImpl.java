package it.academy.DAO.impl;

import it.academy.DAO.ProductDAO;
import it.academy.models.Product;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ProductDAOImpl extends DAOImpl<Product, Long> implements ProductDAO {

    public ProductDAOImpl(){
        super(Product.class);
    }

    @Override
    public List<Product> getAllProduct() {
        TypedQuery<Product> typedQuery = transactionHelper.entityManager()
                .createQuery("select p from Product p", Product.class);
        return typedQuery.getResultList();
    }

    @Override
    public List<Product> getPageOfProduct(int page, int countPerPage) {
        TypedQuery<Product> typedQuery = transactionHelper.entityManager()
                .createQuery("select p from Product p", Product.class);
        typedQuery.setMaxResults(countPerPage);
        typedQuery.setFirstResult(countPerPage * (page - 1));
        return typedQuery.getResultList();
    }
}
