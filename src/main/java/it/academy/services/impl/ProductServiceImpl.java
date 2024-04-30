package it.academy.services.impl;

import it.academy.DAO.CategoryDAO;
import it.academy.DAO.OrderItemDAO;
import it.academy.DAO.ProductDAO;
import it.academy.DAO.impl.CategoryDAOImpl;
import it.academy.DAO.impl.OrderItemDAOImpl;
import it.academy.DAO.impl.ProductDAOImpl;
import it.academy.DTO.request.CreateProductDTO;
import it.academy.DTO.request.GetProductPageByCategoryDTO;
import it.academy.DTO.request.RequestDataDetailsDTO;
import it.academy.DTO.request.UpdateProductDTO;
import it.academy.DTO.response.ProductDTO;
import it.academy.DTO.response.ProductsDTO;
import it.academy.exceptions.CategoryNotFoundException;
import it.academy.exceptions.NotFoundException;
import it.academy.exceptions.ProductNotFoundException;
import it.academy.exceptions.ProductUsedInOrdersException;
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
import java.util.Optional;
import java.util.function.Supplier;

public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;
    private final OrderItemDAO orderItemDAO;
    private final TransactionHelper transactionHelper;

    public ProductServiceImpl() {
        transactionHelper = new TransactionHelper();
        productDAO = new ProductDAOImpl(transactionHelper);
        categoryDAO = new CategoryDAOImpl(transactionHelper);
        orderItemDAO = new OrderItemDAOImpl(transactionHelper);

    }

    public void addProduct(@NonNull CreateProductDTO createProductDTO) {
        Runnable method = () -> {
            Category category = categoryDAO.get(createProductDTO.getCategoryId());
            Optional.ofNullable(category).orElseThrow(CategoryNotFoundException::new);
            Product product = Converter.convertCreateProdDTOToEntity(createProductDTO);
            product.setCategoryId(category);
            productDAO.create(product);
        };
        transactionHelper.transaction(method);
    }

    public void updateProduct(@NonNull UpdateProductDTO updateProdDTO) {
        Runnable method = () -> {
            Optional.ofNullable(categoryDAO.get(updateProdDTO.getCategoryId())).orElseThrow(CategoryNotFoundException::new);
            if (updateProdDTO.getId() == 0 || productDAO.get(updateProdDTO.getId()) == null) {
                throw new ProductNotFoundException();
            }
            Product product = Converter.convertUpdateProdDTOToEntity(updateProdDTO);
            productDAO.update(product);
        };
        transactionHelper.transaction(method);
    }

    public void deleteProduct(@NonNull Long id) {
        Runnable runnable = () -> {
            Optional.of(orderItemDAO.existByProductId(id)).filter(p -> p.equals(Boolean.FALSE))
                    .orElseThrow(ProductUsedInOrdersException::new);
            try {
                productDAO.delete(id);
            } catch (NotFoundException e) {
                throw new ProductNotFoundException();
            }
        };
        transactionHelper.transaction(runnable);

    }

    public ProductDTO getProductById(@NonNull Long id) {
        Product product = productDAO.get(id);
        Optional.ofNullable(product).orElseThrow(ProductNotFoundException::new);
        return Converter.convertProdEntityToDTO(product);
    }

    public ProductsDTO getAllExistProducts(@NonNull RequestDataDetailsDTO requestDataDetailsDTO) {
        Supplier<ProductsDTO> supplier = () -> {
            Long count = productDAO.getCountOf();
            List<Product> products = productDAO.getPage(requestDataDetailsDTO.getCountPerPage(),
                    requestDataDetailsDTO.getPageNum());
            return Converter.convertProdListToDTO(products, count);
        };
        return transactionHelper.transaction(supplier);
    }

    public ProductsDTO getAllExistProductByCategoryName(@NonNull GetProductPageByCategoryDTO dto) {
        Supplier<ProductsDTO> supplier = () -> {
            CriteriaBuilder cb = transactionHelper.criteriaBuilder();
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            Root<Product> root = cq.from(Product.class);
            Join<Product, Category> categoryJoin = root.join(Product_.CATEGORY_ID);
            cq.select(root)
                    .where(cb.equal(categoryJoin.get(Category_.ID), dto.getCategoryId()));
            List<Product> products = transactionHelper.entityManager()
                    .createQuery(cq)
                    .setFirstResult(dto.getCountPerPage() * (dto.getPageNum() - 1))
                    .setMaxResults(dto.getCountPerPage())
                    .getResultList();

            CriteriaQuery<Long> cq1 = cb.createQuery(Long.class);
            Root<Product> root1 = cq1.from(Product.class);
            Join<Product, Category> categoryJoin1 = root1.join(Product_.CATEGORY_ID);
            cq1.select(cb.count(root1))
                    .where(cb.equal(categoryJoin1.get(Category_.ID), dto.getCategoryId()));

            Long count = transactionHelper.entityManager()
                    .createQuery(cq1)
                    .getSingleResult();
            return Converter.convertProdListToDTO(products, count);
        };
        return transactionHelper.transaction(supplier);
    }

    public ProductsDTO getAllExistProductByFilterParam(@NonNull Integer pageNum,
                                                       @NonNull Integer countPerPage, @NonNull Map<String, Object> paramMap) {
        Supplier<ProductsDTO> supplier = () -> {
            CriteriaBuilder builder = transactionHelper.criteriaBuilder();
            CriteriaQuery<Product> productQuery = builder.createQuery(Product.class);
            Root<Product> root = productQuery.from(Product.class);
            Map<String, Object> catParams = new HashMap<>();
            Predicate predicate = builder.conjunction();
            Optional.ofNullable(paramMap.get(Category_.CATEGORY_NAME)).ifPresent(p -> {
                catParams.put(Category_.CATEGORY_NAME, p);
                paramMap.remove(Category_.CATEGORY_NAME);
                Join<Product, Category> categoryJoin = root.join(Product_.CATEGORY_ID);
                transactionHelper.collectParamsToPredicate(catParams, categoryJoin, predicate);
            });
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
