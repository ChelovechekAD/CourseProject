package it.academy.services.impl;

import it.academy.DAO.CategoryDAO;
import it.academy.DAO.ProductDAO;
import it.academy.DAO.impl.CategoryDAOImpl;
import it.academy.DAO.impl.ProductDAOImpl;
import it.academy.DTO.request.CreateProductDTO;
import it.academy.DTO.response.ProductDTO;
import it.academy.DTO.response.ProductsDTO;
import it.academy.exceptions.CatalogNotFoundException;
import it.academy.exceptions.NotFoundException;
import it.academy.exceptions.ProductNotFoundException;
import it.academy.models.Category;
import it.academy.models.Category_;
import it.academy.models.Product;
import it.academy.models.Product_;
import it.academy.services.ProductService;
import it.academy.utilities.Converter;
import it.academy.utilities.TransactionHelper;
import jakarta.persistence.criteria.*;
import lombok.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;
    private final TransactionHelper transactionHelper;

    public ProductServiceImpl(){
        productDAO = new ProductDAOImpl();
        categoryDAO = new CategoryDAOImpl();
        transactionHelper = TransactionHelper.getTransactionHelper();
    }

    public void addProduct(@NonNull CreateProductDTO createProductDTO) {
        Runnable method = () -> {
            Category category = categoryDAO.get(createProductDTO.getCategoryId());
            if (category == null) {
                throw new CatalogNotFoundException();
            }
            Product product = Converter.convertCreateProdDTOToEntity(createProductDTO);
            product.setCategoryId(category);
            productDAO.create(product);
        };
        transactionHelper.transaction(method);
    }

    public void updateProduct(@NonNull CreateProductDTO createProductDTO) {
        Runnable method = () -> {
            if (categoryDAO.get(createProductDTO.getCategoryId()) == null) {
                throw new CatalogNotFoundException();
            }
            if (createProductDTO.getId() == 0 || productDAO.get(createProductDTO.getId()) == null){
                throw new ProductNotFoundException();
            }
            Product product = Converter.convertCreateProdDTOToEntity(createProductDTO);
            productDAO.update(product);
        };
        transactionHelper.transaction(method);
    }

    public void deleteProduct(@NonNull Long id) {
        try {
            transactionHelper.transaction(()-> productDAO.delete(id));
        } catch (NotFoundException e){
            ProductNotFoundException productNotFoundException = new ProductNotFoundException();
            productNotFoundException.setStackTrace(e.getStackTrace());
            throw productNotFoundException;
        }
    }

    public ProductDTO getProductById(@NonNull Long id){
        Product product = productDAO.get(id);
        if (product == null){
            throw new ProductNotFoundException();
        }
        return Converter.convertProdEntityToDTO(product);
    }

    public ProductsDTO getAllExistProducts(@NonNull Integer pageNum, @NonNull Integer countPerPage){
        Supplier<ProductsDTO> supplier = () -> {
            Long count = productDAO.getCountOf();
            List<Product> products = productDAO.getPage(countPerPage, pageNum);
            return Converter.convertProdListToDTO(products, count);
        };
        return transactionHelper.transaction(supplier);
    }

    public ProductsDTO getAllExistProductByCategoryName(@NonNull Long categoryId,
                                                        @NonNull Integer pageNum, @NonNull Integer countPerPage){
        Supplier<ProductsDTO> supplier = () -> {
            List<Product> products = transactionHelper.entityManager()
                    .createQuery("select p from Product p where categoryId.id=:category", Product.class)
                    .setParameter("category", categoryId)
                    .setFirstResult(countPerPage * (pageNum - 1))
                    .setMaxResults(countPerPage)
                    .getResultList();
            Long count = transactionHelper.entityManager()
                    .createQuery("select count(p) from Product p where categoryId.id=:category", Long.class)
                    .setParameter("category", categoryId)
                    .getSingleResult();
            return Converter.convertProdListToDTO(products, count);
        };
        return transactionHelper.transaction(supplier);
    }

    public ProductsDTO getAllExistProductByFilterParam(@NonNull Integer pageNum,
                                                       @NonNull Integer countPerPage, @NonNull Map<String, Object> paramMap){
        Supplier<ProductsDTO> supplier = () -> {
            CriteriaBuilder builder = transactionHelper.criteriaBuilder();
            CriteriaQuery<Product> productQuery = builder.createQuery(Product.class);
            Root<Product> root = productQuery.from(Product.class);
            Map<String, Object> catParams = new HashMap<>();
            Predicate predicate = builder.conjunction();
            if (paramMap.get(Category_.CATEGORY_NAME) != null) {
                catParams.put(Category_.CATEGORY_NAME, paramMap.get(Category_.CATEGORY_NAME));
                paramMap.remove(Category_.CATEGORY_NAME);
                Join<Product, Category> categoryJoin = root.join(Product_.CATEGORY_ID);
                transactionHelper.collectParamsToPredicate(catParams, categoryJoin, predicate);
            }
            transactionHelper.collectParamsToPredicate(paramMap, root, builder.conjunction());
            productQuery.select(root)
                    .where(predicate);
            List<Product> products = transactionHelper.entityManager().createQuery(productQuery)
                    .setMaxResults(countPerPage)
                    .setFirstResult(countPerPage * (pageNum - 1))
                    .getResultList();
            Long count = productDAO.getCountOf();
            return Converter.convertProdListToDTO(products, count);
        };
        return transactionHelper.transaction(supplier);
    }
}
