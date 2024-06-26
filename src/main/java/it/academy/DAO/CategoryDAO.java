package it.academy.DAO;

import it.academy.models.Category;

import java.util.List;

public interface CategoryDAO extends DAO<Category, Long> {

    Category getCategoryByName(String categoryName);

    List<Category> getCategoriesPage();

    boolean existByCategoryName(String categoryName);

}
