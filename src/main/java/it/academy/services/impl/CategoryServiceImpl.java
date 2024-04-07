package it.academy.services.impl;

import it.academy.DAO.CategoryDAO;
import it.academy.DAO.impl.CategoryDAOImpl;
import it.academy.DTO.request.CategoryDTO;
import it.academy.DTO.response.CategoriesDTO;
import it.academy.models.Category;
import it.academy.services.CategoryService;
import it.academy.utilities.Converter;
import it.academy.utilities.TransactionHelper;
import lombok.NonNull;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private final TransactionHelper transactionHelper = TransactionHelper.getTransactionHelper();
    private final CategoryDAO categoryDAO = new CategoryDAOImpl();
    public void addCategory(@NonNull CategoryDTO categoryDTO){
        transactionHelper.transaction(()-> Converter.convertCategoryDTOToEntity(categoryDTO));
    }
    public CategoryDTO getCategoryById(@NonNull Long id){
        return transactionHelper.transaction(()->Converter.convertCategoryEntityToDTO(categoryDAO.get(id)));
    }
    public CategoryDTO getCategoryByName(@NonNull String name){
        return transactionHelper.transaction(()->Converter.convertCategoryEntityToDTO(categoryDAO.getCategoryByName(name)));
    }
    public CategoriesDTO getAllCategoriesPage(@NonNull Integer countPerPage, @NonNull Integer pageNum){
        List<Category> list = categoryDAO.getCategoriesPage(countPerPage, pageNum);
        Long count = categoryDAO.getCountOf();
        return Converter.convertCategoriesListToDTO(list, count);
    }

}