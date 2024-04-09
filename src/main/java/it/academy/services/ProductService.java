package it.academy.services;

import it.academy.DTO.request.CreateProductDTO;
import it.academy.DTO.response.ProductDTO;
import it.academy.DTO.response.ProductsDTO;
import lombok.NonNull;

import java.util.Map;

public interface ProductService {
    void addProduct(@NonNull CreateProductDTO createProductDTO);
    void updateProduct(@NonNull CreateProductDTO createProductDTO);
    void deleteProduct(@NonNull Long id);
    ProductDTO getProductById(@NonNull Long id);
    ProductsDTO getAllExistProducts(@NonNull Integer pageNum, @NonNull Integer countPerPage);
    ProductsDTO getAllExistProductByCategoryName(@NonNull Long categoryId,
                                                 @NonNull Integer pageNum, @NonNull Integer countPerPage);
    ProductsDTO getAllExistProductByFilterParam(@NonNull Integer pageNum,
                                                @NonNull Integer countPerPage, @NonNull Map<String, Object> paramMap);
}
