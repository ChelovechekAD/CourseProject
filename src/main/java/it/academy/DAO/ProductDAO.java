package it.academy.DAO;

import it.academy.models.Product;

import java.util.List;

public interface ProductDAO extends DAO<Product, Long> {

    List<Product> getPageOfProduct(int page, int countPerPage);

}
