package it.academy.services.impl;

import it.academy.DAO.CategoryDAO;
import it.academy.DAO.OrderItemDAO;
import it.academy.DAO.ProductDAO;
import it.academy.DAO.impl.CategoryDAOImpl;
import it.academy.DAO.impl.OrderItemDAOImpl;
import it.academy.DAO.impl.ProductDAOImpl;
import it.academy.DTO.request.CategoryDTO;
import it.academy.DTO.response.CategoriesDTO;
import it.academy.exceptions.CategoryDeleteException;
import it.academy.exceptions.CategoryExistException;
import it.academy.exceptions.CategoryNotFoundException;
import it.academy.exceptions.ProductUsedInOrdersException;
import it.academy.models.*;
import it.academy.models.embedded.OrderItemPK_;
import it.academy.services.CategoryService;
import it.academy.utilities.Constants;
import it.academy.utilities.Converter;
import it.academy.utilities.TransactionHelper;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {

    private final TransactionHelper transactionHelper;
    private final CategoryDAO categoryDAO;
    private final ProductDAO productDAO;
    private final OrderItemDAO orderItemDAO;

    public CategoryServiceImpl() {
        transactionHelper = new TransactionHelper();
        categoryDAO = new CategoryDAOImpl(transactionHelper);
        productDAO = new ProductDAOImpl(transactionHelper);
        orderItemDAO = new OrderItemDAOImpl(transactionHelper);
    }

    public void addCategory(@NonNull String categoryName) {
        Optional.of(categoryDAO.existByCategoryName(categoryName)).filter(p -> p.equals(Boolean.FALSE))
                .orElseThrow(CategoryExistException::new);
        Category category = Category.builder()
                .categoryName(categoryName)
                .build();
        transactionHelper.transaction(() -> Converter.convertCategoryEntityToDTO(categoryDAO.create(category)));
    }

    public void deleteCategory(@NonNull Long categoryId, @NonNull Boolean root) {
        Runnable runnable = () -> {
            Optional.ofNullable(categoryDAO.get(categoryId)).orElseThrow(CategoryNotFoundException::new);
            boolean prodExist = productDAO.existProductsByCategory(categoryId);
            if (!root && prodExist) {
                throw new CategoryDeleteException(Constants.CATEGORY_PRODUCT_EXIST_EXCEPTION_MESSAGE);
            } else if (prodExist) {
                CriteriaBuilder cb = transactionHelper.criteriaBuilder();
                CriteriaQuery<Long> cq = cb.createQuery(Long.class);
                Root<OrderItem> rootCq = cq.from(OrderItem.class);
                cq.select(cb.count(rootCq))
                        .where(cb.equal(rootCq.get(OrderItem_.ORDER_ITEM_PK)
                                .get(OrderItemPK_.PRODUCT_ID)
                                .get(Product_.CATEGORY_ID)
                                .get(Category_.ID), categoryId));
                Optional.of(transactionHelper.entityManager().createQuery(cq).getResultList())
                        .filter(r -> r.size() == 0)
                        .orElseThrow(ProductUsedInOrdersException::new);
            }
            categoryDAO.delete(categoryId);
        };
        transactionHelper.transaction(runnable);
    }

    public CategoryDTO getCategoryById(@NonNull Long id) {
        return transactionHelper.transaction(() -> Converter.convertCategoryEntityToDTO(categoryDAO.get(id)));
    }

    public CategoryDTO getCategoryByName(@NonNull String name) {
        return transactionHelper.transaction(() -> Converter.convertCategoryEntityToDTO(categoryDAO.getCategoryByName(name)));
    }

    public CategoriesDTO getAllCategories() {

        List<Category> list = transactionHelper.transaction(categoryDAO::getCategoriesPage);
        return Converter.convertCategoriesListToDTO(list);
    }

}
